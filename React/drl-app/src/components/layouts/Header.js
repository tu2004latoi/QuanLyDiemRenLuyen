import { useContext } from "react";
import { Link } from "react-router-dom";
import { useTranslation } from "react-i18next";
import {
  FaHome,
  FaPlus,
  FaClipboardList,
  FaChartBar,
  FaTrophy,
  FaCheckCircle,
  FaExclamationTriangle,
  FaClipboardCheck,
  FaStar,
  FaSignInAlt,
  FaUserPlus,
  FaComments,
  FaCalendarAlt
} from "react-icons/fa";
import { MyDispatcherContext, MyUserContext } from "../../configs/MyContexts";

const Header = () => {
  const { t } = useTranslation();
  const user = useContext(MyUserContext);
  const dispatch = useContext(MyDispatcherContext);

  const navItem = (
    to,
    icon,
    label,
    color = "text-blue-700",
    hover = "hover:bg-blue-100 hover:text-blue-900"
  ) => (
    <Link
      to={to}
      className={`flex items-center gap-3 font-medium ${color} ${hover} px-4 py-2 rounded-lg transition-all duration-200 hover:shadow-md hover:scale-[1.02] no-underline`}
    >
      {icon}
      <span className="truncate">{label}</span>
    </Link>
  );

  return (
    <div className="fixed top-0 left-0 h-full z-50 group">
      {/* Hover Area to Trigger Sidebar */}
      <div className="w-2 h-full bg-transparent absolute left-0 top-0 group-hover:w-64 transition-all duration-300 ease-in-out" />

      {/* Sidebar */}
      <div className="w-64 h-full bg-white shadow-xl -translate-x-full group-hover:translate-x-0 transition-transform duration-300 ease-in-out p-4 flex flex-col justify-between overflow-y-auto">
        {/* Header */}
        <div>
          <h1 className="text-2xl font-bold mb-6 text-center text-indigo-700 border-b pb-3 border-indigo-300 tracking-wide">
            {t("appTitle")}
          </h1>

          {/* Navigation */}
          <nav className="flex flex-col space-y-3">
            {navItem("/", <FaHome />, t("nav.home"))}

            {user !== null &&
              navItem(
                "/settings",
                <FaClipboardList />,
                t("nav.settings"),
                "text-indigo-700",
                "hover:bg-indigo-100 hover:text-indigo-900"
              )}

            {user !== null &&
              navItem(
                "/calendar",
                <FaCalendarAlt />,
                t("nav.calendar"),
                "text-indigo-700",
                "hover:bg-indigo-100 hover:text-indigo-900"
              )}

            {user?.role === "STUDENT" && (
              <>
                {navItem(
                  "/registeredactivity",
                  <FaClipboardCheck />,
                  t("nav.registeredActivity")
                )}
                {navItem(
                  "/activityparticipated",
                  <FaCheckCircle />,
                  t("nav.activityParticipated")
                )}
                {navItem("/viewpoint", <FaStar />, t("nav.viewPoint"))}
                {navItem(
                  "/missingreport",
                  <FaExclamationTriangle />,
                  t("nav.missingReport"),
                  "text-red-600",
                  "hover:bg-red-100 hover:text-red-800"
                )}
                {navItem(
                  "/chat",
                  <FaComments />,
                  t("nav.chat"),
                  "text-green-600",
                  "hover:bg-green-100 hover:text-green-800"
                )}
              </>
            )}

            {user?.role === "STAFF" && (
              <>
                {navItem(
                  "/viewachievement",
                  <FaTrophy />,
                  t("nav.viewAchievement")
                )}
                {navItem(
                  "/reportlist",
                  <FaClipboardList />,
                  t("nav.reportList"),
                  "text-orange-600",
                  "hover:bg-orange-100 hover:text-orange-800"
                )}
                {navItem("/addactivity", <FaPlus />, t("nav.addActivity"))}
                {navItem("/statistics", <FaChartBar />, t("nav.statistics"))}
                {navItem(
                  "/chat",
                  <FaComments />,
                  t("nav.chat"),
                  "text-green-600",
                  "hover:bg-green-100 hover:text-green-800"
                )}
                {navItem(
                  "/training-points",
                  <FaClipboardCheck />,
                  t("nav.trainingPoints"),
                  "text-blue-600",
                  "hover:bg-blue-100 hover:text-blue-800"
                )}
              </>
            )}

            {user === null ? (
              <>
                {navItem(
                  "/login",
                  <FaSignInAlt />,
                  t("nav.login"),
                  "text-green-600",
                  "hover:bg-green-100 hover:text-green-800"
                )}
                {navItem(
                  "/register",
                  <FaUserPlus />,
                  t("nav.register"),
                  "text-yellow-500",
                  "hover:bg-yellow-100 hover:text-yellow-800"
                )}
              </>
            ) : (
              <>
                <div className="flex flex-col items-center space-y-2 mt-6 border-t pt-4">
                  <img
                    src={user.avatar}
                    alt="avatar"
                    className="w-20 h-20 rounded-full border-4 border-transparent shadow-lg bg-gradient-to-tr from-indigo-400 to-indigo-600 p-[2px] hover:scale-105 transition-transform"
                  />
                  <span className="text-lg font-semibold text-gray-800">{`${t(
                    "greeting"
                  )} ${user.firstName}`}</span>
                  <span className="text-sm text-gray-500 uppercase tracking-wider">
                    {t(`roles.${user.role}`)}
                  </span>
                </div>
                <button
                  className="flex items-center justify-center gap-2 font-semibold text-white bg-red-500 hover:bg-red-600 px-4 py-2 mt-4 rounded-lg shadow-md transition-all"
                  onClick={() => dispatch({ type: "logout" })}
                >
                  <FaSignInAlt />
                  <span>{t("nav.logout")}</span>
                </button>
              </>
            )}
          </nav>
        </div>
      </div>
    </div>
  );
};

export default Header;
