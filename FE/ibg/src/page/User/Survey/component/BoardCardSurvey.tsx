import { Game } from "../index";
import { styled } from "@mui/material/styles";
import StarRating from "../../../../component/StarRating";
import { RootStateOrAny, useSelector } from "react-redux";
import {
  Card,
  CardContent,
  CardMedia,
  CardActionArea,
  Grid,
  Box,
} from "@mui/material";
const StyledCard = styled(Card)(() => ({
  position: "relative",

  "@keyframes circlemove": {
    "0%,100%": { transform: "translate(-1%,-1%)" },
    "50%": {
      transform: "translate(-1%,-2%)",
      boxShadow: "rgba(0, 0, 0, 0.35) 0px 5px 15px",
    },
  },
}));

const ImgWrapper = styled("div")(() => ({
  position: "relative",
  width: "100%",
  height: 0,
  overflow: "hidden",
  paddingBottom: "100%",
}));

const GameTitle = styled("div")(({ theme }) => ({
  fontSize: "1.1rem",
  fontWeight: 600,
  whiteSpace: "nowrap",
  textOverflow: "ellipsis",
  overflow: "hidden",
  marginBottom: theme.spacing(1),
}));

interface ICount {
  parentCallback: (ratedGameNo: number, score: number) => void;
  game: Game;
}

export default function BoardCard({ game, parentCallback }: ICount) {
  const setStarRatings = (score: number) => {
    //차라리 score가 0인것만 보내서  부모에서 SET으로 받고
    parentCallback(game.gameNo, score);
  };
  const userno = useSelector((state: RootStateOrAny) => state.user.userNo);

  return (
    <Grid item xs={12} sm={4} md={3} lg={2.3}>
      <StyledCard variant="outlined">
        <CardActionArea>
          <ImgWrapper>
            <CardMedia
              sx={{
                position: "absolute",
                width: "100%",
                height: "100%",
                objectFit: "contain",
              }}
              component="img"
              image={game.gameImg}
              alt={game.gameName}
            />
          </ImgWrapper>
          <CardContent>
            <Grid container justifyContent="center">
              <GameTitle>{game.gameKorName}</GameTitle>
              <Box width="100%" />
              <StarRating
                initStarRate={0}
                gameNo={game.gameNo}
                userNo={userno}
                size={35}
                parentCallback={setStarRatings}
              />
            </Grid>
          </CardContent>
        </CardActionArea>
      </StyledCard>
    </Grid>
  );
}
