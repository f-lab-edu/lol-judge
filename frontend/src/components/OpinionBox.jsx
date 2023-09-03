import { Grid, TextField, Typography } from "@mui/material";
import React from "react";
import { Controller, useFormContext } from "react-hook-form";

export default function OpinionBox({ maxLength }) {
  const { control, watch } = useFormContext();
  const count = watch("opinion").length;

  return (
    <Grid container spacing={1} className="pt-3 pb-3">
      <Grid item xs={12} md={12}>
        <Controller
          control={control}
          name="opinion"
          rules={{
            required: true,
            validate: () => {
              if (count > maxLength) {
                return `최대 ${maxLength} 글자 입력 가능합니다`;
              }
              return true;
            },
          }}
          render={({
            field: { value, onChange },
            fieldState: { invalid, error },
          }) => (
            <TextField
              required
              label="의견을 입력하세요"
              value={value}
              onChange={onChange}
              error={!!(value && invalid)}
              helperText={error?.message}
              fullWidth
              multiline
            />
          )}
        />

        <Typography variant="body2" align="right">
          {count}/{maxLength}
        </Typography>
      </Grid>
    </Grid>
  );
}
