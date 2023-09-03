import { Grid, TextField } from "@mui/material";
import React from "react";
import { Controller, useFormContext } from "react-hook-form";

export default function YoutubeLinkInputBox() {
  const { control } = useFormContext();
  return (
    <Grid container spacing={1} className="m-3">
      <Grid item xs={12}>
        <Controller
          Controller={control}
          name="youtubeLink"
          rules={{
            required: true,
            validate: (value) => {
              const regex =
                /^http(?:s?):\/\/(?:www\.)?youtu(?:be\.com\/watch\?v=|\.be\/)([\w\-\_]*)(&(amp;)?‌​[\w\?‌​=]*)?$/;
              if (!regex.test(value)) {
                return "유튜브 링크를 입력하세요";
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
              label="유튜브링크"
              value={value}
              onChange={onChange}
              error={!!(value && invalid)}
              helperText={error?.message}
              fullWidth
            />
          )}
        />
      </Grid>
    </Grid>
  );
}
