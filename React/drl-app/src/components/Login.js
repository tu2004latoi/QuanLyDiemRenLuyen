import { Lock, User } from "lucide-react";
import Button from "./ui/buttonLogin";
import Input from "./ui/inputLogin";

const Login = () => {
    return (
        <div className="flex items-center justify-center min-h-screen from-blue-200 to-blue-400 px-4">
            <div className="w-full max-w-md bg-white rounded-2xl shadow-2xl p-8 space-y-6 animate-fade-in">
                <div className="text-center">
                    <h1 className="text-2xl md:text-3xl font-bold text-blue-800 mb-1">Hệ thống quản lý </h1>
                    <h1 className="text-2xl md:text-3xl font-bold text-blue-800 mb-1">Điểm rèn luyện </h1>
                    <p className="text-sm text-gray-500">Đăng nhập để bắt đầu sử dụng hệ thống</p>
                </div>

                <div className="space-y-4">
                    <div className="relative">
                        <User className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400" size={20} />
                        <Input
                            placeholder="Tên đăng nhập"
                            className="pl-10 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-400 focus:outline-none"
                        />
                    </div>

                    <div className="relative">
                        <Lock className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400" size={20} />
                        <Input
                            type="password"
                            placeholder="Mật khẩu"
                            className="pl-10 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-400 focus:outline-none"
                        />
                    </div>

                    <Button className="w-full bg-blue-600 hover:bg-blue-700 text-white font-semibold py-2 rounded-lg shadow-md transition duration-200">
                        Đăng nhập
                    </Button>
                </div>

                <div className="flex justify-between text-sm text-gray-600 mt-2">
                    <a href="/" className="hover:underline">Quên mật khẩu?</a>
                    <a href="/register" className="text-blue-600 hover:underline">Đăng ký tài khoản</a>
                </div>
            </div>
        </div>
    );
}

export default Login;