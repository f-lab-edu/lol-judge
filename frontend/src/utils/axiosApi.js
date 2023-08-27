import axios from "axios";

export const post = async ({ url, data }) => {
    if (url === undefined) {
      return new Promise((resolve, reject) => {
        reject(new Error("api url is undefined"));
      });
    }
    const apiUrl = convertUrl(url);
    return axios.post(apiUrl, data);
};

function convertUrl(str) {
    const schme = "http://";
    const host = schme + process.env.REACT_APP_API_URL;
    if (str.startsWith("/")) {
        return host + str;
    } else {
        return host + "/" + str;
    }
}