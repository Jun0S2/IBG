import React from "react";
import { Button } from "@mui/material";
type Props = {
  value: number;
};

export default function CheckButton({ value }: Props) {
  return (
    <>
      <Button
        type="submit"
        fullWidth
        variant="contained"
        color="primary"
        size={value < 600 ? "small" : "large"}
        sx={{ py: 2 }}
      >
        {value < 600 ? "확인" : "증복 확인"}
      </Button>
    </>
  );
}
