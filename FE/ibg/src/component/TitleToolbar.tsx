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

interface Title {
  title: string;
}

export default function TitleToolbar({ title }: Title) {
  return (
    <StyledToolBar sx={{ mt: { xs: 7, sm: 8, md: 8 } }}>
      <Container maxWidth="xl">
        <Toolbar disableGutters sx={{ justifyContent: "space-between" }}>
          <Typography
            sx={{
              fontSize: { xs: 20, md: 36 },
              fontWeight: "bold",
              mb: 1,
              ml: { md: 15 },
              mr: { xs: 0, md: 0 },
              minWidth: 65,
              cursor: "pointer",
              display: { xs: "block", md: "block" },
            }}
            color={"white"}
          >
            {title}
          </Typography>
        </Toolbar>
      </Container>
    </StyledToolBar>
  );
}
