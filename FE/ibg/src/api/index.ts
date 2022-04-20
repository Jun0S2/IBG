import axios from "axios";
import { API_BASE_URL } from "../config/index";

function apiInstance() {
  const instance = axios.create({
    baseURL: API_BASE_URL,
    headers: {
      "Content-type": "application/json",
    },
  });
  return instance;
}

function loginApiInstance() {
  const jwtToken = sessionStorage.getItem("accessToken");
  const instance = axios.create({
    baseURL: API_BASE_URL,
    headers: {
      "Content-type": "application/json",
      Authorization: `${jwtToken}`,
    },
  });
  return instance;
}

export { apiInstance, loginApiInstance };
