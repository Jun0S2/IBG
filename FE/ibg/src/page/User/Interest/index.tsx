import { useEffect, useState } from "react";
import BoardCardMain from "../../../component/BoardCardMain";
import { Box, Container, Grid, Typography } from "@mui/material";
import { getLikedList } from "../../../api/user";
import { RootStateOrAny, useSelector } from "react-redux";
import TitleToolbar from "../../../component/TitleToolbar";

export interface Game {
  gameNo: number;
  gameImg: string;
  gameName: string;
  gameMinPlayer: number;
  gameMaxPlayer: number;
  gameCategory: string;
  gameTotalScore: number;
  like: boolean;
  gameKorName: string;
}

// 테마별 게임리스트: sm(600) 이상(pc)에서는 버튼으로, 이하(모바일)에서는 스크롤로 동작
export default function MyGames() {
  const [gameList, setGameList] = useState<Game[]>([]);
  const [userno] = useState(
    useSelector((state: RootStateOrAny) => state.user.userNo)
  );
  //  const logoImg = require("../../../assets/logo_tears.png");
  useEffect(() => {
    // API 연결(게임리스트 불러오기)
    const init = async () => {
      let data = await getLikedList(userno);
      if (data !== null) setGameList(data); //gameImg, gameName, gameNo를 준다
    };
    init();
  });

  return (
    <>
      {/* 타이틀 */}
      <TitleToolbar title="관심목록" />
      <Container style={{ marginTop: 20, padding: 10 }}>
        {/* 관심 목록 리스트 */}
        <Grid container spacing={2}>
          {gameList.length !== 0 ? (
            gameList.map((game) => (
              <BoardCardMain key={game.gameNo} game={game}></BoardCardMain>
            ))
          ) : (
            <Grid
              container
              spacing={0}
              justifyContent="center"
              direction="column"
              alignItems="center"
              style={{ minHeight: "70vh" }}
            >
              <Grid item xs={3}>
                <Typography
                  sx={{
                    fontSize: { xs: 18, md: 24 },
                    fontWeight: "bold",
                    mb: 1,
                    minWidth: 65,
                    display: { xs: "block", md: "block" },
                  }}
                >
                  아직 관심 등록된 게임이 없어요
                </Typography>
              </Grid>
            </Grid>
          )}
        </Grid>
        <Box sx={{ mb: { md: 5 } }} />
      </Container>
    </>
  );
}
