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
import { useTranslation } from "react-i18next";

const ActivityDetail = () => {
  const { t } = useTranslation(); // dùng t() cho i18n
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
      alert(t("activityDetails.loginToRegister"));
      return;
    }

    const res = await Apis.get(endpoints.activityRegistratons);
    const registrations = res.data;

    const hasRegistered = registrations.some(
      (r) => r.activityId === parseInt(activityId) && r.userId === user.id
    );

    if (hasRegistered) {
      alert(t("activityDetails.alreadyRegistered"));
      return;
    }

    if (activity.endDate) {
      const endTime = new Date(activity.endDate);
      const now = new Date();

      if (now > endTime) {
        alert(t("activityDetails.registrationExpired"));
        return; // Dừng không gọi API
      }
    }

    const confirmRegister = window.confirm(
      t("activityDetails.confirmRegister", { activityName: activity.name })
    );
    if (!confirmRegister) return;

    try {
      await Apis.post(endpoints.activityRegister, {
        userId: user.id,
        activityId: parseInt(activityId),
      });

      alert(
        t("activityDetails.registerSuccess", { activityName: activity.name })
      );
      loadActivityDetail();
    } catch (err) {
      console.error(err);
      const msg = err.response?.data || "Unknown error";

      if (typeof msg === "string") {
        if (msg.includes("đã đăng ký")) {
          alert(t("activityDetails.alreadyRegistered"));
        } else if (msg.includes("hết hạn đăng ký")) {
          alert(t("activityDetails.registrationExpired"));
        } else if (msg.includes("đủ số lượng")) {
          alert(t("activityDetails.fullParticipants"));
        } else {
          alert(t("activityDetails.registerFail", { error: msg }));
        }
      } else {
        alert(t("activityDetails.registerFail", { error: "Unknown error" }));
      }
    }
  };

  const handleLike = async () => {
    const token = cookie.load("token");
    if (!user) {
      alert(t("activityDetails.loginToLike"));
      return;
    }

    if (!token) {
      alert(t("activityDetails.tokenMissing"));
      return;
    }

    try {
      const api = authApis();
      const res = await api.post(endpoints["likes"](activityId));
      setLikeCount(res.data.likes);
    } catch (err) {
      console.error("Lỗi khi xử lý like:", err);
      const msg =
        err.response?.data?.message || err.response?.data || err.message;
      alert(t("activityDetails.likeFail", { error: msg }));
    }
  };

  const handleComment = async (e) => {
    e.preventDefault();
    const token = cookie.load("token");
    if (!user) {
      alert(t("activityDetails.loginToComment"));
      return;
    }

    if (!token) {
      alert(t("activityDetails.tokenMissing"));
      return;
    }

    try {
      const api = authApis();
      const res = await api.post(endpoints["comments"](activityId), {
        userId: user.id,
        activityId: parseInt(activityId),
        content: commentContent,
      });
      setCommentContent("");
      loadComments();
    } catch (err) {
      console.error("Lỗi khi xử lý comment:", err);
      const msg =
        err.response?.data?.message || err.response?.data || err.message;
      alert(t("activityDetails.commentFail", { error: msg }));
    }
  };

  useEffect(() => {
    loadActivityDetail();
    loadLikeCount();
    loadComments();
  }, [activityId]);

  if (loading) return <div>{t("loading") || "Loading..."}</div>;
  if (!activity)
    return (
      <div>{t("activityDetails.notFound") || "Không tìm thấy hoạt động."}</div>
    );

  return (
    <div className="p-6 max-w-7xl mx-auto space-y-6 mb-20">
      <div className="max-w-6xl mx-auto px-4 py-6 text-center border-b border-gray-300">
        <h1 className="text-4xl font-extrabold text-blue-700 drop-shadow-lg">
          {t("activityDetails.title")}
        </h1>
        <p className="text-xl font-semibold text-gray-700 mt-1 drop-shadow-md">
          {t("activityDetails.subtitle")}
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
            <span className="font-semibold">
              {t("activityDetails.description")}:
            </span>{" "}
            {activity.description}
          </p>
          <p>
            <span className="font-semibold">
              {t("activityDetails.location")}:
            </span>{" "}
            {activity.location}
          </p>
          <p>
            <span className="font-semibold">
              {t("activityDetails.pointType")}:
            </span>{" "}
            {activity.pointTypeLabel}
          </p>
          <p>
            <span className="font-semibold">
              {t("activityDetails.startDate")}:
            </span>{" "}
            {new Date(activity.startDate).toLocaleString("vi-VN")}
          </p>
          <p>
            <span className="font-semibold">
              {t("activityDetails.endDate")}:
            </span>{" "}
            {new Date(activity.endDate).toLocaleString("vi-VN")}
          </p>
          <p>
            <span className="font-semibold">
              {t("activityDetails.status")}:
            </span>{" "}
            {activity.statusLabel}
          </p>
          <p>
            <span className="font-semibold">{t("activityDetails.point")}:</span>{" "}
            {activity.pointValue}
          </p>
          <p>
            <span className="font-semibold">
              {t("activityDetails.faculty")}:
            </span>{" "}
            {activity.faculty}
          </p>
          <p>
            <span className="font-semibold">
              {t("activityDetails.organizer")}:
            </span>{" "}
            {activity.organizer}
          </p>
          <p>
            <span className="font-semibold">
              {t("activityDetails.likeCount")}:
            </span>{" "}
            {likeCount}
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
            <FaArrowLeft /> {t("activityDetails.back")}
          </button>

          <button
            onClick={handleLike}
            className="flex items-center gap-2 bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded-xl"
          >
            <FaThumbsUp /> {t("activityDetails.like")} ({likeCount})
          </button>
        </div>

        <div className="flex gap-2">
          {user?.role === "STUDENT" && (
            <button
              onClick={handleRegister}
              className="flex items-center gap-2 bg-emerald-600 hover:bg-emerald-700 text-white px-4 py-2 rounded-xl"
            >
              <FaClipboardList /> {t("activityDetails.register")}
            </button>
          )}
          {user?.role === "STAFF" && (
            <>
              <button className="flex items-center gap-2 bg-yellow-500 hover:bg-yellow-600 text-white px-4 py-2 rounded-xl">
                <FaEdit /> {t("activityDetails.edit")}
              </button>
              <button className="flex items-center gap-2 bg-red-600 hover:bg-red-700 text-white px-4 py-2 rounded-xl">
                <FaTrash /> {t("activityDetails.delete")}
              </button>
            </>
          )}
        </div>
      </div>

      {/* Bình luận */}
      <div className="bg-white rounded-2xl shadow p-6 space-y-4">
        <h2 className="text-xl font-semibold">
          {t("activityDetails.comments")}
        </h2>

        <div className="space-y-0 px-4">
          {comments.map((cmt) => (
            <div key={cmt.id} className="flex items-start gap-2 pt-2">
              <div className="flex-shrink-0">
                <img
                  src={cmt.userAvatar || "/default-avatar.png"}
                  alt={cmt.userName || t("activityDetails.anonymous")}
                  className="w-10 h-10 rounded-full object-cover border-2 border-black"
                />
              </div>
              <div className="flex-1 text-sm">
                <div className="bg-gray-100 p-2 rounded-md">
                  <p className="font-bold text-base mb-1">
                    {cmt.userName || t("activityDetails.anonymous")}
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
            placeholder={t("activityDetails.enterComment")}
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
            {t("activityDetails.sendComment")}
          </button>
        </form>
      </div>
    </div>
  );
};

export default ActivityDetail;
