import React from "react";
import ReactDOM from "react-dom/client";
import { RouterProvider, createBrowserRouter } from "react-router-dom";
import App from "./App";
import "./index.css";
import ElectionListPage from "./pages/ElectionListPage";
import ElectionDetailPage from "./pages/ElectionDetailPage";
import ElectionRegisterPage from "./pages/ElectionRegisterPage";
import ScoreRankingPage from "./pages/ScoreRankingPage";
import SignUpPage from "./pages/SignUpPage";
import StatisticsPage from "./pages/StatisticsPage";
import LoginPage from "./pages/LoginPage";
import ErrorPage from "./pages/ErrorPage";

const router = createBrowserRouter([
  {
    path: "/",
    element: <App />,
    errorElement: <ErrorPage />,
    children: [
      { index: true, element: <ElectionListPage /> },
      { path: "/elections/:electionId", element: <ElectionDetailPage /> },
      { path: "/elections/register", element: <ElectionRegisterPage /> },
      { path: "/ranking", element: <ScoreRankingPage /> },
      { path: "/signUp", element: <SignUpPage /> },
      { path: "/statistics", element: <StatisticsPage /> },
      { path: "/login", element: <LoginPage /> },
    ],
  },
]);

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>
);
