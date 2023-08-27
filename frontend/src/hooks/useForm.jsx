import axios from "axios";

export default function useForm({ url, data }) {
    const apiUrl = convertUrl(url);
    const handleSubmit = async (event) => {
    event.preventDefault();
    axios
      .post(apiUrl, data)
      .catch((e) => {
        console.log(e);
      })
      .then((res) => {
        if (res) {
            console.log(res.json())
        }
    });
  };
  return handleSubmit;
}

function convertUrl(str) {
    const schme = "http://";
    const host = schme + process.env.REACT_APP_API_URL;
    if (str.startsWith("/")) {
        return host + str;
    } else {
        return host + "/" + str;
    }
}