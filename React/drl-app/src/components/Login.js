import { Alert, Button, Form } from "react-bootstrap";
import { useContext, useState } from "react";
import Apis, { authApis, endpoints } from "../configs/Apis";
import { useNavigate } from "react-router-dom";
import MySpinner from "./layouts/MySpinner";
import cookie from 'react-cookies';
import { MyDispatcherContext } from "../configs/MyContexts";

const Login = () => {
    const info = [{
        title: "Tên đăng nhập",
        field: "email",
        type: "email"
    }, {
        title: "Mật khẩu",
        field: "password",
        type: "password"
    }];

    const [user, setUser] = useState({}); //Biến set user

    const [msg, setMsg] = useState(null); //Hiển thị lỗi ràng buộc khi đăng nhập

    const [selectedRole, setSelectedRole] = useState("");

    const [loading, setLoading] = useState(false); //Biến hiển thị đang load dữ liệu

    const nav = useNavigate(); //Hàm điều hướng

    const setState = (value, field) => { //Hàm set trạng thái user
        setUser({ ...user, [field]: value });
    };

    const dispatch = useContext(MyDispatcherContext);

    const login = async (e) => { //Hàm đăng nhập
        e.preventDefault();
        try {
            setLoading(true);

            // Gửi request đăng nhập
            let res = await Apis.post(endpoints['login'], { ...user });
            cookie.save('token', res.data.token);

            // Lấy thông tin user
            let u = await authApis().get(endpoints['current-user']);
            console.info(u.data);

            // Kiểm tra role
            const roleMap = {
                "ADMIN": "Quản Trị Viên",
                "STAFF": "CTV sinh viên",
                "STUDENT": "Sinh viên"
            };

            if (roleMap[u.data.role] !== selectedRole) {
                setMsg(`Bạn không có quyền đăng nhập với vai trò "${selectedRole}".`);
                cookie.remove('token'); // Xóa token nếu sai vai trò
                return;
            }


            dispatch({
                "type": "login",
                "payload": u.data
            });
            nav("/");
        } catch (ex) {
            console.error(ex);
        } finally {
            setLoading(false);
        }
    };

    return (
        <>
            <div className="text-center">
                <h1 className="text-2xl md:text-3xl font-bold text-blue-800 mb-1">Hệ thống quản lý </h1>
                <h1 className="text-2xl md:text-3xl font-bold text-blue-800 mb-1">Điểm rèn luyện </h1>
                <p className="text-sm text-gray-500">Đăng nhập để truy cập hệ thống!</p>
            </div>

            {msg && <Alert variant="danger">{msg}</Alert>}
            <Form onSubmit={login}>
                <Form.Select className="mt-2 mb-2" value={selectedRole} onChange={(e) => setSelectedRole(e.target.value)} required>
                    <option value="">-- Chọn vai trò --</option>
                    <option value="Sinh viên">Sinh viên</option>
                    <option value="CTV sinh viên">CTV sinh viên</option>
                    <option value="Quản Trị Viên">Quản Trị Viên</option>
                </Form.Select>

                {info.map(i => <Form.Control className="mt-2 mb-1" value={user[i.field]} onChange={e => setState(e.target.value, i.field)}
                    type={i.type} key={i.field} placeholder={i.title} required />)}

                {loading === true ? <MySpinner /> :
                    <div className="flex items-center justify-center mt-2 mb-2">
                        <Button type="submit" variant="success">Đăng nhập</Button>
                    </div>
                }
            </Form>
        </>
    );
};

export default Login;