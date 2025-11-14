package com.fbasketball.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fbasketball.demo.model.Player;
import com.fbasketball.demo.service.PlayerService;

/**
 * REST Controller for NBA players
 * Base URL: /api/players
 */
@RestController
@RequestMapping("/api/players")
public class PlayerController {
    
    private final PlayerService playerService;
    
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }
    
    // ========================================================================
    // BASIC ENDPOINTS
    // ========================================================================
    
    /**
     * GET /api/players
     * Get all players
     */
    @GetMapping
    public ResponseEntity<List<Player>> getAllPlayers() {
        return ResponseEntity.ok(playerService.getAllPlayers());
    }
    
    /**
     * GET /api/players/{id}
     * Get player by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Player> getPlayerById(@PathVariable Long id) {
        return playerService.getPlayerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * GET /api/players/count
     * Get total player count
     */
    @GetMapping("/count")
    public ResponseEntity<Long> getPlayerCount() {
        return ResponseEntity.ok(playerService.getTotalPlayerCount());
    }
    
    // ========================================================================
    // SEARCH & FILTER
    // ========================================================================
    
    /**
     * GET /api/players/search?name=lebron
     * Search by name
     */
    @GetMapping("/search")
    public ResponseEntity<List<Player>> searchPlayersByName(@RequestParam String name) {
        return playerService.searchPlayersByName(name)
                .isEmpty() ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(playerService.searchPlayersByName(name));
    }
    
    /**
     * GET /api/players/team/{teamAbbr}
     * Get team roster
     */
    @GetMapping("/team/{teamAbbr}")
    public ResponseEntity<List<Player>> getPlayersByTeam(@PathVariable String teamAbbr) {
        return ResponseEntity.ok(playerService.getPlayersByTeam(teamAbbr));
    }
    
    // ========================================================================
    // TOP PERFORMERS
    // ========================================================================
    
    /**
     * GET /api/players/top-fantasy?limit=10
     * Top fantasy players
     */
    @GetMapping("/top-fantasy")
    public ResponseEntity<List<Player>> getTopFantasyPlayers(
            @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(playerService.getTopFantasyPlayers(limit));
    }
    
    /**
     * GET /api/players/top-scorers?limit=10
     * Top scorers
     */
    @GetMapping("/top-scorers")
    public ResponseEntity<List<Player>> getTopScorers(
            @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(playerService.getTopScorers(limit));
    }
    
    /**
     * GET /api/players/efficient-shooters?limit=10
     * Most efficient shooters
     */
    @GetMapping("/efficient-shooters")
    public ResponseEntity<List<Player>> getEfficientShooters(
            @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(playerService.getEfficientShooters(limit));
    }
     // ========================================================================
    // WRITE OPERATIONS (for Python scraper)
    // ========================================================================
    
    /**
     * POST /api/players
     * Create a new player
     */
    @PostMapping
    public ResponseEntity<?> createPlayer(@RequestBody Player player) {
        try {
            if (playerService.playerExists(player.getPlayerId())) {
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body("Player with ID " + player.getPlayerId() + " already exists");
            }
            
            Player savedPlayer = playerService.createPlayer(player);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedPlayer);
            
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating player: " + e.getMessage());
        }
    }
    
    /**
     * PUT /api/players/{id}
     * Update a player
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePlayer(@PathVariable Long id, @RequestBody Player player) {
        try {
            return playerService.getPlayerById(id)
                    .map(existing -> ResponseEntity.ok(playerService.updatePlayer(id, player)))
                    .orElse(ResponseEntity.notFound().build());
                    
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating player: " + e.getMessage());
        }
    }
    
    /**
     * DELETE /api/players/{id}
     * Delete a player
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePlayer(@PathVariable Long id) {
        return playerService.getPlayerById(id)
                .map(existing -> {
                    playerService.deletePlayer(id);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}