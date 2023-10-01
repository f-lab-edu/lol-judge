export const convertUrl = (url) => {
  const schme = "http://";
  const host = schme + process.env.REACT_APP_API_URL;
  if (url?.startsWith("/")) {
    return host + url;
  } else {
    return host + "/" + url;
  }
};
