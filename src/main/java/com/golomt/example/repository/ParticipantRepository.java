package com.golomt.example.repository;

import com.golomt.example.entity.Participant;
import org.springframework.data.repository.CrudRepository;

public interface ParticipantRepository extends CrudRepository<Participant, Integer> {
}
