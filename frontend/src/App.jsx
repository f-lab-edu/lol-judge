import React from "react";
import { Outlet } from "react-router-dom";
import Navbar from "./components/Navbar";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";

const queryClient = new QueryClient();

export default function App() {
  return (
    <div>
      <Navbar />
      <QueryClientProvider client={queryClient}>
      <Outlet/>
      </QueryClientProvider>
    </div>
  );
}
