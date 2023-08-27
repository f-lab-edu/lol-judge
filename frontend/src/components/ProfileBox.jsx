import {
  FormControl,
  FormHelperText,
  Grid,
  InputLabel,
  MenuItem,
  Select,
  TextField,
} from "@mui/material";
import React from "react";
import { Controller, useFormContext } from "react-hook-form";

export default function ProfileBox() {
  const { control, getValues } = useFormContext();

  return (
    <Grid container spacing={1}>
      <Grid item xs={12}>
        <Controller
          control={control}
          name="email"
          rules={{
            required: true,
            validate: (value) => {
              const regex = /^[^\s@]+@[^\s@]+\.[^\s@]{2,}$/i;
              if (!regex.test(value)) {
                return "이메일 형식을 확인해주세요";
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
              label="이메일 주소"
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
          control={control}
          name="gameLoginId"
          rules={{
            required: true,
            maxLength: { value: 20, message: "20글자 이하로 입력해주세요." },
          }}
          render={({
            field: { value, onChange },
            fieldState: { invalid, error },
          }) => (
            <TextField
              required
              label="롤 로그인 계정"
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
          control={control}
          name="password"
          rules={{
            required: true,
            validate: (value) => {
              const regex =
              /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$/;
              if (!regex.test(value)) {
                return "비밀번호는 영어, 숫자, 특수문자로 구성된 8글자이어야 합니다";
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
              label="비밀번호"
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
          control={control}
          name="repassword"
          rules={{
            required: true,
            validate: (value) => {
              const password = getValues("password");
              if (password !== value) {
                return "비밀번호와 일치하지 않습니다";
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
              label="비밀번호 확인"
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
          control={control}
          name="position"
          rules={{ required: true, message: "포지션은 필수값입니다" }}
          render={({
            field: { value, onChange },
            fieldState: { invalid, error },
          }) => (
            <FormControl fullWidth>
              <InputLabel>
                포지션
              </InputLabel>
              <Select
                required
                label="postion"
                value={value}
                onChange={onChange}
                error={!!(value && invalid)}
                fullWidth
              >
                <MenuItem value="TOP">탑</MenuItem>
                <MenuItem value="JUNGLE">정글</MenuItem>
                <MenuItem value="MID">미드</MenuItem>
                <MenuItem value="ADC">원딜</MenuItem>
                <MenuItem value="SUPPORT">서포터</MenuItem>
              </Select>
              <FormHelperText>{error?.message}</FormHelperText>
            </FormControl>
          )}
        />
      </Grid>
    </Grid>
  );
}
