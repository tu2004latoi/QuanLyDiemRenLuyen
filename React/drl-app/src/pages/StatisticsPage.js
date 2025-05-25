import React, { useEffect, useState } from "react";
import { useTranslation } from "react-i18next";
import StatisticsFilterForm from "../components/Staffs/StatisticsFilterForm";
import StatisticsTable from "../components/Staffs/StatisticsTable";
import AchievementChart from "../components/Staffs/AchievementChart";
import Apis, { endpoints } from "../configs/Apis";

const StatisticsPage = () => {
  const { t } = useTranslation();
  const [data, setData] = useState({ students: [], achievementCounts: {} });
  const [filters, setFilters] = useState({
    faculty: "",
    className: "",
    classification: "",
  });

  const fetchData = async (params = {}) => {
    try {
      const res = await Apis.get(endpoints["statistics"], { params });
      setData(res.data);
    } catch (err) {
      console.error(t("statistics.fetchError"), err);
    }
  };

  useEffect(() => {
    fetchData(filters);
  }, [filters]);

  const handleFilter = (newFilters) => {
    setFilters(newFilters);
  };

  const handleExport = async (type) => {
    try {
      const url = endpoints[type];
      const res = await Apis.get(url, {
        params: filters,
        responseType: "blob",
      });

      const blob = new Blob([res.data], {
        type: type === "csv" ? "text/csv" : "application/pdf",
      });
      const downloadUrl = window.URL.createObjectURL(blob);

      const link = document.createElement("a");
      link.href = downloadUrl;
      link.download = type === "csv" ? "thong_ke.csv" : "thong_ke.pdf";
      document.body.appendChild(link);
      link.click();

      link.remove();
      window.URL.revokeObjectURL(downloadUrl);
    } catch (error) {
      console.error(t("statistics.exportError"), error);
    }
  };

  return (
    <div className="container py-5">
      <h2
        className="fw-bold text-primary mb-4 text-center"
        style={{ fontSize: "2.5rem", letterSpacing: "1.2px" }}
      >
        <i className="fas fa-chart-bar me-2"></i>
        {t("statistics.pageTitle")}
      </h2>

      <div className="card shadow-sm mb-4 p-4">
        <StatisticsFilterForm onFilter={handleFilter} />
      </div>

      <div className="mb-4 d-flex justify-content-center gap-3 flex-wrap">
        <button
          className="btn btn-success btn-lg shadow-sm"
          onClick={() => handleExport("csv")}
          style={{ minWidth: "140px", fontWeight: "600" }}
          title={t("statistics.csvTitle")}
        >
          <i className="fas fa-file-csv me-2"></i> {t("statistics.exportCsv")}
        </button>
        <button
          className="btn btn-danger btn-lg shadow-sm"
          onClick={() => handleExport("pdf")}
          style={{ minWidth: "140px", fontWeight: "600" }}
          title={t("statistics.pdfTitle")}
        >
          <i className="fas fa-file-pdf me-2"></i> {t("statistics.exportPdf")}
        </button>
      </div>

      <div className="card shadow-sm mb-5">
        <div className="card-body p-4">
          <StatisticsTable
            students={data.students}
            tableClassName="table table-hover table-bordered align-middle"
          />
        </div>
      </div>

      <div className="card shadow-sm p-4">
        <AchievementChart data={data.achievementCounts} />
      </div>
    </div>
  );
};

export default StatisticsPage;
