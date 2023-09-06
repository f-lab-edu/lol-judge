import * as React from "react";
import Button from "@mui/material/Button";
import Container from "@mui/material/Container";
import LinkBox from "../components/LinkBox";
import ProfileBox from "../components/ProfileBox";
import { FormProvider, useForm } from "react-hook-form";
import { defaultSignUpFormData } from "../utils/defaultData";
import { useNavigate } from "react-router-dom";
import { Typography } from "@mui/material";
import axios from "axios";
import { convertUrl } from "../utils/urlUtil";

export default function SignUp() {
  const navigate = useNavigate();

  const signUpForm = useForm({
    mode: "onChange",
    defaultValues: defaultSignUpFormData,
  });

  const signUp = (data) => {
    const url = convertUrl("/signUp");
    axios
      .post(url, data)
      .catch((e) => {
        signUpForm.setError("root.serverError", { type: "400" });
        if (e.response?.data?.message === "Member Already Exists") {
          alert("이미 가입한 회원입니다");
          return;
        }
        console.log(e);
        alert("네트워크 오류가 발생하였습니다");
      })
      .then((res) => {
        if (res?.data.status === "success") {
          alert("회원가입 완료");
          navigate("/login");
        }
      });
  };

  const onSubmit = (data) => {
    signUp(data);
  };

  const onError = (e) => {
    alert("모든 입력란를 채워주세요");
  }

  return (
    <Container component="main" maxWidth="xs" className="mt-10 pt-10">
      <Typography component="h1" align="center" variant="h5" className="p-3">
        회원가입
      </Typography>
      <FormProvider {...signUpForm}>
        <form>
          <ProfileBox />
        </form>
      </FormProvider>
      <Button
        type="submit"
        fullWidth
        variant="contained"
        sx={{ mt: 3, mb: 2 }}
        onClick={signUpForm.handleSubmit(onSubmit, onError)}
      >
        가입하기
      </Button>
      <LinkBox to="/login"> 회원이신가요? 로그인 </LinkBox>
    </Container>
  );
}
