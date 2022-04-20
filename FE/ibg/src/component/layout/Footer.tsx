import { styled } from "@mui/material/styles";
import AppBar from "@mui/material/AppBar";
import Container from "@mui/material/Container";
import Toolbar from "@mui/material/Toolbar";
import Typography from "@mui/material/Typography";

const StyledFooter = styled(AppBar)(({ theme }) => ({
  position: "relative",
  transform: "translateY(-100%)",
  padding: theme.spacing(2, 0),
  //  backgroundColor: theme.palette.warning.main,
  backgroundColor: "#f5f5f5",
  boxShadow: "none",
  color: theme.palette.common.black,
  zIndex: 1,
}));

const FooterLink = styled("span")(({ theme }) => ({
  fontSize: 13,
  marginRight: theme.spacing(2),
  cursor: "pointer",
  "&:hover": {
    color: theme.palette.grey[600],
  },
}));

const StyledImage = styled("img")(() => ({
  position: "absolute",
  right: 0,
  bottom: 2,
  width: 50,
}));

export default function Footer() {
  const puzzleImg = require("../../assets/puzzle.png");

  return (
    <StyledFooter sx={{ mt: { xs: 4 } }}>
      <Container maxWidth="xl">
        <Toolbar sx={{ display: "block" }}>
          <Typography sx={{ mb: 2 }}>
            <FooterLink>서비스 이용약관</FooterLink>
            <FooterLink sx={{ fontWeight: "bold" }}>
              개인정보 처리방침
            </FooterLink>
          </Typography>
          <Typography variant="caption" gutterBottom>
            (주)IBG 현준 오범 민주 혜준 미래 하은
          </Typography>
          <Typography variant="caption" component="div">
            사업자등록번호 202-26-13211
          </Typography>
          <Typography variant="caption" gutterBottom>
            © 2022 by SSAFY 6기 대전 1반 1팀, All rights reserved.
          </Typography>
          <StyledImage src={puzzleImg}></StyledImage>
        </Toolbar>
      </Container>
    </StyledFooter>
  );
}
