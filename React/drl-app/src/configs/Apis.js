import axios from "axios";
import cookie from 'react-cookies';

const BASE_URL = 'http://localhost:8080/drlapp/api';

export const endpoints = {
    'activities': '/activities',
    'activityDetail': (activityId) => `/activities/${activityId}`,
    'register': '/users',
    'login': '/login',
    'current-user': '/secure/profile',
    'faculties': "/faculties",
    'classes': (facultyId) => `/users/faculty/${facultyId}/classes`,
    'likeCount': (activityId) => `/activities/${activityId}/likes/count`,
    'comments': (activityId) => `/activities/${activityId}/comments`,
    'activityRegister': "/users/activity-registration",
}

export const authApis = () => {
    return axios.create({
        baseURL: BASE_URL,
        headers: {
            'Authorization': `Bearer ${cookie.load('token')}`
        }
    })
}

export default axios.create({
    baseURL: BASE_URL
});