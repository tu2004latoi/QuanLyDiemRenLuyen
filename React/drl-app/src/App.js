import { BrowserRouter, Routes, Route } from "react-router-dom";
import Header from "./components/layouts/Header";
import Footer from "./components/layouts/Footer";
import Home from "./components/Home";
import Register from "./components/Register";
import Login from "./components/Login";
import ActivityDetail from "./components/ActivityDetails";
import AddActivity from "./components/Staffs/AddActivity";
import ReportList from "./components/Staffs/ReportList";
import Stats from "./components/Staffs/Stats";
import ViewAchievement from "./components/Staffs/ViewAchievement";
import ActivityParticipated from "./components/Students/ActivityParticipated";
import MissingReport from "./components/Students/MissingReport";
import RegisteredActivity from "./components/Students/RegisteredActivity";
import ViewPoint from "./components/Students/ViewPoint";
import 'bootstrap/dist/css/bootstrap.min.css';
import { Container } from "react-bootstrap";
import { MyDispatcherContext, MyUserContext } from "./configs/MyContexts";
import { useEffect, useReducer } from "react";
import MyUserReducer from "./components/reducers/MyUserReducer";
import cookie from "react-cookies";
import { authApis, endpoints } from "./configs/Apis";



const App = () => {
  const [user, dispatch] = useReducer(MyUserReducer, null);

  useEffect(() => {
    const loadUser = async () => {
      const token = cookie.load("token");
      if (token !== undefined) {
        try {
          let res = await authApis().get(endpoints['current-user']);
          dispatch({
            "type": "login",
            "payload": res.data
          });
        } catch (err) {
          console.error("Lỗi load user từ token:", err);
          cookie.remove("token");
        }
      }
    }

    loadUser();
  }, []);

  return (
    <MyUserContext.Provider value={user}>
      <MyDispatcherContext.Provider value={dispatch}>
        <BrowserRouter>
          <Header />

          <Container>
            <Routes>
              {/* General */}
              <Route path="/" element={<Home />} />
              <Route path="/login" element={<Login />} />
              <Route path="/register" element={<Register />} />
              <Route path="/activitydetails/:activityId" element={<ActivityDetail />} />
              {/* Staff */}
              <Route path="/addactivity" element={<AddActivity />} />
              <Route path="/reportlist" element={<ReportList />} />
              <Route path="/stats" element={<Stats />} />
              <Route path="/viewachievement" element={<ViewAchievement />} />
              {/* Student */}
              <Route path="/activityparticipated" element={<ActivityParticipated />} />
              <Route path="/missingreport" element={<MissingReport />} />
              <Route path="/registeredactivity" element={<RegisteredActivity />} />
              <Route path="/viewpoint" element={<ViewPoint />} />
            </Routes>
          </Container>

          <Footer />
        </BrowserRouter>
      </MyDispatcherContext.Provider>
    </MyUserContext.Provider>
  )
}

export default App;
