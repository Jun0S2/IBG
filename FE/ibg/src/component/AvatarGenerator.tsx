import { Avatar } from "@mui/material";
/* User 가 나인 경우와 내가 아닌 경우 
나인 경우 : 세션 스토리지에서 가져오고, 내가 아닌 유저는 랜덤으로 세팅하기*/
interface User {
  userName: string;
  isNav: boolean;
}

function randomColor() {
  let hex = Math.floor(Math.random() * 0xffffff);
  let color = "#" + hex.toString(16);
  return color;
}

export default function AvatarGenerator({ userName, isNav }: User) {
  const avatarColor = isNav
    ? sessionStorage.getItem("avatarColor")
    : randomColor();
  return (
    <Avatar
      style={{
        backgroundColor: avatarColor != null ? avatarColor : "#8ab7ff",
        // backgroundColor: randomColor(),
      }}
    >
      {userName.charAt(0)}
    </Avatar>
  );
}
