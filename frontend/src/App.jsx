import React from "react";
import { Outlet } from "react-router-dom";
import Navbar from "./components/Navbar";
import { LoginProvider } from "./context/LoginContext";

export default function App() {
  return (
    <div>
      <LoginProvider>
        <Navbar />
        <Outlet />
      </LoginProvider>
    </div>
  );
}
