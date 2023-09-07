import axios from "axios";
import React, { useContext, useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { Button, Container, Paper, Typography } from "@mui/material";
import ElectionStep from "../components/ElectionStep";
import { FormProvider, useForm } from "react-hook-form";
import YoutubeLinkInputBox from "../components/YoutubeLinkInputBox";
import IntegerInputBox from "../components/IntegerInputBox";
import ChampionSelectBox from "../components/ChampionSelectBox";
import OpinionBox from "../components/OpinionBox";
import { convertUrl } from "../utils/urlUtil";
import { defaultElectionEditData } from "../utils/defaultData";
import LegendBox from "../components/LegendBox";
import { LoginContext } from "../context/LoginContext";
import { ChampionListContext } from "../context/ChampionListContext";

export default function ElectionEdit() {
  const electionEditForm = useForm({
    mode: "onChange",
    defaultValues: defaultElectionEditData,
  });
  const [election, setElection] = useState({});
  const { electionId } = useParams();
  const { loginState } = useContext(LoginContext);
  const { champions } = useContext(ChampionListContext);
  const navigate = useNavigate();

  const isGuest = () => {
    return ![election.hostId, election.participantId].includes(
      loginState.memberId
    );
  };

  const isHost = () => {
    return election.hostId === loginState.memberId;
  };

  useEffect(() => {
    const url = convertUrl(`/elections/${electionId}`);
    axios
      .get(url, { withCredentials: true })
      .catch((e) => console.log(e))
      .then((res) => res?.data)
      .then((payload) => payload?.data)
      .then((data) => {
        const hostChampion = champions.find(
          (c) => c.value === data.hostChampion
        );
        const participantChampion = champions.find(
          (c) => c.value === data.participantChampion
        );
        console.log(data);
        const defaultData = {
          ...data,
          id: electionId,
          hostChampion: hostChampion,
          participantChampion: participantChampion,
        };
        electionEditForm.reset(defaultData);
        setElection(defaultData);
      });
  }, [champions]);

  const onSubmit = (data) => {
    const url = convertUrl("/elections");
    axios
      .put(
        url,
        {
          ...data,
          hostChampion: data.hostChampion.value,
          participantChampion: data.participantChampion.value,
        },
        { withCredentials: true }
      )
      .catch((e) => console.error("네트워크에러"))
      .then(() => {
        console.log('aa');
        alert("재판이 등록되었습니다!");
        navigate("/");
      });
  };

  const onError = (e) => {
    console.log(e);
  };

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
            <LegendBox title="상대 의견">
              <ChampionSelectBox
                name={isHost() ? "participantChampion" : "hostChampion"}
                disabled={true}
                style="p-3"
              />
              <OpinionBox
                name={isHost() ? "participantOpinion" : "hostOpinion"}
                disabled={true}
                maxLength={500}
                style="p-3"
              />
            </LegendBox>
            <LegendBox title="본인 의견">
              <ChampionSelectBox
                name={isHost() ? "hostChampion" : "participantChampion"}
                style="p-3"
              />
              <OpinionBox
                name={isHost() ? "hostOpinion" : "participantOpinion"}
                maxLength={500}
                style="p-3"
              />
            </LegendBox>
          </form>
        </FormProvider>
        <Button
          type="submit"
          fullWidth
          variant="contained"
          sx={{ mt: 3, mb: 2 }}
          onClick={electionEditForm.handleSubmit(onSubmit, onError)}
        >
          등록하기
        </Button>
      </Paper>
    </Container>
  );
}
