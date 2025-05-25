import { Alert, Button, Form, Card, Container } from "react-bootstrap";
import { useContext, useState } from "react";
import Apis, { authApis, endpoints } from "../configs/Apis";
import { useNavigate } from "react-router-dom";
import MySpinner from "../components/layouts/MySpinner";
import cookie from "react-cookies";
import { MyDispatcherContext } from "../configs/MyContexts";
import { useTranslation } from "react-i18next";

const Login = () => {
  const { t } = useTranslation();
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
        ADMIN: t("login.roleAdmin"),
        STAFF: t("login.roleStaff"),
        STUDENT: t("login.roleStudent"),
      };

      if (roleMap[u.data.role] !== selectedRole) {
        setMsg(t("login.errorRoleMismatch", { role: selectedRole }));
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
      setMsg(t("login.errorLoginFailed"));
    } finally {
      setLoading(false);
    }
  };

  const info = [
    { title: t("login.username"), field: "email", type: "email" },
    { title: t("login.password"), field: "password", type: "password" },
  ];

  return (
    <Container className="d-flex justify-content-center align-items-center min-vh-100">
      <Card className="p-4 shadow rounded-4" style={{ maxWidth: "450px", width: "100%" }}>
        <div className="text-center mb-4">
          <h2 className="fw-bold text-primary">{t("login.systemTitle")}</h2>
          <h4 className="fw-semibold text-secondary">{t("login.systemSubtitle")}</h4>
          <p className="text-muted small">{t("login.loginPrompt")}</p>
        </div>

        {msg && <Alert variant="danger">{msg}</Alert>}

        <Form onSubmit={login}>
          <Form.Group className="mb-3">
            <Form.Label>{t("login.roleLabel")}</Form.Label>
            <Form.Select
              value={selectedRole}
              onChange={(e) => setSelectedRole(e.target.value)}
              required
            >
              <option value="">{t("login.selectRolePlaceholder")}</option>
              <option value={t("login.roleStudent")}>{t("login.roleStudent")}</option>
              <option value={t("login.roleStaff")}>{t("login.roleStaff")}</option>
              <option value={t("login.roleAdmin")}>{t("login.roleAdmin")}</option>
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
                {t("login.loginButton")}
              </Button>
            </div>
          )}
        </Form>
      </Card>
    </Container>
  );
};

export default Login;
