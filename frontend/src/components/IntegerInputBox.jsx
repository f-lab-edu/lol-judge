import { Grid, TextField } from "@mui/material";
import React from "react";
import { Controller, useFormContext } from "react-hook-form";

export default function IntegerInputBox({ name, text, min, max }) {
  const { control, watch, setValue } = useFormContext();
  const input = watch(name, 0);

  return (
    <Grid container spacing={1} className="pt-3 pb-3">
      <Grid item xs={12}>
        <Controller
          name={name}
          control={control}
          defaultValue={0}
          rules={{
            required: true,
            max: max,
            min: min,
            validate: (value) => {
              if (value < min || value > max) {
                return `${min}~${max} 사이의 숫자를 입력하세요`;
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
              type="number"
              label={`${text} (${Number(input)}/${max})`}
              value={value}
              error={!!(value && invalid)}
              onChange={onChange}
              helperText={error?.message}
              fullWidth
            />
          )}
        />
      </Grid>
    </Grid>
  );
}
