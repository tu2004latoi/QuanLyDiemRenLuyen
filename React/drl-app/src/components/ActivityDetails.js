import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import Apis, { endpoints } from "../configs/Apis";

const ActivityDetail = () => {
    const { activityId } = useParams(); // Lấy activityId từ URL
    const [activity, setActivity] = useState(null);
    const [loading, setLoading] = useState(true);

    const loadActivityDetail = async () => {
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

    useEffect(() => {
        loadActivityDetail();
    }, [activityId]);

    if (loading) {
        return <div>Loading...</div>;
    }

    if (!activity) {
        return <div>Không tìm thấy hoạt động.</div>;
    }

    return (
        <div className="activity-details">
            <h1>{activity.name}</h1>
            <p>ID: {activity.id}</p>
            <p>Điểm: {activity.pointValue}</p>
            <p>
                Bắt đầu: {new Date(activity.startDate).toLocaleString('vi-VN', { hour: '2-digit', minute: '2-digit', day: '2-digit', month: '2-digit', year: 'numeric' })}
            </p>
            <p>
                Kết thúc: {new Date(activity.endDate).toLocaleString('vi-VN', { hour: '2-digit', minute: '2-digit', day: '2-digit', month: '2-digit', year: 'numeric' })}
            </p>
            {/* Thêm thông tin chi tiết khác của hoạt động tại đây */}
        </div>
    );
};

export default ActivityDetail;