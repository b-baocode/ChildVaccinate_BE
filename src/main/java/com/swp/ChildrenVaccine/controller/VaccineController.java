package com.swp.ChildrenVaccine.controller;

import com.swp.ChildrenVaccine.entities.Vaccine;
import com.swp.ChildrenVaccine.entities.VacinePackage;
import com.swp.ChildrenVaccine.service.VaccineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vaccine")
@CrossOrigin(origins = "http://localhost:3000") // Port của React
public class VaccineController {

    @Autowired
    private VaccineService vaccineService;

    @GetMapping("/vaccines")
    public ResponseEntity<List<Vaccine>> getAllVaccines() {
        List<Vaccine> vaccines = vaccineService.getAllVaccines();
        return ResponseEntity.ok(vaccines);
    }

    @GetMapping("/vaccine-packages")
    public ResponseEntity<List<VacinePackage>> getAllPackages() {
        List<VacinePackage> packages = vaccineService.getAllPackages();
        return ResponseEntity.ok(packages);
    }
}
