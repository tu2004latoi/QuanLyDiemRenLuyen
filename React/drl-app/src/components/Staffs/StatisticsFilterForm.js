import React, { useState, useEffect } from "react";
import Apis, { endpoints } from "../../configs/Apis";
import { useTranslation } from "react-i18next";

const StatisticsFilterForm = ({ onFilter }) => {
  const { t } = useTranslation();
  const [faculties, setFaculties] = useState([]);
  const [classes, setClasses] = useState([]);
  const [filters, setFilters] = useState({
    faculty: "",
    className: "",
    classification: ""
  });

  useEffect(() => {
    const fetchFaculties = async () => {
      const res = await Apis.get(endpoints["faculties"]);
      setFaculties(res.data);
    };
    fetchFaculties();
  }, []);

  useEffect(() => {
    const fetchClasses = async () => {
      if (filters.faculty) {
        const res = await Apis.get(endpoints["classes-by-faculty"](filters.faculty));
        setClasses(res.data);
      } else {
        setClasses([]);
      }
    };
    fetchClasses();
  }, [filters.faculty]);

  const handleChange = (e) => {
    setFilters({ ...filters, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    onFilter(filters);
  };

  return (
    <form className="card p-4 shadow-sm mb-4" onSubmit={handleSubmit}>
      <div className="row g-3">
        <div className="col-md-4">
          <label className="form-label">{t("statisticsFilterForm.labels.faculty")}</label>
          <select
            name="faculty"
            className="form-select"
            value={filters.faculty}
            onChange={handleChange}
          >
            <option value="">{t("statisticsFilterForm.placeholders.faculty")}</option>
            {faculties.map(f => (
              <option key={f.id} value={f.id}>
                {f.name}
              </option>
            ))}
          </select>
        </div>

        <div className="col-md-4">
          <label className="form-label">{t("statisticsFilterForm.labels.className")}</label>
          <select
            name="className"
            className="form-select"
            value={filters.className}
            onChange={handleChange}
          >
            <option value="">{t("statisticsFilterForm.placeholders.className")}</option>
            {classes.map(c => (
              <option key={c.id} value={c.id}>
                {c.name}
              </option>
            ))}
          </select>
        </div>

        <div className="col-md-4">
          <label className="form-label">{t("statisticsFilterForm.labels.classification")}</label>
          <select
            name="classification"
            className="form-select"
            value={filters.classification}
            onChange={handleChange}
          >
            <option value="">{t("statisticsFilterForm.placeholders.classification")}</option>
            <option value="Xuất sắc">{t("statisticsFilterForm.classifications.excellent")}</option>
            <option value="Giỏi">{t("statisticsFilterForm.classifications.good")}</option>
            <option value="Khá">{t("statisticsFilterForm.classifications.fair")}</option>
            <option value="Trung bình">{t("statisticsFilterForm.classifications.average")}</option>
            <option value="Yếu">{t("statisticsFilterForm.classifications.weak")}</option>
          </select>
        </div>
      </div>

      <div className="text-end mt-3">
        <button className="btn btn-primary" type="submit">
          {t("statisticsFilterForm.button.filter")}
        </button>
      </div>
    </form>
  );
};

export default StatisticsFilterForm;
