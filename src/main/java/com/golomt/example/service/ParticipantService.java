package com.golomt.example.service;

import com.golomt.example.entity.Participant;
import com.golomt.example.repository.ParticipantRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParticipantService {

    /**
     * Autowire
     **/

    @Autowired
    ParticipantRepository repository;
}
