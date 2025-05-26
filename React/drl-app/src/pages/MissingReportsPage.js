import React, { useEffect, useState } from "react";
import { useTranslation } from "react-i18next";
import Apis, { authApis, endpoints } from "../configs/Apis";

const STATUS = {
  PENDING: "PENDING",
  CONFIRMED: "CONFIRMED",
  REJECTED: "REJECTED",
};

const MissingReportsPage = () => {
  const [reports, setReports] = useState([]);
  const [loading, setLoading] = useState(false);
  const { t } = useTranslation();

  const fetchReports = async () => {
    setLoading(true);
    try {
      const res = await authApis().get(endpoints["missingReports"]);
      if (Array.isArray(res.data)) {
        setReports(res.data);
      } else {
        setReports([]);
        console.warn("API did not return an array.");
      }
    } catch (err) {
      console.error("Failed to fetch missing reports:", err);
      setReports([]);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchReports();
  }, []);

  const updateReportStatus = async (id, action) => {
    try {
      await authApis().patch(`${endpoints["missingReports"]}/${action}/${id}`);
      fetchReports(); // Refresh list after action
    } catch (err) {
      console.error("Error updating missing report:", err);
    }
  };

  return (
    <div className="container py-5">
      <h2 className="fw-bold text-primary mb-4">
        {t("missingReportsPage.title")}
      </h2>

      <div className="table-responsive">
        <table className="table table-bordered table-hover align-middle shadow-sm">
          <thead className="table-primary text-center">
            <tr>
              <th>ID</th>
              <th>{t("missingReportsPage.headers.activityId")}</th>
              <th>{t("missingReportsPage.headers.trainingPointId")}</th>
              <th>{t("missingReportsPage.headers.userId")}</th>
              <th>{t("missingReportsPage.headers.point")}</th>
              <th>{t("missingReportsPage.headers.dateReport")}</th>
              <th>{t("missingReportsPage.headers.image")}</th>
              <th>{t("missingReportsPage.headers.status")}</th>
              <th>{t("missingReportsPage.headers.actions")}</th>
            </tr>
          </thead>
          <tbody>
            {loading ? (
              <tr>
                <td colSpan="9" className="text-center">
                  {t("loading", "Đang tải dữ liệu...")}
                </td>
              </tr>
            ) : reports.length === 0 ? (
              <tr>
                <td colSpan="9" className="text-center">
                  {t("missingReportsPage.noData")}
                </td>
              </tr>
            ) : (
              reports.map((r) => (
                <tr key={r.id} className="text-center">
                  <td>{r.id}</td>
                  <td>{r.activityId}</td>
                  <td>{r.trainingPointId}</td>
                  <td>{r.userId}</td>
                  <td>{r.point}</td>
                  <td>{r.dateReport}</td>
                  <td>
                    {r.image ? (
                      <a href={r.image} target="_blank" rel="noreferrer">
                        <img
                          src={r.image}
                          alt="evidence"
                          style={{
                            width: 100,
                            height: 100,
                            objectFit: "cover",
                            borderRadius: 6,
                            boxShadow: "0 0 5px rgba(0,0,0,0.2)",
                          }}
                        />
                      </a>
                    ) : (
                      t("missingReportsPage.noImage")
                    )}
                  </td>
                  <td>
                    {r.status === STATUS.PENDING
                      ? t("missingReportsPage.status.pending")
                      : r.status === STATUS.CONFIRMED
                      ? t("missingReportsPage.status.confirmed")
                      : r.status === STATUS.REJECTED
                      ? t("missingReportsPage.status.rejected")
                      : r.status}
                  </td>
                  <td>
                    {r.status === STATUS.PENDING && (
                      <>
                        <button
                          className="btn btn-sm btn-primary me-2"
                          onClick={() =>
                            updateReportStatus(r.id, "confirm")
                          }
                        >
                          <i className="fas fa-check"></i>{" "}
                          {t("trainingPointsPage.table.actions.confirm")}
                        </button>
                        <button
                          className="btn btn-sm btn-danger"
                          onClick={() =>
                            updateReportStatus(r.id, "reject")
                          }
                        >
                          <i className="fas fa-times"></i>{" "}
                          {t("trainingPointsPage.table.actions.reject")}
                        </button>
                      </>
                    )}
                    {r.status === STATUS.CONFIRMED && (
                      <button
                        className="btn btn-sm btn-danger"
                        onClick={() =>
                          updateReportStatus(r.id, "reject-after-confirm")
                        }
                      >
                        <i className="fas fa-times"></i>{" "}
                        {t("trainingPointsPage.table.actions.reject")}
                      </button>
                    )}
                    {r.status === STATUS.REJECTED && (
                      <button
                        className="btn btn-sm btn-primary"
                        onClick={() =>
                          updateReportStatus(r.id, "confirm")
                        }
                      >
                        <i className="fas fa-check"></i>{" "}
                        {t("trainingPointsPage.table.actions.confirm")}
                      </button>
                    )}
                  </td>
                </tr>
              ))
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default MissingReportsPage;
