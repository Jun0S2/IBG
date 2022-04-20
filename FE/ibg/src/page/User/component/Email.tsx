import { TextField } from "@mui/material/";
import React, { useState } from "react";

//로그인과 회원가입에 둘 다 쓰임
//parent에 입력값 넘겨줘야함

export default function Email() {
  const [value, setValue] = useState("");
  const onChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setValue(e.target.value);
  };
  const validation = () => {
    let check = /@/;
    return !check.test(value) && value.length > 1;
  };
  return (
    <>
      <TextField
        variant="outlined"
        required
        fullWidth
        id="email"
        type="email"
        onChange={onChange}
        value={value}
        error={validation()}
        helperText={validation() ? "올바른 이메일형식이 아닙니다" : ""}
        label="이메일 주소 입력"
        name="email"
        autoComplete="email"
        autoFocus
      />
    </>
  );
}
