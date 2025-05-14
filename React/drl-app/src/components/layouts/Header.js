import { useContext } from "react";
import { Link } from "react-router-dom";
import { MyDispatcherContext, MyUserContext } from "../../configs/MyContexts";
import { FaHome, FaSignInAlt, FaUserPlus } from "react-icons/fa";

const Header = () => {
    const user = useContext(MyUserContext);
    const dispatch = useContext(MyDispatcherContext);

    return (
        <div className="fixed top-0 left-0 h-full z-50 group">
            <div className="w-2 h-full bg-transparent absolute left-0 top-0 group-hover:w-64 transition-all duration-300 ease-in-out" />


            <div className="w-64 h-full bg-white shadow-lg -translate-x-full group-hover:translate-x-0 transition-transform duration-300 ease-in-out p-4 flex flex-col justify-between">
                {/* Sidebar Header & Navigation */}
                <div>
                    <h1 className="text-xl font-bold mb-6 text-center">OU TRAINING POINT</h1>
                    <nav className="flex flex-col space-y-4">
                        <Link to="/" className="flex items-center space-x-2 font-bold text-blue-700 hover:bg-blue-100 hover:text-blue-900 px-3 py-2 rounded no-underline transition-transform hover:translate-x-1">
                            <FaHome />
                            <span>Trang chủ</span>
                        </Link>
                    </nav>
                </div>

                {/* Sidebar Footer: Avatar + Logout or Login/Register */}
                <div className="flex flex-col space-y-2 mt-6">
                    {user === null ? (
                        <>
                            <Link to="/login" className="flex items-center space-x-2 font-bold text-red-600 hover:bg-red-600 hover:text-white px-4 py-2 rounded transition">
                                <FaSignInAlt />
                                <span>Đăng nhập</span>
                            </Link>
                            <Link to="/register" className="flex items-center space-x-2 font-bold text-green-600 hover:bg-green-600 hover:text-white px-4 py-2 rounded transition">
                                <FaUserPlus />
                                <span>Đăng ký</span>
                            </Link>
                        </>
                    ) : (
                        <>
                            <div className="flex flex-col items-center space-y-1">
                                <img src={user.avatar} alt="avatar" className="w-14 h-14 rounded-full border border-black shadow border-4" />
                                <span className="text-xl font-semibold">Chào {user.firstName}</span>
                            </div>
                            <button className="flex items-center space-x-2 font-bold text-white bg-red-500 hover:bg-red-600 px-4 py-2 rounded transition" onClick={() => dispatch({ type: "logout" })}>
                                <FaSignInAlt /> {/* Add an icon for the logout button */}
                                <span>Đăng xuất</span>
                            </button>
                        </>
                    )}
                </div>
            </div>
        </div>
    );
};

export default Header;
