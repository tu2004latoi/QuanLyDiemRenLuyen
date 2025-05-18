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
    <div className="p-6 max-w-xl mx-auto bg-white shadow rounded-xl mt-10">
      <h2 className="text-2xl font-bold mb-4">{t("settings.title")}</h2>

      <div className="space-y-4">
        <div>
          <label className="block text-gray-700 font-medium mb-2">
            {t("settings.language")}
          </label>
          <div className="flex gap-4">
            <button
              onClick={() => changeLanguage("vi")}
              className={`px-4 py-2 rounded-lg border transition ${
                i18n.language === "vi"
                  ? "bg-blue-600 text-white"
                  : "bg-gray-100 text-gray-700"
              }`}
            >
              🇻🇳 Tiếng Việt
            </button>
            <button
              onClick={() => changeLanguage("en")}
              className={`px-4 py-2 rounded-lg border transition ${
                i18n.language === "en"
                  ? "bg-blue-600 text-white"
                  : "bg-gray-100 text-gray-700"
              }`}
            >
              🇺🇸 English
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default SettingsPage;
