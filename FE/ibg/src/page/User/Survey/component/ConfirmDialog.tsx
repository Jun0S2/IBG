import React from "react";
//모달
import DialogTitle from "@mui/material/DialogTitle";
import DialogContent from "@mui/material/DialogContent";
import DialogActions from "@mui/material/DialogActions";
import Dialog from "@mui/material/Dialog";
import WarningIcon from "@mui/icons-material/Warning";

import { Button, Typography, Grid } from "@mui/material";
interface IProps {
  open: boolean;
  count: number;
  handleClose: () => void;
  handleSubmit: () => void;
}
const ConfirmDialog = ({ open, count, handleClose, handleSubmit }: IProps) => {
  return (
    // props received from App.js
    <Dialog
      open={open}
      onClose={handleClose}
      sx={{ "& .MuiDialog-paper": { width: "80%", maxHeight: 435 } }}
      maxWidth="xs"
    >
      <Grid
        container
        spacing={0}
        direction="column"
        alignItems="center"
        justifyContent="center"
        style={{ background: "#FF2E00" }}
      >
        <DialogTitle>
          <WarningIcon sx={{ color: "white", fontSize: 40 }} />
        </DialogTitle>
      </Grid>

      <DialogContent>
        <Typography variant="body2">
          10개 이상 게임에 평점을 등록 시 개인 맞춤 추천이 가능합니다.
          넘어가시겠습니까? (현재 {count}개 )
        </Typography>
      </DialogContent>

      <DialogActions>
        <Button variant="outlined" color="error" onClick={handleClose}>
          취소
        </Button>
        <Button variant="contained" color="error" onClick={handleSubmit}>
          확인
        </Button>
      </DialogActions>
    </Dialog>
  );
};

export default ConfirmDialog;
