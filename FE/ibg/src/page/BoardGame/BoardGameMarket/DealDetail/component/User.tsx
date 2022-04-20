import React, { useEffect } from "react";
import { useState } from "react";
import { Grid, Avatar, Typography } from "@mui/material";

interface Deal {
  dealer: string;
}

function randomColor() {
  let hex = Math.floor(Math.random() * 0xffffff);
  let color = "#" + hex.toString(16);

  return color;
}

export default function User({ dealer }: Deal) {
  return (
    <Grid direction="column">
      <Avatar
        style={{
          backgroundColor: randomColor(),
        }}
      >
        {dealer.charAt(0)}
      </Avatar>
      <Typography>{dealer}</Typography>
    </Grid>
  );
}
