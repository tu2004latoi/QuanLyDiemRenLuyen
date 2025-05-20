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
  statistics: "/statistics",
  "classes-by-faculty": (facultyId) => `/users/faculty/${facultyId}/classes`,
  csv: "/export/csv",
  pdf: "/export/pdf",
  trainingPoints: "/training-points",
  createTraining: "/training-points/create",
  myActivities: "/my-activities",
  students: "/users/students",
  studentDetails: (userId) => `/users/students/${userId}`,
  activityRegistratons: "/activity-registrations",
  deleteEvidence: (evidenceId) => `/evidences/${evidenceId}`,
  reportMissing: "/missing-reports/create"
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
