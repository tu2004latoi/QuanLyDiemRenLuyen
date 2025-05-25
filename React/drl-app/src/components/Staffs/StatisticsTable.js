import React from "react";
import { useTranslation } from "react-i18next";

const StatisticsTable = ({ students }) => {
  const { t } = useTranslation();

  return (
    <div className="table-responsive mb-4">
      <table className="table table-bordered table-hover rounded-4 overflow-hidden shadow-sm">
        <thead className="table-primary text-center align-middle">
          <tr>
            <th>#</th>
            <th>{t("statisticsTable.studentCode")}</th>
            <th>{t("statisticsTable.name")}</th>
            <th>{t("statisticsTable.email")}</th>
            <th>{t("statisticsTable.faculty")}</th>
            <th>{t("statisticsTable.class")}</th>
            <th>{t("statisticsTable.point1")}</th>
            <th>{t("statisticsTable.point2")}</th>
            <th>{t("statisticsTable.point3")}</th>
            <th>{t("statisticsTable.point4")}</th>
            <th>{t("statisticsTable.totalPoint")}</th>
            <th>{t("statisticsTable.classify")}</th>
          </tr>
        </thead>
        <tbody className="text-center align-middle">
          {students.map((s, index) => (
            <tr key={s.id}>
              <td>{index + 1}</td>
              <td>{s.studentCode}</td>
              <td>{s.name}</td>
              <td>{s.email}</td>
              <td>{s.faculty}</td>
              <td>{s.class}</td>
              <td>{s.point_1}</td>
              <td>{s.point_2}</td>
              <td>{s.point_3}</td>
              <td>{s.point_4}</td>
              <td>{s.point_total}</td>
              <td>{s.classify}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default StatisticsTable;
