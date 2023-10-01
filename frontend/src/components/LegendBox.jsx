import { Box } from "@mui/material";
import { grey } from "@mui/material/colors";
import React from "react";

export default function LegendBox({ title, children }) {
  const color = grey[400];

  return (
    <Box
      component="fieldset"
      border={1}
      borderColor={color}
      borderRadius={2}
      className="mb-3"
    >
      <legend className="m-auto">{title}</legend>
      {children}
    </Box>
  );
}
