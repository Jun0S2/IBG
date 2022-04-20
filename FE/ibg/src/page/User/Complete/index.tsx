//회원가입 완료 페이지(static)
import React, { useState } from "react";
import { Grid, Box, Button } from "@mui/material/";
import WelcomeStepper from "../component/WelcomeStepper";
import Congrats from "./component/congrats";
import { Link } from "react-router-dom";

export default function SignUp() {
  const [width] = useState(window.innerWidth);
  const pic = require("./component/빙글빙글 이보게.gif");
  return (
    <>
      <Grid
        container
        spacing={0}
        direction="column"
        alignItems="center"
        sx={{ mt: { xs: 5, sm: 5, md: 20 } }}
      >
        <Box sx={{ width: width < 600 ? "100%" : "33%" }}>
          <WelcomeStepper value="2" />
        </Box>
      </Grid>
      <Grid
        style={{ minHeight: "70vh" }}
        container
        spacing={0}
        direction="column"
        alignItems="center"
        justifyContent="center"
      >
        <Grid item xs={2} sx={{ flexGrow: 1, m: { xs: 4, md: 4 } }}>
          <Congrats />
        </Grid>
        <Box
          component="img"
          display="block"
          sx={{
            height: "300",
            width: "400",
            maxHeight: { xs: 186, md: 300 },
            maxWidth: { xs: 200, md: 400 },
          }}
          alt="Congrats."
          src={pic}
        />

        <Grid sx={{ mt: 5, mb: 10 }}>
          <Button
            component={Link}
            to="/"
            variant="contained"
            color="primary"
            size="large"
            // className={classes.submit}
          >
            추천게임 보러가기
          </Button>
        </Grid>
      </Grid>
    </>
  );
}
