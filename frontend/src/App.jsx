import React from "react";
import { Outlet } from "react-router-dom";
import Navbar from "./components/Navbar";
import { LoginProvider } from "./context/LoginContext";
import ChampionListProvider from "./context/ChampionListContext";

export default function App() {
  return (
    <div>
      <LoginProvider>
        <ChampionListProvider>
          <Navbar />
          <Outlet />
        </ChampionListProvider>
      </LoginProvider>
    </div>
  );
}
