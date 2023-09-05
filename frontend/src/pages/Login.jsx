import * as React from "react";
import Avatar from "@mui/material/Avatar";
import Button from "@mui/material/Button";
import CssBaseline from "@mui/material/CssBaseline";
import TextField from "@mui/material/TextField";
import FormControlLabel from "@mui/material/FormControlLabel";
import Checkbox from "@mui/material/Checkbox";
import Grid from "@mui/material/Grid";
import Box from "@mui/material/Box";
import LockOutlinedIcon from "@mui/icons-material/LockOutlined";
import Container from "@mui/material/Container";
import LinkBox from "../components/LinkBox";
import {useNavigate} from "react-router-dom";
import axios from "axios";
import {convertUrl} from "../utils/urlUtil";
import {LoginContext} from "../context/LoginContext";

export default function Login() {
    const navigate = useNavigate();
    const {setLoginState} = React.useContext(LoginContext);

    const login = (email, password) => {
        const url = convertUrl("/login");
        axios
            .post(url, {email, password}, {withCredentials: true})
            .catch(() => {
                alert("이메일, 패스워드를 확인하세요");
            })
            .then((res) => res?.data)
            .then((payload) => {
                if (payload?.status === "success") {
                    setLoginState({lolLoginId: payload.data.lolLoginId});
                    navigate("/");
                }
            });
    };

    const handleSubmit = (event) => {
        event.preventDefault();
        const data = new FormData(event.currentTarget);
        const email = data.get("email");
        const password = data.get("password");
        if (!(email && password)) {
            alert("이메일, 패스워드를 입력하세요");
            return;
        }
        login(email, password);
    };

    return (
        <Container component="main" maxWidth="xs">
            <CssBaseline/>
            <Box
                sx={{
                    marginTop: 8,
                    display: "flex",
                    flexDirection: "column",
                    alignItems: "center",
                }}
            >
                <Avatar sx={{m: 1, bgcolor: "secondary.main"}}>
                    <LockOutlinedIcon/>
                </Avatar>
                <Box component="form" onSubmit={handleSubmit} noValidate sx={{mt: 1}}>
                    <TextField
                        margin="normal"
                        required
                        fullWidth
                        id="email"
                        label="이메일 주소"
                        name="email"
                        autoComplete="email"
                        autoFocus
                    />
                    <TextField
                        margin="normal"
                        required
                        fullWidth
                        name="password"
                        label="비밀번호"
                        type="password"
                        id="password"
                        autoComplete="current-password"
                    />
                    <FormControlLabel
                        control={<Checkbox value="remember" color="primary"/>}
                        label="로그인 유지하기"
                    />
                    <Button
                        type="submit"
                        fullWidth
                        variant="contained"
                        sx={{mt: 3, mb: 2}}
                    >
                        로그인
                    </Button>
                    <Grid container>
                        <LinkBox to="#"> 비밀번호 찾기 </LinkBox>
                        <LinkBox to="/signUp"> 회원가입 </LinkBox>
                    </Grid>
                </Box>
            </Box>
        </Container>
    );
}
