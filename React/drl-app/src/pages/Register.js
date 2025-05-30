import { Alert, Button, Form } from "react-bootstrap";
import { useRef, useState, useEffect } from "react";
import Apis, { endpoints } from "../configs/Apis";
import { useNavigate } from "react-router-dom";
import MySpinner from "../components/layouts/MySpinner";
import { useTranslation } from "react-i18next";

const Register = () => {
  const { t } = useTranslation();
  const info = [
    { title: t("register.firstName"), field: "firstName", type: "text" },
    { title: t("register.lastName"), field: "lastName", type: "text" },
    { title: t("register.email"), field: "email", type: "email" },
    { title: t("register.password"), field: "password", type: "password" },
    { title: t("register.confirm"), field: "confirm", type: "password" },
  ];

  const [user, setUser] = useState({});
  const [faculties, setFaculties] = useState([]);
  const [classes, setClasses] = useState([]);
  const [msg, setMsg] = useState(null);
  const [loading, setLoading] = useState(false);
  const [emailError, setEmailError] = useState(null);
  const emailTimeoutRef = useRef(null);

  const avatar = useRef();
  const nav = useNavigate();

  const setState = (value, field) => {
    setUser((prev) => ({ ...prev, [field]: value }));

    // Nếu đang nhập email thì debounce kiểm tra
    if (field === "email") {
      if (emailTimeoutRef.current) clearTimeout(emailTimeoutRef.current);

      emailTimeoutRef.current = setTimeout(() => {
        checkEmailExists(value);
      }, 500);
    }
  };

  const register = async (e) => {
    e.preventDefault();

    if (emailError) {
      setMsg(emailError);
      return;
    }

    if (user.password !== user.confirm) {
      setMsg(t("register.passwordMismatch"));
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
      setMsg(t("register.errorOccurred"));
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

  const checkEmailExists = async (email) => {
    if (!email) {
      setEmailError(null);
      return;
    }

    try {
      const res = await Apis.get(endpoints["checkEmail"], {
        params: { email },
      });

      // API trả về 200 nhưng có thể là "Email hợp lệ" hoặc "Email chưa được đăng ký"
      if (res.data === "Email đã được sử dụng để đăng ký tài khoản") {
        setEmailError("Email đã được sử dụng để đăng ký tài khoản");
      } else {
        setEmailError(null); // Email hợp lệ hoặc chưa tồn tại
      }
    } catch (ex) {
      // Nếu lỗi 409 (email tồn tại và đã có user)
      if (ex.response && ex.response.status === 409) {
        setEmailError("Email đã được sử dụng để đăng ký tài khoản");
      } else {
        setEmailError("Lỗi kiểm tra email");
      }
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
        <h1 className="text-3xl font-bold text-blue-700">
          {t("register.systemTitle")}
        </h1>
        <h1 className="text-3xl font-bold text-blue-700 mb-2">
          {t("register.systemSubtitle")}
        </h1>
        <p className="text-sm text-gray-500">{t("register.registerPrompt")}</p>
      </div>

      {msg && <Alert variant="danger">{msg}</Alert>}

      <Form onSubmit={register}>
        {info.map((i) => (
          <div key={i.field} className="mb-2">
            <Form.Control
              className="mt-3 p-2 rounded-xl border border-gray-300 focus:ring-2 focus:ring-blue-400"
              value={user[i.field] || ""}
              onChange={(e) => setState(e.target.value, i.field)}
              type={i.type}
              placeholder={i.title}
              required
              isInvalid={i.field === "email" && !!emailError}
            />
            {i.field === "email" && emailError && (
              <Form.Control.Feedback type="invalid">
                {emailError}
              </Form.Control.Feedback>
            )}
          </div>
        ))}
        <Form.Control.Feedback type="invalid">{emailError}</Form.Control.Feedback>

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
          <option value="">{t("register.selectFaculty")}</option>
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
          <option value="">{t("register.selectClass")}</option>
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
          placeholder={t("register.avatar")}
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
            {t("register.submitButton")}
          </Button>
        )}
      </Form>
    </div>
  );
};

export default Register;
