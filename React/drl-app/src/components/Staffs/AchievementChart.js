import React from "react";
import { Pie } from "react-chartjs-2";
import { Chart as ChartJS, ArcElement, Tooltip, Legend, Title } from "chart.js";
import { useTranslation } from "react-i18next";

ChartJS.register(ArcElement, Tooltip, Legend, Title);

export default function AchievementChart({ data }) {
  const { t } = useTranslation();

  const labels = Object.keys(data);
  const values = Object.values(data);

  return (
    <div className="card shadow-sm p-4 mb-4 rounded-4 border-0">
      <h5 className="mb-3 text-primary text-center">
        <i className="fas fa-chart-pie me-2"></i>
        {t("achievementChart.distribution")}
      </h5>
      <div className="text-center">
        <Pie
          data={{
            labels,
            datasets: [
              {
                label: t("achievementChart.count"),
                data: values,
                backgroundColor: ['#4CAF50', '#2196F3', '#FFC107', '#FF5722', '#9C27B0'],
                borderColor: "#ffffff",
                borderWidth: 1,
              },
            ],
          }}
          options={{
            responsive: true,
            plugins: {
              legend: { position: "bottom" },
              title: {
                display: true,
                text: t("achievementChart.distributionStudent"),
              },
            },
          }}
        />
      </div>
    </div>
  );
}
