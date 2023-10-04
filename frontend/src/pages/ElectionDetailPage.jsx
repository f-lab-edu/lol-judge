import axios from "axios";
import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { convertUrl } from "../utils/urlUtil";
import {
  Box,
  Button,
  Card,
  CardActions,
  CardContent,
  CardHeader,
  Container,
  Grid,
  Typography,
} from "@mui/material";
import YouTube from "react-youtube";

export default function ElectionDetailPage() {
  const [electionDetail, setElectionDetail] = useState({});
  const param = useParams();
  const url = convertUrl(`/elections/${param.electionId}`);

  useEffect(() => {
    axios
      .get(url)
      .catch((e) => console.error(e))
      .then((res) => res?.data)
      .then((value) => value?.data)
      .then((e) => {
        setElectionDetail(e);
        console.log(e);
      });
  }, param);

  const vote = (candidateId) => {
    const url = convertUrl(`/vote/${candidateId}`);
    axios
      .get(url, { withCredentials: true })
      .catch((e) => {
        const message = e?.response?.data?.message;
        if (message === "JudgePoint not enough") {
          alert("포인트가 부족합니다");
        } else if (message === "Already voted") {
          alert("이미 투표에 참여하셨습니다");
        } else if (message === "Access is Denied") {
          alert("로그인이 필요한 서비스입니다");
        }
      })
      .then((res) => res?.status)
      .then((result) => {
        if (result === 200) {
          alert("투표 참여 완료!");
        }
      });
  };

  return (
    <Container component="main" maxWidth="md">
      <Typography component="h1" variant="h4" align="center">
        {electionDetail.title}
      </Typography>
      <Typography>투표 종료 시간: {electionDetail.endedAt}</Typography>
      <YouTube videoId={electionDetail.youtubeUrl} />
      {electionDetail?.candidateDetails?.map((c) => (
        <Grid item key={c.candidateId} xs={12} md={4}>
          <Card>
            <CardHeader
              title={c.opinion?.champion}
              titleTypographyProps={{ align: "center" }}
              subheaderTypographyProps={{
                align: "center",
              }}
              sx={{
                backgroundColor: (theme) =>
                  theme.palette.mode === "light"
                    ? theme.palette.grey[200]
                    : theme.palette.grey[700],
              }}
            />
            <CardContent>
              <Box
                sx={{
                  display: "flex",
                  justifyContent: "center",
                  alignItems: "baseline",
                  mb: 2,
                }}
              >
                <Typography variant="h6" color="text.secondary">
                  {c?.opinion?.description}
                </Typography>
              </Box>
            </CardContent>
            <CardActions>
              <Button fullWidth onClick={(e) => vote(c?.candidateId, e)}>
                투표
              </Button>
            </CardActions>
          </Card>
        </Grid>
      ))}
    </Container>
  );
}
