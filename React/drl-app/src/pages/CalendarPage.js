import React, { useState, useEffect } from "react";
import { FaChevronLeft, FaChevronRight } from "react-icons/fa";
import { useNavigate } from "react-router-dom";
import { authApis, endpoints } from "../configs/Apis";
import { useTranslation } from "react-i18next";

function CalendarPage() {
  const { t } = useTranslation();
  const [activities, setActivities] = useState([]);
  const [currentDate, setCurrentDate] = useState(new Date());
  const [selectedDate, setSelectedDate] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchActivities = async () => {
      try {
        const res = await authApis().get(endpoints["activities"]);
        setActivities(res.data);
      } catch (error) {
        alert(t("calendar.noActivities")); // Hoặc tạo riêng key báo lỗi tải hoạt động
        console.error(error);
      }
    };
    fetchActivities();
  }, [t]);

  const generateCalendarMatrix = (date) => {
    const year = date.getFullYear();
    const month = date.getMonth();

    const firstDay = new Date(year, month, 1);
    const lastDay = new Date(year, month + 1, 0);

    const startDay = firstDay.getDay();
    const daysInMonth = lastDay.getDate();

    let matrix = [];
    let dayCounter = 1 - startDay;

    for (let row = 0; row < 6; row++) {
      let weekRow = [];
      for (let col = 0; col < 7; col++) {
        if (dayCounter > 0 && dayCounter <= daysInMonth) {
          weekRow.push(new Date(year, month, dayCounter));
        } else {
          weekRow.push(null);
        }
        dayCounter++;
      }
      matrix.push(weekRow);
    }
    return matrix;
  };

  const calendarMatrix = generateCalendarMatrix(currentDate);

  const activitiesByDate = activities.reduce((acc, activity) => {
    if (!activity.startDate) return acc;
    const d = new Date(activity.startDate);
    const key = d.toISOString().split("T")[0];
    if (!acc[key]) acc[key] = [];
    acc[key].push(activity);
    return acc;
  }, {});

  const changeMonth = (offset) => {
    const newDate = new Date(currentDate);
    newDate.setMonth(currentDate.getMonth() + offset);
    setCurrentDate(newDate);
    setSelectedDate(null);
  };

  const todayKey = new Date().toISOString().split("T")[0];

  const goToActivityDetail = (id) => {
    navigate(`/activitydetails/${id}`);
  };

  return (
    <div className="max-w-4xl mx-auto p-6 bg-white rounded-lg shadow-lg">
      <h2 className="text-3xl font-extrabold mb-6 text-center text-indigo-700">
        {t("calendar.title", {
          monthYear: currentDate.toLocaleString("vi-VN", {
            month: "long",
            year: "numeric",
          }),
        })}
      </h2>

      <div className="flex justify-between items-center mb-6">
        <button
          onClick={() => changeMonth(-1)}
          className="flex items-center gap-2 px-4 py-2 rounded-md bg-indigo-600 text-white hover:bg-indigo-700 transition"
          aria-label={t("calendar.prevMonth")}
          title={t("calendar.prevMonth")}
        >
          <FaChevronLeft />
          {t("calendar.prevMonth")}
        </button>

        <button
          onClick={() => changeMonth(1)}
          className="flex items-center gap-2 px-4 py-2 rounded-md bg-indigo-600 text-white hover:bg-indigo-700 transition"
          aria-label={t("calendar.nextMonth")}
          title={t("calendar.nextMonth")}
        >
          {t("calendar.nextMonth")}
          <FaChevronRight />
        </button>
      </div>

      <table className="w-full border-collapse shadow-md rounded-lg overflow-hidden">
        <thead className="bg-indigo-100 text-indigo-700 font-semibold">
          <tr>
            {t("calendar.weekDays", { returnObjects: true }).map((day) => (
              <th key={day} className="p-3 border border-indigo-200">
                {day}
              </th>
            ))}
          </tr>
        </thead>
        <tbody>
          {calendarMatrix.map((week, idx) => (
            <tr key={idx} className="even:bg-indigo-50">
              {week.map((date, idx2) => {
                const dayKey = date ? date.toISOString().split("T")[0] : null;
                const hasActivity = dayKey && activitiesByDate[dayKey]?.length > 0;
                const isSelected =
                  dayKey ===
                  (selectedDate ? selectedDate.toISOString().split("T")[0] : null);
                const isToday = dayKey === todayKey;

                return (
                  <td
                    key={idx2}
                    className={`relative border border-indigo-200 p-3 align-top cursor-pointer select-none
                      ${
                        isSelected
                          ? "bg-indigo-200 border-indigo-600 font-semibold"
                          : ""
                      }
                      ${
                        isToday && !isSelected
                          ? "bg-indigo-50 border-indigo-400"
                          : ""
                      }
                      hover:bg-indigo-100
                      transition`}
                    onClick={() => date && setSelectedDate(date)}
                    title={
                      hasActivity
                        ? t("calendar.activitiesCount", {
                            count: activitiesByDate[dayKey].length,
                          })
                        : date
                        ? t("calendar.noActivities")
                        : ""
                    }
                  >
                    {date ? (
                      <>
                        <div
                          className={`w-8 h-8 flex items-center justify-center rounded-full
                          ${
                            isSelected
                              ? "bg-indigo-600 text-white"
                              : "text-indigo-800"
                          }`}
                        >
                          {date.getDate()}
                        </div>

                        {hasActivity && (
                          <div className="absolute bottom-2 left-1/2 transform -translate-x-1/2 w-2.5 h-2.5 bg-red-500 rounded-full shadow-md"></div>
                        )}
                      </>
                    ) : (
                      ""
                    )}
                  </td>
                );
              })}
            </tr>
          ))}
        </tbody>
      </table>

      {selectedDate && (
        <div className="mt-8 p-4 border border-indigo-200 rounded-lg shadow-inner bg-indigo-50">
          <h3 className="text-2xl font-semibold mb-4 text-indigo-700">
            {selectedDate.toLocaleDateString("vi-VN")}
          </h3>
          {activitiesByDate[selectedDate.toISOString().split("T")[0]] ? (
            <ul className="list-disc pl-5 space-y-2 text-indigo-900">
              {activitiesByDate[selectedDate.toISOString().split("T")[0]].map(
                (act) => (
                  <li
                    key={act.id}
                    className="text-lg font-medium hover:text-indigo-600 cursor-pointer transition"
                    onClick={() => goToActivityDetail(act.id)}
                    title={t("calendar.viewDetails")}
                  >
                    <span className="font-semibold underline">{act.name}</span>{" "}
                    <span className="italic text-indigo-700">
                      - {act.location || t("calendar.noActivities")}
                    </span>
                  </li>
                )
              )}
            </ul>
          ) : (
            <p className="text-indigo-600 text-lg">
              {t("calendar.noActivityForDay")}
            </p>
          )}
        </div>
      )}
    </div>
  );
}

export default CalendarPage;
