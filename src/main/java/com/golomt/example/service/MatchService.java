package com.golomt.example.service;

import com.golomt.example.entity.Match;
import com.golomt.example.entity.Participant;
import com.golomt.example.entity.Roster;
import com.golomt.example.exception.NotFoundException;
import com.golomt.example.exception.RMIException;
import com.golomt.example.repository.MatchRepository;
import com.golomt.example.repository.ParticipantRepository;
import com.golomt.example.repository.RosterRepository;
import com.golomt.example.service.helper.PubgHelperService;
import com.golomt.example.utilities.LogUtilities;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Service
public class MatchService {

    /**
     * Autowire
     **/

    @Autowired
    PubgHelperService helper;

    @Autowired
    MatchRepository repository;

    @Autowired
    ParticipantRepository participantRepository;

    @Autowired
    RosterRepository rosterRepository;

    /**
     * do.Inquire.Match
     **/

    public void doInquireMatch() throws RMIException {
        try {
            LogUtilities.info(this.getClass().getName(), "[srvc][match][inq][ini]");

            ResteasyClient client = new ResteasyClientBuilder().build();
            Response response = helper.doSendRequest(client);

            try {
                if (response.getStatus() == HttpStatus.OK.value()) {
                    this.doInquireMatchExtension(new JSONObject(response.readEntity(String.class)));
                } else {
                    throw new RMIException("[response.invalid][" + response.getStatus() + "][" + response.getStatusInfo() + "]");
                }
            } finally {
                helper.doClose(client, response);
            }

            LogUtilities.info(this.getClass().getName(), "[srvc][match][inq][end]");
        } catch (RMIException ex) {
            LogUtilities.error(this.getClass().getName(), "[srvc][match][inq][rmi]");
            throw ex;
        } catch (Exception ex) {
            LogUtilities.fatal(this.getClass().getName(), "[srvc][match][inq][unknown]", ex);
            throw ex;
        }
    }

    /**
     * do.Inquire.Match.Extension
     **/

    private void doInquireMatchExtension(JSONObject response) {

        try {

            Match match = new Match();
            List<Roster> rosterList = new ArrayList<>();

            JSONObject data = response.getJSONObject("data");
            JSONObject attributes = data.getJSONObject("attributes");
            JSONArray rosters = data.getJSONObject("relationships").getJSONObject("rosters").getJSONArray("data");

            JSONArray included = response.getJSONArray("included");

            match.setMatchId(data.getString("id"));
            match.setCustomMatch(attributes.getBoolean("isCustomMatch"));
            match.setGameMode(attributes.getString("gameMode"));
            match.setMapName(attributes.getString("mapName"));
            match.setDuration(attributes.getInt("duration"));
            match.setRosterCount(rosters.length());

            if (!repository.existsByMatchId(match.getMatchId()))
                repository.save(match);

            for (Object object : included) {
                if (object instanceof JSONObject) {
                    if (((JSONObject) object).get("type").equals("roster")) {

                        attributes = ((JSONObject) object).getJSONObject("attributes");
                        JSONObject stats = attributes.getJSONObject("stats");
                        JSONArray participants = ((JSONObject) object).getJSONObject("relationships").getJSONObject("participants").getJSONArray("data");

                        Roster roster = new Roster();
                        roster.setMatchId(match.getId());
                        roster.setRosterId(((JSONObject) object).getString("id"));
                        roster.setRank(stats.getInt("rank"));
                        roster.setTeamId(stats.getInt("teamId"));
                        roster.setWon(Boolean.valueOf(attributes.getString("won")));

                        for (Object participant : participants) {
                            if (participant instanceof JSONObject) {
                                roster.getParticipants().add(((JSONObject) participant).getString("id"));
                            }
                        }

                        rosterRepository.save(roster);
                        rosterList.add(roster);

                    }
                }
            }

            int participantCount = 0;
            for (Object object : included) {
                if (object instanceof JSONObject) {
                    if (((JSONObject) object).get("type").equals("participant")) {

                        JSONObject stats = ((JSONObject) object).getJSONObject("attributes").getJSONObject("stats");

                        for (Roster roster : rosterList) {
                            if (roster.getParticipants().contains(((JSONObject) object).getString("id"))) {
                                Participant participant = new Participant();
                                participant.setMatchId(match.getId());
                                participant.setRosterId(roster.getId());
                                participant.setParticipantId(((JSONObject) object).getString("id"));
                                participant.setDbnos(stats.getInt("DBNOs"));
                                participant.setAssists(stats.getInt("assists"));
                                participant.setBoosts(stats.getInt("boosts"));
                                participant.setDamageDealt(stats.getDouble("damageDealt"));
                                participant.setDeathType(stats.getString("deathType"));
                                participant.setHeadshotKills(stats.getInt("headshotKills"));
                                participant.setHeals(stats.getInt("heals"));
                                participant.setKillPlace(stats.getInt("killPlace"));
                                participant.setKillStreaks(stats.getInt("killStreaks"));
                                participant.setKills(stats.getInt("kills"));
                                participant.setLongestKill(stats.getDouble("longestKill"));
                                participant.setName(stats.getString("name"));
                                participant.setPlayerId(stats.getString("playerId"));
                                participant.setRevives(stats.getInt("revives"));
                                participant.setRideDistance(stats.getDouble("rideDistance"));
                                participant.setRoadKills(stats.getInt("roadKills"));
                                participant.setSwimDistance(stats.getDouble("swimDistance"));
                                participant.setTeamKills(stats.getInt("teamKills"));
                                participant.setTimeSurvived(stats.getDouble("timeSurvived"));
                                participant.setVehicleDestroys(stats.getInt("vehicleDestroys"));
                                participant.setWalkDistance(stats.getDouble("walkDistance"));
                                participant.setWeaponsAcquired(stats.getInt("weaponsAcquired"));
                                participant.setWinPlace(stats.getInt("winPlace"));

                                participantRepository.save(participant);

                                if (participant.getId() != null)
                                    participantCount++;

                                break;
                            }
                        }
                    }
                }
            }

            match.setParticipantCount(participantCount);
            repository.save(match);

        } catch (Exception ex) {
            throw ex;
        }
    }

}