import { useContext, useEffect, useState } from "react";
import { MyUserContext } from "../../configs/MyContexts";
import { authApis, endpoints } from "../../configs/Apis";

const RegisteredActivity = () => {
    const [activities, setActivities] = useState([]);
    const user = useContext(MyUserContext);

    useEffect(() => {
        const fetchMyActivities = async () => {
            try {
                const res = await authApis().get(endpoints.myActivities);
                const registrations = res.data;

                const getActivityInfo = async (activityId) => {
                    try {
                        const res = await authApis().get(endpoints.activityDetail(activityId));
                        return {
                            name: res.data.name,
                            point: res.data.pointValue
                        };
                    } catch (err) {
                        console.error(`Không thể lấy thông tin hoạt động ${activityId}:`, err);
                        return {
                            name: `Hoạt động #${activityId}`,
                            point: 0
                        };
                    }
                };


                const data = await Promise.all(
                    registrations.map(async (item) => {
                        const date = new Date(item.registrationDate);
                        const registeredDate = date.toLocaleDateString("vi-VN");
                        const registeredTime = date.toLocaleTimeString("vi-VN", { hour: "2-digit", minute: "2-digit" });

                        const { name, point } = await getActivityInfo(item.activityId);

                        return {
                            id: item.id,
                            activityId: item.activityId,
                            name: name,
                            point: point,
                            registeredAt: `${registeredDate} ${registeredTime}`,
                            evidence: item.filePath ? item.filePath : null,
                            isSubmitted: item.verifyStatus === "APPROVED" || item.verifyStatus === "PENDING"
                        };
                    })
                );


                setActivities(data);
            } catch (err) {
                console.error("Lỗi khi tải danh sách hoạt động:", err);
            }
        };

        fetchMyActivities();
    }, []);



    const handleUpload = (event, id) => {
        const file = event.target.files[0];
        if (file) {
            const url = URL.createObjectURL(file);
            setActivities(prev =>
                prev.map(act =>
                    act.id === id ? { ...act, evidence: url, file: file } : act
                )
            );
        }
    };

    const handleSubmitEvidence = async (id) => {
        const activity = activities.find(act => act.id === id);
        if (!activity || !activity.file) return;

        console.log({
            arId: activity.id,
            userId: user.id,
            activityId: activity.activityId,
            point: activity.point,
            file: activity.file
        });
        const formData = new FormData();
        formData.append("arId", activity.id); // hoặc `activity.arId` nếu có sẵn
        formData.append("userId", user.id); // đảm bảo bạn đã lưu userId khi đăng nhập
        formData.append("activityId", activity.activityId); // bạn cần đảm bảo lấy được activityId
        formData.append("point", activity.point); // hoặc 10, hay lấy từ đâu đó
        formData.append("file", activity.file);

        try {
            await authApis().post(endpoints.createTraining, formData, {
                headers: {
                    "Content-Type": "multipart/form-data",
                },
            });

            setActivities(prev =>
                prev.map(act => act.id === id ? { ...act, isSubmitted: true } : act)
            );
        } catch (err) {
            console.error("Lỗi khi gửi minh chứng:", err);
        }
    };

    const handleCancel = async (id) => {
        try {
            await authApis().delete(`${endpoints.myActivities}/${id}`);
            setActivities(prev => prev.filter(act => act.id !== id));
        } catch (err) {
            console.error("Lỗi khi hủy đăng ký:", err);
        }
    };

    return (
        <div className="p-6 max-w-7xl mx-auto space-y-6">
            <div className="max-w-6xl mx-auto px-4 py-6 text-center border-b border-gray-300">
                <h1 className="text-4xl font-extrabold text-blue-700 drop-shadow-lg">OPEN TRAINING POINT</h1>
                <p className="text-xl font-semibold text-gray-700 mt-1 drop-shadow-md">CÁC HOẠT ĐỘNG ĐÃ DĂNG KÝ</p>
            </div>

            <div className="bg-white shadow-lg rounded-lg overflow-auto">
                <table className="min-w-full border-collapse border border-gray-300">
                    <thead className="bg-gray-200">
                        <tr>
                            <th className="py-3 px-4 border text-blue-600 font-bold">ID</th>
                            <th className="py-3 px-4 border text-blue-600 font-bold">Tên hoạt động</th>
                            <th className="py-3 px-4 border text-blue-600 font-bold">Thời gian đăng ký</th>
                            <th className="py-3 px-4 border text-blue-600 font-bold">Minh chứng</th>
                            <th className="py-3 px-4 border text-blue-600 font-bold">Hành động</th>
                            <th className="py-3 px-4 border text-blue-600 font-bold">Trạng thái</th>
                        </tr>
                    </thead>
                    <tbody>
                        {activities.map(({ id, name, registeredAt, evidence, isSubmitted }) => (
                            <tr key={id} className="hover:bg-gray-50">
                                <td className="py-3 px-4 border">{id}</td>
                                <td className="py-3 px-4 border">{name}</td>
                                <td className="py-3 px-4 border">{registeredAt}</td>
                                <td className="py-3 px-4 border text-center">
                                    {evidence ? (
                                        <img src={evidence} alt="Minh chứng" className="w-16 h-16 object-cover rounded" />
                                    ) : (
                                        <span className="text-gray-400 italic">Chưa có</span>
                                    )}
                                </td>
                                <td className="py-3 px-4 border text-center space-y-2">
                                    <div>
                                        <input
                                            type="file"
                                            accept="image/*"
                                            onChange={(e) => handleUpload(e, id)}
                                            className="block text-sm"
                                        />
                                    </div>
                                    <div className="flex space-x-2">
                                        <button
                                            className="bg-green-600 text-white text-sm px-3 py-1 rounded hover:bg-green-700"
                                            onClick={() => handleSubmitEvidence(id)}
                                            disabled={!evidence || isSubmitted}
                                        >
                                            Gửi minh chứng
                                        </button>
                                        <button
                                            className="bg-red-500 text-white text-sm px-3 py-1 rounded hover:bg-red-600"
                                            onClick={() => handleCancel(id)}
                                        >
                                            Hủy đăng ký
                                        </button>
                                    </div>
                                </td>
                                <td className="py-3 px-4 border text-center">

                                </td>
                            </tr>
                        ))}
                        {activities.length === 0 && (
                            <tr>
                                <td colSpan="6" className="py-4 text-center text-gray-500 italic">
                                    Không có hoạt động nào được đăng ký.
                                </td>
                            </tr>
                        )}
                    </tbody>
                </table>
            </div>
        </div>
    );
};

export default RegisteredActivity;
