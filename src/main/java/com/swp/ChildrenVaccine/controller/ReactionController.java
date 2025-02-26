package com.swp.ChildrenVaccine.controller;


import com.swp.ChildrenVaccine.dto.request.ReactionRequest;
import com.swp.ChildrenVaccine.entities.Reaction;
import com.swp.ChildrenVaccine.service.ReactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reactions")
public class ReactionController {

    @Autowired
    private ReactionService reactionService;

    @PostMapping
    public ResponseEntity<Reaction> createReaction(@RequestBody ReactionRequest reactionRequest) {
        Reaction savedReaction = reactionService.createReaction(reactionRequest);
        return ResponseEntity.ok(savedReaction);
    }

    @GetMapping("/child/{childId}")
    public ResponseEntity<List<Reaction>> getReactionsByChildId(@PathVariable String childId) {
        List<Reaction> reactions = reactionService.getReactionsByChildId(childId);
        return ResponseEntity.ok(reactions);
    }
}