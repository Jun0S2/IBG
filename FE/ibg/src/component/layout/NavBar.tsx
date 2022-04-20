import { useState, Fragment, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { RootStateOrAny, useSelector, useDispatch } from "react-redux";
import SearchBar from "./component/SearchBar";
import AvatarGenerator from "../AvatarGenerator";
import { getAutoAllGame } from "../../api/game";

// material ui
import { styled } from "@mui/material/styles";
import AppBar from "@mui/material/AppBar";
import Box from "@mui/material/Box";
import Toolbar from "@mui/material/Toolbar";
import IconButton from "@mui/material/IconButton";
import Menu from "@mui/material/Menu";
import SwipeableDrawer from "@mui/material/SwipeableDrawer";
import List from "@mui/material/List";
import Divider from "@mui/material/Divider";
import ListItem from "@mui/material/ListItem";
import ListItemIcon from "@mui/material/ListItemIcon";
import ListItemText from "@mui/material/ListItemText";
import Container from "@mui/material/Container";
import Button from "@mui/material/Button";
import Tooltip from "@mui/material/Tooltip";
import MenuItem from "@mui/material/MenuItem";
import ImageListItem from "@mui/material/ImageListItem";

// 아이콘
import MenuIcon from "@mui/icons-material/Menu";
import ExtensionOutlinedIcon from "@mui/icons-material/ExtensionOutlined";
import StorefrontOutlinedIcon from "@mui/icons-material/StorefrontOutlined";
import LoginIcon from "@mui/icons-material/Login";
import Logout from "@mui/icons-material/Logout";
import FavoriteBorderIcon from "@mui/icons-material/FavoriteBorder";
// import ChatBubbleOutlineOutlinedIcon from "@mui/icons-material/ChatBubbleOutlineOutlined";
import MapIcon from "@mui/icons-material/Map";
import { Typography } from "@mui/material";

// Nav 항목 - link가 존재하면 페이지 이동, method가 존재하면 해당 함수 실행(handleNavMethod 추가 필요)
const pages = [
  { label: "보드게임", icon: <ExtensionOutlinedIcon />, link: "/search" },
  { label: "BGM", icon: <StorefrontOutlinedIcon />, link: "/market" },
  { label: "보드게임 카페", icon: <MapIcon />, link: "/map" },
];
const userNav = [
  {
    label: "관심목록",
    icon: <FavoriteBorderIcon />,
    link: "/mygames",
  },
  // {
  //   label: "채팅",
  //   icon: <ChatBubbleOutlineOutlinedIcon />,
  //   method: "",
  // },
  { label: "로그아웃", icon: <Logout />, method: "logout" },
];

const StyledAppBar = styled(AppBar)(({ theme }) => ({
  position: "fixed",
  //backgroundColor: "error",
  backgroundColor: theme.palette.warning.main,
  //boxShadow: "rgba(33, 35, 38, 0.1) 0px 10px 10px -10px",
  boxShadow: "rgba(33, 35, 38, 0.1) 0px 0px 0px 0px",
  //  maxHeight: 50,
}));

export default function NavBar() {
  const dispatch = useDispatch();
  // 로그인 여부
  const auth = useSelector((state: RootStateOrAny) => state.isLogin);
  const userName = useSelector((state: RootStateOrAny) => state.user.userNick);
  // 사용자 메뉴 Open/Close(PC)
  const [userMenu, setUserMenu] = useState<null | HTMLElement>(null);
  // Mobild 메뉴 Open/Close
  const [mobileMenu, setMobileMenu] = useState(false);
  // 자동완성을 위한 게임 목록
  const [autoGameList, setAutoGameList] = useState([]);

  const logoImg = require("../../assets/logo.png");

  // 페이지 이동
  const navigate = useNavigate();

  useEffect(() => {
    getAutoAllGame().then((data) => {
      if (data.code === 200) {
        setAutoGameList(data.data);
      }
    });
  }, []);

  //로그아웃 메서드
  const logoutMethod = () => {
    sessionStorage.clear();
    dispatch({ type: "logout" });
    navigate("/");
  };

  // 메뉴 메서드 실행
  const handleNavMethod = (val: string) => {
    switch (val) {
      case "logout":
        logoutMethod();
        break;
      default:
        break;
    }
  };

  // 사용자 메뉴 열고 닫기(로그인 했을 때)
  const handleOpenUserMenu = (event: React.MouseEvent<HTMLElement>) => {
    setUserMenu(event.currentTarget);
  };
  const handleCloseUserMenu = () => {
    setUserMenu(null);
  };

  // Mobile 메뉴 열고 닫기
  const toggleMobileMenu =
    (open: boolean) => (event: React.KeyboardEvent | React.MouseEvent) => {
      if (
        (event &&
          event.type === "keydown" &&
          (event as React.KeyboardEvent).key === "Tab") ||
        (event as React.KeyboardEvent).key === "Shift"
      ) {
        return;
      }
      setMobileMenu(open);
    };

  // Mobile 메뉴 리스트
  const MobileMenuList = () => (
    <Box
      sx={{ width: 250 }}
      role="presentation"
      onClick={toggleMobileMenu(false)}
      onKeyDown={toggleMobileMenu(false)}
    >
      <List>
        {pages.map((page) => (
          <ListItem button key={page.label} onClick={() => movePage(page.link)}>
            <ListItemIcon>{page.icon}</ListItemIcon>
            <ListItemText primary={page.label} />
          </ListItem>
        ))}
      </List>
      <Divider />
      {auth ? (
        <List>
          {userNav.map((item) => (
            <ListItem
              button
              key={item.label}
              onClick={() =>
                item.link
                  ? movePage(item.link)
                  : handleNavMethod(item.method as string)
              }
            >
              <ListItemIcon>{item.icon}</ListItemIcon>
              <ListItemText primary={item.label} />
            </ListItem>
          ))}
        </List>
      ) : (
        <List>
          <ListItem button onClick={() => movePage("/signin")}>
            <ListItemIcon>
              <LoginIcon />
            </ListItemIcon>
            <ListItemText primary="로그인" />
          </ListItem>
        </List>
      )}
    </Box>
  );

  // 페이지 이동
  const movePage = (page: string) => {
    handleCloseUserMenu();
    navigate(page);
  };

  return (
    <StyledAppBar>
      <Container maxWidth="xl">
        <Toolbar disableGutters sx={{ justifyContent: "space-between" }}>
          <ImageListItem
            sx={{
              maxWidth: 40,
              cursor: "pointer",
              //  mb: { md: 2, xs: 1 },
              ml: { md: 15 },
            }}
            onClick={() => movePage("/")}
          >
            <img src={logoImg} alt="logo" />
          </ImageListItem>
          <Typography
            sx={{
              ml: { md: 1.5 },
              mr: { xs: 0, md: 3 },
              minWidth: 65,
              cursor: "pointer",
              display: { xs: "none", md: "block" },
              fontSize: 22,
            }}
            onClick={() => movePage("/")}
          >
            이보게
          </Typography>
          {/* Nav 반응형 - PC --------------------------------------*/}
          {/* 페이지 이동 Nav */}
          <Box sx={{ flexGrow: 1, display: { xs: "none", md: "flex" } }}>
            {pages.map((page) => (
              <Button
                key={page.label}
                sx={{
                  color: "white",
                  display: "block",
                  // mb: 2
                }}
                onClick={() => movePage(page.link)}
              >
                {page.label}
              </Button>
            ))}
          </Box>
          {/* 검색창 */}
          <SearchBar
            placeholder="보드게임 검색"
            gameList={autoGameList}
            onClickItem={(no: number) => movePage(`/detail/${no}`)}
          />
          {/* 오른쪽 메뉴(사용자) auth: 로그인 여부 */}
          {auth ? (
            <Box
              sx={{
                flexGrow: 0,
                display: { xs: "none", md: "flex" },
              }}
            >
              <Tooltip title="사용자 메뉴">
                <IconButton onClick={handleOpenUserMenu} sx={{ p: 0 }}>
                  <AvatarGenerator userName={userName} isNav={true} />
                </IconButton>
              </Tooltip>
              <Menu
                sx={{ mt: "45px" }}
                id="account-menu"
                anchorEl={userMenu}
                anchorOrigin={{
                  vertical: "top",
                  horizontal: "right",
                }}
                keepMounted
                transformOrigin={{
                  vertical: "top",
                  horizontal: "right",
                }}
                open={Boolean(userMenu)}
                onClose={handleCloseUserMenu}
                PaperProps={{
                  elevation: 0,
                  sx: {
                    overflow: "visible",
                    filter: "drop-shadow(0px 2px 8px rgba(0, 0, 0, 0.15))",
                    mt: 1.5,
                    "& .MuiAvatar-root": {
                      width: 32,
                      height: 32,
                      ml: -0.5,
                      mr: 1,
                    },
                    "&:before": {
                      content: '""',
                      display: "block",
                      position: "absolute",
                      top: 0,
                      right: 14,
                      width: 10,
                      height: 10,
                      bgcolor: "background.paper",
                      transform: "translateY(-50%) rotate(45deg)",
                      zIndex: 0,
                    },
                  },
                }}
              >
                {userNav.map((item) => (
                  <MenuItem
                    sx={{ fontSize: "0.9rem" }}
                    key={item.label}
                    onClick={() =>
                      item.link
                        ? movePage(item.link)
                        : handleNavMethod(item.method as string)
                    }
                  >
                    <ListItemIcon>{item.icon}</ListItemIcon>
                    {item.label}
                  </MenuItem>
                ))}
              </Menu>
            </Box>
          ) : (
            <Box sx={{ flexGrow: 0 }}>
              <Button
                color="warning"
                sx={{
                  color: "white",
                  display: { xs: "none", md: "block" },
                  // mb: { md: 2 },
                  mr: { md: 15 },
                }}
                onClick={() => movePage("/signin")}
              >
                로그인
              </Button>
            </Box>
          )}
          {/* Nav 반응형 Mobile --------------------------------------- */}
          <Box sx={{ display: { xs: "block", md: "none" } }}>
            <Fragment key="right">
              <IconButton size="large" onClick={toggleMobileMenu(true)}>
                <MenuIcon />
              </IconButton>

              <SwipeableDrawer
                anchor="right"
                open={mobileMenu}
                onOpen={toggleMobileMenu(true)}
                onClose={toggleMobileMenu(false)}
              >
                {MobileMenuList()}
              </SwipeableDrawer>
            </Fragment>
          </Box>
        </Toolbar>
      </Container>
    </StyledAppBar>
  );
}
