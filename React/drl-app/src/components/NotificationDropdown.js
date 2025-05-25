import { useState, useRef, useEffect } from "react";
import { FaBell } from "react-icons/fa";
import { authApis, endpoints } from "../configs/Apis";
import { useTranslation } from "react-i18next";

const NotificationDropdown = ({ user }) => {
  const { t } = useTranslation();
  const [isOpen, setIsOpen] = useState(false);
  const [notifications, setNotifications] = useState([]);
  const [loading, setLoading] = useState(false);
  const dropdownRef = useRef();

  const loadNotifications = async () => {
    try {
      setLoading(true);
      const res = await authApis().get(endpoints["notifications"](user.id));
      setNotifications(res.data);
    } catch (err) {
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const markAllRead = async () => {
    try {
      await authApis().post(endpoints["markAllRead"](user.id));
      await loadNotifications();
    } catch (err) {
      console.error(err);
    }
  };

  const toggleDropdown = () => {
    setIsOpen(!isOpen);
    if (!isOpen) loadNotifications();
  };

  const handleClickOutside = (e) => {
    if (dropdownRef.current && !dropdownRef.current.contains(e.target)) {
      setIsOpen(false);
    }
  };

  useEffect(() => {
    document.addEventListener("mousedown", handleClickOutside);
    return () => document.removeEventListener("mousedown", handleClickOutside);
  }, []);

  useEffect(() => {
    loadNotifications();
  }, []);

  if (!user) return null;

  const unreadCount = notifications.filter((n) => !n.isRead).length;

  return (
    <div className="relative" ref={dropdownRef}>
      <button
        className="relative text-indigo-600 hover:text-indigo-800 transition"
        onClick={toggleDropdown}
        aria-label={t("notificationDropdown.title")}
      >
        <FaBell size={22} />
        {unreadCount > 0 && (
          <>
            <span className="absolute top-0 right-0 block h-2 w-2 rounded-full bg-red-500 ring-2 ring-white animate-ping"></span>
            <span className="absolute top-0 right-0 block h-2 w-2 rounded-full bg-red-500 ring-2 ring-white"></span>
          </>
        )}
      </button>

      {isOpen && (
        <div className="w-60 bg-white border border-gray-200 rounded-lg shadow-xl z-50">
          <div className="p-3 font-semibold text-gray-700 border-b flex justify-between items-center">
            <span>{t("notificationDropdown.title")}</span>
            <button
              onClick={markAllRead}
              className="text-xs text-indigo-600 hover:underline"
              disabled={loading}
            >
              {t("notificationDropdown.markAllRead")}
            </button>
          </div>

          <div className="max-h-64 overflow-y-auto">
            {loading ? (
              <div className="p-4 text-center text-gray-500 text-sm">
                {t("notificationDropdown.loading")}
              </div>
            ) : notifications.length > 0 ? (
              <ul className="divide-y">
                {notifications.map((noti) => (
                  <li
                    key={noti.id}
                    className={`px-4 py-2 hover:bg-gray-50 text-sm ${
                      noti.isRead
                        ? "bg-gray-100 text-gray-500"
                        : "bg-white text-gray-800"
                    }`}
                  >
                    <div className="font-medium">{noti.content}</div>
                    <div className="text-xs mt-1">{noti.createAt}</div>
                  </li>
                ))}
              </ul>
            ) : (
              <div className="p-4 text-center text-gray-500 text-sm">
                {t("notificationDropdown.noNotifications")}
              </div>
            )}
          </div>
        </div>
      )}
    </div>
  );
};

export default NotificationDropdown;
