import { Card, CardContent, Grid, Skeleton } from "@mui/material";

export default function SkeletonLoader(props: { marginX?: number }) {
  return (
    <Grid item xs={12} sm={4} md={3} lg={2.3}>
      <Card variant="outlined" sx={{ mx: props.marginX }}>
        <Skeleton
          variant="rectangular"
          animation="wave"
          sx={{ paddingBottom: "80%" }}
        />
        <CardContent sx={{ px: 1 }}>
          <Skeleton animation="wave" width="80%" />
          <Skeleton animation="wave" width="60%" />
        </CardContent>
      </Card>
    </Grid>
  );
}
