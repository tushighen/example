package com.golomt.example.entity;

import com.golomt.example.dto.IGeneralDTO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ROSTER")
public class Roster implements IGeneralDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @Column(nullable = false, name = "MATCH_ID")
    private Integer matchId;

    @Column(unique = true, nullable = false, name = "ROSTER_ID")
    private String rosterId;

    @Column(nullable = false, name = "RANK")
    private Integer rank;

    @Column(nullable = false, name = "TEAM_ID")
    private Integer teamId;

    @Column(nullable = false, name = "IS_WON")
    private boolean isWon;

    @Transient
    private List<String> participants;

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

    public String getRosterId() {
        return rosterId;
    }

    public void setRosterId(String rosterId) {
        this.rosterId = rosterId;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public boolean isWon() {
        return isWon;
    }

    public void setWon(boolean won) {
        isWon = won;
    }

    public List<String> getParticipants() {
        return participants != null ? this.participants : (this.participants = new ArrayList<>());
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }
}