import { Grid, TextField } from "@mui/material";
import React from "react";
import { Controller, useFormContext } from "react-hook-form";

export default function ElectionInfoBox() {
  const { control, watch } = useFormContext();
  const progressTime = watch("progressTime", 0);
  const maxTitleLength = 150;
  const minProgressTime = 1;
  const maxProgressTime = 72;

  return (
    <Grid container spacing={1}>
      <Grid item xs={12} md={12}>
        <Controller
          Controller={control}
          name="title"
          rules={{
            required: true,
            max: maxTitleLength,
          }}
          render={({
            field: { value, onChange },
            fieldState: { invalid, error },
          }) => (
            <TextField
              required
              label="제목을 입력하세요"
              value={value}
              onChange={onChange}
              error={!!(value && invalid)}
              helperText={error?.message}
              fullWidth
              multiline
            />
          )}
        />
      </Grid>
      <Grid item xs={12} md={12}>
        <Controller
          Controller={control}
          name="youtubeUrl"
          rules={{
            required: true,
            validate: (value) => {
              const regex =
                /^((?:https?:)?\/\/)?((?:www|m)\.)?((?:youtube(-nocookie)?\.com|youtu.be))(\/(?:[\w\-]+\?v=|embed\/|live\/|v\/)?)([\w\-]+)(\S+)?$/;
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
      <Grid item xs={12}>
        <Controller
          name="progressTime"
          control={control}
          defaultValue={0}
          rules={{
            required: true,
            validate: (value) => {
              if (value < minProgressTime || value > maxProgressTime) {
                return `${minProgressTime}~${maxProgressTime} 사이의 숫자를 입력하세요`;
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
              label={`투표 진행 시간을 입력하세요 (${Number(
                progressTime
              )}/${maxProgressTime})`}
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
