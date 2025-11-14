import requests
import pandas as pd
pd.set_option('display.max_columns', None)
import time
import numpy as np
from datetime import datetime, timezone
import os
from supabase import create_client, Client
from dotenv import load_dotenv

# Load environment variables
load_dotenv()

def season_averages():
    stats_url = "https://stats.nba.com/stats/leaguedashplayerstats"

    params = {
        'College': '',
        'Conference': '',
        'Country': '',
        'DateFrom': '',
        'DateTo': '',
        'Division': '',
        'DraftPick': '',
        'DraftYear': '',
        'GameScope': '',
        'GameSegment': '',
        'Height': '',
        'ISTRound': '',
        'LastNGames': '0',
        'LeagueID': '00',
        'Location': '',
        'MeasureType': 'Base',
        'Month': '0',
        'OpponentTeamID': '0',
        'Outcome': '',
        'PORound': '0',
        'PaceAdjust': 'N',
        'PerMode': 'PerGame',
        'Period': '0',
        'PlayerExperience': '',
        'PlayerPosition': '',
        'PlusMinus': 'N',
        'Rank': 'N',
        'Season': '2025-26',
        'SeasonSegment': '',
        'SeasonType': 'Regular Season',
        'ShotClockRange': '',
        'StarterBench': '',
        'TeamID': '0',
        'VsConference': '',
        'VsDivision': '',
        'Weight': ''
    }

    headers = {
        'Accept': '*/*',
        'Accept-Encoding': 'gzip, deflate, br, zstd',
        'Accept-Language': 'en-US,en;q=0.9',
        'Connection': 'keep-alive',
        'Host': 'stats.nba.com',
        'Origin': 'https://www.nba.com',
        'Referer': 'https://www.nba.com/',
        'Sec-Fetch-Dest': 'empty',
        'Sec-Fetch-Mode': 'cors',
        'Sec-Fetch-Site': 'same-site',
        'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/142.0.0.0 Safari/537.36',
        'sec-ch-ua': '"Chromium";v="142", "Google Chrome";v="142", "Not_A Brand";v="99"',
        'sec-ch-ua-mobile': '?0',
        'sec-ch-ua-platform': '"macOS"'
    }

    time.sleep(1)
    print("Fetching data...")
    r = requests.get(stats_url, headers=headers, params=params, timeout=30)

    if r.status_code != 200:
        print("Failed to fetch data:", r.status_code)
        return pd.DataFrame()

    data = r.json()
    result_headers = data['resultSets'][0]['headers']
    rows = data['resultSets'][0]['rowSet']
    df = pd.DataFrame(rows, columns=result_headers)

    keep_columns = [
        "PLAYER_ID",
        "PLAYER_NAME",
        "TEAM_ID",
        "TEAM_ABBREVIATION",
        "AGE",
        "GP",
        "W",
        "L",
        "W_PCT",
        "MIN",
        "FGM",
        "FGA",
        "FG_PCT",
        "FG3M",
        "FG3A",
        "FG3_PCT",
        "FTM",
        "FTA",
        "FT_PCT",
        "OREB",
        "DREB",
        "REB",
        "AST",
        "TOV",
        "STL",
        "BLK",
        "PF",
        "PTS",
        "PLUS_MINUS",
        "NBA_FANTASY_PTS",
    ]

    df = df[[col for col in keep_columns if col in df.columns]]
    
    # Convert column names to lowercase for database
    df.columns = df.columns.str.lower()
    
    print(f"✓ Fetched {len(df)} players")
    return df


def upsert_to_supabase(df, supabase: Client):
    """
    Upsert DataFrame rows to Supabase players table
    """
    # Convert DataFrame to list of dictionaries
    records = df.to_dict('records')
    
    # Clean data: replace NaN with None
    for record in records:
        for key, value in record.items():
            if pd.isna(value):
                record[key] = None
    
    try:
        print(f"Upserting {len(records)} players to Supabase...")
        
        # Upsert in batches (Supabase has limits)
        batch_size = 1000
        for i in range(0, len(records), batch_size):
            batch = records[i:i + batch_size]
            
            # Upsert with player_id as the conflict key
            response = supabase.table('players').upsert(
                batch,
                on_conflict='player_id'
            ).execute()
            
            print(f"✓ Batch {i//batch_size + 1}: Upserted {len(batch)} players")
        
        print(f"✓ Successfully upserted all {len(records)} players")
        
    except Exception as e:
        print(f"✗ Error upserting data: {e}")




if __name__ == "__main__":
    url: str = os.environ.get("SUPABASE_URL")
    key: str = os.environ.get("SUPABASE_KEY")
    
    if not url or not key:
        print("✗ Error: SUPABASE_URL and SUPABASE_KEY must be set in .env file")
        exit(1)
    
    # Create Supabase client
    supabase: Client = create_client(url, key)
    
    # Fetch NBA stats
    df = season_averages()

    
    if df.empty:
        print("✗ No data fetched. Exiting.")
        exit(1)
    
    # Upsert to Supabase
    upsert_to_supabase(df, supabase)

    # Get all players
    # players = supabase.table('players').select('player_id').execute()

    # Update each player's headshot URL
    # for player in players.data:
    #     headshot_url = f"https://cdn.nba.com/headshots/nba/latest/260x190/{player['player_id']}.png"
    #     supabase.table('players').update({
    #         'headshot': headshot_url
    #     }).eq('player_id', player['player_id']).execute()
    
    # print("\n✓ Done!")