import React from "react";
import ReactDOM from "react-dom/client";
import { RouterProvider, createBrowserRouter } from "react-router-dom";
import App from "./App";
import Home from "./pages/Home";
import ElectionList from "./pages/ElectionList";
import ElectionDetail from "./pages/ElectionDetail";
import Error from "./pages/Error";
import ScoreRanking from "./pages/ScoreRanking";
import SignUp from "./pages/SignUp";
import Login from "./pages/Login";
import "./index.css";
import ElectionRegister from "./pages/ElectionRegister";
import ElectionEdit from "./pages/ElectionEdit";

const router = createBrowserRouter([
  {
    path: "/",
    element: <App />,
    errorElement: <Error />,
    children: [
      { index: true, element: <Home /> },
      { path: "/elections", element: <ElectionList /> },
      { path: "/elections/:electionId", element: <ElectionDetail /> },
      { path: "/elections/register", element: <ElectionRegister /> },
      { path: "/elections/edit/:electionId", element: <ElectionEdit /> },
      { path: "/ranking", element: <ScoreRanking /> },
      { path: "/signUp", element: <SignUp /> },
      { path: "/login", element: <Login /> },
    ],
  },
]);

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>
);
