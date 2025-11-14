package com.fbasketball.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fbasketball.demo.model.Player;

/**
 * Repository interface for Player entity
 * Simple queries for searching players and basic filtering
 */
@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    
    // ========================================================================
    // SEARCH BY NAME
    // ========================================================================
    
    /**
     * Find players by name (case-insensitive partial match)
     * Example: "lebr" finds "LeBron James"
     */
    List<Player> findByPlayerNameContainingIgnoreCase(String name);
    
    // ========================================================================
    // FILTER BY TEAM
    // ========================================================================
    
    /**
     * Find all players on a specific team
     * Example: "LAL" returns all Lakers
     */
    List<Player> findByTeamAbbreviation(String teamAbbreviation);
    
    // ========================================================================
    // ORDERING (Top N queries)
    // ========================================================================
    
    /**
     * Get top N fantasy performers
     */
    @Query("SELECT p FROM Player p ORDER BY p.nbaFantasyPts DESC LIMIT :limit")
    List<Player> findTopNByOrderByNbaFantasyPtsDesc(@Param("limit") int limit);
    
    /**
     * Get top N scorers (highest PPG)
     */
    @Query("SELECT p FROM Player p ORDER BY p.pts DESC LIMIT :limit")
    List<Player> findTopNByOrderByPtsDesc(@Param("limit") int limit);
    
    /**
     * Get most efficient shooters (highest FG%)
     */
    @Query("SELECT p FROM Player p ORDER BY p.fgPct DESC LIMIT :limit")
    List<Player> findTopNByOrderByFgPctDesc(@Param("limit") int limit);
}