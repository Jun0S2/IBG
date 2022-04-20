import React from "react";
import { Container, Typography } from "@mui/material/";

export default function congrats() {
  return (
    <Container component="main" maxWidth="sm">
      <div>
        <Typography component="h1" variant="h5" fontWeight={"bold"}>
          🎉 이제 내게 맞는 보드게임을 즐겨보세요 🎉
        </Typography>
      </div>
    </Container>
  );
}
