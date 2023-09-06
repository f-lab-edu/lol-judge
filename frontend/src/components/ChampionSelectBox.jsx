import { FormHelperText, Grid } from "@mui/material";
import React from "react";
import { Controller, useFormContext } from "react-hook-form";
import Select from "react-select";

export default function ChampionSelectBox() {
  const champions = [{ label: "쉬바나", value: "shivana" }];
  const { control } = useFormContext();

  return (
    <Grid container spacing={1} className="pt-3 pb-3">
      <Grid item xs={12}>
        <Controller
          name="champion"
          control={control}
          rules={{
            required: true,
            validate: (value) => {
              if (value?.length === 0) {
                return "챔피언을 선택해주세요";
              }
              return true;
            },
          }}
          render={({ field: { value, onChange, defaultValue }, fieldState: { error }}) => (
            <>
              <Select
                options={champions}
                onChange={onChange}
                value={champions.find((c) => c.value === value)}
                defaultValue={champions.find((c) => c.value === defaultValue)}
                required
                placeholder="플레이한 챔피언을 선택하세요 *"
              />
              <FormHelperText>{error?.message}</FormHelperText>
            </>
          )}
        />
      </Grid>
    </Grid>
  );
}
