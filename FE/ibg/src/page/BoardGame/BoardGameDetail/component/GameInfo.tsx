import { RootStateOrAny, useSelector } from "react-redux";
import StarRating from "../../../../component/StarRating";
import LikeButton from "../../../../component/LikeButton";

import { IGameDetail } from "../../../../types/IGame";

import { styled } from "@mui/material/styles";
import {
  Box,
  Divider,
  Grid,
  ImageListItem,
  SvgIcon,
  Typography,
} from "@mui/material";
import PersonIcon from "@mui/icons-material/Person";
import AccessTimeIcon from "@mui/icons-material/AccessTime";
import EqualizerIcon from "@mui/icons-material/Equalizer";
import CategoryIcon from "@mui/icons-material/Category";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import ExpandLessIcon from "@mui/icons-material/ExpandLess";
import { useMemo, useRef, useState } from "react";

// 게임 이미지 영역
const ImgWrapper = styled(ImageListItem)(() => ({
  width: "100%",
  objectFit: "contain",
}));

// 게임 정보 영역
const InfoWrapper = styled(Box)(({ theme }) => ({
  position: "relative",
  display: "flex",
  flexDirection: "column",
  padding: theme.spacing(0, 1),
  [theme.breakpoints.up("sm")]: {
    padding: theme.spacing(0, 3),
  },
}));

// 게임 정보 텍스트 스타일
const InfoText = styled(Typography)(({ theme }) => ({
  display: "flex",
  alignItems: "center",
  fontSize: 14,
  [theme.breakpoints.up("md")]: {
    fontSize: 17,
  },
}));

// 게임 정보 각 항목 앞의 아이콘 스타일
const InfoIcon = styled(SvgIcon)(({ theme }) => ({
  marginRight: theme.spacing(1),
  color: theme.palette.grey[600],
  fontSize: 19,
  [theme.breakpoints.up("sm")]: {
    fontSize: 21,
  },
}));

// 게임 평균 평점 스타일
const AvgRate = styled("div")(({ theme }) => ({
  position: "absolute",
  top: theme.spacing(2),
  right: theme.spacing(2),
  fontSize: 22,
  padding: theme.spacing(2),
  fontWeight: 600,
  borderRadius: "50%",
  boxShadow: "rgba(252, 181, 0, 0.24) 0px 3px 8px",
  [theme.breakpoints.up("md")]: {
    fontSize: 30,
    padding: theme.spacing(3),
  },
}));

// 별점 및 관심 버튼 영역
const ScoreAndLike = styled("div")(({ theme }) => ({
  display: "flex",
  alignItems: "center",
  marginTop: theme.spacing(3),
}));

// 별점 및 관심 버튼 가운데 정렬을 위한 div
const AlignDiv = styled("div")(() => ({
  textAlign: "center",
}));

export default function GameInfo(props: { game: IGameDetail }) {
  const user = useSelector((state: RootStateOrAny) => state.user);
  const [isShowMore, setIsShowMore] = useState(false);
  const textLimit = useRef(330);

  const gameDesc = useMemo(() => {
    const shortDesc = props.game.gameKorDesc.slice(0, textLimit.current);

    if (props.game.gameKorDesc.length > textLimit.current) {
      if (isShowMore) return props.game.gameKorDesc;
      return shortDesc;
    }
    return props.game.gameKorDesc;
  }, [isShowMore, props.game.gameKorDesc]);

  return (
    <Grid container spacing={2} sx={{ my: { xs: 0, md: 4 } }}>
      <Grid item xs={12} sm={4}>
        <ImgWrapper>
          <img src={props.game.gameImg} alt={props.game.gameName} />
        </ImgWrapper>
      </Grid>
      <Grid item xs={12} sm={8}>
        <InfoWrapper>
          <Typography sx={{ fontSize: { xs: 18, md: 25 }, fontWeight: "bold" }}>
            {props.game.gameKorName}
            <Typography component="span">({props.game.gameYear})</Typography>
          </Typography>
          <Typography
            sx={{ fontSize: { xs: 13, md: 16 }, color: "gray", mb: 1.5 }}
          >
            {props.game.gameName}
          </Typography>
          <InfoText>
            <InfoIcon>
              <PersonIcon />
            </InfoIcon>
            게임인원: {props.game.gameMinPlayer}~{props.game.gameMaxPlayer}명
          </InfoText>
          <InfoText>
            <InfoIcon>
              <AccessTimeIcon />
            </InfoIcon>
            플레이시간: {props.game.gameMinTime}~{props.game.gameMaxTime}분
          </InfoText>
          <InfoText>
            <InfoIcon>
              <EqualizerIcon />
            </InfoIcon>
            난이도: {props.game.gameWeight} / 5
          </InfoText>
          <InfoText>
            <InfoIcon>
              <CategoryIcon />
            </InfoIcon>
            카테고리: {props.game.gameCategory}
          </InfoText>

          <InfoText sx={{ mt: 1.5 }}>{gameDesc}</InfoText>
          <Box
            sx={{
              mt: 0.5,
              display: "flex",
              justifyContent: "center",
              alignItems: "center",
              color: "gray",
              cursor: "pointer",
            }}
            onClick={() => setIsShowMore(!isShowMore)}
          >
            {props.game.gameKorDesc.length > textLimit.current &&
              (isShowMore ? (
                <>
                  <ExpandLessIcon />
                  닫기
                </>
              ) : (
                <>
                  <ExpandMoreIcon />
                  더보기
                </>
              ))}
          </Box>

          <AvgRate>
            {(Math.floor(props.game.gameTotalScore * 100) / 100).toFixed(2)}
            {/* <Typography component="span"> 점</Typography> */}
          </AvgRate>
          <ScoreAndLike>
            <AlignDiv>
              <Typography sx={{ fontSize: { xs: 13, md: 16 } }}>
                내 점수는요
              </Typography>
              <StarRating
                initStarRate={props.game.myScore}
                size={35}
                gameNo={props.game.gameNo}
                userNo={user.userNo}
              />
            </AlignDiv>
            <Divider
              orientation="vertical"
              variant="middle"
              flexItem
              sx={{ mx: { xs: 1.5, md: 3 } }}
            />
            <AlignDiv>
              <Typography sx={{ fontSize: { xs: 13, md: 16 } }}>
                관심있어요
              </Typography>
              <LikeButton
                initLike={props.game.like}
                size={30}
                gameNo={props.game.gameNo}
                userNo={user.userNo}
              />
            </AlignDiv>
          </ScoreAndLike>
        </InfoWrapper>
      </Grid>
    </Grid>
  );
}
