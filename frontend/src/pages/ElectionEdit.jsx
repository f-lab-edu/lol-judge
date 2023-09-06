import axios from "axios";
import React, { useEffect } from "react";
import { useParams } from "react-router-dom";
import { Button, Container, Paper, Typography } from "@mui/material";
import ElectionStep from "../components/ElectionStep";
import { FormProvider, useForm } from "react-hook-form";
import YoutubeLinkInputBox from "../components/YoutubeLinkInputBox";
import IntegerInputBox from "../components/IntegerInputBox";
import ChampionSelectBox from "../components/ChampionSelectBox";
import OpinionBox from "../components/OpinionBox";
import { convertUrl } from "../utils/urlUtil";
import { defaultElectionEditData } from "../utils/defaultData";

export default function ElectionEdit() {
  const electionEditForm = useForm({
    mode: "onChange",
    defaultValues: defaultElectionEditData,
  });
  const { electionId } = useParams();

  useEffect(() => {
    const url = convertUrl(`/elections/${electionId}`);
    axios
      .get(url, { withCredentials: true })
      .catch(() => alert("접근 불가능한 컨텐츠입니다"))
      .then((res) => res.data)
      .then((payload) => { 
        electionEditForm.reset(payload?.data);
      });
  }, []);

  return (
    <Container component="main" maxWidth="sm" sx={{ mb: 4 }}>
      <Paper
        variant="outlined"
        sx={{ my: { xs: 3, md: 6 }, p: { xs: 2, md: 3 } }}
      >
        <Typography component="h1" variant="h4" align="center">
          재판 내용 변경
        </Typography>
        <ElectionStep activeStep={1} />
        <FormProvider {...electionEditForm}>
          <form>
            <YoutubeLinkInputBox />
            <IntegerInputBox
              name="cost"
              text="비용을 입력하세요"
              min={10}
              max={300}
            />
            <ChampionSelectBox />
            <OpinionBox name="hostOpinion" maxLength={500} />
          </form>
        </FormProvider>
        <Button
          type="submit"
          fullWidth
          variant="contained"
          sx={{ mt: 3, mb: 2 }}
        >
          변경하기
        </Button>
      </Paper>
    </Container>
  );
}
