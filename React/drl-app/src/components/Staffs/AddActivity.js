import { useState, useEffect } from "react";
import axios, { authApis, endpoints } from "../../configs/Apis";
import { useNavigate } from "react-router-dom";

const AddActivity = () => {
    const [activity, setActivity] = useState({
        name: "",
        description: "",
        startDate: "",
        endDate: "",
        location: "",
        facultyId: "",
        maxParticipants: 0,
        pointType: "POINT_1",
        pointValue: 0,
        status: "UPCOMING",
        file: null,
        active: false,  // gán mặc định để không bị undefined
    });

    const [faculties, setFaculties] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        const loadFaculties = async () => {
            try {
                const res = await axios.get(endpoints.faculties);
                setFaculties(res.data);
            } catch (err) {
                console.error("Error loading faculties", err);
            }
        };
        loadFaculties();
    }, []);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setActivity((prev) => ({
            ...prev,
            [name]: value,
        }));
    };

    const handleFileChange = (e) => {
        setActivity((prev) => ({
            ...prev,
            file: e.target.files[0],
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        const formData = new FormData();
        for (const key in activity) {
            if (activity[key] !== undefined && activity[key] !== null)
                formData.append(key, activity[key]);
        }

        try {
            const res = await authApis().post(endpoints.activities, formData, {
                headers: {
                    "Content-Type": "multipart/form-data",
                },
            });
            alert("Hoạt động đã được thêm!");
            navigate("/"); // hoặc chuyển hướng đến danh sách hoạt động
        } catch (err) {
            console.error("Error adding activity", err);
            alert("Không thể thêm hoạt động!");
        }
    };

    return (
        <div className="max-w-3xl mx-auto p-6 bg-white rounded-xl shadow-md mt-6">
            <h2 className="text-2xl font-bold mb-4 text-center">Thêm Hoạt Động Mới</h2>
            <form onSubmit={handleSubmit} className="space-y-4">
                <div>
                    <label className="block font-medium mb-1">Tên hoạt động</label>
                    <input
                        type="text"
                        name="name"
                        value={activity.name}
                        onChange={handleChange}
                        className="w-full border rounded-lg px-3 py-2"
                        required
                    />
                </div>

                <div>
                    <label className="block font-medium mb-1">Mô tả</label>
                    <textarea
                        name="description"
                        value={activity.description}
                        onChange={handleChange}
                        className="w-full border rounded-lg px-3 py-2"
                    />
                </div>

                <div className="grid grid-cols-2 gap-4">
                    <div>
                        <label className="block font-medium mb-1">Thời gian bắt đầu</label>
                        <input
                            type="datetime-local"
                            name="startDate"
                            value={activity.startDate}
                            onChange={handleChange}
                            className="w-full border rounded-lg px-3 py-2"
                            required
                        />
                    </div>

                    <div>
                        <label className="block font-medium mb-1">Thời gian kết thúc</label>
                        <input
                            type="datetime-local"
                            name="endDate"
                            value={activity.endDate}
                            onChange={handleChange}
                            className="w-full border rounded-lg px-3 py-2"
                            required
                        />
                    </div>
                </div>

                <div>
                    <label className="block font-medium mb-1">Địa điểm</label>
                    <input
                        type="text"
                        name="location"
                        value={activity.location}
                        onChange={handleChange}
                        className="w-full border rounded-lg px-3 py-2"
                    />
                </div>

                <div>
                    <label className="block font-medium mb-1">Khoa tổ chức</label>
                    <select
                        name="facultyId"
                        value={activity.facultyId}
                        onChange={handleChange}
                        className="w-full border rounded-lg px-3 py-2"
                        required
                    >
                        <option value="">-- Chọn khoa --</option>
                        {faculties.map((f) => (
                            <option key={f.id} value={f.id}>
                                {f.name}
                            </option>
                        ))}
                    </select>
                </div>

                <div>
                    <label className="block font-medium mb-1">Số lượng tối đa</label>
                    <input
                        type="number"
                        name="maxParticipants"
                        value={activity.maxParticipants}
                        onChange={handleChange}
                        className="w-full border rounded-lg px-3 py-2"
                    />
                </div>

                <div className="grid grid-cols-2 gap-4">
                    <div>
                        <label className="block font-medium mb-1">Loại điểm</label>
                        <select
                            name="pointType"
                            value={activity.pointType}
                            onChange={handleChange}
                            className="w-full border rounded-lg px-3 py-2"
                        >
                            <option value="POINT_1">Điều 1</option>
                            <option value="POINT_2">Điều 2</option>
                            <option value="POINT_3">Điều 3</option>
                            <option value="POINT_4">Điều 4</option>
                        </select>
                    </div>

                    <div>
                        <label className="block font-medium mb-1">Số điểm</label>
                        <input
                            type="number"
                            name="pointValue"
                            value={activity.pointValue}
                            onChange={handleChange}
                            className="w-full border rounded-lg px-3 py-2"
                        />
                    </div>
                </div>

                <div>
                    <label className="block font-medium mb-1">Trạng thái</label>
                    <select
                        name="status"
                        value={activity.status}
                        onChange={handleChange}
                        className="w-full border rounded-lg px-3 py-2"
                    >
                        <option value="UPCOMING">Sắp diễn ra</option>
                        <option value="ONGOING">Đang diễn ra</option>
                        <option value="COMPLETED">Đã kết thúc</option>
                        <option value="CANCELLED">Đã hủy</option>
                    </select>
                </div>

                <div>
                    <label className="block font-medium mb-1">Hình ảnh</label>
                    <input
                        type="file"
                        name="file"
                        accept="image/*"
                        onChange={handleFileChange}
                        className="w-full"
                    />
                </div>

                <div className="flex items-center space-x-2">
                    <input
                        type="checkbox"
                        name="active"
                        checked={activity.active}
                        onChange={(e) =>
                            setActivity((prev) => ({ ...prev, active: e.target.checked }))
                        }
                        className="form-checkbox h-5 w-5 text-blue-600"
                    />
                    <label htmlFor="active" className="font-medium">
                        Kích hoạt
                    </label>
                </div>

                <div className="text-center mt-6">
                    <button
                        type="submit"
                        className="bg-blue-600 hover:bg-blue-700 text-white font-semibold py-2 px-6 rounded-lg mb-20"
                    >
                        Thêm hoạt động
                    </button>
                </div>
            </form>
        </div>
    );
};

export default AddActivity;
