import { Grid, TextField, Typography } from "@mui/material";
import React from "react";
import { Controller, useFormContext } from "react-hook-form";

export default function OpinionBox({ disabled, name, maxLength, style }) {
  const { control, watch } = useFormContext();
  const count = watch(name).length;

  return (
    <Grid container spacing={1} className={style}>
      <Grid item xs={12} md={12}>
        <Controller
          control={control}
          name={name}
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
              disabled={disabled}
              label="상황 설명과 의견을 입력하세요"
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
