import { Button, Container, Paper, Typography } from "@mui/material";
import React from "react";
import { FormProvider, useForm } from "react-hook-form";
import { defaultElectionFormData } from "../utils/defaultData";
import axios from "axios";
import { convertUrl } from "../utils/urlUtil";
import { useNavigate } from "react-router-dom";
import OpinionBox from "../containers/OpinionBox";
import ElectionInfoBox from "../containers/ElectionInfoBox";

export default function ElectionRegisterPage() {
  const navigate = useNavigate();
  const electionForm = useForm({
    mode: "onChange",
    defaultValues: defaultElectionFormData,
  });

  const onSubmit = (data) => {
    const url = convertUrl("/elections");
    const opinions = data.opinions.map((opinion) => ({
      ...opinion,
      champion: opinion.champion.value,
    }));
    if (opinions.length < 2) {
      alert("최소 2개의 의견을 입력해주세요");
      return;
    }
    const formData = { ...data, opinions };
    axios
      .post(url, formData, { withCredentials: true })
      .catch(() => console.log("network error"))
      .then((res) => res?.data)
      .then((payload) => {
        var message = "신청 실패! 관리자에게 문의해주세요";
        if (payload?.status === "success") {
          message = "신청 완료! 상대방이 의견을 제출하면 재판이 시작됩니다";
          navigate("/");
        }
        alert(message);
      });
  };

  const onError = (e) => {
    console.log(e);
    alert("모든 입력란를 채워주세요");
  };

  return (
    <Container component="main" maxWidth="sm" sx={{ mb: 4 }}>
      <Paper
        variant="outlined"
        sx={{ my: { xs: 3, md: 6 }, p: { xs: 2, md: 3 } }}
      >
        <Typography component="h1" variant="h4" align="center">
          재판 신청
        </Typography>
        <FormProvider {...electionForm}>
          <form>
            <ElectionInfoBox />
            <OpinionBox />
          </form>
        </FormProvider>
        <Button
          type="submit"
          fullWidth
          variant="contained"
          sx={{ mt: 3, mb: 2 }}
          onClick={electionForm.handleSubmit(onSubmit, onError)}
        >
          신청하기
        </Button>
      </Paper>
    </Container>
  );
}
