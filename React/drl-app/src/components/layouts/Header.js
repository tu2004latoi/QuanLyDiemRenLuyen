import { useContext } from "react";
import { Link } from "react-router-dom";
import { MyDispatcherContext, MyUserContext } from "../../configs/MyContexts";
import { FaHome, FaPlus, FaClipboardList, FaChartBar, FaTrophy, FaCheckCircle, FaExclamationTriangle, FaClipboardCheck, FaStar, FaSignInAlt, FaUserPlus } from "react-icons/fa";

const Header = () => {
    const user = useContext(MyUserContext);
    const dispatch = useContext(MyDispatcherContext);

    return (
        <div className="fixed top-0 left-0 h-full z-50 group">
            <div className="w-2 h-full bg-transparent absolute left-0 top-0 group-hover:w-64 transition-all duration-300 ease-in-out" />
            <div className="w-64 h-full bg-white shadow-lg -translate-x-full group-hover:translate-x-0 transition-transform duration-300 ease-in-out p-4 flex flex-col justify-between">
                {/* Sidebar Header & Navigation */}
                <div>
                    <h1 className="text-xl font-bold mb-6 text-center border-b border-gray-300">OU TRAINING POINT</h1>
                    <nav className="flex flex-col space-y-2">
                        {/* General */}
                        <Link to="/" className="flex items-center gap-2 font-semibold text-blue-700 hover:bg-blue-100 hover:text-blue-900 px-4 py-2 rounded-xl shadow transition-transform hover:translate-x-1 no-underline whitespace-nowrap">
                            <FaHome />
                            <span>Trang chủ</span>
                        </Link>

                        {/* Nếu là STUDENT */}
                        {user?.role === "STUDENT" && (
                            <>
                                <Link to="/registeredactivity" className="flex items-center gap-2 font-semibold text-blue-700 hover:bg-blue-100 hover:text-blue-900 px-4 py-2 rounded-xl shadow transition-transform hover:translate-x-1 no-underline whitespace-nowrap">
                                    <FaClipboardCheck />
                                    <span>Đã đăng ký</span>
                                </Link>
                                <Link to="/activityparticipated" className="flex items-center gap-2 font-semibold text-blue-700 hover:bg-blue-100 hover:text-blue-900 px-4 py-2 rounded-xl shadow transition-transform hover:translate-x-1 no-underline whitespace-nowrap">
                                    <FaCheckCircle />
                                    <span>Đã tham gia</span>
                                </Link>
                                <Link to="/viewpoint" className="flex items-center gap-2 font-semibold text-blue-700 hover:bg-blue-100 hover:text-blue-900 px-4 py-2 rounded-xl shadow transition-transform hover:translate-x-1 no-underline whitespace-nowrap">
                                    <FaStar />
                                    <span>Xem điểm</span>
                                </Link>
                                <Link to="/missingreport" className="flex items-center gap-2 font-semibold text-blue-700 hover:bg-blue-100 hover:text-blue-900 px-4 py-2 rounded-xl shadow transition-transform hover:translate-x-1 no-underline whitespace-nowrap">
                                    <FaExclamationTriangle />
                                    <span>Báo thiếu</span>
                                </Link>
                                <Link to="/chat" className="flex items-center gap-2 font-semibold text-blue-700 hover:bg-blue-100 hover:text-blue-900 px-4 py-2 rounded-xl shadow transition-transform hover:translate-x-1 no-underline whitespace-nowrap">
                                    <FaExclamationTriangle />
                                    <span>Nhắn tin</span>
                                </Link>
                            </>
                        )}

                        {/* Nếu là STAFF */}
                        {user?.role === "STAFF" && (
                            <>
                                <Link to="/viewachievement" className="flex items-center gap-2 font-semibold text-blue-700 hover:bg-blue-100 hover:text-blue-900 px-4 py-2 rounded-xl shadow transition-transform hover:translate-x-1 no-underline whitespace-nowrap">
                                    <FaTrophy />
                                    <span>Thành tích</span>
                                </Link>
                                <Link to="/reportlist" className="flex items-center gap-2 font-semibold text-blue-700 hover:bg-blue-100 hover:text-blue-900 px-4 py-2 rounded-xl shadow transition-transform hover:translate-x-1 no-underline whitespace-nowrap">
                                    <FaClipboardList />
                                    <span>Báo thiếu</span>
                                </Link>
                                <Link to="/addactivity" className="flex items-center gap-2 font-semibold text-blue-700 hover:bg-blue-100 hover:text-blue-900 px-4 py-2 rounded-xl shadow transition-transform hover:translate-x-1 no-underline whitespace-nowrap">
                                    <FaPlus />
                                    <span>Thêm hoạt động</span>
                                </Link>
                                <Link to="/stats" className="flex items-center gap-2 font-semibold text-blue-700 hover:bg-blue-100 hover:text-blue-900 px-4 py-2 rounded-xl shadow transition-transform hover:translate-x-1 no-underline whitespace-nowrap">
                                    <FaChartBar />
                                    <span>Thống kê</span>
                                </Link>
                                <Link to="/chat" className="flex items-center gap-2 font-semibold text-blue-700 hover:bg-blue-100 hover:text-blue-900 px-4 py-2 rounded-xl shadow transition-transform hover:translate-x-1 no-underline whitespace-nowrap">
                                    <FaExclamationTriangle />
                                    <span>Nhắn tin</span>
                                </Link>
                            </>
                        )}

                        {/* Đăng nhập & đăng ký */}
                        {user === null ? (
                            <>
                                <Link to="/login" className="flex items-center gap-2 font-semibold text-green-600 hover:bg-green-200 hover:text-green-800 px-4 py-2 rounded-xl shadow transition-transform hover:translate-x-1 no-underline whitespace-nowrap">
                                    <FaSignInAlt />
                                    <span>Đăng nhập</span>
                                </Link>

                                <Link to="/register" className="flex items-center gap-2 font-semibold text-yellow-500 hover:bg-yellow-200 hover:text-yellow-800 px-4 py-2 rounded-xl shadow transition-transform hover:translate-x-1 no-underline whitespace-nowrap">
                                    <FaUserPlus />
                                    <span>Đăng ký</span>
                                </Link>
                            </>
                        ) : (
                            <>
                                <div className="flex flex-col items-center space-y-1 mt-4">
                                    <img src={user.avatar} alt="avatar" className="w-20 h-20 rounded-full border-4 border-gray-500 shadow" />
                                    <span className="text-xl font-semibold">Chào {user.firstName}</span>
                                    <span className="text-sm text-gray-600 capitalize">
                                        {user.role === "STUDENT" ? "SINH VIÊN" : user.role === "STAFF" ? "CTV SINH VIÊN" : user.role === "ADMIN" ? "QUẢN TRỊ VIÊN" : user.role}
                                    </span>
                                </div>
                                <button className="flex items-center space-x-2 font-bold text-white bg-red-500 hover:bg-red-600 px-4 py-2 rounded transition mt-3" onClick={() => dispatch({ type: "logout" })}>
                                    <FaSignInAlt />
                                    <span>Đăng xuất</span>
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
