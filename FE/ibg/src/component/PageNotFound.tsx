import { styled, Typography } from "@mui/material";
import { Box } from "@mui/system";

const Wrapper = styled(Box)(() => ({
  textAlign: "center",
  img: {
    width: "100%",
  },
}));

export default function PageNotFound() {
  const PageNotFoundImg = require("../assets/pnf.jpg");
  return (
    <>
      <Wrapper>
        <Typography variant="h6">이보게에는 없는 페이지에요!</Typography>
        <img src={PageNotFoundImg} alt="404" />
      </Wrapper>
    </>
  );
}
