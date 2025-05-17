import { useEffect, useState, useContext } from "react";
import { useParams } from "react-router-dom";
import Apis, { authApis, endpoints } from "../configs/Apis";
import {
  FaArrowLeft,
  FaClipboardList,
  FaEdit,
  FaThumbsUp,
  FaTrash,
} from "react-icons/fa";
import { MyUserContext } from "../configs/MyContexts";
import cookie from "react-cookies";

const ActivityDetail = () => {
  const { activityId } = useParams();
  const [activity, setActivity] = useState(null);
  const [loading, setLoading] = useState(true);
  const [likeCount, setLikeCount] = useState(0);
  const [comments, setComments] = useState([]);
  const [commentContent, setCommentContent] = useState("");
  const [commentLoading, setCommentLoading] = useState(true);
  const user = useContext(MyUserContext);

  const loadActivityDetail = async () => {
    try {
      setLoading(true);
      let res = await Apis.get(endpoints["activityDetail"](activityId));
      setActivity(res.data);
    } catch (ex) {
      console.error(ex);
    } finally {
      setLoading(false);
    }
  };

  const loadLikeCount = async () => {
    try {
      let res = await Apis.get(endpoints["likes"](activityId) + "/count");
      setLikeCount(res.data);
    } catch (ex) {
      console.error("Lỗi khi lấy lượt like:", ex);
    }
  };

  const loadComments = async () => {
    try {
      setCommentLoading(true);
      let res = await Apis.get(endpoints["comments"](activityId));
      setComments(res.data);
    } catch (ex) {
      console.error("Lỗi khi tải bình luận:", ex);
    } finally {
      setCommentLoading(false);
    }
  };

  const handleRegister = async () => {
    if (!user) {
      alert("Vui lòng đăng nhập để đăng ký hoạt động!");
      return;
    }

    const confirm = window.confirm(
      `Bạn có muốn đăng ký hoạt động "${activity.name}" không?`
    );
    if (!confirm) return;

    try {
      let res = await Apis.post(endpoints.activityRegister, {
        userId: user.id,
        activityId: parseInt(activityId),
      });

      alert(`Đăng ký hoạt động "${activity.name}" thành công!`);
      loadActivityDetail();
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

  const handleLike = async () => {
    const token = cookie.load("token");
    console.log("Token khi gọi like:", token);
    if (!user) {
      alert("Vui lòng đăng nhập để thích hoạt động!");
      return;
    }

    if (!token) {
      alert("Token không tồn tại, vui lòng đăng nhập lại!");
      return;
    }

    try {
      const api = authApis();
      const res = await api.post(endpoints["likes"](activityId));
      console.log("Response data:", res.data);
      setLikeCount(res.data.likes);
    } catch (err) {
      console.error("Lỗi khi xử lý like:", err);
      const msg =
        err.response?.data?.message || err.response?.data || err.message;
      alert("Không thể thực hiện thao tác thích. Chi tiết: " + msg);
    }
  };

  const handleComment = async (e) => {
    e.preventDefault();
    const token = cookie.load("token");
    console.log("Token khi gọi comment:", token);
    if (!user) {
      alert("Vui lòng đăng nhập để bình luận hoạt động!");
      return;
    }

    if (!token) {
      alert("Token không tồn tại, vui lòng đăng nhập lại!");
      return;
    }

    try {
      const api = authApis();
      const res = await api.post(endpoints["comments"](activityId),{
        userId: user.id,
        activityId: parseInt(activityId),
        content: commentContent,
      });
      setCommentContent("");
      loadComments();
      console.log("Response data:", res.data);
    } catch (err) {
      console.error("Lỗi khi xử lý comment:", err);
      const msg =
        err.response?.data?.message || err.response?.data || err.message;
      alert("Không thể thực hiện thao tác bình luận. Chi tiết: " + msg);
    }
  };

  useEffect(() => {
    loadActivityDetail();
    loadLikeCount();
    loadComments();
  }, [activityId]);

  if (loading) return <div>Loading...</div>;
  if (!activity) return <div>Không tìm thấy hoạt động.</div>;

  return (
    <div className="p-6 max-w-7xl mx-auto space-y-6 mb-20">
      <div className="max-w-6xl mx-auto px-4 py-6 text-center border-b border-gray-300">
        <h1 className="text-4xl font-extrabold text-blue-700 drop-shadow-lg">
          OPEN UNIVERSITY TRAINING POINT
        </h1>
        <p className="text-xl font-semibold text-gray-700 mt-1 drop-shadow-md">
          THÔNG TIN CHI TIẾT HOẠT ĐỘNG
        </p>
      </div>

      {/* Thông tin hoạt động */}
      <div className="flex flex-col md:flex-row bg-white rounded-2xl shadow p-6 gap-6">
        <div className="md:w-2/5 w-full">
          <img
            src={activity.image}
            alt={activity.name}
            className="w-full h-full rounded-xl object-cover"
          />
        </div>

        <div className="md:w-3/5 space-y-1">
          <h1 className="text-2xl font-bold mb-2">{activity.name}</h1>
          <p>
            <span className="font-semibold">Mô tả:</span> {activity.description}
          </p>
          <p>
            <span className="font-semibold">Địa điểm:</span> {activity.location}
          </p>
          <p>
            <span className="font-semibold">Loại điểm:</span>{" "}
            {activity.pointTypeLabel}
          </p>
          <p>
            <span className="font-semibold">Bắt đầu:</span>{" "}
            {new Date(activity.startDate).toLocaleString("vi-VN")}
          </p>
          <p>
            <span className="font-semibold">Kết thúc:</span>{" "}
            {new Date(activity.endDate).toLocaleString("vi-VN")}
          </p>
          <p>
            <span className="font-semibold">Trạng thái:</span>{" "}
            {activity.statusLabel}
          </p>
          <p>
            <span className="font-semibold">Điểm:</span> {activity.pointValue}
          </p>
          <p>
            <span className="font-semibold">Khoa tổ chức:</span>{" "}
            {activity.faculty}
          </p>
          <p>
            <span className="font-semibold">Người tổ chức:</span>{" "}
            {activity.organizer}
          </p>
          <p>
            <span className="font-semibold">Lượt thích:</span> {likeCount}
          </p>
        </div>
      </div>

      {/* Nút hành động */}
      <div className="flex justify-between flex-wrap gap-2">
        <div className="flex gap-2">
          <button
            onClick={() => window.history.back()}
            className="flex items-center gap-2 bg-gray-500 hover:bg-gray-600 text-white px-4 py-2 rounded-xl"
          >
            <FaArrowLeft /> Quay lại
          </button>

          <button
            onClick={handleLike}
            className="flex items-center gap-2 bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded-xl"
          >
            <FaThumbsUp /> Thích ({likeCount})
          </button>
        </div>

        <div className="flex gap-2">
          <button
            onClick={handleRegister}
            className="flex items-center gap-2 bg-emerald-600 hover:bg-emerald-700 text-white px-4 py-2 rounded-xl"
          >
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
              <div className="flex-shrink-0">
                <img
                  src={cmt.userAvatar || "/default-avatar.png"}
                  alt={cmt.userName || "Ẩn danh"}
                  className="w-10 h-10 rounded-full object-cover border-2 border-black"
                />
              </div>
              <div className="flex-1 text-sm">
                <div className="bg-gray-100 p-2 rounded-md">
                  <p className="font-bold text-base mb-1">
                    {cmt.userName || "Ẩn danh"}
                  </p>
                  <p className="text-gray-700 text-sm leading-tight">
                    {cmt.content}
                  </p>
                </div>
              </div>
            </div>
          ))}
        </div>

        <form className="space-y-2">
          <textarea
            placeholder="Nhập bình luận của bạn..."
            className="w-full p-3 border rounded-lg resize-none"
            value={commentContent}
            onChange={(e) => setCommentContent(e.target.value)}
            rows={3}
          />
          <button
            type="submit"
            onClick={handleComment}
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
