import { IReview } from "../../../types/IReview";

import { Box, Typography, Divider, styled } from "@mui/material";
import StarIcon from "@mui/icons-material/Star";

const Seller = styled("span")(({ theme }) => ({
  fontSize: 13,
  color: theme.palette.grey[500],
  borderRadius: 8,
  border: "1px solid " + theme.palette.grey[500],
  backgroundColor: "transparent",
  padding: theme.spacing(0.3, 1),
}));

export default function ReviewItem(props: {
  review: IReview;
  dealUserNick?: string;
}) {
  return (
    <>
      <Box sx={{ px: 0.8, py: 1.5, position: "relative" }}>
        <Box
          sx={{
            display: "flex",
            alignItems: "center",
          }}
        >
          <Typography
            sx={{ fontSize: { xs: 14, md: 17 }, fontWeight: "bold", mr: 2 }}
          >
            {props.review.userNick}
          </Typography>
          {typeof props.review.scoreRating === "number" && (
            <Typography
              sx={{
                display: "flex",
                alignItems: "center",
                fontSize: { xs: 13, md: 16 },
                color: "gray",
              }}
            >
              <StarIcon color="warning" /> {props.review.scoreRating}
            </Typography>
          )}
          {typeof props.review.scoreRating !== "number" &&
            props.review.userNick === props.dealUserNick && (
              <Seller>판매자</Seller>
            )}
        </Box>
        <Typography
          component="div"
          sx={{
            fontSize: { xs: 12, md: 15 },
            mt: 0.7,
            whiteSpace: "pre-line",
            wordBreak: "break-all",
          }}
        >
          {props.review.reviewContent
            ? props.review.reviewContent
            : props.review.dealReviewContent}
        </Typography>
        <Typography
          sx={{
            fontSize: { xs: 11, md: 14 },
            position: "absolute",
            top: 10,
            right: 5,
            color: "gray",
          }}
        >
          {props.review.reviewReg}
        </Typography>
      </Box>
      <Divider />
    </>
  );
}
