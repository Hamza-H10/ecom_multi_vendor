import axios from 'axios';

export const API_URL = "http://localhost:5454";
// export const DEPLOYED_URL = "https://zosh-bazzar-backend.onrender.com"
export const DEPLOYED_URL = "https://ecommultivendor-production.up.railway.app";
// change api

// Determine the base URL based on the environment
const baseURL = process.env.NODE_ENV === 'production' ? DEPLOYED_URL : API_URL;


export const api = axios.create({
  // baseURL: API_URL, 
  baseURL: baseURL,
  withCredentials: false,
  headers: {
    'Content-Type': 'application/json',
  },
});