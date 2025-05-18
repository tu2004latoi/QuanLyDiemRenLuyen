import axios from "axios";
import cookie from "react-cookies";

export const BASE_URL = "http://localhost:8080/drlapp/api";

export const endpoints = {
  activities: "/activities",
  activityDetail: (activityId) => `/activities/${activityId}`,
  register: "/users",
  login: "/login",
  "current-user": "/secure/profile",
  faculties: "/faculties",
  classes: (facultyId) => `/users/faculty/${facultyId}/classes`,
  likes: (activityId) => `/activities/${activityId}/likes`,
  comments: (activityId) => `/activities/${activityId}/comments`,
  activityRegister: "/users/activity-registration",
<<<<<<< HEAD
  statistics: "/statistics",
  "classes-by-faculty": (facultyId) => `/users/faculty/${facultyId}/classes`,
  csv: "/export/csv",
  pdf: "/export/pdf",
  trainingPoints: "/training-points",
=======
  myActivities: "/my-activities"
>>>>>>> 540a89c210c888685fd2fa803565c66155fc2020
};

export const authApis = () => {
  const token = cookie.load("token");
  return axios.create({
    baseURL: BASE_URL,
    headers: {
      Authorization: `Bearer ${token}`,
      "Content-Type": "application/json",
    },
  });
};

export default axios.create({
  baseURL: BASE_URL,
});
