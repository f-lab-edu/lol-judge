import { Button, Container, Paper, Typography } from "@mui/material";
import React from "react";
import ElectionStep from "../components/ElectionStep";
import { FormProvider, useForm } from "react-hook-form";
import { defaultElectionFormData } from "../utils/defaultData";
import YoutubeLinkInputBox from "../components/YoutubeLinkInputBox";
import ChampionSelectBox from "../components/ChampionSelectBox";
import OpinionBox from "../components/OpinionBox";
import IntegerInputBox from "../components/IntegerInputBox";
import UserSelectBox from "../components/UserSelectBox";
import axios from "axios";
import { convertUrl } from "../utils/urlUtil";
import { useNavigate } from "react-router-dom";

export default function ElectionRegister() {
  const navigate = useNavigate();
  const electionForm = useForm({
    mode: "onChange",
    defaultValues: defaultElectionFormData,
  });

  const onSubmit = (data) => {
    const url = convertUrl("/elections");
    const payload = {
      ...data,
      participantEmail: data.participantEmail.value,
      champion: data.champion.value,
    };
    axios
      .post(url, payload, { withCredentials: true })
      .catch(() => console.log("network error"))
      .then(res => res?.data)
      .then(payload => {
        var message = "신청 실패! 관리자에게 문의해주세요";
        if (payload?.status === "success") {
          message = "신청 완료! 상대방이 의견을 제출하면 재판이 시작됩니다"; 
          navigate("/");
        }
        alert(message);
      });
  };

  const onError = (e) => {
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
        <ElectionStep activeStep={0} />
        <FormProvider {...electionForm}>
          <form>
            <UserSelectBox />
            <YoutubeLinkInputBox />
            <IntegerInputBox
              name="cost"
              text="비용을 입력하세요"
              min={10}
              max={300}
            />
            <IntegerInputBox
              name="progressTime"
              text="투표 진행 시간을 입력하세요(시간)"
              min={1}
              max={72}
            />
            <ChampionSelectBox />
            <OpinionBox name="opinion" maxLength={500} />
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
