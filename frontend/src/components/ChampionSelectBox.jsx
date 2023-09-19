import { FormHelperText, Grid } from "@mui/material";
import React, { useContext, useEffect } from "react";
import { Controller, useFormContext } from "react-hook-form";
import Select from "react-select";
import { ChampionListContext } from "../context/ChampionListContext";

export default function ChampionSelectBox({ name, disabled, style }) {
  const { champions } = useContext(ChampionListContext);
  const { control, setValue } = useFormContext();

  useEffect(() => {
    setValue(
      name,
      champions.find((c) => c.value === control._defaultValues.hostChampion)
    );
  }, [champions]);

  return (
    <Grid container spacing={1} className={style}>
      <Grid item xs={12}>
        <Controller
          name={name}
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
          render={({ field: { value, onChange }, fieldState: { error } }) => (
            <>
              {disabled ? (
                <Select
                  menuIsOpen={false}
                  options={champions}
                  onChange={onChange}
                  value={value}
                  required
                  placeholder="플레이한 챔피언을 선택하세요 *"
                />
              ) : (
                <Select
                  options={champions}
                  onChange={onChange}
                  value={value}
                  required
                  placeholder="플레이한 챔피언을 선택하세요 *"
                />
              )}
              <FormHelperText>{error?.message}</FormHelperText>
            </>
          )}
        />
      </Grid>
    </Grid>
  );
}
