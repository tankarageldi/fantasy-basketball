import ChartEx from "@/components/example-chart";
import Link from "next/link";

export default function Home() {
  return (
    <main className="min-h-screen">
      <div className="mx-auto max-w-7xl px-4 sm:px-6 lg:px-8 py-12">
        <div className="text-center space-y-8">
          <div className="space-y-4">
            <h1 className="text-5xl font-bold text-white">
              Fantasy Basketball Dashboard
            </h1>
            <p className="text-xl text-gray-400 max-w-2xl mx-auto">
              Track NBA player stats, analyze team performance, and stay updated
              with daily leaders
            </p>
          </div>
          <ChartEx />

          <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mt-12 max-w-4xl mx-auto">
            <Link
              href="/players"
              className="p-8 bg-zinc-800 hover:bg-zinc-700 border border-gray-700 rounded-lg transition-colors group"
            >
              <h2 className="text-2xl font-semibold text-white mb-2">
                Players
              </h2>
              <p className="text-gray-400 group-hover:text-gray-300">
                Browse and search player statistics
              </p>
            </Link>

            <Link
              href="/teams"
              className="p-8 bg-zinc-800 hover:bg-zinc-700 border border-gray-700 rounded-lg transition-colors group"
            >
              <h2 className="text-2xl font-semibold text-white mb-2">Teams</h2>
              <p className="text-gray-400 group-hover:text-gray-300">
                View team rosters and performance
              </p>
            </Link>

            <Link
              href="/daily-leaders"
              className="p-8 bg-zinc-800 hover:bg-zinc-700 border border-gray-700 rounded-lg transition-colors group"
            >
              <h2 className="text-2xl font-semibold text-white mb-2">
                Daily Leaders
              </h2>
              <p className="text-gray-400 group-hover:text-gray-300">
                Top performers and trending stats
              </p>
            </Link>
          </div>
        </div>
      </div>
    </main>
  );
}
