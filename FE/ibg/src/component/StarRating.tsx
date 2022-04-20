import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { rateGame } from "../api/user";
import { Rating } from "@mui/material";

interface IStar {
  parentCallback?: ((score: number) => void) | null;
  initStarRate: number; // 초기 별점(사용자가 별점을 준 경우 해당 별점)
  size: number; // 별 크기
  gameNo: number; // 별점을 등록할 게임No
  userNo: number; // 로그인한 사용자No
}

// initStarRate 속성으로 초기 별점(사용자가 별점을 준 경우 해당 별점)을 받는다.(default = 0)
export default function StarRating({
  initStarRate = 0,
  size = 25,
  gameNo = 0,
  userNo = 0,
  parentCallback = null,
}: IStar) {
  // starRate는 10점 만점으로 관리(DB에 저장되는 점수 기준)되며, Rating 컴포넌트(5점 만점)에서는 / 2 또는 * 2를 해준다.
  const [starRate, setStarRate] = useState(initStarRate);
  const navigate = useNavigate();

  const setScore = (score: number) => {
    // 로그인하지 않은 경우 로그인 페이지로 이동
    if (!userNo) {
      navigate("/signin");
    } else {
      rateGame(userNo, gameNo, score);
      setStarRate(score);
      if (parentCallback !== null) parentCallback(score); //parent에 score 넘겨주기
    }
  };

  return (
    <div>
      <Rating
        name="half-rating"
        value={starRate / 2}
        defaultValue={initStarRate}
        precision={0.5}
        onChange={(e, newVal) => {
          setScore((newVal as number) * 2);
        }}
        sx={{ fontSize: size }}
      />
    </div>
  );
}
