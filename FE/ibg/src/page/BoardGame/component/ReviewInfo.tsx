import React, { useState } from "react";
import { useNavigate } from "react-router";
import ReviewItem from "./ReviewItem";
import { IReview } from "../../../types/IReview";
import { Typography, TextField, Box, Button } from "@mui/material";

export default function ReviewInfo(props: {
  title: string;
  reviewList: IReview[];
  userNo: number;
  dealUserNick?: string;
  addCallback: Function;
}) {
  const [newReview, setNewReview] = useState("");
  const navigate = useNavigate();

  const onChangeNewReview = (e: React.ChangeEvent<HTMLInputElement>) => {
    setNewReview(e.target.value);
  };

  const registerReview = () => {
    props.addCallback(newReview);
    setNewReview("");
  };

  const movePage = () => {
    navigate("/signin");
  };

  return (
    <Box sx={{ my: { xs: 2, md: 4 } }}>
      <Typography
        sx={{ fontSize: { xs: 16, md: 21 }, fontWeight: "bold", mb: 1 }}
      >
        {props.title}
        {/* <Typography component="span">({props.reviewList.length})</Typography> */}
      </Typography>

      {props.userNo ? (
        <>
          <TextField
            fullWidth
            multiline
            value={newReview}
            maxRows={5}
            onChange={onChangeNewReview}
          ></TextField>
          <Box sx={{ textAlign: "right", my: 1 }}>
            {newReview ? (
              <Button
                variant="contained"
                sx={{ padding: 0.3 }}
                onClick={registerReview}
              >
                등록
              </Button>
            ) : (
              <Button
                disabled
                variant="contained"
                sx={{ padding: 0.3 }}
                onClick={registerReview}
              >
                등록
              </Button>
            )}
          </Box>
        </>
      ) : (
        <Box textAlign="center" sx={{ mb: 1 }}>
          <Typography sx={{ fontSize: { xs: 15, md: 20 } }}>
            로그인하면 리뷰를 등록할 수 있어요 :)
          </Typography>
          <Button variant="contained" sx={{ mt: 1 }} onClick={movePage}>
            로그인하러 가기
          </Button>
        </Box>
      )}

      {props.reviewList.map((review) => {
        return (
          <ReviewItem
            key={review.reviewNo ? review.reviewNo : review.dealReviewContent}
            review={review}
            dealUserNick={props.dealUserNick}
          />
        );
      })}
    </Box>
  );
}
