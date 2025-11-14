package com.fbasketball.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Player entity representing an NBA player with stats from NBA.com
 * Column names match exactly with the scraped data from stats.nba.com
 */
@Entity
@Table(name = "players")
public class Player {
    
    // Using PLAYER_ID from NBA as the primary key (no auto-generation needed)
    @Id
    @NotNull(message = "Player ID is required")
    @Column(name = "player_id")
    private Long playerId;
    
    @NotBlank(message = "Player name is required")
    @Size(min = 2, max = 100, message = "Player name must be between 2 and 100 characters")
    @Column(name = "player_name", nullable = false, length = 100)
    private String playerName;
    
    @Column(name = "team_id")
    private Long teamId;
    
    @NotBlank(message = "Team abbreviation is required")
    @Size(min = 2, max = 5, message = "Team abbreviation must be between 2 and 5 characters")
    @Column(name = "team_abbreviation", nullable = false, length = 5)
    private String teamAbbreviation;
    
    @Min(value = 0, message = "Age cannot be negative")
    @Column(name = "age")
    private Double age;
    
    // Games and Win/Loss stats
    @Min(value = 0, message = "Games played cannot be negative")
    @Column(name = "gp")
    private Double gp;  // games played
    
    @Min(value = 0, message = "Wins cannot be negative")
    @Column(name = "w")
    private Double w;  // wins
    
    @Min(value = 0, message = "Losses cannot be negative")
    @Column(name = "l")
    private Double l;  // losses
    
    @DecimalMin(value = "0.0", message = "Win percentage must be at least 0.0")
    @DecimalMax(value = "1.0", message = "Win percentage cannot exceed 1.0")
    @Column(name = "w_pct")
    private Double wPct;  // win percentage
    
    @Min(value = 0, message = "Minutes cannot be negative")
    @Column(name = "min")
    private Double min;  // average minutes per game
    
    // Field Goal stats
    @Min(value = 0, message = "Field goals made cannot be negative")
    @Column(name = "fgm")
    private Double fgm;  // field goals made
    
    @Min(value = 0, message = "Field goals attempted cannot be negative")
    @Column(name = "fga")
    private Double fga;  // field goals attempted
    
    @DecimalMin(value = "0.0", message = "Field goal percentage must be at least 0.0")
    @DecimalMax(value = "1.0", message = "Field goal percentage cannot exceed 1.0")
    @Column(name = "fg_pct")
    private Double fgPct;  // field goal percentage
    
    // Three-Point stats
    @Min(value = 0, message = "Three-pointers made cannot be negative")
    @Column(name = "fg3m")
    private Double fg3m;  // 3-pointers made
    
    @Min(value = 0, message = "Three-pointers attempted cannot be negative")
    @Column(name = "fg3a")
    private Double fg3a;  // 3-pointers attempted
    
    @DecimalMin(value = "0.0", message = "Three-point percentage must be at least 0.0")
    @DecimalMax(value = "1.0", message = "Three-point percentage cannot exceed 1.0")
    @Column(name = "fg3_pct")
    private Double fg3Pct;  // 3-point percentage
    
    // Free Throw stats
    @Min(value = 0, message = "Free throws made cannot be negative")
    @Column(name = "ftm")
    private Double ftm;  // free throws made
    
    @Min(value = 0, message = "Free throws attempted cannot be negative")
    @Column(name = "fta")
    private Double fta;  // free throws attempted
    
    @DecimalMin(value = "0.0", message = "Free throw percentage must be at least 0.0")
    @DecimalMax(value = "1.0", message = "Free throw percentage cannot exceed 1.0")
    @Column(name = "ft_pct")
    private Double ftPct;  // free throw percentage
    
    // Rebound stats
    @Min(value = 0, message = "Offensive rebounds cannot be negative")
    @Column(name = "oreb")
    private Double oreb;  // offensive rebounds
    
    @Min(value = 0, message = "Defensive rebounds cannot be negative")
    @Column(name = "dreb")
    private Double dreb;  // defensive rebounds
    
    @Min(value = 0, message = "Total rebounds cannot be negative")
    @Column(name = "reb")
    private Double reb;  // total rebounds
    
    // Other stats
    @Min(value = 0, message = "Assists cannot be negative")
    @Column(name = "ast")
    private Double ast;  // assists
    
    @Min(value = 0, message = "Turnovers cannot be negative")
    @Column(name = "tov")
    private Double tov;  // turnovers
    
    @Min(value = 0, message = "Steals cannot be negative")
    @Column(name = "stl")
    private Double stl;  // steals
    
    @Min(value = 0, message = "Blocks cannot be negative")
    @Column(name = "blk")
    private Double blk;  // blocks
    
    @Min(value = 0, message = "Personal fouls cannot be negative")
    @Column(name = "pf")
    private Double pf;  // personal fouls
    
    @Min(value = 0, message = "Points cannot be negative")
    @Column(name = "pts")
    private Double pts;  // points per game
    
    @Column(name = "plus_minus")
    private Double plusMinus;  // plus/minus rating
    
    @Column(name = "nba_fantasy_pts")
    private Double nbaFantasyPts;  // NBA fantasy points
    
    // Default constructor required by JPA
    public Player() {
    }
    
    // Full constructor
    public Player(Long playerId, String playerName, Long teamId, String teamAbbreviation,
                  Double age, Double gp, Double w, Double l, Double wPct, Double min,
                  Double fgm, Double fga, Double fgPct, Double fg3m, Double fg3a, Double fg3Pct,
                  Double ftm, Double fta, Double ftPct, Double oreb, Double dreb, Double reb,
                  Double ast, Double tov, Double stl, Double blk, Double pf, Double pts,
                  Double plusMinus, Double nbaFantasyPts) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.teamId = teamId;
        this.teamAbbreviation = teamAbbreviation;
        this.age = age;
        this.gp = gp;
        this.w = w;
        this.l = l;
        this.wPct = wPct;
        this.min = min;
        this.fgm = fgm;
        this.fga = fga;
        this.fgPct = fgPct;
        this.fg3m = fg3m;
        this.fg3a = fg3a;
        this.fg3Pct = fg3Pct;
        this.ftm = ftm;
        this.fta = fta;
        this.ftPct = ftPct;
        this.oreb = oreb;
        this.dreb = dreb;
        this.reb = reb;
        this.ast = ast;
        this.tov = tov;
        this.stl = stl;
        this.blk = blk;
        this.pf = pf;
        this.pts = pts;
        this.plusMinus = plusMinus;
        this.nbaFantasyPts = nbaFantasyPts;
    }
    
    // Getters and Setters
    public Long getPlayerId() {
        return playerId;
    }
    
    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }
    
    public String getPlayerName() {
        return playerName;
    }
    
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
    
    public Long getTeamId() {
        return teamId;
    }
    
    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }
    
    public String getTeamAbbreviation() {
        return teamAbbreviation;
    }
    
    public void setTeamAbbreviation(String teamAbbreviation) {
        this.teamAbbreviation = teamAbbreviation;
    }
    
    public Double getAge() {
        return age;
    }
    
    public void setAge(Double age) {
        this.age = age;
    }
    
    public Double getGp() {
        return gp;
    }
    
    public void setGp(Double gp) {
        this.gp = gp;
    }
    
    public Double getW() {
        return w;
    }
    
    public void setW(Double w) {
        this.w = w;
    }
    
    public Double getL() {
        return l;
    }
    
    public void setL(Double l) {
        this.l = l;
    }
    
    public Double getWPct() {
        return wPct;
    }
    
    public void setWPct(Double wPct) {
        this.wPct = wPct;
    }
    
    public Double getMin() {
        return min;
    }
    
    public void setMin(Double min) {
        this.min = min;
    }
    
    public Double getFgm() {
        return fgm;
    }
    
    public void setFgm(Double fgm) {
        this.fgm = fgm;
    }
    
    public Double getFga() {
        return fga;
    }
    
    public void setFga(Double fga) {
        this.fga = fga;
    }
    
    public Double getFgPct() {
        return fgPct;
    }
    
    public void setFgPct(Double fgPct) {
        this.fgPct = fgPct;
    }
    
    public Double getFg3m() {
        return fg3m;
    }
    
    public void setFg3m(Double fg3m) {
        this.fg3m = fg3m;
    }
    
    public Double getFg3a() {
        return fg3a;
    }
    
    public void setFg3a(Double fg3a) {
        this.fg3a = fg3a;
    }
    
    public Double getFg3Pct() {
        return fg3Pct;
    }
    
    public void setFg3Pct(Double fg3Pct) {
        this.fg3Pct = fg3Pct;
    }
    
    public Double getFtm() {
        return ftm;
    }
    
    public void setFtm(Double ftm) {
        this.ftm = ftm;
    }
    
    public Double getFta() {
        return fta;
    }
    
    public void setFta(Double fta) {
        this.fta = fta;
    }
    
    public Double getFtPct() {
        return ftPct;
    }
    
    public void setFtPct(Double ftPct) {
        this.ftPct = ftPct;
    }
    
    public Double getOreb() {
        return oreb;
    }
    
    public void setOreb(Double oreb) {
        this.oreb = oreb;
    }
    
    public Double getDreb() {
        return dreb;
    }
    
    public void setDreb(Double dreb) {
        this.dreb = dreb;
    }
    
    public Double getReb() {
        return reb;
    }
    
    public void setReb(Double reb) {
        this.reb = reb;
    }
    
    public Double getAst() {
        return ast;
    }
    
    public void setAst(Double ast) {
        this.ast = ast;
    }
    
    public Double getTov() {
        return tov;
    }
    
    public void setTov(Double tov) {
        this.tov = tov;
    }
    
    public Double getStl() {
        return stl;
    }
    
    public void setStl(Double stl) {
        this.stl = stl;
    }
    
    public Double getBlk() {
        return blk;
    }
    
    public void setBlk(Double blk) {
        this.blk = blk;
    }
    
    public Double getPf() {
        return pf;
    }
    
    public void setPf(Double pf) {
        this.pf = pf;
    }
    
    public Double getPts() {
        return pts;
    }
    
    public void setPts(Double pts) {
        this.pts = pts;
    }
    
    public Double getPlusMinus() {
        return plusMinus;
    }
    
    public void setPlusMinus(Double plusMinus) {
        this.plusMinus = plusMinus;
    }
    
    public Double getNbaFantasyPts() {
        return nbaFantasyPts;
    }
    
    public void setNbaFantasyPts(Double nbaFantasyPts) {
        this.nbaFantasyPts = nbaFantasyPts;
    }
    
    @Override
    public String toString() {
        return "Player{" +
                "playerId=" + playerId +
                ", playerName='" + playerName + '\'' +
                ", teamAbbreviation='" + teamAbbreviation + '\'' +
                ", pts=" + pts +
                ", reb=" + reb +
                ", ast=" + ast +
                '}';
    }
}