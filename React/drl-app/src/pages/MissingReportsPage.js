import React, { useEffect, useState } from "react";
import axios from "axios";
import Apis, { authApis, endpoints } from "../configs/Apis";

const STATUS = {
  PENDING: "PENDING",
  CONFIRMED: "CONFIRMED",
  REJECTED: "REJECTED",
};

const MissingReportsPage = () => {
  const [reports, setReports] = useState([]);
  const [loading, setLoading] = useState(false);

  const fetchReports = async () => {
    setLoading(true);
    try {
      const res = await authApis().get(endpoints["missingReports"]);
      console.log("Fetched reports:", res.data);
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

  return (
    <div className="container py-5">
      <h2 className="fw-bold text-primary mb-4">Danh sách báo cáo thiếu</h2>

      <div className="table-responsive">
        <table className="table table-bordered table-hover align-middle shadow-sm">
          <thead className="table-primary text-center">
            <tr>
              <th>ID</th>
              <th>Activity ID</th>
              <th>Training Point ID</th>
              <th>User ID</th>
              <th>Điểm</th>
              <th>Ngày báo cáo</th>
              <th>Hình ảnh minh chứng</th>
              <th>Trạng thái</th>
            </tr>
          </thead>
          <tbody>
            {loading ? (
              <tr>
                <td colSpan="8" className="text-center">Đang tải dữ liệu...</td>
              </tr>
            ) : reports.length === 0 ? (
              <tr>
                <td colSpan="8" className="text-center">Không có báo cáo nào.</td>
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
                  </td>
                  <td>{r.status}</td>
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
