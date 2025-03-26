package com.swp.ChildrenVaccine.controller;

import com.swp.ChildrenVaccine.dto.request.VaccineRequest;
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

//    @GetMapping("/vaccine-packages")
//    public ResponseEntity<List<VacinePackage>> getAllPackages() {
//        List<VacinePackage> packages = vaccineService.getAllPackages();
//        return ResponseEntity.ok(packages);
//    }

    @GetMapping("/vaccines/{id}")
    public ResponseEntity<Vaccine> getVaccineById(@PathVariable String id) {
        Vaccine vaccine = vaccineService.getVaccineById(id);
        return ResponseEntity.ok(vaccine);
    }

    @PostMapping("/vaccines")
    public ResponseEntity<Vaccine> createVaccine(@RequestBody VaccineRequest vaccineRequest) {
        Vaccine vaccine = new Vaccine();
        vaccine.setName(vaccineRequest.getName());
        vaccine.setDescription(vaccineRequest.getDescription());
        vaccine.setManufacturer(vaccineRequest.getManufacturer());
        vaccine.setShotNumber(vaccineRequest.getShotNumber());
        vaccine.setQuantity(vaccineRequest.getQuantity());
        vaccine.setPrice(vaccineRequest.getPrice());
        vaccine.setGapDays(vaccineRequest.getGapDays());
        Vaccine createdVaccine = vaccineService.saveVaccine(vaccine);
        return ResponseEntity.ok(createdVaccine);
    }

//    @PutMapping("/vaccines/{id}")
//    public ResponseEntity<Vaccine> updateVaccine(@PathVariable String id, @RequestBody Vaccine vaccineDetails) {
//        Vaccine updatedVaccine = vaccineService.updateVaccine(id, vaccineDetails);
//        return ResponseEntity.ok(updatedVaccine);
//    }
    @PutMapping("/vaccines/{id}")
    public ResponseEntity<Vaccine> updateVaccine(@PathVariable String id, @RequestBody VaccineRequest vaccineRequest) {
        Vaccine vaccineDetails = new Vaccine();
        vaccineDetails.setName(vaccineRequest.getName());
        vaccineDetails.setDescription(vaccineRequest.getDescription());
        vaccineDetails.setManufacturer(vaccineRequest.getManufacturer());
        vaccineDetails.setShotNumber(vaccineRequest.getShotNumber());
        vaccineDetails.setQuantity(vaccineRequest.getQuantity());
        vaccineDetails.setPrice(vaccineRequest.getPrice());
        vaccineDetails.setGapDays(vaccineRequest.getGapDays());
        Vaccine updatedVaccine = vaccineService.updateVaccine(id, vaccineDetails);
        return ResponseEntity.ok(updatedVaccine);
    }

    @DeleteMapping("/vaccines/{id}")
    public ResponseEntity<Void> deleteVaccine(@PathVariable String id) {
        vaccineService.deleteVaccine(id);
        return ResponseEntity.noContent().build();
    }
}
