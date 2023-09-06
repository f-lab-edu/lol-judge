import { ListItem, ListItemText, Typography } from "@mui/material";
import React from "react";

export default function ThumbnailItem({ image, text }) {
  return (
    <ListItem>
      <img src={image} alt="thumbnail" className="object-cover h-16 w-24" />
      <Typography>{text}</Typography>
    </ListItem>
  );
}
