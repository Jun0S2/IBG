import { Fab, Zoom } from "@mui/material";
import { useEffect, useState } from "react";
import KeyboardArrowUpIcon from "@mui/icons-material/KeyboardArrowUp";

export default function ScrollToTop() {
  // 현재 스크롤 위치
  const [curPosition, setCurPosition] = useState(0);
  // 버튼 상태(스크롤이 아래로 내려갔을 때만 보이게 하기 위함)
  const [isShow, setIsShow] = useState(false);

  const handlePosition = () => {
    setCurPosition(window.scrollY);

    if (curPosition > 100) setIsShow(true);
    else setIsShow(false);
  };

  const moveToTop = () => {
    window.scrollTo({
      top: 0,
      behavior: "smooth",
    });
    setCurPosition(0);
    setIsShow(false);
  };

  // 스크롤이 움직일 때 이벤트 발생시키기
  useEffect(() => {
    const watch = () => {
      window.addEventListener("scroll", handlePosition);
    };
    watch();
    return () => {
      window.removeEventListener("scroll", handlePosition);
    };
  });

  return (
    <Zoom in={isShow} timeout={{ enter: 225, exit: 195 }} unmountOnExit>
      <Fab
        sx={{ position: "fixed", bottom: 16, right: 16 }}
        aria-label="scroll to top button"
        color="primary"
        onClick={moveToTop}
      >
        <KeyboardArrowUpIcon />
      </Fab>
    </Zoom>
  );
}
