import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import Apis, { endpoints } from "../configs/Apis";
import { FaArrowLeft, FaClipboardList, FaEdit, FaThumbsUp, FaTrash } from "react-icons/fa";
import { MyUserContext } from "../configs/MyContexts";
import { useContext } from "react";

const ActivityDetail = () => {
    const { activityId } = useParams(); // Lấy activityId từ URL
    const [activity, setActivity] = useState(null);
    const [loading, setLoading] = useState(true);
    const [likeCount, setLikeCount] = useState(0);
    const [comments, setComments] = useState([]);
    const [commentLoading, setCommentLoading] = useState(true);
    const user = useContext(MyUserContext);

    const loadActivityDetail = async () => { //Tải thông tin chi tiết activity
        try {
            setLoading(true);
            let res = await Apis.get(endpoints['activityDetail'](activityId));
            console.info(res.data);
            setActivity(res.data);
        } catch (ex) {
            console.error(ex);
        } finally {
            setLoading(false);
        }
    };

    const loadLikeCount = async () => { //Đếm tổng lượt like
        try {
            let res = await Apis.get(endpoints['likeCount'](activityId));
            setLikeCount(res.data);
        } catch (ex) {
            console.error("Lỗi khi lấy lượt like:", ex);
        }
    };

    const loadComments = async () => { //Tải các bình luận
        try {
            setCommentLoading(true);
            let res = await Apis.get(endpoints['comments'](activityId));
            setComments(res.data);
        } catch (ex) {
            console.error("Lỗi khi tải bình luận:", ex);
        } finally {
            setCommentLoading(false);
        }
    };

    const handleRegister = async () => { //Đăng ký activity
        if (!user) {
            alert("Vui lòng đăng nhập để đăng ký hoạt động!");
            return;
        }

        const confirm = window.confirm(`Bạn có muốn đăng ký hoạt động "${activity.name}" không?`);
        if (!confirm) return;

        try {
            let res = await Apis.post(endpoints.activityRegister, {
                userId: user.id,
                activityId: parseInt(activityId),
            });

            alert(`Đăng ký hoạt động "${activity.name}" thành công!`);
            loadActivityDetail(); // cập nhật lại dữ liệu
        } catch (err) {
            console.error(err);
            const msg = err.response?.data;

            if (typeof msg === "string" && msg.includes("đã đăng ký")) {
                alert("Bạn đã đăng ký hoạt động này rồi!");
            } else {
                alert("Đăng ký thất bại: " + (msg || "Lỗi không xác định"));
            }
        }
    };

    useEffect(() => {
        loadActivityDetail();
        loadLikeCount();
        loadComments(); // gọi luôn khi activityId thay đổi
    }, [activityId]);

    if (loading) {
        return <div>Loading...</div>;
    }

    if (!activity) {
        return <div>Không tìm thấy hoạt động.</div>;
    }

    return (
        <div className="p-6 max-w-7xl mx-auto space-y-6 mb-20">
            <div className="max-w-6xl mx-auto px-4 py-6 text-center border-b border-gray-300">
                <h1 className="text-4xl font-extrabold text-blue-700 drop-shadow-lg">OPEN UNIVERSITY TRAINING POINT</h1>
                <p className="text-xl font-semibold text-gray-700 mt-1 drop-shadow-md">THÔN TIN CHI TIẾT HOẠT ĐỘNG</p>
            </div>
            {/* Thông tin hoạt động */}
            <div className="flex flex-col md:flex-row bg-white rounded-2xl shadow p-6 gap-6">
                {/* Ảnh bên trái */}
                <div className="md:w-2/5 w-full">
                    <img
                        src={activity.image}
                        alt={activity.name}
                        className="w-full h-full rounded-xl object-cover"
                    />
                </div>

                {/* Thông tin bên phải */}
                <div className="md:w-3/5 space-y-1">
                    <h1 className="text-2xl font-bold mb-2">{activity.name}</h1>
                    <p className="mb-1"><span className="font-semibold">ID:</span> {activity.id}</p>
                    <p className="mb-1"><span className="font-semibold">Mô tả:</span> {activity.description}</p>
                    <p className="mb-1"><span className="font-semibold">Địa điểm:</span> {activity.location}</p>
                    <p className="mb-1"><span className="font-semibold">Loại điểm:</span> {activity.pointType}</p>
                    <p className="mb-1">
                        <span className="font-semibold">Bắt đầu:</span>{" "}
                        {new Date(activity.startDate).toLocaleString("vi-VN", { hour: "2-digit", minute: "2-digit", day: "2-digit", month: "2-digit", year: "numeric", })}
                    </p>
                    <p className="mb-1">
                        <span className="font-semibold">Kết thúc:</span>{" "}
                        {new Date(activity.endDate).toLocaleString("vi-VN", { hour: "2-digit", minute: "2-digit", day: "2-digit", month: "2-digit", year: "numeric", })}
                    </p>
                    <p className="mb-1"><span className="font-semibold">Trạng thái:</span> {activity.status}</p>
                    <p className="mb-1"><span className="font-semibold">Điểm:</span> {activity.pointValue}</p>
                    <p className="mb-1"><span className="font-semibold">Khoa tổ chức:</span> {activity.faculty}</p>
                    <p className="mb-1"><span className="font-semibold">Người tổ chức:</span> {activity.organizer}</p>
                    <p className="mb-1"><span className="font-semibold">Lượt thích:</span> {likeCount}</p>
                </div>
            </div>

            {/* Nút hành động */}
            <div className="flex justify-between flex-wrap gap-2">
                <div className="flex gap-2">
                    <button
                        onClick={() => window.history.back()}
                        className="flex items-center gap-2 bg-gray-500 hover:bg-gray-600 text-white px-4 py-2 rounded-xl">
                        <FaArrowLeft /> Quay lại
                    </button>

                    <button className="flex items-center gap-2 bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded-xl">
                        <FaThumbsUp /> Thích ({likeCount})
                    </button>
                </div>

                <div className="flex gap-2">
                    <button onClick={handleRegister} className="flex items-center gap-2 bg-emerald-600 hover:bg-emerald-700 text-white px-4 py-2 rounded-xl">
                        <FaClipboardList /> Đăng ký
                    </button>
                    {user?.role === "STAFF" && (
                        <>
                            <button className="flex items-center gap-2 bg-yellow-500 hover:bg-yellow-600 text-white px-4 py-2 rounded-xl">
                                <FaEdit /> Sửa
                            </button>
                            <button className="flex items-center gap-2 bg-red-600 hover:bg-red-700 text-white px-4 py-2 rounded-xl">
                                <FaTrash /> Xóa
                            </button>
                        </>
                    )}
                </div>
            </div>

            {/* Bình luận */}
            <div className="bg-white rounded-2xl shadow p-6 space-y-4">
                <h2 className="text-xl font-semibold">Bình luận</h2>

                <div className="space-y-0 px-4">
                    {comments.map((cmt) => (
                        <div key={cmt.id} className="flex items-start gap-2 pt-2">
                            {/* Avatar */}
                            <div className="flex-shrink-0">
                                <img
                                    src={cmt.user?.avatar || "/default-avatar.png"}
                                    alt={cmt.user?.name || "Ẩn danh"}
                                    className="w-10 h-10 rounded-full object-cover border-2 border-black"
                                />
                            </div>

                            {/* Nội dung bình luận với nền xám nhẹ */}
                            <div className="flex-1 text-sm">
                                <div className="bg-gray-100 p-2 rounded-md">
                                    <p className="font-bold text-base mb-1">{cmt.user?.name || "Ẩn danh"}</p>
                                    <p className="text-gray-700 text-sm leading-tight">{cmt.content}</p>
                                </div>
                            </div>
                        </div>
                    ))}
                </div>

                {/* Nhập bình luận */}
                <form className="space-y-2">
                    <textarea
                        placeholder="Nhập bình luận của bạn..."
                        className="w-full p-3 border rounded-lg resize-none"
                        rows={3}
                    />
                    <button
                        type="submit"
                        className="bg-green-600 hover:bg-green-700 text-white px-4 py-2 rounded-xl"
                    >
                        Gửi bình luận
                    </button>
                </form>
            </div>
        </div>
    );

};

export default ActivityDetail;