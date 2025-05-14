import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import Apis, { endpoints } from "../configs/Apis";

const ActivityDetail = () => {
    const { activityId } = useParams(); // Lấy activityId từ URL
    const [activity, setActivity] = useState(null);
    const [loading, setLoading] = useState(true);
    const [likeCount, setLikeCount] = useState(0);
    const [comments, setComments] = useState([]);
    const [commentLoading, setCommentLoading] = useState(true);

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

    const loadLikeCount = async () => {
        try {
            let res = await Apis.get(endpoints['likeCount'](activityId));
            setLikeCount(res.data);
        } catch (ex) {
            console.error("Lỗi khi lấy lượt like:", ex);
        }
    };

    const loadComments = async () => {
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
        <div className="activity-details">
            <h1>{activity.name}</h1>
            <p>ID: {activity.id}</p>
            <p>Mô tả: {activity.description}</p>
            <p>Địa điểm: {activity.location}</p>
            <p>Loại điểm: {activity.pointType}</p>
            <p>
                Bắt đầu: {new Date(activity.startDate).toLocaleString('vi-VN', { hour: '2-digit', minute: '2-digit', day: '2-digit', month: '2-digit', year: 'numeric' })}
            </p>
            <p>
                Kết thúc: {new Date(activity.endDate).toLocaleString('vi-VN', { hour: '2-digit', minute: '2-digit', day: '2-digit', month: '2-digit', year: 'numeric' })}
            </p>
            <p>Trạng thái: {activity.status}</p>
            <p>Điểm: {activity.pointValue}</p>
            <p>Khoa tổ chức: {activity.faculty}</p>
            <p>Người tổ chức: {activity.organizer}</p>
            <p>Lượt thích: {likeCount}</p>

            <h2>Bình luận</h2>
            {commentLoading ? (
                <p>Đang tải bình luận...</p>
            ) : comments.length === 0 ? (
                <p>Chưa có bình luận nào.</p>
            ) : (
                <ul>
                    {comments.map((cmt) => (
                        <li key={cmt.id}>
                            <strong>{cmt.user?.name || "Ẩn danh"}:</strong> {cmt.content}
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
};

export default ActivityDetail;