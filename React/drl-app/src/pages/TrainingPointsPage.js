import React, { useEffect, useState } from "react";
import { useTranslation } from "react-i18next";
import axios from "axios";
import Apis, { authApis, endpoints } from "../configs/Apis";

const STATUS = {
  PENDING: "PENDING",
  CONFIRMED: "CONFIRMED",
  REJECTED: "REJECTED",
};

const TrainingPointsPage = () => {
  const { t } = useTranslation();
  const [trainingPoints, setTrainingPoints] = useState([]);
  const [filters, setFilters] = useState({
    firstName: "",
    lastName: "",
    email: "",
    activityName: "",
  });
  const [loading, setLoading] = useState(false);

  const fetchTrainingPoints = async () => {
    setLoading(true);
    try {
      const params = {};

      if (filters.firstName) params.firstName = filters.firstName;
      if (filters.lastName) params.lastName = filters.lastName;
      if (filters.email) params.email = filters.email;
      if (filters.activityName) params.activityName = filters.activityName;

      const res = await Apis.get(endpoints["trainingPoints"], { params });
      setTrainingPoints(res.data);
    } catch (error) {
      console.error("Error fetching training points:", error);
    }
    setLoading(false);
  };

  useEffect(() => {
    fetchTrainingPoints();
  }, []);

  const handleInputChange = (e) => {
    setFilters({
      ...filters,
      [e.target.name]: e.target.value,
    });
  };

  const handleSearch = (e) => {
    e.preventDefault();
    fetchTrainingPoints();
  };

  const updateTrainingPoint = async (id, action) => {
    try {
      await authApis().patch(`${endpoints["trainingPoints"]}/${action}/${id}`);
      fetchTrainingPoints();
    } catch (error) {
      console.error("Error updating training point:", error);
    }
  };

  return (
    <div className="container py-5">
      <h2 className="fw-bold text-primary mb-4">
        {t("trainingPointsPage.title")}
      </h2>

      {/* Form tìm kiếm */}
      <form className="card shadow-sm p-4 mb-4" onSubmit={handleSearch}>
        <div className="row g-3">
          <div className="col-md-3">
            <input
              type="text"
              className="form-control"
              placeholder={t("trainingPointsPage.search.placeholder.firstName")}
              name="firstName"
              value={filters.firstName}
              onChange={handleInputChange}
            />
          </div>
          <div className="col-md-3">
            <input
              type="text"
              className="form-control"
              placeholder={t("trainingPointsPage.search.placeholder.lastName")}
              name="lastName"
              value={filters.lastName}
              onChange={handleInputChange}
            />
          </div>
          <div className="col-md-3">
            <input
              type="email"
              className="form-control"
              placeholder={t("trainingPointsPage.search.placeholder.email")}
              name="email"
              value={filters.email}
              onChange={handleInputChange}
            />
          </div>
          <div className="col-md-3">
            <input
              type="text"
              className="form-control"
              placeholder={t(
                "trainingPointsPage.search.placeholder.activityName"
              )}
              name="activityName"
              value={filters.activityName}
              onChange={handleInputChange}
            />
          </div>
          <div className="col-12 text-end mt-3">
            <button type="submit" className="btn btn-primary rounded-pill px-4">
              <i className="fas fa-search"></i>{" "}
              {t("trainingPointsPage.search.button")}
            </button>
          </div>
        </div>
      </form>

      {/* Bảng dữ liệu */}
      <div className="table-responsive">
        <table className="table table-bordered table-hover align-middle shadow-sm">
          <thead className="table-primary text-center">
            <tr>
              <th>{t("trainingPointsPage.table.headers.id")}</th>
              <th>{t("trainingPointsPage.table.headers.fullName")}</th>
              <th>{t("trainingPointsPage.table.headers.email")}</th>
              <th>{t("trainingPointsPage.table.headers.activityName")}</th>
              <th>{t("trainingPointsPage.table.headers.pointType")}</th>
              <th>{t("trainingPointsPage.table.headers.point")}</th>
              <th>{t("trainingPointsPage.table.headers.time")}</th>
              <th>{t("trainingPointsPage.table.headers.evidence")}</th>
              <th>{t("trainingPointsPage.table.headers.status")}</th>
              <th>{t("trainingPointsPage.table.headers.processedBy")}</th>
              <th>{t("trainingPointsPage.table.headers.actions")}</th>
            </tr>
          </thead>
          <tbody>
            {loading ? (
              <tr>
                <td colSpan="11" className="text-center">
                  {t("trainingPointsPage.table.loading")}
                </td>
              </tr>
            ) : trainingPoints.length === 0 ? (
              <tr>
                <td colSpan="11" className="text-center">
                  {t("trainingPointsPage.table.noData")}
                </td>
              </tr>
            ) : (
              trainingPoints.map((tp) => (
                <tr key={tp.id} className="text-center">
                  <td>{tp.id}</td>
                  <td>{tp.userName}</td>
                  <td>
                    {tp.userEmail || t("trainingPointsPage.table.noEmail")}
                  </td>
                  <td>{tp.activityName}</td>
                  <td>
                    {tp.pointType ||
                      t("trainingPointsPage.table.unknownPointType")}
                  </td>
                  <td>{tp.point}</td>
                  <td>{tp.dataAwarded}</td>
                  <td>
                    {tp.evidence ? (
                      <a href={tp.evidence} target="_blank" rel="noreferrer">
                        <img
                          src={tp.evidence}
                          alt={t("trainingPointsPage.table.headers.evidence")}
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
                      t("trainingPointsPage.table.noEvidence")
                    )}
                  </td>
                  <td>
                    {tp.status === STATUS.PENDING
                      ? t("trainingPointsPage.table.unconfirmed")
                      : tp.status}
                  </td>
                  <td>
                    {tp.comfirmBy || t("trainingPointsPage.table.unconfirmed")}
                  </td>
                  <td>
                    {tp.status === STATUS.PENDING && (
                      <>
                        <button
                          className="btn btn-sm btn-primary me-2"
                          onClick={() => updateTrainingPoint(tp.id, "confirm")}
                        >
                          <i className="fas fa-check"></i>{" "}
                          {t("trainingPointsPage.table.actions.confirm")}
                        </button>
                        <button
                          className="btn btn-sm btn-danger"
                          onClick={() => updateTrainingPoint(tp.id, "reject")}
                        >
                          <i className="fas fa-times"></i>{" "}
                          {t("trainingPointsPage.table.actions.reject")}
                        </button>
                      </>
                    )}
                    {tp.status === STATUS.CONFIRMED && (
                      <button
                        className="btn btn-sm btn-danger"
                        onClick={() =>
                          updateTrainingPoint(tp.id, "reject-after-approved")
                        }
                      >
                        <i className="fas fa-times"></i>{" "}
                        {t("trainingPointsPage.table.actions.reject")}
                      </button>
                    )}
                    {tp.status === STATUS.REJECTED && (
                      <button
                        className="btn btn-sm btn-primary"
                        onClick={() => updateTrainingPoint(tp.id, "confirm")}
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

export default TrainingPointsPage;
