package com.fbasketball.demo.repository;

// Import JpaRepository from Spring Data JPA
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fbasketball.demo.model.Player;

// This is an INTERFACE, not a class! Interfaces define method signatures only
// Extend JpaRepository<Player, Long>
// The <Player, Long> means: Player entity, Long is the ID type
// JpaRepository provides basic CRUD operations automatically (save, findById, findAll, delete, etc.)
public interface PlayerRepository extends JpaRepository<Player, Long> {

    // Find players by name (case-insensitive, partial match)
    // Method name matters! Spring reads it to generate SQL automatically
    // "findBy" + "PlayerName" + "ContainingIgnoreCase" = SQL: WHERE player_name LIKE '%name%' (case-insensitive)
    List<Player> findByPlayerNameContainingIgnoreCase(String name);

    // Find players by team
    // Spring generates: SELECT * FROM players WHERE team = ?
    List<Player> findByTeam(String team);

    // Find players by position
    // Spring generates: SELECT * FROM players WHERE position = ?
    List<Player> findByPosition(String position);

    // Find top 10 players ordered by fantasy rank (ascending)
    // "findTop10" limits to 10 results
    // "OrderBy" + "FantasyRank" + "Asc" = ORDER BY fantasy_rank ASC
    // Lower rank numbers are better (rank 1 is best)
    List<Player> findTop10ByOrderByFantasyPointsPerGameDesc();

    boolean existsByPlayerNameAndJerseyNumber(String playerName, Integer jerseyNumber);

    // REMEMBER: No method bodies! Just method signatures (no curly braces)
    // Spring Data JPA automatically implements these based on the method names
}