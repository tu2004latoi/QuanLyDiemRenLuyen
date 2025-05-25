import { useState, useEffect } from "react";
import axios, { authApis, endpoints } from "../../configs/Apis";
import { useNavigate } from "react-router-dom";
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
      alert(t("addActivity.submitSuccess", "Hoạt động đã được thêm!"));
      navigate("/");
    } catch (err) {
      console.error("Error adding activity", err);
      alert(t("addActivity.submitFail", "Không thể thêm hoạt động!"));
    }
  };

  return (
    <div className="max-w-3xl mx-auto p-6 bg-white rounded-xl shadow-md mt-6">
      <h2 className="text-2xl font-bold mb-4 text-center">
        {t("addActivity.title")}
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
            name="facultyId"
            value={activity.facultyId}
            onChange={handleChange}
            className="w-full border rounded-lg px-3 py-2"
            required
          >
            <option value="">{t("addActivity.facultyOption")}</option>
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
            {t("addActivity.submitButton")}
          </button>
        </div>
      </form>
    </div>
  );
};

export default AddActivity;
