import axios from "axios";

const BASE_URL = 'http://localhost:8080/drlapp/api';

export const endpoints = {
    'activities': '/activities',
    'register': '/users',
    'login': '/login',
    'current-user': '/secure/profile'

}

export default axios.create({
    baseURL: BASE_URL
});