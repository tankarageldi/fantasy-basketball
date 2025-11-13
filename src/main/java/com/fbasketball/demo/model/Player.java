package com.fbasketball.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Player entity representing an NBA player
 * Contains both JPA annotations (for database mapping) and validation annotations (for input validation)
 */
@Entity
@Table(name = "players",
    uniqueConstraints = {
        // Database constraint: Ensure no duplicate player name + jersey number combinations
        @UniqueConstraint(columnNames = {"player_name", "jersey_number"}, 
                         name = "uk_player_name_jersey")
    }
)
public class Player {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // APPLICATION VALIDATION: @NotBlank ensures non-null, non-empty, non-whitespace
    // DATABASE VALIDATION: nullable = false (NOT NULL constraint), length = 100 (VARCHAR limit)
    @NotBlank(message = "Player name is required and cannot be empty")
    @Size(min = 2, max = 100, message = "Player name must be between 2 and 100 characters")
    @Column(name = "player_name", nullable = false, length = 100)
    private String playerName;
    
    @NotBlank(message = "Team name is required and cannot be empty")
    @Size(min = 2, max = 50, message = "Team name must be between 2 and 50 characters")
    @Column(nullable = false, length = 50)
    private String team;
    
    @NotBlank(message = "Position is required and cannot be empty")
    @Size(min = 1, max = 5, message = "Position must be between 1 and 5 characters")
    @Column(nullable = false, length = 5)
    private String position;
    
    // APPLICATION VALIDATION: @Min and @Max for range checking
    // DATABASE VALIDATION: nullable = false (NOT NULL), CHECK constraint added via SQL
    @NotNull(message = "Jersey number is required")
    @Min(value = 0, message = "Jersey number must be at least 0")
    @Max(value = 99, message = "Jersey number cannot exceed 99")
    @Column(name = "jersey_number", nullable = false)
    private Integer jerseyNumber;
    
    // All stats validated at application level with @Min(0)
    // Database level validation added via CHECK constraints in SQL
    @NotNull(message = "Points per game is required")
    @Min(value = 0, message = "Points per game cannot be negative")
    @Column(name = "points_per_game", nullable = false)
    private Double pointsPerGame;
    
    @NotNull(message = "Assists per game is required")
    @Min(value = 0, message = "Assists per game cannot be negative")
    @Column(name = "assists_per_game", nullable = false)
    private Double assistsPerGame;
    
    @NotNull(message = "Rebounds per game is required")
    @Min(value = 0, message = "Rebounds per game cannot be negative")
    @Column(name = "rebounds_per_game", nullable = false)
    private Double reboundsPerGame;
    
    @NotNull(message = "Steals per game is required")
    @Min(value = 0, message = "Steals per game cannot be negative")
    @Column(name = "steals_per_game", nullable = false)
    private Double stealsPerGame;
    
    @NotNull(message = "Blocks per game is required")
    @Min(value = 0, message = "Blocks per game cannot be negative")
    @Column(name = "blocks_per_game", nullable = false)
    private Double blocksPerGame;
    
    @NotNull(message = "Turnovers per game is required")
    @Min(value = 0, message = "Turnovers per game cannot be negative")
    @Column(name = "turnovers_per_game", nullable = false)
    private Double turnoversPerGame;
    
    // Shooting percentages must be between 0.0 (0%) and 1.0 (100%)
    @NotNull(message = "Field goal percentage is required")
    @DecimalMin(value = "0.0", message = "Field goal percentage must be at least 0.0")
    @DecimalMax(value = "1.0", message = "Field goal percentage cannot exceed 1.0")
    @Column(name = "field_goal_percentage", nullable = false)
    private Double fieldGoalPercentage;
    
    @NotNull(message = "Three point percentage is required")
    @DecimalMin(value = "0.0", message = "Three point percentage must be at least 0.0")
    @DecimalMax(value = "1.0", message = "Three point percentage cannot exceed 1.0")
    @Column(name = "three_point_percentage", nullable = false)
    private Double threePointPercentage;
    
    @NotNull(message = "Free throw percentage is required")
    @DecimalMin(value = "0.0", message = "Free throw percentage must be at least 0.0")
    @DecimalMax(value = "1.0", message = "Free throw percentage cannot exceed 1.0")
    @Column(name = "free_throw_percentage", nullable = false)
    private Double freeThrowPercentage;
    
    @NotNull(message = "Fantasy points per game is required")
    @Min(value = 0, message = "Fantasy points per game cannot be negative")
    @Column(name = "fantasy_points_per_game", nullable = false)
    private Double fantasyPointsPerGame;
    
    @NotNull(message = "Fantasy ranking is required")
    @Min(value = 0, message = "Fantasy ranking cannot be negative")
    @Column(name = "fantasy_ranking", nullable = false)
    private Integer fantasyRanking;

    @Min(value = 0, message = "Games played cannot be negative")
    @Column(name = "games_played")
    private Integer gamesPlayed;
    
    // Default constructor required by JPA
    public Player() {
    }
    
    // Constructor for creating new Player objects
    public Player(String playerName, String team, String position, Integer jerseyNumber,
                  Double pointsPerGame, Double assistsPerGame, Double reboundsPerGame,
                  Double stealsPerGame, Double blocksPerGame, Double turnoversPerGame,
                  Double fieldGoalPercentage, Double threePointPercentage, Double freeThrowPercentage,
                  Double fantasyPointsPerGame, Integer fantasyRanking) {
        this.playerName = playerName;
        this.team = team;
        this.position = position;
        this.jerseyNumber = jerseyNumber;
        this.pointsPerGame = pointsPerGame;
        this.assistsPerGame = assistsPerGame;
        this.reboundsPerGame = reboundsPerGame;
        this.stealsPerGame = stealsPerGame;
        this.blocksPerGame = blocksPerGame;
        this.turnoversPerGame = turnoversPerGame;
        this.fieldGoalPercentage = fieldGoalPercentage;
        this.threePointPercentage = threePointPercentage;
        this.freeThrowPercentage = freeThrowPercentage;
        this.fantasyPointsPerGame = fantasyPointsPerGame;
        this.fantasyRanking = fantasyRanking;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getPlayerName() {
        return playerName;
    }
    
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
    
    public String getTeam() {
        return team;
    }
    
    public void setTeam(String team) {
        this.team = team;
    }
    
    public String getPosition() {
        return position;
    }
    
    public void setPosition(String position) {
        this.position = position;
    }
    
    public Integer getJerseyNumber() {
        return jerseyNumber;
    }
    
    public void setJerseyNumber(Integer jerseyNumber) {
        this.jerseyNumber = jerseyNumber;
    }
    
    public Double getPointsPerGame() {
        return pointsPerGame;
    }
    
    public void setPointsPerGame(Double pointsPerGame) {
        this.pointsPerGame = pointsPerGame;
    }
    
    public Double getAssistsPerGame() {
        return assistsPerGame;
    }
    
    public void setAssistsPerGame(Double assistsPerGame) {
        this.assistsPerGame = assistsPerGame;
    }
    
    public Double getReboundsPerGame() {
        return reboundsPerGame;
    }
    
    public void setReboundsPerGame(Double reboundsPerGame) {
        this.reboundsPerGame = reboundsPerGame;
    }
    
    public Double getStealsPerGame() {
        return stealsPerGame;
    }
    
    public void setStealsPerGame(Double stealsPerGame) {
        this.stealsPerGame = stealsPerGame;
    }
    
    public Double getBlocksPerGame() {
        return blocksPerGame;
    }
    
    public void setBlocksPerGame(Double blocksPerGame) {
        this.blocksPerGame = blocksPerGame;
    }
    
    public Double getTurnoversPerGame() {
        return turnoversPerGame;
    }
    
    public void setTurnoversPerGame(Double turnoversPerGame) {
        this.turnoversPerGame = turnoversPerGame;
    }
    
    public Double getFieldGoalPercentage() {
        return fieldGoalPercentage;
    }
    
    public void setFieldGoalPercentage(Double fieldGoalPercentage) {
        this.fieldGoalPercentage = fieldGoalPercentage;
    }
    
    public Double getThreePointPercentage() {
        return threePointPercentage;
    }
    
    public void setThreePointPercentage(Double threePointPercentage) {
        this.threePointPercentage = threePointPercentage;
    }
    
    public Double getFreeThrowPercentage() {
        return freeThrowPercentage;
    }
    
    public void setFreeThrowPercentage(Double freeThrowPercentage) {
        this.freeThrowPercentage = freeThrowPercentage;
    }
    
    public Double getFantasyPointsPerGame() {
        return fantasyPointsPerGame;
    }
    
    public void setFantasyPointsPerGame(Double fantasyPointsPerGame) {
        this.fantasyPointsPerGame = fantasyPointsPerGame;
    }
    
    public Integer getFantasyRanking() {
        return fantasyRanking;
    }
    
    public void setFantasyRanking(Integer fantasyRanking) {
        this.fantasyRanking = fantasyRanking;
    }

    public Integer getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(Integer gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }
}