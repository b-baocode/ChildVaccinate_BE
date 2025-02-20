package com.swp.ChildrenVaccine.controller;

import com.swp.ChildrenVaccine.dto.request.FeedbackRequest;
import com.swp.ChildrenVaccine.service.FeedbackService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @PostMapping("/submit")
    public ResponseEntity<String> submitFeedback(@RequestBody FeedbackRequest request, HttpSession httpSession) {
        feedbackService.saveFeedback(request, httpSession);
        return ResponseEntity.ok("Đánh giá đã được lưu thành công!");
    }
}
