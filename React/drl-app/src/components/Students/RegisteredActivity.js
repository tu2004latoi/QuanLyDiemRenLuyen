import { useContext, useEffect, useState } from "react";
import { MyUserContext } from "../../configs/MyContexts";
import { authApis, endpoints } from "../../configs/Apis";
import { FaTrashAlt } from "react-icons/fa";
import { useTranslation } from "react-i18next";

const RegisteredActivity = () => {
  const { t } = useTranslation();
  const [activities, setActivities] = useState([]);
  const user = useContext(MyUserContext);

  const fetchMyActivities = async () => {
    try {
      const res = await authApis().get(endpoints.myActivities);
      const registrations = res.data;

      const getActivityInfo = async (activityId) => {
        try {
          const res = await authApis().get(endpoints.activityDetail(activityId));
          return {
            name: res.data.name,
            point: res.data.pointValue,
          };
        } catch (err) {
          console.error(`Không thể lấy thông tin hoạt động ${activityId}:`, err);
          return { name: `${t("registeredActivity.activity")} #${activityId}`, point: 0 };
        }
      };

      const data = await Promise.all(
        registrations.map(async (item) => {
          const date = new Date(item.registrationDate);
          const registeredDate = date.toLocaleDateString("vi-VN");
          const registeredTime = date.toLocaleTimeString("vi-VN", {
            hour: "2-digit",
            minute: "2-digit",
          });

          const { name, point } = await getActivityInfo(item.activityId);

          return {
            id: item.id,
            activityId: item.activityId,
            name,
            point,
            registeredAt: `${registeredDate} ${registeredTime}`,
            evidence: item.filePath || null,
            evidenceId: item.evidenceId || null,
            verifyStatus: item.verifyStatus,
            file: null,
            isSubmitted:
              item.verifyStatus === "APPROVED" || item.verifyStatus === "PENDING",
            hasSentEvidence: item.filePath ? true : false,
          };
        })
      );

      setActivities(data);
    } catch (err) {
      console.error(t("registeredActivity.fetchError"), err);
    }
  };

  useEffect(() => {
    fetchMyActivities();
  }, []);

  const handleUpload = (event, id) => {
    const file = event.target.files[0];
    if (file) {
      const url = URL.createObjectURL(file);
      setActivities((prev) =>
        prev.map((act) =>
          act.id === id ? { ...act, evidence: url, file } : act
        )
      );
    }
  };

  const handleSubmitEvidence = async (id) => {
    const activity = activities.find((act) => act.id === id);
    if (!activity || !activity.file) return;

    const formData = new FormData();
    formData.append("arId", activity.id);
    formData.append("userId", user.id);
    formData.append("activityId", activity.activityId);
    formData.append("point", activity.point);
    formData.append("file", activity.file);

    try {
      await authApis().post(endpoints.createTraining, formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      });

      await fetchMyActivities();
    } catch (err) {
      console.error(t("registeredActivity.submitError"), err);
    }
  };

  const handleCancel = async (id) => {
    try {
      await authApis().delete(`${endpoints.myActivities}/${id}`);
      setActivities((prev) => prev.filter((act) => act.id !== id));
    } catch (err) {
      console.error(t("registeredActivity.cancelError"), err);
    }
  };

  const handleDeleteEvidence = async (evidenceId) => {
    try {
      await authApis().delete(endpoints.deleteEvidence(evidenceId));
      setActivities((prev) =>
        prev.map((act) =>
          act.evidenceId === evidenceId
            ? {
                ...act,
                evidence: null,
                file: null,
                isSubmitted: false,
                hasSentEvidence: false,
                evidenceId: null,
              }
            : act
        )
      );
    } catch (err) {
      console.error(t("registeredActivity.deleteEvidenceError"), err);
    }
  };

  const handleReportMissing = async (id) => {
    const activity = activities.find((act) => act.id === id);
    if (!activity || !activity.file) return;

    const formData = new FormData();
    formData.append("arId", activity.id);
    formData.append("userId", user.id);
    formData.append("activityId", activity.activityId);
    formData.append("point", activity.point);
    formData.append("file", activity.file);

    try {
      await authApis().post(endpoints.reportMissing, formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      });
      await fetchMyActivities();
    } catch (err) {
      console.error(t("registeredActivity.reportMissingError"), err);
    }
  };

  return (
    <div className="p-6 max-w-7xl mx-auto space-y-6">
      <div className="max-w-6xl mx-auto px-4 py-6 text-center border-b border-gray-300">
        <h1 className="text-4xl font-extrabold text-blue-700 drop-shadow-lg">
          {t("registeredActivity.title")}
        </h1>
        <p className="text-xl font-semibold text-gray-700 mt-1 drop-shadow-md">
          {t("registeredActivity.subtitle")}
        </p>
      </div>

      <div className="bg-white shadow-lg rounded-lg overflow-auto">
        <table className="min-w-full border-collapse border border-gray-300">
          <thead className="bg-gray-200">
            <tr>
              <th className="py-3 px-4 border text-blue-600 font-bold">{t("registeredActivity.table.id")}</th>
              <th className="py-3 px-4 border text-blue-600 font-bold">{t("registeredActivity.table.name")}</th>
              <th className="py-3 px-4 border text-blue-600 font-bold">{t("registeredActivity.table.registeredAt")}</th>
              <th className="py-3 px-4 border text-blue-600 font-bold">{t("registeredActivity.table.evidence")}</th>
              <th className="py-3 px-4 border text-blue-600 font-bold">{t("registeredActivity.table.actions")}</th>
              <th className="py-3 px-4 border text-blue-600 font-bold">{t("registeredActivity.table.status")}</th>
            </tr>
          </thead>
          <tbody>
            {activities.map(
              ({
                id,
                name,
                registeredAt,
                evidence,
                verifyStatus,
                hasSentEvidence,
                evidenceId,
                file,
              }) => (
                <tr key={id} className="hover:bg-gray-50">
                  <td className="py-3 px-4 border">{id}</td>
                  <td className="py-3 px-4 border">{name}</td>
                  <td className="py-3 px-4 border">{registeredAt}</td>
                  <td className="py-3 px-4 border text-center">
                    {evidence && (
                      <div className="flex flex-col items-center space-y-2">
                        <a href={evidence} target="_blank" rel="noopener noreferrer">
                          <img
                            src={evidence}
                            alt={t("registeredActivity.evidenceAlt")}
                            className="w-16 h-16 object-cover rounded hover:opacity-80 transition"
                          />
                        </a>
                        {verifyStatus !== "APPROVED" && evidenceId && !file && (
                          <button
                            className="flex items-center gap-1 bg-red-500 text-white text-sm px-3 py-1 rounded hover:bg-red-700 transition"
                            onClick={() => handleDeleteEvidence(evidenceId)}
                          >
                            <FaTrashAlt className="text-base" />
                            {t("registeredActivity.delete")}
                          </button>
                        )}
                      </div>
                    )}
                    {!evidence && <span className="text-gray-400 italic">{t("registeredActivity.noEvidence")}</span>}
                  </td>
                  <td className="py-3 px-4 border space-y-2">
                    {verifyStatus === "APPROVED" ? (
                      <span className="text-green-600 text-sm font-medium">{t("registeredActivity.evidenceSent")}</span>
                    ) : (
                      <>
                        <div className="flex flex-col space-y-1">
                          {hasSentEvidence && (
                            <span className="text-green-600 text-sm font-medium">{t("registeredActivity.evidenceSent")}</span>
                          )}
                          <input
                            type="file"
                            accept="image/*"
                            onChange={(e) => handleUpload(e, id)}
                            className="block text-sm"
                          />
                        </div>
                        <div className="flex space-x-2">
                          {hasSentEvidence ? (
                            <button
                              className="bg-yellow-600 text-white text-sm px-3 py-1 rounded hover:bg-yellow-700"
                              onClick={() => handleReportMissing(id)}
                              disabled={!evidence}
                            >
                              {t("registeredActivity.reportMissing")}
                            </button>
                          ) : (
                            <button
                              className="bg-green-600 text-white text-sm px-3 py-1 rounded hover:bg-green-700"
                              onClick={() => handleSubmitEvidence(id)}
                              disabled={!evidence}
                            >
                              {t("registeredActivity.submitEvidence")}
                            </button>
                          )}
                          <button
                            className="bg-red-500 text-white text-sm px-3 py-1 rounded hover:bg-red-600"
                            onClick={() => handleCancel(id)}
                          >
                            {t("registeredActivity.cancelRegistration")}
                          </button>
                        </div>
                      </>
                    )}
                  </td>
                  <td className="py-3 px-4 border text-center">
                    {verifyStatus === "APPROVED" ? (
                      <span className="text-green-600 font-semibold">{t("registeredActivity.status.approved")}</span>
                    ) : verifyStatus === "PENDING" ? (
                      <span className="text-yellow-600 font-semibold">{t("registeredActivity.status.pending")}</span>
                    ) : verifyStatus === "REJECTED" ? (
                      <span className="text-red-600 font-semibold">{t("registeredActivity.status.rejected")}</span>
                    ) : (
                      <span className="text-red-600 italic">{t("registeredActivity.status.notSubmitted")}</span>
                    )}
                  </td>
                </tr>
              )
            )}
            {activities.length === 0 && (
              <tr>
                <td colSpan={6} className="text-center py-6 italic text-gray-500">
                  {t("registeredActivity.noData")}
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
