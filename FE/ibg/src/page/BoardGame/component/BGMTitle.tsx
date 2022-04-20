// 메뉴 별 타이틀
import { styled } from "@mui/material/styles";
import Toolbar from "@mui/material/Toolbar";
import Container from "@mui/material/Container";
import { Typography } from "@mui/material";

const StyledToolBar = styled(Toolbar)(({ theme }) => ({
  backgroundColor: theme.palette.warning.main,
  margin: "auto",
  width: "100%",
}));

export default function BGMTitle() {
  return (
    <StyledToolBar sx={{ mt: { xs: 7, sm: 8, md: 8 } }}>
      <Container maxWidth="xl">
        <Toolbar disableGutters>
          <Typography
            sx={{
              fontSize: { xs: 24, md: 40 },
              fontWeight: "bold",
              mb: 1,
              ml: { md: 15 },
              display: { xs: "block", md: "block" },
            }}
            color="error"
          >
            B
          </Typography>
          <Typography
            sx={{
              fontSize: { xs: 24, md: 40 },
              fontWeight: "bold",
              mb: 1,
              display: { xs: "block", md: "block" },
            }}
            color="white"
          >
            oard&nbsp;
          </Typography>
          <Typography
            sx={{
              fontSize: { xs: 24, md: 40 },
              fontWeight: "bold",
              mb: 1,

              display: { xs: "block", md: "block" },
            }}
            color="#ffd900"
          >
            G
          </Typography>
          <Typography
            sx={{
              fontSize: { xs: 24, md: 40 },
              fontWeight: "bold",
              mb: 1,

              display: { xs: "block", md: "block" },
            }}
            color="white"
          >
            ame&nbsp;
          </Typography>
          <Typography
            sx={{
              fontSize: { xs: 24, md: 40 },
              fontWeight: "bold",
              mb: 1,

              display: { xs: "block", md: "block" },
            }}
            color="primary"
          >
            M
          </Typography>
          <Typography
            sx={{
              fontSize: { xs: 24, md: 40 },
              fontWeight: "bold",
              mb: 1,

              display: { xs: "block", md: "block" },
              color: "white",
            }}
          >
            arket
          </Typography>
        </Toolbar>
      </Container>
    </StyledToolBar>
  );
}
