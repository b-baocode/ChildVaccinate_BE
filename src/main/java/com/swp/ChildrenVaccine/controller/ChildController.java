package com.swp.ChildrenVaccine.controller;


import com.swp.ChildrenVaccine.entities.Child;
import com.swp.ChildrenVaccine.repository.ChildRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/childrens")
public class ChildController {
    @Autowired
    private ChildRepository childrenRepository;

    @PostMapping("/register")
    public ResponseEntity<Child> registerChild(@RequestBody Child child) {

        Child savedChild = childrenRepository.save(child);
        return ResponseEntity.status(HttpStatus.OK).body(savedChild);
    }

    @GetMapping("")
    List<Child> getListOfChildren() {
        return childrenRepository.findAll();
    }

    @GetMapping("/get/{id}")
    Child getChildById(@PathVariable String id) {
        return childrenRepository.findById(id).orElse(null);
    }

    @GetMapping("/getByCusID/{cusID}")
    List<Child> getChildrenByCusID(@PathVariable String cusID) {
        return childrenRepository.getChildrenByCusId(cusID);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteChild(@PathVariable String id) {
        Child child = childrenRepository.findById(id).orElse(null);
        if (child == null) {
            return ResponseEntity.notFound().build();
        }
        childrenRepository.delete(child);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Child> updateChild(@PathVariable String id, @RequestBody Child childDetails) {
        Child child = childrenRepository.findById(id).orElse(null);
        if (child == null) {
            return ResponseEntity.notFound().build();
        }
        child.setFullName(childDetails.getFullName());
        child.setHeight(childDetails.getHeight());
        child.setWeight(childDetails.getWeight());
        Child updatedChild = childrenRepository.save(child);
        return ResponseEntity.ok(updatedChild);
    }

}
