import { useState, useEffect } from "react";
import axios, { authApis, endpoints } from "../../configs/Apis";
import { useNavigate, useParams } from "react-router-dom";
import { useTranslation } from "react-i18next";

const AddActivity = () => {
  const { t } = useTranslation();
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
    active: false,
  });

  const [faculties, setFaculties] = useState([]);
  const { activityId } = useParams(); // <-- Lấy activityId từ URL
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

  useEffect(() => {
    const loadActivityDetails = async () => {
      try {
        const res = await axios.get(`${endpoints.activities}/${activityId}`);
        const a = res.data;

        const formatDate = (timestamp) => {
          if (!timestamp) return "";
          const date = new Date(timestamp);
          return date.toISOString().slice(0, 16);
        };

        const facultyObj = faculties.find(f => f.name === a.faculty);

        setActivity({
          ...a,
          startDate: formatDate(a.startDate),
          endDate: formatDate(a.endDate),
          facultyId: facultyObj?.id || "",
          file: null,
          image: a.image || null,
        });
      } catch (err) {
        console.error("Error loading activity details", err);
      }
    };

    if (activityId && faculties.length > 0) {
      loadActivityDetails();
    }
  }, [activityId, faculties]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setActivity((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleFileChange = (e) => {
    const file = e.target.files[0];
    setActivity((prev) => ({
      ...prev,
      file: file || null,
      image: file ? null : prev.image, // nếu có file mới thì xóa image cũ
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const formData = new FormData();

    // Hàm chuyển date string local sang ISO chuẩn UTC (backend hay cần)
    const formatDateToISO = (localDateStr) => {
      if (!localDateStr) return "";
      const date = new Date(localDateStr);
      return date.toISOString();
    };

    // Gửi các trường lên formData, chú ý trường facultyId thay vì faculty
    if (activity.name) formData.append("name", activity.name);
    if (activity.description) formData.append("description", activity.description);
    if (activity.startDate) formData.append("startDate", formatDateToISO(activity.startDate));
    if (activity.endDate) formData.append("endDate", formatDateToISO(activity.endDate));
    if (activity.location) formData.append("location", activity.location);
    if (activity.facultyId) formData.append("facultyId", activity.facultyId); // chỉ gửi id
    if (activity.maxParticipants) formData.append("maxParticipants", activity.maxParticipants);
    if (activity.pointType) formData.append("pointType", activity.pointType);
    if (activity.pointValue !== undefined && activity.pointValue !== null)
      formData.append("pointValue", Number(activity.pointValue)); // chuyển số
    if (activity.status) formData.append("status", activity.status);
    if (activity.active !== undefined)
      formData.append("active", activity.active ? "true" : "false"); // gửi string

    // Gửi file nếu có
    if (activity.file) {
      formData.append("file", activity.file); // gửi file nếu có
    } else if (activity.image) {
      formData.append("image", activity.image); // giữ lại ảnh cũ nếu không upload mới
    }

    // Hidden id nếu có (để backend biết update hay tạo mới)
    if (activity.id) formData.append("id", activity.id);

    try {
      if (activity.id) {
        await authApis().put(`${endpoints.activities}/${activity.id}`, formData, {
          headers: { "Content-Type": "multipart/form-data" },
        });
        alert("Cập nhật hoạt động thành công!");
      } else {
        await authApis().post(endpoints.activities, formData, {
          headers: { "Content-Type": "multipart/form-data" },
        });
        alert("Thêm hoạt động thành công!");
      }
      navigate("/");
    } catch (err) {
      console.error(err);
      alert(activity.id ? "Không thể cập nhật hoạt động!" : "Không thể thêm hoạt động!");
    }
  };


  return (
    <div className="max-w-3xl mx-auto p-6 bg-white rounded-xl shadow-md mt-6">
      <h2 className="text-2xl font-bold mb-4 text-center">
        {activityId ? t("addActivity.updateTitle") : t("addActivity.title")}
      </h2>
      <form onSubmit={handleSubmit} className="space-y-4">
        <div>
          <label className="block font-medium mb-1">
            {t("addActivity.labels.name")}
          </label>
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
          <label className="block font-medium mb-1">
            {t("addActivity.labels.description")}
          </label>
          <textarea
            name="description"
            value={activity.description}
            onChange={handleChange}
            className="w-full border rounded-lg px-3 py-2"
          />
        </div>

        <div className="grid grid-cols-2 gap-4">
          <div>
            <label className="block font-medium mb-1">
              {t("addActivity.labels.startDate")}
            </label>
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
            <label className="block font-medium mb-1">
              {t("addActivity.labels.endDate")}
            </label>
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
          <label className="block font-medium mb-1">
            {t("addActivity.labels.location")}
          </label>
          <input
            type="text"
            name="location"
            value={activity.location}
            onChange={handleChange}
            className="w-full border rounded-lg px-3 py-2"
          />
        </div>

        <div>
          <label className="block font-medium mb-1">
            {t("addActivity.labels.faculty")}
          </label>
          <select
            className="form-select"
            value={activity.facultyId || ""}
            onChange={(e) => setActivity({ ...activity, facultyId: Number(e.target.value) })}
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
          <label className="block font-medium mb-1">
            {t("addActivity.labels.maxParticipants")}
          </label>
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
            <label className="block font-medium mb-1">
              {t("addActivity.labels.pointType")}
            </label>
            <select
              name="pointType"
              value={activity.pointType}
              onChange={handleChange}
              className="w-full border rounded-lg px-3 py-2"
            >
              <option value="POINT_1">
                {t("addActivity.pointTypeOptions.POINT_1")}
              </option>
              <option value="POINT_2">
                {t("addActivity.pointTypeOptions.POINT_2")}
              </option>
              <option value="POINT_3">
                {t("addActivity.pointTypeOptions.POINT_3")}
              </option>
              <option value="POINT_4">
                {t("addActivity.pointTypeOptions.POINT_4")}
              </option>
            </select>
          </div>

          <div>
            <label className="block font-medium mb-1">
              {t("addActivity.labels.pointValue")}
            </label>
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
          <label className="block font-medium mb-1">
            {t("addActivity.labels.status")}
          </label>
          <select
            name="status"
            value={activity.status}
            onChange={handleChange}
            className="w-full border rounded-lg px-3 py-2"
          >
            <option value="UPCOMING">
              {t("addActivity.statusOptions.UPCOMING")}
            </option>
            <option value="ONGOING">
              {t("addActivity.statusOptions.ONGOING")}
            </option>
            <option value="COMPLETED">
              {t("addActivity.statusOptions.COMPLETED")}
            </option>
            <option value="CANCELLED">
              {t("addActivity.statusOptions.CANCELLED")}
            </option>
          </select>
        </div>

        <div>
          <label className="block font-medium mb-1">
            {t("addActivity.labels.image")}
          </label>
          <input
            type="file"
            name="file"
            accept="image/*"
            onChange={handleFileChange}
            className="w-full"
          />
        </div>

        {activity.image && (
          <div className="mb-2">
            <label className="block font-medium mb-1">{t("addActivity.labels.currentImage", "Ảnh hiện tại:")}</label>
            <img src={activity.image} alt="Current activity" className="w-48 h-auto rounded-lg shadow" />
          </div>
        )}

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
            {t("addActivity.labels.active")}
          </label>
        </div>

        <div className="text-center mt-6">
          <button
            type="submit"
            className="bg-blue-600 hover:bg-blue-700 text-white font-semibold py-2 px-6 rounded-lg mb-20"
          >
            {activityId
              ? t("addActivity.updateButton", "Cập nhật hoạt động")
              : t("addActivity.submitButton", "Thêm hoạt động")}
          </button>
        </div>
      </form>
    </div>
  );
};

export default AddActivity;
