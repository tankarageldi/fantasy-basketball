package com.fbasketball.demo.service;

// Import @Service annotation - marks this class as a service component
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fbasketball.demo.model.Player;
import com.fbasketball.demo.repository.PlayerRepository;

// @Service marks this as a service component in Spring's application context
// Service layer contains business logic and sits between Controller and Repository
@Service
public class PlayerService {

    // Declare PlayerRepository field - this is how we access the database
    // @Autowired tells Spring to automatically inject (provide) the PlayerRepository instance
    // This is called "Dependency Injection"
    @Autowired
    private PlayerRepository playerRepository;
    
    // Method to get all players from database
    // Returns: List of all Player objects
    public List<Player> getAllPlayers() {
        // Call the repository's findAll() method - this is provided by JpaRepository
        // findAll() retrieves all records from the players table
        return playerRepository.findAll();
    }

    // Method to get a single player by ID
    // Returns: Optional<Player> - Optional is a container that may or may not contain a Player
    // Using Optional prevents null pointer exceptions
    @SuppressWarnings("null")
    public Optional<Player> getPlayerById(Long id) {
        // Call findById(id) - searches for a player with the given ID
        // Returns Optional.empty() if no player found, or Optional.of(player) if found
        return playerRepository.findById(id);
    }

    // Method to search players by name (partial match, case-insensitive)
    // Example: searching "lebr" will find "LeBron James"
    // Returns: List of players whose names contain the search string
    public List<Player> searchPlayersByName(String name) {
        // Call our custom repository method that we defined earlier
        // This searches for player_name LIKE '%name%' (case-insensitive)
        return playerRepository.findByPlayerNameContainingIgnoreCase(name);
    }

    // Method to get players by team
    // Example: "Lakers" returns all Lakers players
    // Returns: List of players on the specified team
    public List<Player> getPlayersByTeam(String team) {
        // Call our custom repository method
        // This searches for exact team match: WHERE team = ?
        return playerRepository.findByTeam(team);
    }

    // Method to get players by position
    // Example: "PG" returns all point guards
    // Returns: List of players at the specified position
    public List<Player> getPlayersByPosition(String position) {
        // Call our custom repository method
        // This searches for exact position match: WHERE position = ?
        return playerRepository.findByPosition(position);
    }

    // Method to get top 10 fantasy players
    // Returns: List of top 10 players ordered by fantasy rank (1 is best)
    public List<Player> getTopFantasyPlayers() {
        // Call our custom repository method
        // This returns top 10 players ordered by fantasy_rank ascending (lowest rank = best)
        return playerRepository.findTop10ByOrderByFantasyPointsPerGameDesc();
    }

    // Method to create/save a new player
    // Parameter: player - the Player object to save
    // Returns: The saved Player object (now includes the auto-generated ID)
    @SuppressWarnings("null")
    public Player createPlayer(Player player) {
        return playerRepository.save(player);
        // Call save() method - this inserts a new record into the database
        // The database auto-generates the ID, and save() returns the player with the new ID
    }
    public boolean playerExists(String playerName, Integer jerseyNumber) {
        return playerRepository.existsByPlayerNameAndJerseyNumber(playerName, jerseyNumber);
    }

    // Method to update an existing player
    // Parameter: id - the ID of the player to update
    // Parameter: playerDetails - the new player data
    // Returns: The updated Player object
    // Throws: RuntimeException if player with given ID doesn't exist
    public Player updatePlayer(Long id, Player playerDetails) {
        // Step 1: Find the existing player by ID
        // findById() returns an Optional<Player>
        // orElseThrow() unwraps the Optional or throws exception if player not found
        @SuppressWarnings("null")
        Player existingPlayer = playerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Player not found with id: " + id));

        // Step 2: Update all fields of the existing player with new data
        existingPlayer.setPlayerName(playerDetails.getPlayerName());
        existingPlayer.setTeam(playerDetails.getTeam());
        existingPlayer.setPosition(playerDetails.getPosition());
        existingPlayer.setJerseyNumber(playerDetails.getJerseyNumber());
        existingPlayer.setPointsPerGame(playerDetails.getPointsPerGame());
        existingPlayer.setAssistsPerGame(playerDetails.getAssistsPerGame());
        existingPlayer.setReboundsPerGame(playerDetails.getReboundsPerGame());
        existingPlayer.setStealsPerGame(playerDetails.getStealsPerGame());
        existingPlayer.setBlocksPerGame(playerDetails.getBlocksPerGame());
        existingPlayer.setFieldGoalPercentage(playerDetails.getFieldGoalPercentage());
        existingPlayer.setThreePointPercentage(playerDetails.getThreePointPercentage());
        existingPlayer.setFreeThrowPercentage(playerDetails.getFreeThrowPercentage());
        existingPlayer.setFantasyPointsPerGame(playerDetails.getFantasyPointsPerGame());
        existingPlayer.setFantasyRanking(playerDetails.getFantasyRanking());
        existingPlayer.setGamesPlayed(playerDetails.getGamesPlayed());

        // Step 3: Save the updated player back to the database
        // save() works for both insert and update - since ID exists, it updates
        
        return playerRepository.save(existingPlayer);
    }

    // Method to delete a player
    // Parameter: id - the ID of the player to delete
    // Returns: nothing (void)
    @SuppressWarnings("null")
    public void deletePlayer(Long id) {
        // Call deleteById(id) - removes the player record from the database
        // If the ID doesn't exist, this will throw an exception
        playerRepository.deleteById(id);
    }
}