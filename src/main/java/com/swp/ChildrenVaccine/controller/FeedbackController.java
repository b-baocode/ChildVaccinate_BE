package com.swp.ChildrenVaccine.controller;

import com.swp.ChildrenVaccine.dto.request.FeedbackRequest;
import com.swp.ChildrenVaccine.dto.response.FeedbackDTO;
import com.swp.ChildrenVaccine.entities.RatingFeedback;
import com.swp.ChildrenVaccine.service.AppointmentService;
import com.swp.ChildrenVaccine.service.FeedbackService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping("/submit")
    public ResponseEntity<FeedbackDTO> submitFeedback(@RequestBody FeedbackRequest request, HttpSession httpSession) {
        FeedbackDTO feedbackDTO = feedbackService.saveFeedback(request, httpSession);
        return ResponseEntity.ok(feedbackDTO);
    }

    @GetMapping("/getall") // Changed from POST to GET for better RESTful design
    public ResponseEntity<List<FeedbackDTO>> getAllFeedback() {
        List<FeedbackDTO> feedbackList = feedbackService.getAllFeedback();
        return ResponseEntity.ok(feedbackList);
    }

    @GetMapping("/getByAppId/{appId}")
    public ResponseEntity<List<FeedbackDTO>> getFeedbackByAppId(@PathVariable String appId) {
        List<FeedbackDTO> feedbackList = feedbackService.getFeedbackByAppId(appId);
        return ResponseEntity.ok(feedbackList);
    }

    @GetMapping("/getByCusId/{cusId}")
    public ResponseEntity<List<FeedbackDTO>> getFeedbackByCusId(@PathVariable String cusId) {
        List<FeedbackDTO> feedbackList = feedbackService.getFeedbackByCusId(cusId);
        return ResponseEntity.ok(feedbackList);
    }
}