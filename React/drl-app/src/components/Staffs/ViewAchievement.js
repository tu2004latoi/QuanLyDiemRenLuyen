import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { useTranslation } from "react-i18next";
import Apis, { endpoints } from "../../configs/Apis";

const ViewAchievement = () => {
  const { t } = useTranslation();
  const [students, setStudents] = useState([]);

  useEffect(() => {
    Apis.get(endpoints["students"])
      .then((res) => setStudents(res.data))
      .catch((err) => console.error("Error fetching students:", err));
  }, []);

  return (
    <div className="container py-5">
      <div className="text-center mb-4">
        <h2 className="fw-bold text-primary">{t("achievementTable.title")}</h2>
        <p className="text-muted">{t("achievementTable.description")}</p>
      </div>

      <div className="table-responsive shadow-sm rounded-4 overflow-hidden">
        <table className="table table-hover align-middle text-center mb-0">
          <thead className="table-light">
            <tr className="align-middle">
              <th>{t("achievementTable.studentId")}</th>
              <th>{t("achievementTable.name")}</th>
              <th>{t("achievementTable.email")}</th>
              <th>{t("achievementTable.totalPoint")}</th>
              <th>{t("achievementTable.classify")}</th>
              <th>{t("achievementTable.details")}</th>
            </tr>
          </thead>
          <tbody>
            {students.map((student) => (
              <tr key={student.id}>
                <td>{student.studentId}</td>
                <td className="text-start">{student.studentName}</td>
                <td>{student.email}</td>
                <td className="fw-bold">{student.totalPoint}</td>
                <td>
                  <span className="badge bg-info text-dark">
                    {student.classify}
                  </span>
                </td>
                <td>
                  <Link
                    to={`/users/students/${student.id}`}
                    className="btn btn-outline-primary btn-sm rounded-pill px-3"
                  >
                    {t("achievementTable.details")}
                  </Link>
                </td>
              </tr>
            ))}
            {students.length === 0 && (
              <tr>
                <td colSpan="6" className="text-muted text-center py-4">
                  {t("achievementTable.noData")}
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default ViewAchievement;
