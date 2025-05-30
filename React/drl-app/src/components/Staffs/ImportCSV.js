import { useState, useEffect, useRef } from "react";
import { authApis, endpoints } from "../../configs/Apis";
import {
    FaClipboardList,
    FaFileImport,
    FaCheckCircle,
    FaTimesCircle,
    FaFileAlt,
    FaTrashAlt,
} from "react-icons/fa";

export default function ImportCSV() {
    const [records, setRecords] = useState([]);
    const [file, setFile] = useState(null);
    const [message, setMessage] = useState("");
    const [error, setError] = useState("");
    const fileInputRef = useRef(null);


    useEffect(() => {
        const fetchAttendances = async () => {
            try {
                const res = await authApis().get(endpoints.attendances);
                const parsedRecords = res.data.map((at) => ({
                    id: at.id,
                    userName: at.userId ? at.userId.name : "N/A",
                    activityName: at.activityId ? at.activityId.name : "N/A",
                    status: at.status,
                    timestamp: at.timestamp,
                    isRegister: at.is_register,
                }));
                setRecords(parsedRecords);
            } catch (err) {
                console.error("Lỗi khi tải dữ liệu:", err);
                setError("❌ Không thể tải dữ liệu từ máy chủ.");
            }
        };
        fetchAttendances();
    }, []);

    const handleDelete = async (id) => {
        if (!window.confirm("Bạn có chắc chắn muốn xóa bản ghi này không?")) return;

        try {
            await authApis().delete(`${endpoints.attendances}/${id}`);
            setRecords(records.filter(r => r.id !== id));
            setMessage(
                <span className="flex items-center text-green-600">
                    <FaCheckCircle className="mr-2" />
                    Xóa bản ghi thành công!
                </span>
            );
            setError("");
        } catch (err) {
            setError(
                <span className="flex items-center text-red-600">
                    <FaTimesCircle className="mr-2" />
                    Xóa bản ghi thất bại!
                </span>
            );
        }
    };

    function formatTimestampArray(arr) {
        if (!Array.isArray(arr) || arr.length < 5) return "Không xác định";
        const [year, month, day, hour, minute] = arr;
        const date = new Date(year, month - 1, day, hour, minute);
        if (isNaN(date.getTime())) return "Không xác định";
        return date.toLocaleString("vi-VN");
    }

    return (
        <div className="container mx-auto my-10 px-4">
            <h2 className="text-3xl font-semibold text-blue-700 mb-8 flex items-center">
                <FaClipboardList className="mr-3 text-blue-600" />
                Nhập danh sách điểm danh từ CSV
            </h2>

            {message && (
                <div className="mb-4 rounded bg-green-100 border border-green-400 text-green-700 px-4 py-3" role="alert">
                    {message}
                </div>
            )}
            {error && (
                <div className="mb-4 rounded bg-red-100 border border-red-400 text-red-700 px-4 py-3" role="alert">
                    {error}
                </div>
            )}

            <div className="bg-white shadow-md rounded p-6 mb-8 flex flex-col md:flex-row md:items-center md:gap-4">
                <input
                    ref={fileInputRef}
                    type="file"
                    accept=".csv"
                    className="block w-full md:w-auto border border-gray-300 rounded px-3 py-2 mb-4 md:mb-0
                               focus:outline-none focus:ring-2 focus:ring-blue-500"
                    onChange={(e) => {
                        setFile(e.target.files[0]);
                        setMessage("");
                        setError("");
                    }}
                />
                <button
                    className="bg-blue-600 hover:bg-blue-700 text-white font-semibold rounded px-5 py-2 flex items-center justify-center
                               transition-colors duration-200"
                    onClick={async () => {
                        if (!file) return;
                        const formData = new FormData();
                        formData.append("file", file);
                        try {
                            const response = await authApis().post(endpoints.importCSVFile, formData, {
                                headers: { "Content-Type": "multipart/form-data" },
                            });
                            setMessage(
                                <span className="flex items-center">
                                    <FaCheckCircle className="mr-2" />
                                    {response.data.message} (Thành công: {response.data.successCount}, Thất bại: {response.data.failCount})
                                </span>
                            );
                            setError("");
                            // Reload data
                            const reload = await authApis().get(endpoints.attendances);
                            const parsedRecords = reload.data.map((at) => ({
                                id: at.id,
                                userName: at.userId ? at.userId.name : "N/A",
                                activityName: at.activityId ? at.activityId.name : "N/A",
                                status: at.status,
                                timestamp: at.timestamp,
                                isRegister: at.is_register,
                            }));
                            setRecords(parsedRecords);

                            // Reset file input
                            setFile(null);
                            if (fileInputRef.current) fileInputRef.current.value = null;

                        } catch (err) {
                            setError(
                                <span className="flex items-center">
                                    <FaTimesCircle className="mr-2" />
                                    {err.response?.data?.error || "Lỗi khi nhập dữ liệu từ CSV"}
                                </span>
                            );
                            setMessage("");

                            // Reset file input even khi lỗi (nếu muốn)
                            setFile(null);
                            if (fileInputRef.current) fileInputRef.current.value = null;
                        }
                    }}
                >
                    <FaFileImport className="mr-2" />
                    Nhập dữ liệu
                </button>
            </div>

            {records.length > 0 && (
                <div>
                    <h5 className="mb-4 text-xl font-semibold text-gray-700 flex items-center">
                        <FaFileAlt className="mr-2 text-gray-600" />
                        Dữ liệu điểm danh
                    </h5>
                    <div className="overflow-x-auto shadow rounded-lg">
                        <table className="min-w-full bg-white border border-gray-200">
                            <thead className="bg-gray-800 text-white">
                                <tr>
                                    <th className="text-left py-3 px-4 uppercase font-semibold text-sm">User</th>
                                    <th className="text-left py-3 px-4 uppercase font-semibold text-sm">Activity</th>
                                    <th className="text-left py-3 px-4 uppercase font-semibold text-sm">Trạng thái</th>
                                    <th className="text-left py-3 px-4 uppercase font-semibold text-sm">Thời gian</th>
                                    <th className="text-left py-3 px-4 uppercase font-semibold text-sm">Đã đăng ký</th>
                                    <th className="text-center py-3 px-4 uppercase font-semibold text-sm">Hành động</th>
                                </tr>
                            </thead>
                            <tbody>
                                {records.map((r) => (
                                    <tr key={r.id} className="border-b border-gray-200 hover:bg-gray-100">
                                        <td className="py-3 px-4">{r.userName}</td>
                                        <td className="py-3 px-4">{r.activityName}</td>
                                        <td className="py-3 px-4">{r.status}</td>
                                        <td className="py-3 px-4">{formatTimestampArray(r.timestamp)}</td>
                                        <td className="py-3 px-4">{r.isRegister ? "Đã đăng ký" : "Chưa đăng ký"}</td>
                                        <td className="py-3 px-4 text-center">
                                            <button
                                                className="bg-red-600 hover:bg-red-700 text-white rounded px-3 py-1 transition"
                                                onClick={() => handleDelete(r.id)}
                                                title="Xóa"
                                            >
                                                <FaTrashAlt />
                                            </button>
                                        </td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    </div>
                </div>
            )}

            <button
                className="mt-8 bg-green-600 hover:bg-green-700 text-white font-semibold rounded px-6 py-3 flex items-center justify-center
                           transition-colors duration-200"
                onClick={async () => {
                    try {
                        await authApis().delete(endpoints.confirmCSVFile);
                        setMessage(
                            <span className="flex items-center">
                                <FaCheckCircle className="mr-2" />
                                Xác nhận điểm danh và cộng điểm thành công!
                            </span>
                        );
                        setError("");
                        setRecords([]);
                    } catch (err) {
                        setError(
                            <span className="flex items-center text-red-600">
                                <FaTimesCircle className="mr-2" />
                                Xác nhận thất bại!
                            </span>
                        );
                    }
                }}
            >
                <FaCheckCircle className="mr-2" />
                Xác nhận điểm danh & cộng điểm
            </button>
        </div>
    );
}
