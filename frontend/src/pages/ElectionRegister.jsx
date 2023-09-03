import {
  Button,
  Container,
  Paper,
  Typography,
} from "@mui/material";
import React from "react";
import ElectionStep from "../components/ElectionStep";
import { FormProvider, useForm } from "react-hook-form";
import { defaultElectionFormData } from "../utils/defaultData";
import YoutubeLinkInputBox from "../components/YoutubeLinkInputBox";
import ChampionSelectBox from "../components/ChampionSelectBox";
import OpinionBox from "../components/OpinionBox";

export default function ElectionRegister() {
  const electionForm = useForm({
    mode: "onChange",
    defaultValues: defaultElectionFormData,
  });
  const onSubmit = () => {};

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
            <YoutubeLinkInputBox />
            <ChampionSelectBox />
            <OpinionBox maxLength={500} />
          </form>
        </FormProvider>
        <Button
          type="submit"
          fullWidth
          variant="contained"
          sx={{ mt: 3, mb: 2 }}
          onClick={electionForm.handleSubmit(onSubmit)}
        >
          신청하기
        </Button>
      </Paper>
    </Container>
  );
}
