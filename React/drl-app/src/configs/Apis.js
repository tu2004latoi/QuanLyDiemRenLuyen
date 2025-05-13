import axios from "axios";
import cookie from 'react-cookies';

const BASE_URL = 'http://localhost:8080/drlapp/api';

export const endpoints = {
    'activities': '/activities',
    'register': '/users',
    'login': '/login',
    'current-user': '/secure/profile',
    'faculties': "/faculties",
    'classes': (facultyId) => `/users/faculty/${facultyId}/classes`
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