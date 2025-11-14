"use client";

import { useEffect, useState } from "react";
import { Bar, BarChart, CartesianGrid, XAxis, YAxis } from "recharts";

import {
  ChartConfig,
  ChartContainer,
  ChartTooltip,
  ChartTooltipContent,
} from "@/components/ui/chart";

interface Player {
  player_name: string;
  fantasy_points: number;
}

const chartConfig = {
  fantasyPoints: {
    label: "Fantasy Points",
    color: "#ffffff",
  },
} satisfies ChartConfig;

// Placeholder data for when backend is not available
const placeholderData = [
  { name: "Jokić", fantasyPoints: 58.4 },
  { name: "Dončić", fantasyPoints: 56.2 },
  { name: "Giannis", fantasyPoints: 54.8 },
  { name: "Embiid", fantasyPoints: 52.3 },
  { name: "Tatum", fantasyPoints: 49.7 },
  { name: "Durant", fantasyPoints: 48.1 },
  { name: "Curry", fantasyPoints: 46.5 },
  { name: "Davis", fantasyPoints: 45.2 },
  { name: "Lillard", fantasyPoints: 43.8 },
  { name: "James", fantasyPoints: 42.6 },
];

export default function ChartEx() {
  const [chartData, setChartData] = useState<
    { name: string; fantasyPoints: number }[]
  >(placeholderData);

  useEffect(() => {
    async function fetchTopPlayers() {
      try {
        const response = await fetch(
          "http://localhost:8080/api/players/top-fantasy?limit=10"
        );
        if (!response.ok) throw new Error("Failed to fetch");

        const players: Player[] = await response.json();
        const data = players.map((player) => ({
          name: player.player_name.split(" ").slice(-1)[0], // Last name only
          fantasyPoints: Number(player.fantasy_points.toFixed(1)),
        }));

        setChartData(data);
      } catch (error) {
        console.error("Failed to fetch top players, using placeholder data:", error);
        // Keep using placeholder data on error
      }
    }

    fetchTopPlayers();
  }, []);

  return (
    <div className="max-w-3xl mx-auto">
      <h2 className="text-2xl font-bold text-white mb-4 text-center">
        Top 10 Fantasy Leaders
      </h2>
      <ChartContainer config={chartConfig} className="h-[300px] w-full">
        <BarChart accessibilityLayer data={chartData}>
          <CartesianGrid vertical={false} stroke="#374151" />
          <XAxis
            dataKey="name"
            tickLine={false}
            tickMargin={10}
            axisLine={false}
            tick={{ fill: "#9ca3af" }}
          />
          <YAxis tick={{ fill: "#9ca3af" }} axisLine={false} tickLine={false} />
          <ChartTooltip
            content={<ChartTooltipContent />}
            cursor={{ fill: "#27272a" }}
          />
          <Bar
            dataKey="fantasyPoints"
            fill="var(--color-fantasyPoints)"
            radius={4}
          />
        </BarChart>
      </ChartContainer>
    </div>
  );
}
