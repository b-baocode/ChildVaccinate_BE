package com.swp.ChildrenVaccine.controller;

import com.swp.ChildrenVaccine.dto.request.VaccinationReactionRequest;
import com.swp.ChildrenVaccine.entities.Customer;
import com.swp.ChildrenVaccine.entities.VaccinationReaction;
import com.swp.ChildrenVaccine.repository.CustomerRepository;
import com.swp.ChildrenVaccine.repository.VaccinationReactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/vaccination-reactions")
public class VaccinnationReactionController {

    @Autowired
    private VaccinationReactionRepository vaccinationReactionRepository;



    @PostMapping
    public ResponseEntity<VaccinationReaction> createReaction(@RequestBody VaccinationReactionRequest request) {
        VaccinationReaction reaction = new VaccinationReaction();
        reaction.setReactionDate(request.getReactionDate());
        reaction.setSymptoms(request.getSymptoms());
        reaction.setSeverity(request.getSeverity());
        reaction.setNotes(request.getNotes());
        VaccinationReaction savedReaction = vaccinationReactionRepository.save(reaction);
        return ResponseEntity.ok(savedReaction);
    }


}