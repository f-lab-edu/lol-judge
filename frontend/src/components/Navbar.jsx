import React, { useContext } from "react";
import { Link } from "react-router-dom";
import Logo from "./Logo";
import { LoginContext } from "../context/LoginContext";
import { Button, Typography } from "@mui/material";
import { defaultLoginState } from "../utils/defaultData";
import axios from "axios";
import { convertUrl } from "../utils/urlUtil";

export default function Navbar() {
  const { loginState, setLoginState } = useContext(LoginContext);

  const logout = (event) => {
    event.preventDefault();
    const url = convertUrl("/logout");
    axios.get(url).then(() => setLoginState(defaultLoginState));
  };

  return (
    <header className="flex justify-between border-b border-gray-300 p-2">
      <Link to="/">
        <Logo image="/LOL_Icon.png" text="롤문철 닷컴"></Logo>
      </Link>
      <nav className="flex items-center gap-6 text-2xl">
        <Link to="/ranking">랭킹</Link>
        <Link to="/elections">재판</Link>
        {loginState.lolLoginId === "" ? (
          <Link to="/login">
            <Button variant="contained">로그인</Button>
          </Link>
        ) : (
          <>
            <Typography>{`${loginState.lolLoginId}님`}</Typography>
            <Button variant="contained" onClick={logout}>
              로그아웃
            </Button>
          </>
        )}
      </nav>
    </header>
  );
}
