package com.swp.ChildrenVaccine.controller;

import com.swp.ChildrenVaccine.entities.Child;
import com.swp.ChildrenVaccine.repository.ChildRepository;
import com.swp.ChildrenVaccine.service.ChildService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/children")
public class ChildrenController {
    @Autowired
    private ChildService childService;

    @Autowired
    private ChildRepository childRepository;

    @GetMapping("/all")
    ResponseEntity<?> getAllChildren() {
        return ResponseEntity.ok(childRepository.findAll());
    }

    @PostMapping("/{cusId}/add")
    ResponseEntity<?> addChild(@PathVariable String cusId ,@RequestBody Child child) {
        try{
            childService.addChild(cusId ,child);
            return ResponseEntity.ok("Thêm trẻ thành công");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Thêm trẻ thất bại");
        }
    }

    @GetMapping("/{id}")
    ResponseEntity<?> getChildById(@PathVariable String id) {
        return ResponseEntity.ok(childRepository.findById(id));
    }

    @GetMapping("/{cusId}/all")
    ResponseEntity<?> getChildrenByCusId(@PathVariable String cusId) {
        return ResponseEntity.ok(childRepository.getChildrenByCusId(cusId));
    }

    @PutMapping("/update/{id}")
    ResponseEntity<?> updateChild(@PathVariable String id, @RequestBody Child child) {
        Child childToUpdate = childRepository.findById(id).orElse(null);
        if (childToUpdate == null) {
            return ResponseEntity.notFound().build();
        }
        try{
            childService.updateChild(id, child);
            return ResponseEntity.ok("Cập nhật thông tin trẻ thành công");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Cập nhật thông tin trẻ thất bại");
        }
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<?> deleteChild(@PathVariable String id) {
        Child childToDelete = childRepository.findById(id).orElse(null);
        if (childToDelete == null) {
            return ResponseEntity.notFound().build();
        }
        try{
            childRepository.delete(childToDelete);
            return ResponseEntity.ok("Xóa trẻ thành công");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Xóa trẻ thất bại");
        }
    }

}
