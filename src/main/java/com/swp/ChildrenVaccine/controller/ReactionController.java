package com.swp.ChildrenVaccine.controller;


import com.swp.ChildrenVaccine.dto.request.ReactionRequest;
import com.swp.ChildrenVaccine.dto.response.VaccinationReactionDTO;
import com.swp.ChildrenVaccine.entities.VaccinationReaction;
import com.swp.ChildrenVaccine.service.ReactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reactions")
public class ReactionController {

    @Autowired
    private ReactionService reactionService;

    @PostMapping
    public ResponseEntity<VaccinationReaction> createReaction(@RequestBody ReactionRequest reactionRequest) {
        VaccinationReaction savedReaction = reactionService.createReaction(reactionRequest);
        return ResponseEntity.ok(savedReaction);
    }

    @GetMapping("/child/{childId}")
    public ResponseEntity<List<VaccinationReaction>> getReactionsByChildId(@PathVariable String childId) {
        List<VaccinationReaction> reactions = reactionService.getReactionsByChildId(childId);
        return ResponseEntity.ok(reactions);
    }

    @GetMapping
    public ResponseEntity<List<VaccinationReactionDTO>> getAllReact() {
        List<VaccinationReactionDTO> dtos = reactionService.getAllReactions()
                .stream()
                .map(reaction -> new VaccinationReactionDTO(
                        reaction.getId(),
                        reaction.getChild().getChildId(),
                        reaction.getChild().getFullName(),
                        reaction.getAppointment().getAppId(),
                        reaction.getSymptoms(),
                        reaction.getSeverity().name(),
                        reaction.getReactionDate()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }
}