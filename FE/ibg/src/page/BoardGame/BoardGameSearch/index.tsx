import { useEffect, useState } from "react";
import { RootStateOrAny, useSelector } from "react-redux";
import { IGame } from "../../../types/IGame";
import { SearchByName, searchByFilter } from "../../../api/game";
import BoardCardMain from "../../../component/BoardCardMain";
import CustomSelect, { StyledOption } from "./component/CustomSelect";
import GameFilter, { ISearchFilter } from "./component/GameFilter";
import SkelBoardCard from "../../../component/SkelBoardCard";
import { Box, Container, Grid, Typography } from "@mui/material";
import LegoSpinner from "../../../component/LegoSpinner";
import TitleToolbar from "../../../component/TitleToolbar";

export default function BoardGameSearch() {
  const userNo = useSelector((state: RootStateOrAny) => state.user.userNo);
  const [loading, setLoading] = useState(true);
  const [initGameList, setInitGameList] = useState<IGame[]>([]);
  const [gameList, setGameList] = useState<IGame[]>([]);
  const [sortingOpt, setSortingOpt] = useState<string | null>("recomm");

  // 페이지 접속 시 1회 실행
  useEffect(() => {
    const controller = new AbortController();

    SearchByName("", userNo, controller.signal).then((data) => {
      setInitGameList(data);
      setGameList(data);
      setLoading(false);
    });

    return () => {
      controller.abort();
    };
  }, [userNo]);

  // sortingOpt이 변경되면 실행
  useEffect(() => {
    let sortData = [...initGameList];

    switch (sortingOpt) {
      case "recomm":
        setGameList(initGameList);
        break;
      case "starRate":
        sortData.sort((a, b) => b.gameTotalScore - a.gameTotalScore);
        setGameList(sortData);
        break;
      case "name":
        sortData.sort((a, b) => a.gameKorName.localeCompare(b.gameKorName));
        setGameList(sortData);
        break;
    }
  }, [sortingOpt, initGameList]);

  const getSearchResult = (filter: ISearchFilter) => {
    setLoading(true);

    if (userNo) filter.userNo = userNo;

    searchByFilter(filter).then((data) => {
      if (data.code === 200) {
        setInitGameList(data.data);
        setGameList(data.data);
      } else if (data.code === 204) {
        setInitGameList([]);
        setGameList([]);
      }
      setLoading(false);
    });
  };
  return (
    <>
      {/* 제목 */}
      <TitleToolbar title="보드게임" />
      <Container style={{ marginTop: 20, padding: 20 }}>
        {/* 필터링 박스 */}
        <GameFilter searchCallback={getSearchResult} />
        {/* 제목, 정렬 선택 박스*/}
        <Box
          sx={{
            display: "flex",
            justifyContent: "end",
            mt: 5,
            mb: 1,
          }}
        >
          <CustomSelect value={sortingOpt} onChange={setSortingOpt}>
            <StyledOption value="recomm">추천순</StyledOption>
            <StyledOption value="starRate">평점순</StyledOption>
            <StyledOption value="name">이름순</StyledOption>
          </CustomSelect>
        </Box>
        {/* 보드게임 카드 */}
        {loading ? (
          <Box sx={{ position: "relative" }}>
            <Grid container spacing={2}>
              {[1, 1, 1, 1, 1, 1, 1, 1, 1, 1].map((v, i) => (
                <SkelBoardCard key={i} />
              ))}
            </Grid>
            <LegoSpinner
              sx={{
                position: "absolute",
                top: "20%",
                left: "50%",
                transform: "translateX(-50%)",
              }}
            />
          </Box>
        ) : gameList.length > 0 ? (
          <Grid container spacing={2}>
            {gameList.map((game) => (
              <BoardCardMain key={game.gameNo} game={game}></BoardCardMain>
            ))}
          </Grid>
        ) : (
          <Box textAlign="center">
            <Typography
              sx={{ fontSize: { xs: 15, sm: 23 }, fontWeight: 600, my: 20 }}
            >
              앗❕ 조건에 맞는 보드게임이 없어요😧
            </Typography>
          </Box>
        )}
      </Container>
    </>
  );
}
