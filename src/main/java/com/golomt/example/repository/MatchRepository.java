package com.golomt.example.repository;

import com.golomt.example.entity.Match;
import org.springframework.data.repository.CrudRepository;

public interface MatchRepository extends CrudRepository<Match, Integer> {

    boolean existsByMatchId(String matchId);

}
