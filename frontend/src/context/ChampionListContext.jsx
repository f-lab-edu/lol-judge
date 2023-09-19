import { createContext, useEffect, useState } from "react";

export const ChampionListContext = createContext();

export default function ChampionListProvider({ children }) {
  const [champions, setChampions] = useState([]);

  useEffect(() => {
    setChampions([
      { label: "쉬바나", value: "shivana" },
      { label: "판테온", value: "panteon" },
    ]);
  }, []);

  return (
    <ChampionListContext.Provider value={{ champions, setChampions }}>
      {children}
    </ChampionListContext.Provider>
  );
}
