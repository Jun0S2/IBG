import { Box, styled, SxProps } from "@mui/material";

export default function LegoSpinner(props: { sx?: SxProps }) {
  const spinnerImg = require("../assets/spinner.png");

  const SpriteImage = styled(Box)(() => ({
    width: "150px",
    height: "114.5px",
    background: `url(${spinnerImg}) no-repeat 0 0 / auto 114.5px`,
    animation: "play 1.5s steps(14) infinite",
    "@keyframes play": {
      "0%": {
        backgroundPosition: "0 0",
      },
      "100%": {
        backgroundPosition: "-2100px 0",
      },
    },
  }));

  return <SpriteImage sx={props.sx} />;
}
