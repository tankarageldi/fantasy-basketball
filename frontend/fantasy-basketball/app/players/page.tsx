"use client";

import { useState, useEffect } from "react";

interface Player {
  id: number;
  player_name: string;
  team: string;
  position: string;
  games_played: number;
  minutes: number;
  points: number;
  rebounds: number;
  assists: number;
  steals: number;
  blocks: number;
  field_goal_pct: number;
  three_point_pct: number;
  free_throw_pct: number;
}

export default function PlayersPage() {
  const [players, setPlayers] = useState<Player[]>([]);
  const [searchTerm, setSearchTerm] = useState("");
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    fetchPlayers();
  }, []);

  const fetchPlayers = async () => {
    try {
      setLoading(true);
      const response = await fetch(
        "https://fantasy-basketball-production.up.railway.app/api/players"
      );
      if (!response.ok) throw new Error("Failed to fetch players");
      const data = await response.json();
      setPlayers(data);
      setError("");
    } catch (err) {
      setError("Failed to load players. Make sure the backend is running.");
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const handleSearch = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!searchTerm.trim()) {
      fetchPlayers();
      return;
    }

    try {
      setLoading(true);
      const response = await fetch(
        `http://localhost:8080/api/players/search?name=${encodeURIComponent(
          searchTerm
        )}`
      );
      if (!response.ok) throw new Error("Search failed");
      const data = await response.json();
      setPlayers(data);
      setError("");
    } catch (err) {
      setError("Search failed. Please try again.");
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <main className="min-h-screen">
      <div className="mx-auto max-w-7xl px-4 sm:px-6 lg:px-8 py-8">
        <div className="mb-8">
          <h1 className="text-4xl font-bold text-white mb-2">Players</h1>
          <p className="text-gray-400">Search and view NBA player statistics</p>
        </div>

        <form onSubmit={handleSearch} className="mb-8">
          <div className="flex gap-4">
            <input
              type="text"
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
              placeholder="Search by player name..."
              className="flex-1 px-4 py-3 bg-zinc-800 border border-gray-700 rounded-lg text-white placeholder-gray-500 focus:outline-none focus:border-gray-500"
            />
            <button
              type="submit"
              className="px-6 py-3 bg-white text-black font-medium rounded-lg hover:bg-gray-200 transition-colors"
            >
              Search
            </button>
            {searchTerm && (
              <button
                type="button"
                onClick={() => {
                  setSearchTerm("");
                  fetchPlayers();
                }}
                className="px-6 py-3 bg-zinc-700 text-white font-medium rounded-lg hover:bg-zinc-600 transition-colors"
              >
                Clear
              </button>
            )}
          </div>
        </form>

        {error && (
          <div className="mb-6 p-4 bg-red-900/20 border border-red-800 rounded-lg text-red-400">
            {error}
          </div>
        )}

        {loading ? (
          <div className="text-center py-12 text-gray-400">
            Loading players...
          </div>
        ) : players.length === 0 ? (
          <div className="text-center py-12 text-gray-400">
            No players found
          </div>
        ) : (
          <div className="overflow-x-auto">
            <table className="w-full border-collapse">
              <thead>
                <tr className="border-b border-gray-700">
                  <th className="text-left py-4 px-4 text-sm font-semibold text-gray-400">
                    Player
                  </th>
                  <th className="text-left py-4 px-4 text-sm font-semibold text-gray-400">
                    Team
                  </th>
                  <th className="text-left py-4 px-4 text-sm font-semibold text-gray-400">
                    Pos
                  </th>
                  <th className="text-right py-4 px-4 text-sm font-semibold text-gray-400">
                    GP
                  </th>
                  <th className="text-right py-4 px-4 text-sm font-semibold text-gray-400">
                    MIN
                  </th>
                  <th className="text-right py-4 px-4 text-sm font-semibold text-gray-400">
                    PTS
                  </th>
                  <th className="text-right py-4 px-4 text-sm font-semibold text-gray-400">
                    REB
                  </th>
                  <th className="text-right py-4 px-4 text-sm font-semibold text-gray-400">
                    AST
                  </th>
                  <th className="text-right py-4 px-4 text-sm font-semibold text-gray-400">
                    STL
                  </th>
                  <th className="text-right py-4 px-4 text-sm font-semibold text-gray-400">
                    BLK
                  </th>
                  <th className="text-right py-4 px-4 text-sm font-semibold text-gray-400">
                    FG%
                  </th>
                  <th className="text-right py-4 px-4 text-sm font-semibold text-gray-400">
                    3P%
                  </th>
                  <th className="text-right py-4 px-4 text-sm font-semibold text-gray-400">
                    FT%
                  </th>
                </tr>
              </thead>
              <tbody>
                {players.map((player) => (
                  <tr
                    key={player.id}
                    className="border-b border-gray-800 hover:bg-zinc-800 transition-colors"
                  >
                    <td className="py-4 px-4 text-white font-medium">
                      {player.player_name}
                    </td>
                    <td className="py-4 px-4 text-gray-400">{player.team}</td>
                    <td className="py-4 px-4 text-gray-400">
                      {player.position}
                    </td>
                    <td className="py-4 px-4 text-right text-gray-300">
                      {player.games_played}
                    </td>
                    <td className="py-4 px-4 text-right text-gray-300">
                      {player.minutes.toFixed(1)}
                    </td>
                    <td className="py-4 px-4 text-right text-white font-medium">
                      {player.points.toFixed(1)}
                    </td>
                    <td className="py-4 px-4 text-right text-gray-300">
                      {player.rebounds.toFixed(1)}
                    </td>
                    <td className="py-4 px-4 text-right text-gray-300">
                      {player.assists.toFixed(1)}
                    </td>
                    <td className="py-4 px-4 text-right text-gray-300">
                      {player.steals.toFixed(1)}
                    </td>
                    <td className="py-4 px-4 text-right text-gray-300">
                      {player.blocks.toFixed(1)}
                    </td>
                    <td className="py-4 px-4 text-right text-gray-300">
                      {(player.field_goal_pct * 100).toFixed(1)}%
                    </td>
                    <td className="py-4 px-4 text-right text-gray-300">
                      {(player.three_point_pct * 100).toFixed(1)}%
                    </td>
                    <td className="py-4 px-4 text-right text-gray-300">
                      {(player.free_throw_pct * 100).toFixed(1)}%
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </div>
    </main>
  );
}
