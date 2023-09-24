import { FormHelperText, Grid, TextField, Typography } from "@mui/material";
import { Controller, useFormContext } from "react-hook-form";
import Select from "react-select";
import { champions } from "../utils/championsData";

export default function OpinionCard({ index, opinionRemove }) {
  const { control, watch } = useFormContext();
  const count = watch(`opinions.${index}.contents`)?.length;
  const maxLength = 300;

  return (
    <>
      <Grid item xs={12} md={12}>
        <Controller
          name={`opinions.${index}.champion`}
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
              <Select
                options={champions}
                onChange={onChange}
                value={value}
                required
                placeholder="플레이한 챔피언을 선택하세요 *"
                menuPortalTarget={document.body}
                styles={{menuPortal: base => ({...base, zIndex: 9999})}}
              />
              <FormHelperText>{error?.message}</FormHelperText>
            </>
          )}
        />
      </Grid>
      <Grid item xs={12} md={12}>
        <Controller
          control={control}
          name={`opinions.${index}.contents`}
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
    </>
  );
}
