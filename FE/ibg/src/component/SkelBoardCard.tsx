import { Card, CardContent, Grid, Skeleton } from "@mui/material";

export default function SkelBoardCard(props: { marginX?: number }) {
  return (
    <Grid item xs={6} sm={4} md={3} lg={2.4}>
      <Card variant="outlined" sx={{ mx: props.marginX }}>
        <Skeleton
          variant="rectangular"
          animation="wave"
          sx={{ paddingBottom: "100%" }}
        />
        <CardContent sx={{ px: 1 }}>
          <Skeleton animation="wave" width="50%" />
          <Skeleton animation="wave" />
          <Skeleton animation="wave" width="80%" />
        </CardContent>
      </Card>
    </Grid>
  );
}
