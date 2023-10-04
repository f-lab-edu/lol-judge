import { Button, Grid, Stack } from "@mui/material";
import { useFieldArray, useFormContext } from "react-hook-form";
import AddIcon from "@mui/icons-material/Add";
import { defaultOpinionData } from "../utils/defaultData";
import OpinionCard from "./OpinionCard";

export default function OpinionBox() {
  const { control } = useFormContext();
  const { fields, append, remove } = useFieldArray({
    control,
    name: "opinions",
  });
  const addOpinion = () => {
    const maxOpinionLength = 5;
    if (fields.length >= maxOpinionLength) {
      alert(`최소 최대 ${maxOpinionLength}개의 의견만 등록할 수 있습니다.`);
      return;
    }
    append(defaultOpinionData);
  };

  return (
    <Grid container spacing={1}>
      <Grid item xs={12} md={12}>
        <Stack display="block" paddingTop={1} spacing={5}>
          <Button
            variant="outlined"
            startIcon={<AddIcon />}
            onClick={() => addOpinion()}
            sx={{ float: "right" }}
          >
            의견 추가하기
          </Button>
          <Stack spacing={1}>
            {fields.map((field, index) => (
              <OpinionCard
                index={index}
                key={field.id}
                opinionRemove={() => remove(index)}
              />
            ))}
          </Stack>
        </Stack>
      </Grid>
    </Grid>
  );
}
