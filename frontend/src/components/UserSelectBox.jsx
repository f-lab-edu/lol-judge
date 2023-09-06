import { FormHelperText, Grid } from "@mui/material";
import React from "react";
import { Controller, useFormContext } from "react-hook-form";
import Select from "react-select";

export default function UserSelectBox() {
  const users = [{ label: "corojoon93@naver.com", value: "corojoon93@naver.com" }];
  const { control } = useFormContext();

  return (
    <Grid container spacing={1} className="pt-3 pb-3">
      <Grid item xs={12}>
        <Controller
          name="participantEmail"
          control={control}
          rules={{
            required: true,
          }}
          render={({ field: { value, onChange }, fieldState: { error } }) => (
            <>
              <Select
                options={users}
                required
                onChange={onChange}
                value={value}
                placeholder="상대방 닉네임을 입력해주세요 *"
              />
              <FormHelperText>{error?.message}</FormHelperText>
            </>
          )}
        />
      </Grid>
    </Grid>
  );
}
