import { Alert, Button, Form } from "react-bootstrap";
import { useRef, useState } from "react";
import Apis, { endpoints } from "../configs/Apis";
import { useNavigate } from "react-router-dom";
import MySpinner from "./layouts/MySpinner";

const Register = () => {
    const info = [{
        title: "Tên",
        field: "firstName",
        type: "text"
    }, {
        title: "Họ và tên lót",
        field: "lastName",
        type: "text"
    }, {
        title: "Tên đăng nhập",
        field: "email",
        type: "email"
    }, {
        title: "Mật khẩu",
        field: "password",
        type: "password"
    }, {
        title: "Xác nhận mật khẩu",
        field: "confirm",
        type: "password"
    }];

    const [user, setUser] = useState({}); //Biết set user

    const avatar = useRef(); //Biến set ảnh đại diện

    const [msg, setMsg] = useState(null); //Hiển thị lỗi ràng buộc khi đăng ký

    const [loading, setLoading] = useState(false); //Biến hiển thị đang load dữ liệu

    const nav = useNavigate(); //Hàm điều hướng

    const setState = (value, field) => { //Hàm set trạng thái user
        setUser({ ...user, [field]: value });
    };

    const register = async (e) => { //Hàm đăng ký
        e.preventDefault();
        if (user.password !== user.confirm) {
            setMsg("Mật khẩu không khớp");
        } else {
            let form = new FormData();
            for (let key in user) {
                if (key !== 'confirm')
                    form.append(key, user[key]);
            }

            form.append("avatar", avatar.current.files[0]);
            form.append("role", "STUDENT");
            try {
                setLoading(true);
                await Apis.post(endpoints['register'], form, {
                    headers: {
                        'Content-Type': "multipart/form-data"
                    }
                });

                nav("/login");
            } catch (ex) {
                console.error(ex);
                setMsg("Đã xảy ra lỗi, vui lòng thử lại!");
            } finally {
                setLoading(false);
            }

        }
    };

    return (
        <>
            <div className="text-center">
                <h1 className="text-2xl md:text-3xl font-bold text-blue-800 mb-1">Hệ thống quản lý </h1>
                <h1 className="text-2xl md:text-3xl font-bold text-blue-800 mb-1">Điểm rèn luyện </h1>
                <p className="text-sm text-gray-500">Bạn chưa có tài khoản đăng nhập?</p>
            </div>

            {msg && <Alert variant="danger">{msg}</Alert>}
            <Form onSubmit={register}>
                {info.map(i => <Form.Control className="mt-2 mb-1" value={user[i.field]} onChange={e => setState(e.target.value, i.field)}
                    type={i.type} key={i.field} placeholder={i.title} required />)}

                <Form.Control ref={avatar} className="mt-2 mb-1" type="file" placeholder="Ảnh đại diện" />

                {loading === true ? <MySpinner /> :
                    <div className="flex items-center justify-center mt-2 mb-2">
                        <Button type="submit" variant="success">Đăng ký</Button>
                    </div>
                }
            </Form>
        </>
    );
};

export default Register;