import { Alert, Button, Form, Card, Container } from "react-bootstrap";
import { useContext, useState } from "react";
import Apis, { authApis, endpoints } from "../configs/Apis";
import { useNavigate } from "react-router-dom";
import MySpinner from "../components/layouts/MySpinner";
import cookie from "react-cookies";
import { MyDispatcherContext } from "../configs/MyContexts";

const Login = () => {
  const info = [
    { title: "Tên đăng nhập", field: "email", type: "email" },
    { title: "Mật khẩu", field: "password", type: "password" },
  ];

  const [user, setUser] = useState({});
  const [msg, setMsg] = useState(null);
  const [selectedRole, setSelectedRole] = useState("");
  const [loading, setLoading] = useState(false);
  const nav = useNavigate();
  const dispatch = useContext(MyDispatcherContext);

  const setState = (value, field) => {
    setUser({ ...user, [field]: value });
  };

  const login = async (e) => {
    e.preventDefault();
    try {
      setLoading(true);
      let res = await Apis.post(endpoints["login"], { ...user });
      cookie.save("token", res.data.token);

      let u = await authApis().get(endpoints["current-user"]);
      const roleMap = {
        ADMIN: "Quản Trị Viên",
        STAFF: "CTV sinh viên",
        STUDENT: "Sinh viên",
      };

      if (roleMap[u.data.role] !== selectedRole) {
        setMsg(`Bạn không có quyền đăng nhập với vai trò "${selectedRole}".`);
        cookie.remove("token");
        return;
      }

      dispatch({
        type: "login",
        payload: u.data,
      });
      nav("/");
    } catch (ex) {
      console.error(ex);
      setMsg("Đăng nhập thất bại. Vui lòng kiểm tra lại thông tin.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <Container className="d-flex justify-content-center align-items-center min-vh-100">
      <Card
        className="p-4 shadow rounded-4"
        style={{ maxWidth: "450px", width: "100%" }}
      >
        <div className="text-center mb-4">
          <h2 className="fw-bold text-primary">Hệ thống quản lý</h2>
          <h4 className="fw-semibold text-secondary">Điểm rèn luyện</h4>
          <p className="text-muted small">Đăng nhập để truy cập hệ thống</p>
        </div>

        {msg && <Alert variant="danger">{msg}</Alert>}

        <Form onSubmit={login}>
          <Form.Group className="mb-3">
            <Form.Label>Vai trò</Form.Label>
            <Form.Select
              value={selectedRole}
              onChange={(e) => setSelectedRole(e.target.value)}
              required
            >
              <option value="">-- Chọn vai trò --</option>
              <option value="Sinh viên">Sinh viên</option>
              <option value="CTV sinh viên">CTV sinh viên</option>
              <option value="Quản Trị Viên">Quản Trị Viên</option>
            </Form.Select>
          </Form.Group>

          {info.map((i) => (
            <Form.Group key={i.field} className="mb-3">
              <Form.Label>{i.title}</Form.Label>
              <Form.Control
                type={i.type}
                placeholder={i.title}
                value={user[i.field] || ""}
                onChange={(e) => setState(e.target.value, i.field)}
                required
              />
            </Form.Group>
          ))}

          {loading ? (
            <MySpinner />
          ) : (
            <div className="d-grid">
              <Button type="submit" variant="primary">
                Đăng nhập
              </Button>
            </div>
          )}
        </Form>
      </Card>
    </Container>
  );
};

export default Login;
