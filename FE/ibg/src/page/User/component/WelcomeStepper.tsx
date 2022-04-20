import * as React from "react";
import Box from "@mui/material/Box";
import Stepper from "@mui/material/Stepper";
import Step from "@mui/material/Step";
import StepLabel from "@mui/material/StepLabel";

const steps = ["회원가입🥳", "설문📝", "완료🎲"];
//active Step 만 props로 주면 되는..?
type Props = {
  value: string;
};
export default function WelcomeStepper({ value }: Props) {
  const page: number = +value;
  return (
    <Box sx={{ flexGrow: 1, pt: { xs: 5, md: 0 } }}>
      <Stepper activeStep={page} alternativeLabel>
        {steps.map((label) => (
          <Step key={label}>
            <StepLabel>{label}</StepLabel>
          </Step>
        ))}
      </Stepper>
    </Box>
  );
}
