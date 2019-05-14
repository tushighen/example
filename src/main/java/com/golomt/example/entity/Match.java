package com.golomt.example.entity;

import com.golomt.example.dto.IGeneralDTO;

import javax.persistence.*;

@Entity
@Table(name = "MATCHES")
public class Match implements IGeneralDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @Column(unique = true, nullable = false, name = "MATCH_ID")
    private String matchId;

    @Column(nullable = false, name = "IS_CUSTOM_MATCH")
    private boolean isCustomMatch;

    @Column(nullable = false, name = "GAME_MODE")
    private String gameMode;

    @Column(nullable = false, name = "MAP_NAME")
    private String mapName;

    @Column(nullable = false, name = "DURATION")
    private Integer duration;

    @Column(nullable = false, name = "ROSTER_COUNT")
    private Integer rosterCount;

    @Column(name = "PARTICIPANT_COUNT")
    private Integer participantCount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public boolean isCustomMatch() {
        return isCustomMatch;
    }

    public void setCustomMatch(boolean customMatch) {
        isCustomMatch = customMatch;
    }

    public String getGameMode() {
        return gameMode;
    }

    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getRosterCount() {
        return rosterCount;
    }

    public void setRosterCount(Integer rosterCount) {
        this.rosterCount = rosterCount;
    }

    public Integer getParticipantCount() {
        return participantCount;
    }

    public void setParticipantCount(Integer participantCount) {
        this.participantCount = participantCount;
    }
}
