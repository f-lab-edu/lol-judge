import { Checkbox, FormControlLabel, Grid } from "@mui/material";
import React from "react";
import { Controller, useFormContext } from "react-hook-form";

export default function CheckBox({ label }) {
  const { control } = useFormContext();

  return (
    <Grid container spacing={1} className="pt-3 pb-3">
      <Grid item xs={12}>
        <Controller
          name="agree"
          control={control}
          render={() => (
            <FormControlLabel
              control={
                <Checkbox color="secondary" name="checkbox" value="yes" />
              }
              label={label}
              required
            />
          )}
        />
      </Grid>
    </Grid>
  );
}
