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
  const [usersMap, setUsersMap] = useState({});      // { userId: {firstName, lastName} }
  const [activitiesMap, setActivitiesMap] = useState({}); // { activityId: {name} }
  const [facultyOptions, setFacultyOptions] = useState([]);
  const { t } = useTranslation();
  const [filters, setFilters] = useState({
    nameUser: "",
    nameActivity: "",
    fromPoint: "",
    toPoint: "",
    faculty: "",
  });

  const handleFilterChange = (e) => {
    const { name, value } = e.target;
    setFilters((prev) => ({ ...prev, [name]: value }));
  };

  const handleSearch = async () => {
    setLoading(true);
    try {
      const res = await authApis().get(endpoints.missingReports, {
        params: filters,
      });
      setReports(res.data);
      res.data.forEach((r) => {
        fetchUser(r.userId);
        fetchActivity(r.activityId);
      });
    } catch (err) {
      console.error("Failed to search reports:", err);
      setReports([]);
    } finally {
      setLoading(false);
    }
  };

  const fetchUser = async (userId) => {
    if (!usersMap[userId]) {
      try {
        const res = await authApis().get(endpoints.studentDetails(userId));
        const studentData = res.data;
        if (studentData) {
          setUsersMap((prev) => ({
            ...prev,
            [userId]: {
              fullName: studentData.studentName, // lấy full name từ API
            },
          }));
        } else {
          console.warn("studentData not found", res.data);
        }
      } catch (err) {
        console.error("Failed to fetch student data:", err);
      }
    }
  };

  const fetchActivity = async (activityId) => {
    if (!activitiesMap[activityId]) {
      try {
        const res = await authApis().get(endpoints.activityDetail(activityId));
        setActivitiesMap((prev) => ({
          ...prev,
          [activityId]: {
            name: res.data.name,
          },
        }));
      } catch (err) {
        console.error("Failed to fetch activity", activityId, err);
      }
    }
  };

  const fetchFaculties = async () => {
    try {
      const res = await authApis().get(endpoints.faculties);
      setFacultyOptions(res.data); // Giả sử API trả về [{id, name}, ...]
    } catch (err) {
      console.error("Failed to fetch faculties:", err);
    }
  };

  const fetchReports = async () => {
    setLoading(true);
    try {
      const res = await authApis().get(endpoints.missingReports);
      if (Array.isArray(res.data)) {
        setReports(res.data);
        // Fetch all users and activities for the reports
        res.data.forEach((r) => {
          fetchUser(r.userId);
          fetchActivity(r.activityId);
        });
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
    fetchFaculties();
  }, []);

  const updateReportStatus = async (id, action) => {
    try {
      await authApis().patch(`${endpoints.missingReports}/${action}/${id}`);
      fetchReports();
    } catch (err) {
      console.error("Error updating missing report:", err);
    }
  };

  return (
    <div className="container py-5">
      <h2 className="fw-bold text-primary text-center mb-4">{t("missingReportsPage.title")}</h2>

      <div className="table-responsive">
        <div className="mb-4">
          <div className="row g-2">
            <div className="col-md-3">
              <input
                type="text"
                className="form-control"
                name="nameUser"
                placeholder={t("missingReportsPage.filters.nameUser")}
                value={filters.nameUser}
                onChange={handleFilterChange}
              />
            </div>
            <div className="col-md-3">
              <input
                type="text"
                className="form-control"
                name="nameActivity"
                placeholder={t("missingReportsPage.filters.nameActivity")}
                value={filters.nameActivity}
                onChange={handleFilterChange}
              />
            </div>
            <div className="col-md-2">
              <input
                type="number"
                className="form-control"
                name="fromPoint"
                placeholder={t("missingReportsPage.filters.fromPoint")}
                value={filters.fromPoint}
                onChange={handleFilterChange}
              />
            </div>
            <div className="col-md-2">
              <input
                type="number"
                className="form-control"
                name="toPoint"
                placeholder={t("missingReportsPage.filters.toPoint")}
                value={filters.toPoint}
                onChange={handleFilterChange}
              />
            </div>
            <div className="col-md-2">
              <select
                className="form-select"
                name="faculty"
                value={filters.faculty}
                onChange={handleFilterChange}
              >
                <option value="">{t("missingReportsPage.filters.selectFaculty")}</option>
                {facultyOptions.map((f) => (
                  <option key={f.id} value={f.name}>
                    {f.name}
                  </option>
                ))}
              </select>
            </div>
            <div className="col-md-12 mt-2 text-end">
              <button className="btn btn-primary" onClick={handleSearch}>
                <i className="fas fa-search"></i> {t("search", "Tìm kiếm")}
              </button>
            </div>
          </div>
        </div>
        <table className="table table-bordered table-hover align-middle shadow-sm">
          <thead className="table-primary text-center">
            <tr>
              <th>ID</th>
              <th>{t("missingReportsPage.headers.userId")}</th>
              <th>{t("missingReportsPage.headers.activityId")}</th>
              <th>{t("missingReportsPage.headers.trainingPointId")}</th>
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
                  <td>
                    {usersMap[r.userId]
                      ? usersMap[r.userId].fullName
                      : r.userId}
                  </td>
                  <td>
                    {activitiesMap[r.activityId]
                      ? activitiesMap[r.activityId].name
                      : r.activityId}
                  </td>
                  <td>{r.trainingPointId}</td>
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
                      ? t("missingReportsPage.status.unconfirmed")
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
                          onClick={() => updateReportStatus(r.id, "confirm")}
                        >
                          <i className="fas fa-check"></i>{" "}
                          {t("missingReportsPage.action.confirmed")}
                        </button>
                        <button
                          className="btn btn-sm btn-danger"
                          onClick={() => updateReportStatus(r.id, "reject")}
                        >
                          <i className="fas fa-times"></i>{" "}
                          {t("missingReportsPage.action.rejected")}
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
                        {t("missingReportsPage.action.rejected")}
                      </button>
                    )}
                    {r.status === STATUS.REJECTED && (
                      <button
                        className="btn btn-sm btn-primary"
                        onClick={() => updateReportStatus(r.id, "confirm")}
                      >
                        <i className="fas fa-check"></i>{" "}
                        {t("missingReportsPage.action.confirmed")}
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
