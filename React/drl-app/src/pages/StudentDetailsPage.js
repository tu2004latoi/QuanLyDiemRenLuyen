import React, { useEffect, useState } from "react";
import { useParams, Link } from "react-router-dom";
import Apis, { endpoints } from "../configs/Apis";
import {
  FaArrowLeft,
  FaUserGraduate,
  FaEnvelope,
  FaChartBar,
  FaUniversity,
  FaIdCard,
  FaAward,
} from "react-icons/fa";
import { GiGraduateCap } from "react-icons/gi";
import { motion } from "framer-motion";

const StudentDetail = () => {
  const { userId } = useParams();
  const [student, setStudent] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const loadStudent = async () => {
      try {
        const res = await Apis.get(endpoints.studentDetails(userId));
        setStudent(res.data);
      } catch (err) {
        console.error("Failed to fetch student details:", err);
      } finally {
        setLoading(false);
      }
    };
    loadStudent();
  }, [userId]);

  const getBadgeColor = (classify) => {
    switch (classify) {
      case "Xuất sắc":
        return "bg-gradient-to-r from-purple-600 via-pink-600 to-rose-500";
      case "Giỏi":
        return "bg-gradient-to-r from-blue-600 to-cyan-500";
      case "Khá":
        return "bg-gradient-to-r from-emerald-500 to-teal-400";
      case "Trung bình":
        return "bg-gradient-to-r from-amber-500 to-yellow-400";
      default:
        return "bg-gradient-to-r from-gray-500 to-gray-400";
    }
  };

  const getPointColor = (point) => {
    const num = parseFloat(point);
    if (num >= 90) return "text-purple-600";
    if (num >= 80) return "text-blue-600";
    if (num >= 65) return "text-emerald-600";
    return "text-amber-600";
  };

  if (loading) {
    return (
      <div className="flex justify-center items-center min-h-screen bg-gray-50">
        <motion.div
          animate={{ rotate: 360 }}
          transition={{ duration: 1, repeat: Infinity, ease: "linear" }}
          className="rounded-full h-16 w-16 border-t-4 border-b-4 border-blue-600"
        />
      </div>
    );
  }

  if (!student) {
    return (
      <div className="flex justify-center items-center min-h-screen bg-gray-50">
        <motion.div
          initial={{ scale: 0.9, opacity: 0 }}
          animate={{ scale: 1, opacity: 1 }}
          className="bg-red-100 border-l-4 border-red-500 text-red-700 p-4 rounded shadow-lg max-w-md w-full"
        >
          <div className="flex items-center">
            <svg
              className="w-6 h-6 mr-2"
              fill="none"
              stroke="currentColor"
              viewBox="0 0 24 24"
            >
              <path
                strokeLinecap="round"
                strokeLinejoin="round"
                strokeWidth={2}
                d="M12 8v4m0 4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z"
              />
            </svg>
            <h3 className="font-semibold">
              Không tìm thấy thông tin sinh viên
            </h3>
          </div>
          <p className="mt-2 text-sm">
            Vui lòng kiểm tra lại ID sinh viên hoặc thử lại sau.
          </p>
        </motion.div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gradient-to-br from-gray-50 to-blue-50 py-8 px-4 sm:px-6 lg:px-8">
      <div className="max-w-6xl mx-auto">
        <motion.div
          initial={{ opacity: 0, y: -20 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ duration: 0.3 }}
        >
          <Link
            to="/viewachievement"
            className="inline-flex items-center text-blue-600 hover:text-blue-800 mb-6 transition-colors duration-200 group"
          >
            <motion.div whileHover={{ x: -4 }} className="flex items-center">
              <FaArrowLeft className="mr-2 transition-transform group-hover:-translate-x-1" />
              <span>Quay lại bảng thành tích</span>
            </motion.div>
          </Link>
        </motion.div>

        <motion.div
          initial={{ opacity: 0, y: 20 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ duration: 0.4 }}
          className="bg-white rounded-2xl shadow-xl overflow-hidden border border-gray-100 hover:shadow-2xl transition-shadow duration-300"
        >
          {/* Header */}
          <div className="bg-gradient-to-r from-indigo-700 to-blue-600 p-6 text-white relative overflow-hidden">
            <div className="absolute -right-10 -top-10 w-32 h-32 bg-white/10 rounded-full"></div>
            <div className="absolute -right-20 top-20 w-40 h-40 bg-white/5 rounded-full"></div>

            <div className="flex flex-col sm:flex-row items-start sm:items-center space-y-4 sm:space-y-0 sm:space-x-6 relative z-10">
              <div className="bg-white/20 p-3 rounded-full backdrop-blur-sm border border-white/30">
                <div className="w-20 h-20 rounded-full border-4 border-white/30 overflow-hidden shadow-lg bg-white">
                  <img
                    src={student.avatar}
                    alt={student.studentName}
                    className="w-full h-full object-cover"
                  />
                </div>
              </div>
              <div>
                <h1 className="text-3xl font-bold tracking-tight">
                  {student.studentName}
                </h1>
                <div className="flex flex-wrap items-center gap-3 mt-2">
                  <span className="bg-white/20 px-3 py-1 rounded-full text-sm flex items-center">
                    <FaIdCard className="mr-1" /> {student.studentId}
                  </span>
                  <span className="bg-white/20 px-3 py-1 rounded-full text-sm flex items-center">
                    <FaUniversity className="mr-1" /> {student.classroom}
                  </span>
                  <span className="bg-white/20 px-3 py-1 rounded-full text-sm flex items-center">
                    <FaUniversity className="mr-1" /> {student.facultyName}
                  </span>
                </div>
              </div>
            </div>
          </div>

          {/* Content */}
          <div className="p-6 grid grid-cols-1 lg:grid-cols-3 gap-6">
            {/* Personal Info */}
            <div className="lg:col-span-1 space-y-6">
              <div className="bg-gray-50 p-5 rounded-xl border border-gray-100">
                <h2 className="text-xl font-semibold text-gray-700 pb-3 border-b border-gray-200 flex items-center">
                  <FaUserGraduate className="mr-2 text-indigo-600" />
                  Thông tin cá nhân
                </h2>

                <div className="space-y-4 mt-4">
                  <div className="flex items-start">
                    <div className="bg-indigo-100 p-2 rounded-lg mr-3">
                      <FaEnvelope className="text-indigo-600" />
                    </div>
                    <div>
                      <p className="text-sm text-gray-500">Email</p>
                      <p className="font-medium text-gray-800 break-all">
                        {student.email}
                      </p>
                    </div>
                  </div>
                </div>
              </div>

              {/* Achievements */}
              <div className="bg-gray-50 p-5 rounded-xl border border-gray-100">
                <h2 className="text-xl font-semibold text-gray-700 pb-3 border-b border-gray-200 flex items-center">
                  <FaAward className="mr-2 text-amber-600" />
                  Thành tích nổi bật
                </h2>
                <div className="mt-4 space-y-3">
                  <div className="flex items-center justify-between">
                    <span className="text-gray-600">Tổng điểm:</span>
                    <span className="font-bold text-blue-600">
                      {student.totalPoint}
                    </span>
                  </div>
                  <div className="flex items-center justify-between">
                    <span className="text-gray-600">Xếp loại:</span>
                    <span
                      className={`px-3 py-1 rounded-full text-xs font-semibold text-white ${getBadgeColor(
                        student.classify
                      )}`}
                    >
                      {student.classify}
                    </span>
                  </div>
                </div>
              </div>
            </div>

            {/* Academic Info */}
            <div className="lg:col-span-2">
              <div className="bg-gray-50 p-5 rounded-xl border border-gray-100">
                <h2 className="text-xl font-semibold text-gray-700 pb-3 border-b border-gray-200 flex items-center">
                  <FaChartBar className="mr-2 text-green-600" />
                  Chi tiết kết quả học tập
                </h2>

                <div className="mt-6 grid grid-cols-1 md:grid-cols-2 gap-4">
                  {[1, 2, 3, 4].map((i) => (
                    <motion.div
                      key={i}
                      whileHover={{ y: -5 }}
                      className="bg-white p-4 rounded-lg shadow border border-gray-100 hover:shadow-md transition-shadow"
                    >
                      <div className="flex items-center justify-between">
                        <h3 className="font-medium text-gray-700">{`Điều ${i}`}</h3>
                        <span
                          className={`text-lg font-bold ${getPointColor(
                            student[`point_${i}`]
                          )}`}
                        >
                          {student[`point_${i}`]}
                        </span>
                      </div>
                      <div className="mt-3 h-2 bg-gray-200 rounded-full overflow-hidden">
                        <div
                          className="h-full bg-gradient-to-r from-blue-400 to-blue-600 rounded-full"
                          style={{
                            width: `${Math.min(
                              100,
                              parseFloat(student[`point_${i}`])
                            )}%`,
                          }}
                        />
                      </div>
                    </motion.div>
                  ))}
                </div>

                {/* Summary Card */}
                <div className="mt-8 bg-gradient-to-r from-blue-600 to-indigo-700 rounded-xl p-6 text-white shadow-lg">
                  <div className="flex flex-col md:flex-row justify-between items-center">
                    <div className="text-center md:text-left mb-4 md:mb-0">
                      <p className="text-sm opacity-80">Tổng điểm học tập</p>
                      <p className="text-4xl font-bold">{student.totalPoint}</p>
                    </div>
                    <div className="text-center">
                      <p className="text-sm opacity-80">Xếp hạng</p>
                      <div className="mt-1 flex items-center justify-center">
                        <span
                          className={`px-4 py-2 rounded-full text-white text-lg font-semibold ${getBadgeColor(
                            student.classify
                          )}`}
                        >
                          {student.classify}
                        </span>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </motion.div>
      </div>
    </div>
  );
};

export default StudentDetail;
