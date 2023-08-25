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
import "./index.css";

const router = createBrowserRouter([
  {
    path: "/",
    element: <App />,
    errorElement: <Error />,
    children: [
      { index: true, element: <Home /> },
      { path: "/elections", element: <ElectionList /> },
      { path: "/elections/:electionId", element: <ElectionDetail /> },
      { path: "/ranking", element: <ScoreRanking /> },
      { path: "/signUp", element: <SignUp /> },
    ],
  },
]);

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>
);
