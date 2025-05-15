import React, { useState } from "react";

const RegisteredActivity = () => {
    const [activities, setActivities] = useState([
        { id: 1, name: "Hoạt động thể thao", registeredAt: "2025-05-10 08:00", evidence: null, isSubmitted: false },
        { id: 2, name: "Hội thảo công nghệ", registeredAt: "2025-05-12 14:30", evidence: null, isSubmitted: false },
        { id: 3, name: "Khóa học kỹ năng", registeredAt: "2025-05-13 09:15", evidence: null, isSubmitted: false },
    ]);

    const handleUpload = (event, id) => {
        const file = event.target.files[0];
        if (file) {
            const url = URL.createObjectURL(file);
            setActivities(prev =>
                prev.map(act => act.id === id ? { ...act, evidence: url } : act)
            );
        }
    };

    const handleSubmitEvidence = (id) => {
        setActivities(prev =>
            prev.map(act => act.id === id ? { ...act, isSubmitted: true } : act)
        );
    };

    const handleCancel = (id) => {
        setActivities(prev => prev.filter(act => act.id !== id));
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
                                    <div className="flex items-center justify-center space-x-2">
                                        <button
                                            className="bg-green-600 text-white text-sm px-3 py-1 rounded hover:bg-green-700"
                                            onClick={() => handleSubmitEvidence(id)}
                                            disabled={!evidence || isSubmitted}
                                        >
                                            Gửi minh chứng
                                        </button>
                                        <span className={`text-sm font-semibold ${isSubmitted ? "text-green-700" : "text-red-600"}`}>
                                            {isSubmitted ? "Đã gửi" : "Chưa gửi"}
                                        </span>
                                    </div>
                                </td>
                                <td className="py-3 px-4 border text-center">
                                    <button
                                        className="bg-red-500 text-white text-sm px-3 py-1 rounded hover:bg-red-600"
                                        onClick={() => handleCancel(id)}
                                    >
                                        Hủy đăng ký
                                    </button>
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
