package com.swp.ChildrenVaccine.service;


import com.swp.ChildrenVaccine.dto.request.ReactionRequest;
import com.swp.ChildrenVaccine.entities.Appointment;
import com.swp.ChildrenVaccine.entities.Child;
import com.swp.ChildrenVaccine.entities.Reaction;
import com.swp.ChildrenVaccine.repository.AppointmentRepository;
import com.swp.ChildrenVaccine.repository.ChildRepository;
import com.swp.ChildrenVaccine.repository.ReactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReactionService {

    @Autowired
    private ReactionRepository reactionRepository;
    @Autowired
    private ChildRepository childRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;


    public List<Reaction> getReactionsByChildId(String childId) {
        return reactionRepository.findByChild_ChildId(childId);
    }


    public Reaction createReaction(ReactionRequest reactionRequest) {
        Optional<Child> child = childRepository.findById(reactionRequest.getChildId());
        Optional<Appointment> appointment = appointmentRepository.findById(reactionRequest.getAppointmentId());

        if (child.isPresent() && appointment.isPresent()) {
            Reaction reaction = new Reaction();

            // Tạo ID cho reaction
            String reactionId = "REAC" + System.currentTimeMillis();
            reaction.setId(reactionId);

            reaction.setChild(child.get());
            reaction.setAppointment(appointment.get());
            reaction.setSymptoms(reactionRequest.getSymptoms());
            reaction.setSeverity(reactionRequest.getSeverity());
            reaction.setReactionDate(reactionRequest.getReactionDate());

            return reactionRepository.save(reaction);
        } else {
            throw new IllegalArgumentException("Invalid Child or Appointment ID");
        }
    }
}