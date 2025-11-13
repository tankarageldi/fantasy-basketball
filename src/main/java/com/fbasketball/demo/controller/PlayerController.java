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
 * REST Controller for managing NBA players
 * Handles HTTP requests for CRUD operations on Player entities
 * Base URL: /api/players
 */
@RestController
@RequestMapping("/api/players")
public class PlayerController {
    
    // Constructor-based dependency injection (preferred over field injection)
    // This makes the class immutable and easier to test
    private final PlayerService playerService;
    
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }
    
    /**
     * GET /api/players
     * Retrieves all players in the database
     * @return List of all players with 200 OK status
     */
    @GetMapping
    public ResponseEntity<List<Player>> getAllPlayers() {
        return ResponseEntity.ok(playerService.getAllPlayers());
    }
    
    /**
     * GET /api/players/{id}
     * Retrieves a single player by their ID
     * @param id The player's unique identifier
     * @return Player object with 200 OK, or 404 Not Found if player doesn't exist
     * 
     * Using Optional.map() for cleaner null handling instead of if-else blocks
     * map() transforms the Optional<Player> to ResponseEntity if present
     * orElse() returns 404 if Optional is empty
     */
    @GetMapping("/{id}")
    public ResponseEntity<Player> getPlayerById(@PathVariable Long id) {
        return playerService.getPlayerById(id)
                .map(ResponseEntity::ok)  // Method reference: same as .map(player -> ResponseEntity.ok(player))
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * GET /api/players/search?name=value
     * Searches for players by name (partial match)
     * @param name Search query for player name
     * @return List of matching players with 200 OK status
     * 
     * @RequestParam extracts query parameters from the URL
     * Example: /api/players/search?name=LeBron
     */
    @GetMapping("/search")
    public ResponseEntity<List<Player>> searchPlayersByName(@RequestParam String name) {
        return ResponseEntity.ok(playerService.searchPlayersByName(name));
    }
    
    // TODO: Implement these endpoints for additional filtering
    // GET /api/players/team/{teamName}
    // GET /api/players/position/{position}
    
    /**
     * GET /api/players/top-fantasy
     * Retrieves players with the highest fantasy rankings
     * @return List of top fantasy players with 200 OK status
     */
    @GetMapping("/top-fantasy")
    public ResponseEntity<List<Player>> getTopFantasyPlayers() {
        return ResponseEntity.ok(playerService.getTopFantasyPlayers());
    }
    
    /**
     * POST /api/players
     * Creates a new player in the database
     * @param player The player object from request body (automatically converted from JSON)
     * @return Created player with 201 Created status, or error message with appropriate status
     * 
     * @Valid annotation triggers validation on the Player object
     * Validation rules are defined in the Player entity class using annotations like:
     * @NotBlank, @Min, @Max, etc.
     * 
     * HTTP Status Codes:
     * - 201 Created: Player successfully created
     * - 409 Conflict: Player already exists (duplicate name + jersey number)
     * - 500 Internal Server Error: Unexpected error occurred
     */
    @PostMapping
    public ResponseEntity<?> addPlayer(@RequestBody Player player) {
        try {
            // Check if player with same name and jersey number already exists
            if (playerService.playerExists(player.getPlayerName(), player.getJerseyNumber())) {
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body("Player with the same name and jersey number already exists.");
            }

            // Save the new player
            Player savedPlayer = playerService.createPlayer(player);
            
            // Return 201 Created with the saved player object
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(savedPlayer);

        } catch (Exception e) {
            // Catch any unexpected errors
            // In production, consider using @ControllerAdvice for global exception handling
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while saving the player: " + e.getMessage());
        }
    }
    
    /**
     * PUT /api/players/{id}
     * Updates an existing player's information
     * @param id The ID of the player to update
     * @param player The updated player data from request body
     * @return Updated player with 200 OK, or 404 Not Found if player doesn't exist
     * 
     * PUT is idempotent - calling it multiple times has the same effect as calling it once
     * Uses Optional.map() to chain operations:
     * 1. If player exists, update and return 200 OK
     * 2. If player doesn't exist, return 404 Not Found
     */
    @PutMapping("/{id}")
    public ResponseEntity<Player> updatePlayer(@PathVariable Long id,@RequestBody Player player) {
        return playerService.getPlayerById(id)
                .map(existing -> ResponseEntity.ok(playerService.updatePlayer(id, player)))
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * DELETE /api/players/{id}
     * Deletes a player from the database
     * @param id The ID of the player to delete
     * @return 204 No Content if successful, or 404 Not Found if player doesn't exist
     * 
     * DELETE is idempotent - deleting the same resource multiple times has the same result
     * Returns 204 No Content (not 200 OK) as per REST conventions - no content to return
     * 
     * ResponseEntity<Void> indicates no response body is returned
     * The <Void> cast is necessary for Java's type inference with noContent()
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePlayer(@PathVariable Long id) {
        return playerService.getPlayerById(id)
                .map(existing -> {
                    playerService.deletePlayer(id);
                    return ResponseEntity.noContent().build();  // 204 No Content
                })
                .orElse(ResponseEntity.notFound().build());  // 404 Not Found
    }
}