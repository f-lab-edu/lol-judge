import { Grid } from "@mui/material";
import React from "react";
import { Link } from "react-router-dom";

export default function LinkBox({ to, children }) {
  return (
    <Grid container>
      <Grid item>
        <Link to={to} className="text-sky-600 underline">
          {children}
        </Link>
      </Grid>
    </Grid>
  );
}
