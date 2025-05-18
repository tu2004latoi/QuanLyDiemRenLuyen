import React, { useState, useEffect } from "react";
import Apis, { endpoints } from "../../configs/Apis";

const StatisticsFilterForm = ({ onFilter }) => {
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
    onFilter(filters); // Gửi bộ lọc lên component cha
  };

  return (
    <form className="card p-4 shadow-sm mb-4" onSubmit={handleSubmit}>
      <div className="row g-3">
        <div className="col-md-4">
          <label className="form-label">Khoa</label>
          <select name="faculty" className="form-select" value={filters.faculty} onChange={handleChange}>
            <option value="">-- Chọn khoa --</option>
            {faculties.map(f => <option key={f.id} value={f.id}>{f.name}</option>)}
          </select>
        </div>
        <div className="col-md-4">
          <label className="form-label">Lớp</label>
          <select name="className" className="form-select" value={filters.className} onChange={handleChange}>
            <option value="">-- Chọn lớp --</option>
            {classes.map(c => <option key={c.id} value={c.id}>{c.name}</option>)}
          </select>
        </div>
        <div className="col-md-4">
          <label className="form-label">Thành tích</label>
          <select name="classification" className="form-select" value={filters.classification} onChange={handleChange}>
            <option value="">-- Chọn loại --</option>
            <option value="Xuất sắc">Xuất sắc</option>
            <option value="Giỏi">Giỏi</option>
            <option value="Khá">Khá</option>
            <option value="Trung bình">Trung bình</option>
            <option value="Yếu">Yếu</option>
          </select>
        </div>
      </div>
      <div className="text-end mt-3">
        <button className="btn btn-primary" type="submit">Lọc</button>
      </div>
    </form>
  );
};

export default StatisticsFilterForm;
