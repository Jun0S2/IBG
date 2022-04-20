import BoardCardMain from "../../../component/BoardCardMain";
import AliceCarousel from "react-alice-carousel";
import "react-alice-carousel/lib/alice-carousel.css";
import { IGame } from "../../../types/IGame";
import { alpha, Box, styled, Typography } from "@mui/material";
// import HelpOutlineIcon from "@mui/icons-material/HelpOutline";
import NavigateBeforeIcon from "@mui/icons-material/NavigateBefore";
import NavigateNextIcon from "@mui/icons-material/NavigateNext";

const ArrowBox = styled(Box)(({ theme }) => ({
  width: 40,
  display: "none",
  position: "absolute",
  top: "50%",
  transform: "translateY(-60%)",
  backgroundColor: alpha(theme.palette.grey[500], 0.7),
  cursor: "pointer",
  borderRadius: 5,
  "&:hover": {
    backgroundColor: theme.palette.primary.main,
  },
  [theme.breakpoints.up("sm")]: {
    display: "flex",
    justifyContent: "center",
  },
}));

export default function ThemeList(props: { title: string; gameList: IGame[] }) {
  const cards = props.gameList.map((game) => (
    <BoardCardMain key={game.gameNo} game={game} marginX={0.5}></BoardCardMain>
  ));

  return (
    <>
      <Typography
        sx={{
          fontSize: { xs: 20, md: 24 },
          fontWeight: "bold",
          mt: 4,
          mb: 1,
        }}
      >
        {props.title}
        {/* <HelpOutlineIcon sx={{ verticalAlign: "-0.2rem", ml: 1 }} /> */}
      </Typography>
      <AliceCarousel
        paddingLeft={15}
        paddingRight={15}
        items={cards}
        disableDotsControls
        controlsStrategy="responsive"
        responsive={{
          0: { items: 1.5 },
          400: { items: 2 },
          550: { items: 3 },
          700: { items: 4 },
          900: { items: 5 },
        }}
        renderPrevButton={() => (
          <ArrowBox
            sx={{
              left: "0",
            }}
          >
            <NavigateBeforeIcon sx={{ color: "white", fontSize: 45 }} />
          </ArrowBox>
        )}
        renderNextButton={() => (
          <ArrowBox
            sx={{
              right: "0",
            }}
          >
            <NavigateNextIcon
              sx={{
                color: "white",
                fontSize: 45,
              }}
            />
          </ArrowBox>
        )}
      />
    </>
  );
}
