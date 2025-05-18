import React from "react";

const StatisticsTable = ({ students }) => {
  return (
    <div className="table-responsive mb-4">
      <table className="table table-bordered table-hover rounded-4 overflow-hidden shadow-sm">
        <thead className="table-primary text-center align-middle">
          <tr>
            <th>#</th>
            <th>Mã SV</th>
            <th>Họ và tên</th>
            <th>Email</th>
            <th>Khoa</th>
            <th>Lớp</th>
            <th>Điểm 1</th>
            <th>Điểm 2</th>
            <th>Điểm 3</th>
            <th>Điểm 4</th>
            <th>Tổng điểm</th>
            <th>Xếp loại</th>
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
