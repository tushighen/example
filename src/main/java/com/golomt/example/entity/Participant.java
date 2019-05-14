package com.golomt.example.entity;

import com.golomt.example.dto.IGeneralDTO;

import javax.persistence.*;

@Entity
@Table(name = "PARTICIPANT")
public class Participant implements IGeneralDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @Column(nullable = false, name = "MATCH_ID")
    private Integer matchId;

    @Column(nullable = false, name = "ROSTER_ID")
    private Integer rosterId;

    @Column(unique = true, nullable = false, name = "PARTICIPANT_ID")
    private String participantId;

    @Column(nullable = false, name = "DBNOs")
    private Integer dbnos;

    @Column(nullable = false, name = "ASSISTS")
    private Integer assists;

    @Column(nullable = false, name = "BOOSTS")
    private Integer boosts;

    @Column(nullable = false, name = "DAMAGE_DEALT")
    private Double damageDealt;

    @Column(nullable = false, name = "DEATH_TYPE")
    private String deathType;

    @Column(nullable = false, name = "HEAD_SHOT_KILLS")
    private Integer headshotKills;

    @Column(nullable = false, name = "HEALS")
    private Integer heals;

    @Column(nullable = false, name = "KILL_PLACE")
    private Integer killPlace;

    @Column(nullable = false, name = "KILL_STREAKS")
    private Integer killStreaks;

    @Column(nullable = false, name = "KILLS")
    private Integer kills;

    @Column(nullable = false, name = "LONGEST_KILL")
    private Double longestKill;

    @Column(nullable = false, name = "NAME")
    private String name;

    @Column(nullable = false, name = "PLAYED_ID")
    private String playerId;

    @Column(nullable = false, name = "REVIVES")
    private Integer revives;

    @Column(nullable = false, name = "RIDE_DISTANCE")
    private Double rideDistance;

    @Column(nullable = false, name = "ROAD_KILLS")
    private Integer roadKills;

    @Column(nullable = false, name = "SWIM_DISTANCE")
    private Double swimDistance;

    @Column(nullable = false, name = "TEAM_KILLS")
    private Integer teamKills;

    @Column(nullable = false, name = "TIME_SURVIVED")
    private Double timeSurvived;

    @Column(nullable = false, name = "VEHICLE_DESTROYS")
    private Integer vehicleDestroys;

    @Column(nullable = false, name = "WALK_DISTANCE")
    private Double walkDistance;

    @Column(nullable = false, name = "WEAPONS_ACQUIRED")
    private Integer weaponsAcquired;

    @Column(nullable = false, name = "WIN_PLACE")
    private Integer winPlace;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMatchId() {
        return matchId;
    }

    public void setMatchId(Integer matchId) {
        this.matchId = matchId;
    }

    public Integer getRosterId() {
        return rosterId;
    }

    public void setRosterId(Integer rosterId) {
        this.rosterId = rosterId;
    }

    public String getParticipantId() {
        return participantId;
    }

    public void setParticipantId(String participantId) {
        this.participantId = participantId;
    }

    public Integer getDbnos() {
        return dbnos;
    }

    public void setDbnos(Integer dbnos) {
        this.dbnos = dbnos;
    }

    public Integer getAssists() {
        return assists;
    }

    public void setAssists(Integer assists) {
        this.assists = assists;
    }

    public Integer getBoosts() {
        return boosts;
    }

    public void setBoosts(Integer boosts) {
        this.boosts = boosts;
    }

    public Double getDamageDealt() {
        return damageDealt;
    }

    public void setDamageDealt(Double damageDealt) {
        this.damageDealt = damageDealt;
    }

    public String getDeathType() {
        return deathType;
    }

    public void setDeathType(String deathType) {
        this.deathType = deathType;
    }

    public Integer getHeadshotKills() {
        return headshotKills;
    }

    public void setHeadshotKills(Integer headshotKills) {
        this.headshotKills = headshotKills;
    }

    public Integer getHeals() {
        return heals;
    }

    public void setHeals(Integer heals) {
        this.heals = heals;
    }

    public Integer getKillPlace() {
        return killPlace;
    }

    public void setKillPlace(Integer killPlace) {
        this.killPlace = killPlace;
    }

    public Integer getKillStreaks() {
        return killStreaks;
    }

    public void setKillStreaks(Integer killStreaks) {
        this.killStreaks = killStreaks;
    }

    public Integer getKills() {
        return kills;
    }

    public void setKills(Integer kills) {
        this.kills = kills;
    }

    public Double getLongestKill() {
        return longestKill;
    }

    public void setLongestKill(Double longestKill) {
        this.longestKill = longestKill;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public Integer getRevives() {
        return revives;
    }

    public void setRevives(Integer revives) {
        this.revives = revives;
    }

    public Double getRideDistance() {
        return rideDistance;
    }

    public void setRideDistance(Double rideDistance) {
        this.rideDistance = rideDistance;
    }

    public Integer getRoadKills() {
        return roadKills;
    }

    public void setRoadKills(Integer roadKills) {
        this.roadKills = roadKills;
    }

    public Double getSwimDistance() {
        return swimDistance;
    }

    public void setSwimDistance(Double swimDistance) {
        this.swimDistance = swimDistance;
    }

    public Integer getTeamKills() {
        return teamKills;
    }

    public void setTeamKills(Integer teamKills) {
        this.teamKills = teamKills;
    }

    public Double getTimeSurvived() {
        return timeSurvived;
    }

    public void setTimeSurvived(Double timeSurvived) {
        this.timeSurvived = timeSurvived;
    }

    public Integer getVehicleDestroys() {
        return vehicleDestroys;
    }

    public void setVehicleDestroys(Integer vehicleDestroys) {
        this.vehicleDestroys = vehicleDestroys;
    }

    public Double getWalkDistance() {
        return walkDistance;
    }

    public void setWalkDistance(Double walkDistance) {
        this.walkDistance = walkDistance;
    }

    public Integer getWeaponsAcquired() {
        return weaponsAcquired;
    }

    public void setWeaponsAcquired(Integer weaponsAcquired) {
        this.weaponsAcquired = weaponsAcquired;
    }

    public Integer getWinPlace() {
        return winPlace;
    }

    public void setWinPlace(Integer winPlace) {
        this.winPlace = winPlace;
    }
}