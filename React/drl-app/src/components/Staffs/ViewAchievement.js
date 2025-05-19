import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import Apis, { endpoints } from "../../configs/Apis";

const ViewAchievement = () => {
  const [students, setStudents] = useState([]);

  useEffect(() => {
    Apis.get(endpoints["students"])
      .then((res) => setStudents(res.data))
      .catch((err) => console.error("Error fetching students:", err));
  }, []);

  return (
    <div className="container py-5">
      <div className="text-center mb-4">
        <h2 className="fw-bold text-primary">Bảng thành tích sinh viên</h2>
        <p className="text-muted">
          Theo dõi thành tích của từng sinh viên theo các tiêu chí đánh giá
        </p>
      </div>

      <div className="table-responsive shadow-sm rounded-4 overflow-hidden">
        <table className="table table-hover align-middle text-center mb-0">
          <thead className="table-light">
            <tr className="align-middle">
              <th>Mã SV</th>
              <th>Họ tên</th>
              <th>Email</th>
              <th>Tổng điểm</th>
              <th>Xếp loại</th>
              <th>Chi tiết</th>
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
                    Chi tiết
                  </Link>
                </td>
              </tr>
            ))}
            {students.length === 0 && (
              <tr>
                <td colSpan="11" className="text-muted text-center py-4">
                  Không có dữ liệu sinh viên.
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
