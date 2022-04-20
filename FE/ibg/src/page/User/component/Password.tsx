import { TextField } from "@mui/material/";
import React, { useState } from "react";

//로그인과 회원가입에 둘 다 쓰임
//parent에 입력값 넘겨줘야함

export default function Password() {
  const [value, setValue] = useState("");

  const onChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setValue(e.target.value);
  };
  const validation = () => {
    //let check = /[~!@#$%^&*()_+|<>?:{}.,/;='"]/;
    return value.length < 6 && value.length > 1;
  };
  return (
    <>
      <TextField
        variant="outlined"
        required
        fullWidth
        onChange={onChange}
        value={value}
        error={validation()}
        helperText={validation() ? "최소 6글자 이상 입력하세요" : ""}
        name="password"
        label="비밀번호 입력"
        type="password"
        id="password"
        autoComplete="current-password"
        autoFocus
      />
    </>
  );
}
