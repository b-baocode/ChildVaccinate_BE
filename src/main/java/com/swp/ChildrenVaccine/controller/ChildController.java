package com.swp.ChildrenVaccine.controller;


import com.swp.ChildrenVaccine.dto.response.ChildDTO;
import com.swp.ChildrenVaccine.entities.Child;
import com.swp.ChildrenVaccine.entities.Customer;
import com.swp.ChildrenVaccine.repository.ChildRepository;
import com.swp.ChildrenVaccine.service.ChildService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/childrens")
public class ChildController {

    @Autowired
    private ChildService childService;

    @Autowired
    private ChildRepository childrenRepository;

    @GetMapping("/all")
    public ResponseEntity<List<ChildDTO>> getAllChildren() {
        List<ChildDTO> children = childService.getAllChildren();
        if (children.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(children);
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

//    @GetMapping("")
//    List<Child> getListOfChildren() {
//        return childrenRepository.findAll();
//    }

    @GetMapping("/{id}")
    Child getChildById(@PathVariable String id) {
        return childrenRepository.findById(id).orElse(null);
    }

    @Autowired
    public ChildController(ChildService childService) {
        this.childService = childService;
    }

    @GetMapping("/getByCusID/{cusID}")
    public ResponseEntity<List<ChildDTO>> getChildrenByCustomerId(@PathVariable String cusID) {
        List<ChildDTO> children = childService.getChildrenByCustomerId(cusID);
        if (children.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(children);
    }

    @PutMapping("/update/{id}")
    ResponseEntity<?> updateChild(@PathVariable String id, @RequestBody Child child) {
        Child childToUpdate = childrenRepository.findById(id).orElse(null);
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
        Child childToDelete = childrenRepository.findById(id).orElse(null);
        if (childToDelete == null) {
            return ResponseEntity.notFound().build();
        }
        try{
            childrenRepository.delete(childToDelete);
            return ResponseEntity.ok("Xóa trẻ thành công");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Xóa trẻ thất bại");
        }
    }

}
