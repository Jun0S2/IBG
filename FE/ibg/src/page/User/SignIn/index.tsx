import Form from "./component/Form";
import { Grid } from "@mui/material/";
import React, { useState } from "react";
import { login, userInfo } from "../../../api/user";
import jwtDecode from "jwt-decode";
import { useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";
import swal from "sweetalert";

interface MyToken {
  userNo: number;
  exp: number;
  userEmail: string; //이메일
  // whatever else is in the JWT.
}
export default function SignIn() {
  /*form에서 입력한 데이터를 받아온다 : api 연결*/
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const [loading, setLoading] = useState(false);

  const init = async (
    email: string,
    password: string,
    userNick: string,
    userNo: number
  ) => {
    dispatch({
      type: "login",
      userData: { email, password, userNick, userNo },
    });
  };

  function randomColor() {
    let hex = Math.floor(Math.random() * 0xffffff);
    let color = "#" + hex.toString(16);
    return color;
  }
  //api 연결
  const callLoginApi = async (email: string, password: string) => {
    sessionStorage.removeItem("accessToken");
    await login(email, password)
      .then((response) => {
        if (response.data.code === 200) {
          //토큰 및 네비바 배경 색 세션스토리지에 저장

          let token = response.headers.authorization;
          let decode_token = jwtDecode<MyToken>(token);
          sessionStorage.setItem("accessToken", token);
          sessionStorage.setItem("avatarColor", randomColor());

          setLoading(true);
          userInfo(decode_token.userNo).then((response) => {
            let userNick = response.userNick;
            let userNo = response.userNo;
            init(email, password, userNick, userNo);
            navigate("/");
            setLoading(false);
          });
        } else {
          swal("비밀번호를 확인해주세요");
        }
      })
      .catch(() => {
        swal("가입되지 않은 이메일입니다");
      });
    setLoading(false);
  };
  return (
    <>
      <Grid
        container
        spacing={0}
        direction="column"
        alignItems="center"
        justifyContent="center"
        style={{ minHeight: "90vh" }}
      >
        {loading ? (
          "loading"
        ) : (
          <Grid
            item
            xs={2}
            sx={{ flexGrow: 1, m: { xs: 5, md: 0 }, pt: { xs: 6 } }}
          >
            <Form sendDataToParent={callLoginApi} />
          </Grid>
        )}
      </Grid>
    </>
  );
}
