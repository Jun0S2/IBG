import React, { useEffect, useState } from "react";
//컴포넌트
import BoardCardMain from "./component/BoardCardSurvey";
import { Button, Box, Grid, Container, Typography } from "@mui/material";
import WelcomeStepper from "../component/WelcomeStepper";
import ConfirmDialog from "./component/ConfirmDialog";
//api 연결
import { initSurvey } from "../../../api/user";
//네비게이션
import { useNavigate } from "react-router-dom";
//스캘래톤로더
import SkeletonLoader from "./component/SkeletonCardSurvey";
// Game 객체
export interface Game {
  gameNo: number;
  gameImg: string;
  gameName: string;
  gameKorName: string;
}
// Rated  객체
export interface IProps {
  ratedGameNo: number;
  score: number;
}

export default function Survey() {
  const [gameList, setGameList] = useState<Game[]>([]);
  const [count, setCount] = useState(0);
  const [ratedGame] = useState<number[]>([]);
  const [width] = useState(window.innerWidth); //width
  const [open, setOpen] = useState(false); //modal
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  // function to handle modal open
  const handleOpen = () => {
    setOpen(true);
  };

  // function to handle modal close
  const handleClose = () => {
    setOpen(false);
  };

  //navigate when "Yes" is pressed on dialog OR pressed Complete
  const handleSubmit = () => {
    setOpen(false);
    navigate("/complete");
  };

  useEffect(() => {
    // API 연결(게임리스트 불러오기)
    const init = async () => {
      await initSurvey().then((data) => {
        setGameList(data); //gameImg, gameName, gameNo를 준다
        setLoading(false);
      });
    };
    init();
  }, []);

  // count 업데이트하는 method
  const countHandler = (ratedGameNo: number, score: number) => {
    if (score > 0) {
      if (ratedGame.length === 0) {
        ratedGame.push(ratedGameNo);
        setCount(count + 1);
      } else {
        let found = ratedGame.includes(ratedGameNo) ? true : false;
        if (found) {
        } //do nothing
        else {
          setCount(count + 1);
          ratedGame.push(ratedGameNo);
        }
      }
    } else if (score === 0) {
      setCount(count - 1);
      var index = ratedGame.indexOf(ratedGameNo);
      if (index !== -1) {
        ratedGame.splice(index, 1);
      }
    }
  };

  return (
    <>
      <Grid
        container
        spacing={0}
        direction="column"
        alignItems="center"
        sx={{ mt: { xs: 5, sm: 5, md: 20 } }}
      >
        <Box sx={{ width: width < 600 ? "90%" : "33%" }}>
          <WelcomeStepper value="1" />
        </Box>
      </Grid>

      <Grid
        container
        spacing={0}
        direction="column"
        alignItems="center"
        style={{ minHeight: "100%" }}
      >
        <Container style={{ marginTop: 20, padding: 10 }}>
          <Grid container spacing={2}>
            {loading
              ? gameList.map(() => <SkeletonLoader />)
              : gameList.map((game) => (
                  <BoardCardMain
                    key={game.gameNo}
                    game={game}
                    parentCallback={countHandler}
                  ></BoardCardMain>
                ))}
          </Grid>
          <Box sx={{ mb: 15 }} />
        </Container>
        <Button
          style={{ position: "fixed", bottom: "0px", borderRadius: 0 }}
          type="submit"
          fullWidth
          variant="contained"
          color="primary"
          size="small"
          onClick={count < 10 ? handleOpen : handleSubmit}
        >
          <Grid>
            <Typography variant="subtitle1">완료({count})</Typography>
            <Typography variant="caption">
              10개 이상 평점을 등록 시 개인 맞춤 추천이 가능합니다
            </Typography>
          </Grid>
        </Button>
        <ConfirmDialog
          open={open}
          count={count}
          handleClose={handleClose}
          handleSubmit={handleSubmit}
        />
      </Grid>
    </>
  );
}
