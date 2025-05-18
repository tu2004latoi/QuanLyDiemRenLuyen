import React from "react";
import { useTranslation } from "react-i18next";

const SettingsPage = () => {
  const { i18n, t } = useTranslation();

  const changeLanguage = (lng) => {
    if (i18n.language !== lng) {
      i18n.changeLanguage(lng);
    }
  };

  return (
    <div className="p-8 max-w-md mx-auto bg-gradient-to-tr from-white to-gray-50 shadow-lg rounded-2xl mt-12">
      <h2 className="text-3xl font-extrabold mb-6 text-gray-900 tracking-wide">
        {t("settings.title")}
      </h2>

      <div className="space-y-6">
        <div>
          <label className="block text-gray-600 font-semibold mb-3 text-lg">
            {t("settings.language")}
          </label>
          <div className="flex gap-6">
            <button
              onClick={() => changeLanguage("vi")}
              className={`flex items-center gap-2 px-5 py-3 rounded-xl border-2 font-semibold transition 
                ${
                  i18n.language === "vi"
                    ? "bg-blue-600 border-blue-600 text-white shadow-md shadow-blue-300"
                    : "bg-white border-gray-300 text-gray-700 hover:bg-blue-50 hover:border-blue-400"
                }`}
              title="Chọn Tiếng Việt"
              aria-label="Chọn Tiếng Việt"
            >
              <span className="text-xl">🇻🇳</span> Tiếng Việt
            </button>

            <button
              onClick={() => changeLanguage("en")}
              className={`flex items-center gap-2 px-5 py-3 rounded-xl border-2 font-semibold transition 
                ${
                  i18n.language === "en"
                    ? "bg-blue-600 border-blue-600 text-white shadow-md shadow-blue-300"
                    : "bg-white border-gray-300 text-gray-700 hover:bg-blue-50 hover:border-blue-400"
                }`}
              title="Select English"
              aria-label="Select English"
            >
              <span className="text-xl">🇺🇸</span> English
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default SettingsPage;
