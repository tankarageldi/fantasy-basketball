package com.fbasketball.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fbasketball.demo.model.Player;
import com.fbasketball.demo.repository.PlayerRepository;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;
    
    // Get all players
    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    // Get player by ID
    public Optional<Player> getPlayerById(Long playerId) {
        return playerRepository.findById(playerId);
    }

    // Search by name
    public List<Player> searchPlayersByName(String name) {
        return playerRepository.findByPlayerNameContainingIgnoreCase(name);
    }

    // Get by team
    public List<Player> getPlayersByTeam(String teamAbbreviation) {
        return playerRepository.findByTeamAbbreviation(teamAbbreviation);
    }

    // Top fantasy players
    public List<Player> getTopFantasyPlayers(int limit) {
        return playerRepository.findTopNByOrderByNbaFantasyPtsDesc(limit);
    }

    // Top scorers
    public List<Player> getTopScorers(int limit) {
        return playerRepository.findTopNByOrderByPtsDesc(limit);
    }

    // Most efficient shooters
    public List<Player> getEfficientShooters(int limit) {
        return playerRepository.findTopNByOrderByFgPctDesc(limit);
    }

    // Player count
    public long getTotalPlayerCount() {
        return playerRepository.count();
    }

    // Check if player exists
    public boolean playerExists(Long playerId) {
        return playerRepository.existsById(playerId);
    }
    // Add these methods to PlayerService

public Player createPlayer(Player player) {
    return playerRepository.save(player);
}

public Player updatePlayer(Long playerId, Player playerDetails) {
    Player existingPlayer = playerRepository.findById(playerId)
            .orElseThrow(() -> new RuntimeException("Player not found with id: " + playerId));
    
    // Update all fields
    existingPlayer.setPlayerName(playerDetails.getPlayerName());
    existingPlayer.setTeamId(playerDetails.getTeamId());
    existingPlayer.setTeamAbbreviation(playerDetails.getTeamAbbreviation());
    existingPlayer.setAge(playerDetails.getAge());
    existingPlayer.setGp(playerDetails.getGp());
    existingPlayer.setW(playerDetails.getW());
    existingPlayer.setL(playerDetails.getL());
    existingPlayer.setWPct(playerDetails.getWPct());
    existingPlayer.setMin(playerDetails.getMin());
    existingPlayer.setFgm(playerDetails.getFgm());
    existingPlayer.setFga(playerDetails.getFga());
    existingPlayer.setFgPct(playerDetails.getFgPct());
    existingPlayer.setFg3m(playerDetails.getFg3m());
    existingPlayer.setFg3a(playerDetails.getFg3a());
    existingPlayer.setFg3Pct(playerDetails.getFg3Pct());
    existingPlayer.setFtm(playerDetails.getFtm());
    existingPlayer.setFta(playerDetails.getFta());
    existingPlayer.setFtPct(playerDetails.getFtPct());
    existingPlayer.setOreb(playerDetails.getOreb());
    existingPlayer.setDreb(playerDetails.getDreb());
    existingPlayer.setReb(playerDetails.getReb());
    existingPlayer.setAst(playerDetails.getAst());
    existingPlayer.setTov(playerDetails.getTov());
    existingPlayer.setStl(playerDetails.getStl());
    existingPlayer.setBlk(playerDetails.getBlk());
    existingPlayer.setPf(playerDetails.getPf());
    existingPlayer.setPts(playerDetails.getPts());
    existingPlayer.setPlusMinus(playerDetails.getPlusMinus());
    existingPlayer.setNbaFantasyPts(playerDetails.getNbaFantasyPts());
    
    return playerRepository.save(existingPlayer);
}

public void deletePlayer(Long playerId) {
    playerRepository.deleteById(playerId);
}
}