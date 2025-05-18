import { Alert, Button, Form } from "react-bootstrap";
import { useRef, useState, useEffect } from "react";
import Apis, { endpoints } from "../configs/Apis";
import { useNavigate } from "react-router-dom";
import MySpinner from "../components/layouts/MySpinner";

const Register = () => {
  const info = [
    { title: "Tên", field: "firstName", type: "text" },
    { title: "Họ và tên lót", field: "lastName", type: "text" },
    { title: "Tên đăng nhập", field: "email", type: "email" },
    { title: "Mật khẩu", field: "password", type: "password" },
    { title: "Xác nhận mật khẩu", field: "confirm", type: "password" },
  ];

  const [user, setUser] = useState({});
  const [faculties, setFaculties] = useState([]);
  const [classes, setClasses] = useState([]);
  const [msg, setMsg] = useState(null);
  const [loading, setLoading] = useState(false);

  const avatar = useRef();
  const nav = useNavigate();

  const setState = (value, field) => {
    setUser((prev) => ({ ...prev, [field]: value }));
  };

  const register = async (e) => {
    e.preventDefault();
    if (user.password !== user.confirm) {
      setMsg("Mật khẩu không khớp");
      return;
    }

    let form = new FormData();
    for (let key in user) {
      if (key !== "confirm") form.append(key, user[key]);
    }
    form.append("avatar", avatar.current.files[0]);
    form.append("role", "STUDENT");

    try {
      setLoading(true);
      await Apis.post(endpoints["register"], form, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      });
      nav("/login");
    } catch (ex) {
      console.error(ex);
      setMsg("Đã xảy ra lỗi, vui lòng thử lại!");
    } finally {
      setLoading(false);
    }
  };

  const loadClasses = async (facultyId) => {
    try {
      let res = await Apis.get(endpoints["classes"](facultyId));
      setClasses(res.data);
      setUser((prev) => ({ ...prev, classRoomId: "", facultyId }));
    } catch (err) {
      console.error(err);
    }
  };

  useEffect(() => {
    const loadFaculties = async () => {
      try {
        let res = await Apis.get(endpoints["faculties"]);
        setFaculties(res.data);
      } catch (err) {
        console.error(err);
      }
    };
    loadFaculties();
  }, []);

  return (
    <div className="max-w-md mx-auto mt-10 p-6 bg-white rounded-2xl shadow-xl">
      <div className="text-center mb-6">
        <h1 className="text-3xl font-bold text-blue-700">Hệ thống quản lý</h1>
        <h1 className="text-3xl font-bold text-blue-700 mb-2">
          Điểm rèn luyện
        </h1>
        <p className="text-sm text-gray-500">
          Bạn chưa có tài khoản? Đăng ký ngay!
        </p>
      </div>

      {msg && <Alert variant="danger">{msg}</Alert>}

      <Form onSubmit={register}>
        {info.map((i) => (
          <Form.Control
            className="mt-3 mb-2 p-2 rounded-xl border border-gray-300 focus:ring-2 focus:ring-blue-400"
            value={user[i.field] || ""}
            onChange={(e) => setState(e.target.value, i.field)}
            type={i.type}
            key={i.field}
            placeholder={i.title}
            required
          />
        ))}

        <Form.Select
          className="mt-3 mb-2 p-2 rounded-xl border border-gray-300"
          required
          onChange={(e) => {
            const facultyId = e.target.value;
            setState(facultyId, "facultyId");
            loadClasses(facultyId);
          }}
          value={user.facultyId || ""}
        >
          <option value="">-- Chọn khoa --</option>
          {faculties.map((f) => (
            <option key={f.id} value={f.id}>
              {f.name}
            </option>
          ))}
        </Form.Select>

        <Form.Select
          className="mt-3 mb-2 p-2 rounded-xl border border-gray-300"
          required
          onChange={(e) => setState(e.target.value, "classRoomId")}
          value={user.classRoomId || ""}
        >
          <option value="">-- Chọn lớp --</option>
          {classes.map((c) => (
            <option key={c.id} value={c.id}>
              {c.name}
            </option>
          ))}
        </Form.Select>

        <Form.Control
          ref={avatar}
          className="mt-3 mb-2 p-2 rounded-xl border border-gray-300"
          type="file"
          placeholder="Ảnh đại diện"
          required
        />

        {loading ? (
          <div className="text-center mt-4">
            <MySpinner />
          </div>
        ) : (
          <Button
            type="submit"
            variant="success"
            className="w-full mt-4 py-2 rounded-xl bg-green-500 hover:bg-green-600 transition-colors"
          >
            Đăng ký
          </Button>
        )}
      </Form>
    </div>
  );
};

export default Register;
