import React, { useState } from "react";
import { Link } from "react-router-dom";
import {
  Button,
  TextField,
  FormControlLabel,
  Checkbox,
  Grid,
  Typography,
  Container,
} from "@mui/material/";
import swal from "sweetalert";

interface User {
  parentCallback: (nickname: string, email: string, password: string) => void;
  emailCallback: (email: string) => void;
  nicknameCallback: (nickname: string) => void;
}

function Form({ parentCallback, emailCallback, nicknameCallback }: User) {
  const [width] = useState(window.innerWidth);
  const [password, setPassword] = useState("");
  const [email, setEmail] = useState("");
  const [nickname, setNickname] = useState("");
  const [passwordCheck, setPasswordCheck] = useState("");
  const [agreement, setAgreement] = useState(false);
  const [canSubmit, setCanSubmit] = useState(false);

  /* 닉네임 검사 */
  const onChangeNickname = (e: React.ChangeEvent<HTMLInputElement>) => {
    setNickname(e.target.value);
  };
  const nicknameValidation = () => {
    return nickname.length > 0 && nickname.length < 2;
  };
  /* 이메일 검사 */
  const onChangeEmail = (e: React.ChangeEvent<HTMLInputElement>) => {
    setEmail(e.target.value);
  };
  const emailValidation = () => {
    let check = /@/;
    return !check.test(email) && email.length > 1;
  };
  /*비밀번호 유효 검사 */
  const onChangePassword = (e: React.ChangeEvent<HTMLInputElement>) => {
    setPassword(e.target.value);
  };
  const passwordValidation = () => {
    return password.length < 6 && password.length > 1;
  };
  /*비밀번호 확인 */
  const onChangePasswordCheck = (e: React.ChangeEvent<HTMLInputElement>) => {
    setPasswordCheck(e.target.value);
  };
  const passwordCheckValidation = () => {
    return password !== passwordCheck && passwordCheck.length > 1;
  };
  /*약관 확인 */
  const agreementCheck = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (e.target.checked) setAgreement(true);
    else if (!e.target.checked) setAgreement(false);
  };

  const checkData = () => {
    if (!email.includes("@")) {
      swal("이메일을 확인해주세요");
      setCanSubmit(false);
    } else if (nickname.length < 2) {
      swal("닉네임을 확인해주세요");
      setCanSubmit(false);
    } else if (agreement === false) {
      swal("개인정보 약관에 동의해주세요");
      setCanSubmit(false);
    } else if (password.length < 6) {
      swal("비밀번호는 6글자 이상이어야합니다.");
      setCanSubmit(false);
    } else if (password !== passwordCheck) {
      swal("비밀번호 확인이 일치하지 않습니다");
      setCanSubmit(false);
    } else setCanSubmit(true);
  };

  //e: React.FormEvent<HTMLFormElement>
  const sendData = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    if (canSubmit) parentCallback(nickname, email, password); // 전달
  };
  /* 이메일 중복 확인 */
  const emailDup = () => {
    emailCallback(email);
  };
  /* 이메일 중복 확인 */
  const nicknameDup = () => {
    nicknameCallback(nickname);
  };

  /*랜더링 */
  return (
    <Container component="main" maxWidth="sm">
      <div>
        <Typography
          component="h1"
          variant="h4"
          sx={{
            py: 4,
            px: 1,
            pb: { xs: 2 },
            pt: { xs: -3 },
            display: { xs: "none", sm: "flex" },
          }}
          fontWeight={"bold"}
        >
          회원가입
        </Typography>
        <form onSubmit={sendData}>
          <Grid container spacing={2}>
            <Grid item xs={9} sm={9}>
              <TextField
                variant="outlined"
                required
                fullWidth
                id="email"
                type="email"
                onChange={onChangeEmail}
                value={email}
                error={emailValidation()}
                helperText={
                  emailValidation() ? "올바른 이메일형식이 아닙니다" : ""
                }
                label="이메일 주소 입력"
                name="email"
                autoComplete="email"
                autoFocus
              />
            </Grid>
            <Grid item xs={3} sm={3}>
              <Button
                type="button"
                fullWidth
                variant="contained"
                color="primary"
                onClick={emailDup}
                size={width < 600 ? "small" : "large"}
                sx={{ py: 2 }}
              >
                {width < 600 ? "확인" : "증복 확인"}
              </Button>
            </Grid>
            <Grid item xs={9} sm={9}>
              <TextField
                variant="outlined"
                required
                fullWidth
                id="nickname"
                onChange={onChangeNickname}
                value={nickname}
                error={nicknameValidation()}
                helperText={
                  nicknameValidation()
                    ? "닉네임은 두글자 이상이여야 합니다"
                    : ""
                }
                label="닉네임 입력"
              />
            </Grid>
            <Grid item xs={3} sm={3}>
              <Button
                type="button"
                fullWidth
                variant="contained"
                color="primary"
                onClick={nicknameDup}
                size={width < 600 ? "small" : "large"}
                sx={{ py: 2 }}
              >
                {width < 600 ? "확인" : "증복 확인"}
              </Button>
            </Grid>
            <Grid item xs={12}>
              <TextField
                variant="outlined"
                required
                fullWidth
                onChange={onChangePassword}
                value={password}
                error={passwordValidation()}
                helperText={
                  passwordValidation() ? "최소 6글자 이상 입력하세요" : ""
                }
                name="password"
                label="비밀번호 입력"
                type="password"
                id="password"
                autoComplete="current-password"
              />
            </Grid>
            <Grid item xs={12}>
              <TextField
                variant="outlined"
                required
                fullWidth
                onChange={onChangePasswordCheck}
                value={passwordCheck}
                error={passwordCheckValidation()}
                helperText={
                  passwordCheckValidation()
                    ? "비밀번호가 일치하지 않습니다"
                    : ""
                }
                name="passwordCheck"
                label="비밀번호 확인"
                type="password"
                id="passwordCheck"
              />
            </Grid>
            <Grid item xs={12}>
              <FormControlLabel
                control={
                  <Checkbox
                    value={agreement}
                    color="primary"
                    onChange={agreementCheck}
                  />
                }
                label="개인정보 동의"
              ></FormControlLabel>
            </Grid>
          </Grid>
          <Button
            type="submit"
            fullWidth
            variant="contained"
            color="primary"
            size="large"
            sx={{ py: 2 }}
            onClick={checkData}
            //onClick={sendData}
            // className={classes.submit}
          >
            회원가입
          </Button>
        </form>
        <Grid sx={{ py: 1 }}>
          <Link to="/signin" replace>
            이미 계정이 있으신가요?
          </Link>
        </Grid>
      </div>
    </Container>
  );
}
export default Form;
