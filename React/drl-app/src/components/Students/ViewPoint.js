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
import { useTranslation } from "react-i18next";

const ViewPoint = () => {
    const { t } = useTranslation();
    const [user, setUser] = useState(null);

    useEffect(() => {
        const loadProfile = async () => {
            try {
                const res = await authApis().get(endpoints["current-user"]);
                setUser(res.data);
            } catch (err) {
                console.error(t("viewPoint.loadError"), err);
            }
        };

        loadProfile();
    }, []);

    if (!user) {
        return <p className="text-center mt-10 text-gray-600">{t("viewPoint.loading")}</p>;
    }

    const pointData = [
        { name: t("viewPoint.point1"), value: user.point_1 },
        { name: t("viewPoint.point2"), value: user.point_2 },
        { name: t("viewPoint.point3"), value: user.point_3 },
        { name: t("viewPoint.point4"), value: user.point_4 },
    ];

    return (
        <div className="max-w-5xl mx-auto mt-10 p-8 bg-white rounded-xl shadow-lg space-y-8">
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
                    <p className="text-sm text-gray-500">
                        {t("viewPoint.role")}: {user.role}
                    </p>
                    {user.admin?.department && (
                        <p className="text-sm text-gray-500">
                            {t("viewPoint.department")}: {user.admin.department.name}
                        </p>
                    )}
                </div>
            </div>

            <div>
                <h3 className="text-2xl font-semibold mb-4 text-center text-blue-600">{t("viewPoint.chartTitle")}</h3>
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
                                name={t("viewPoint.point")}
                                animationDuration={1200}
                                radius={[8, 8, 0, 0]}
                            />
                        </BarChart>
                    </ResponsiveContainer>
                </div>
            </div>
        </div>
    );
};

export default ViewPoint;
