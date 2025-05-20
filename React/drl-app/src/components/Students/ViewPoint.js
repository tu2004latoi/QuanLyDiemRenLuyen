import { useEffect, useState } from "react";
import { authApis, endpoints } from "../../configs/Apis";
import {
    BarChart,
    Bar,
    XAxis,
    YAxis,
    CartesianGrid,
    Tooltip,
    ResponsiveContainer,
    Legend,
} from "recharts";

const ViewPoint = () => {
    const [user, setUser] = useState(null);

    useEffect(() => {
        const loadProfile = async () => {
            try {
                const res = await authApis().get(endpoints["current-user"]);
                setUser(res.data);
            } catch (err) {
                console.error("Failed to load user profile", err);
            }
        };

        loadProfile();
    }, []);

    if (!user) {
        return <p className="text-center mt-10 text-gray-600">Đang tải thông tin người dùng...</p>;
    }

    const pointData = [
        { name: "Điểm 1", value: user.point_1 },
        { name: "Điểm 2", value: user.point_2 },
        { name: "Điểm 3", value: user.point_3 },
        { name: "Điểm 4", value: user.point_4 },
    ];

    return (
        <div className="max-w-5xl mx-auto mt-10 p-8 bg-white rounded-xl shadow-lg space-y-8">
            {/* Thông tin người dùng */}
            <div className="flex items-center space-x-6 border-b pb-6">
                <img
                    src={user.avatar}
                    alt={user.name}
                    className="w-28 h-28 rounded-full object-cover border shadow"
                />
                <div>
                    <h2 className="text-3xl font-bold">
                        {user.lastName} {user.firstName}
                    </h2>
                    <p className="text-gray-600 text-lg">{user.email}</p>
                    <p className="text-sm text-gray-500">Vai trò: {user.role}</p>
                    {user.admin?.department && (
                        <p className="text-sm text-gray-500">
                            Phòng ban: {user.admin.department.name}
                        </p>
                    )}
                </div>
            </div>

            {/* Biểu đồ điểm rèn luyện */}
            <div>
                <h3 className="text-2xl font-semibold mb-4 text-center text-blue-600">Biểu đồ điểm rèn luyện</h3>
                <div className="w-full h-[400px]">
                    <ResponsiveContainer width="100%" height="100%">
                        <BarChart data={pointData} margin={{ top: 20, right: 30, left: 20, bottom: 10 }}>
                            <CartesianGrid strokeDasharray="3 3" />
                            <XAxis dataKey="name" />
                            <YAxis domain={[0, 100]} />
                            <Tooltip />
                            <Legend />
                            <Bar
                                dataKey="value"
                                fill="#3b82f6"
                                name="Điểm"
                                animationDuration={1200} // Hiệu ứng mượt
                                radius={[8, 8, 0, 0]} // Bo góc trên
                            />
                        </BarChart>
                    </ResponsiveContainer>
                </div>
            </div>
        </div>
    );
};

export default ViewPoint;
