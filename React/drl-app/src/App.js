import { BrowserRouter, Routes, Route } from "react-router-dom";
import Header from "./components/layouts/Header";
import Footer from "./components/layouts/Footer";
import Home from "./pages/Home";
import Register from "./pages/Register";
import Login from "./pages/Login";
import ActivityDetail from "./pages/ActivityDetails";
import AddActivity from "./components/Staffs/AddActivity";
import ReportList from "./components/Staffs/ReportList";
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
import Chat from "./pages/Chat";
import StatisticsPage from "./pages/StatisticsPage";
import TrainingPointsPage from "./pages/TrainingPointsPage";
import SettingsPage from "./pages/SettingPage";



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
              <Route path="/settings" element={<SettingsPage />} />
              {/* Staff */}
              <Route path="/addactivity" element={<AddActivity />} />
              <Route path="/reportlist" element={<ReportList />} />
              <Route path="/viewachievement" element={<ViewAchievement />} />
              <Route path="/statistics" element={<StatisticsPage />} />
              <Route path="/training-points" element={<TrainingPointsPage />} />
              {/* Student */}
              <Route path="/activityparticipated" element={<ActivityParticipated />} />
              <Route path="/missingreport" element={<MissingReport />} />
              <Route path="/registeredactivity" element={<RegisteredActivity />} />
              <Route path="/viewpoint" element={<ViewPoint />} />

              <Route path="/chat" element={<Chat />} />
            </Routes>
          </Container>

          <Footer />
        </BrowserRouter>
      </MyDispatcherContext.Provider>
    </MyUserContext.Provider>
  )
}

export default App;
