package com.swp.ChildrenVaccine.service;


import com.swp.ChildrenVaccine.dto.request.ReactionRequest;
import com.swp.ChildrenVaccine.entities.Appointment;
import com.swp.ChildrenVaccine.entities.Child;
import com.swp.ChildrenVaccine.entities.VaccinationReaction;
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


    public List<VaccinationReaction> getReactionsByChildId(String childId) {
        return reactionRepository.findByChild_ChildId(childId);
    }

    public List<VaccinationReaction> getAllReactions() {
        return reactionRepository.findAll();
    }

    public VaccinationReaction createReaction(ReactionRequest reactionRequest) {
        Optional<Child> child = childRepository.findById(reactionRequest.getChildId());
        Optional<Appointment> appointment = appointmentRepository.findByAppId(reactionRequest.getAppointmentId());

        if (child.isPresent() && appointment.isPresent()) {
            VaccinationReaction reaction = new VaccinationReaction();

            // Tạo ID cho reaction
            // Tạo reactionId mới
            String lastId = reactionRepository.findMaxReactionId(); // Lấy ID lớn nhất hiện có
            int newId = 1; // Mặc định là 1 nếu không có ID nào trước đó
            if (lastId != null && lastId.matches("REAC\\d+")) {
                newId = Integer.parseInt(lastId.replace("REAC", "")) + 1;
            }
            reaction.setId(String.format("REAC%03d", newId)); // Format thành REAC001, REAC002, ...

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