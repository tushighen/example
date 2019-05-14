package com.golomt.example.repository;

import com.golomt.example.entity.Roster;
import org.springframework.data.repository.CrudRepository;

public interface RosterRepository extends CrudRepository<Roster, Integer> {
}
