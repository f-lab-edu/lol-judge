import { Step, StepLabel, Stepper } from '@mui/material';
import React from 'react';

export default function ElectionStep({activeStep}) {
    const steps = ['재판 신청', '재판 내용 합의', '재판 등록 완료'];

    return (
        <Stepper activeStep={activeStep} sx={{pt:3, pb:5}}>
            {steps.map((label => (
                <Step key={label}>
                    <StepLabel>{label}</StepLabel>
                </Step>
            )))}
        </Stepper>
    );
}

