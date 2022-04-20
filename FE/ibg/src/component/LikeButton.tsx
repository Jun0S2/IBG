import { useState } from "react";
import { useNavigate } from "react-router";
import { addDelLike } from "../api/user";
import FavoriteBorderOutlinedIcon from "@mui/icons-material/FavoriteBorderOutlined";
import FavoriteOutlinedIcon from "@mui/icons-material/FavoriteOutlined";

export default function LikeButton({
  initLike = false,
  size = 25,
  gameNo = 0,
  userNo = 0,
}) {
  const [isLike, setIsLike] = useState(initLike);
  const navigate = useNavigate();

  const toggleLike = () => {
    // 로그인하지 않은 경우 로그인페이지로 이동
    if (!userNo) {
      navigate("/signin");
    } else {
      addDelLike(userNo, gameNo).then((data) => {
        // 정상적으로 등록/삭제된 경우에만 상태 변경
        if (data.code === 200) {
          setIsLike(!isLike);
        }
      });
    }
  };

  return (
    <>
      {isLike ? (
        <FavoriteOutlinedIcon
          color="error"
          onClick={(e) => {
            e.stopPropagation();
            toggleLike();
          }}
          sx={{ fontSize: size }}
        />
      ) : (
        <FavoriteBorderOutlinedIcon
          color="error"
          onClick={(e) => {
            e.stopPropagation();
            toggleLike();
          }}
          sx={{ fontSize: size }}
        />
      )}
    </>
  );
}
