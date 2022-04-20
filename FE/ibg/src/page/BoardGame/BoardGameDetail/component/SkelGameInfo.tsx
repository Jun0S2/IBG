import { Divider, Grid, Skeleton, Typography } from "@mui/material";

export default function SkelGameInfo() {
  return (
    <>
      <Grid container spacing={2} sx={{ my: { xs: 0, md: 4 } }}>
        <Grid item xs={12} sm={4}>
          <Skeleton
            variant="rectangular"
            animation="wave"
            sx={{ paddingBottom: "100%" }}
          />
        </Grid>
        <Grid item xs={12} sm={8}>
          <Typography
            width="40%"
            sx={{ fontSize: { xs: 18, md: 25 }, fontWeight: "bold" }}
          >
            <Skeleton animation="wave" />
          </Typography>
          <Typography
            width="30%"
            sx={{ fontSize: { xs: 13, md: 16 }, color: "gray", mb: 1.5 }}
          >
            <Skeleton animation="wave" />
          </Typography>
          <Skeleton animation="wave" width="40%" />
          <Skeleton animation="wave" width="40%" />
          <Skeleton animation="wave" width="40%" />
          <Skeleton animation="wave" />
          <Skeleton animation="wave" />
          <Skeleton animation="wave" />
          <Skeleton animation="wave" />
          <Skeleton animation="wave" width="80%" />
        </Grid>
      </Grid>
      <Divider />

      <Typography sx={{ fontSize: 20, mt: 5 }}>
        <Skeleton animation="wave" width="15%" />
      </Typography>
      <Skeleton animation="wave" />
      <Skeleton animation="wave" />
      <Skeleton animation="wave" width="80%" />
      <Divider />
    </>
  );
}
