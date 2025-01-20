import axios from 'axios';

export const API_URL = "http://localhost:5454";
// export const DEPLOYED_URL = "https://zosh-bazzar-backend.onrender.com"
export const DEPLOYED_URL = "https://ecommultivendor-production.up.railway.app";
// change api

export const api = axios.create({
  // baseURL: API_URL, 
  baseURL: DEPLOYED_URL,
  withCredentials: true,
  headers: {
    'Content-Type': 'application/json',
  },
});