package com.swp.ChildrenVaccine.controller;

import com.swp.ChildrenVaccine.dto.request.VacinePackageRequest;
import com.swp.ChildrenVaccine.entities.Vaccine;
import com.swp.ChildrenVaccine.entities.VacinePackage;
import com.swp.ChildrenVaccine.service.VaccineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/vaccine-package")
@CrossOrigin(origins = "http://localhost:3000") // Port của React
public class VaccinePackageController {

    @Autowired
    private VaccineService vaccineService;

    @GetMapping("/vaccine-packages")
    public ResponseEntity<List<VacinePackage>> getAllPackages() {
        List<VacinePackage> packages = vaccineService.getAllPackages();
        return ResponseEntity.ok(packages);
    }


    @GetMapping("/vaccine-packages/{id}")
    public ResponseEntity<VacinePackage> getPackageById(@PathVariable String id) {
        VacinePackage vacinePackage = vaccineService.getPackageById(id);
        return ResponseEntity.ok(vacinePackage);
    }

    //    @PostMapping("/vaccine-packages")
//    public ResponseEntity<VacinePackage> createPackage(@RequestBody VacinePackage vacinePackage) {
//        VacinePackage createdPackage = vaccineService.savePackage(vacinePackage);
//        return ResponseEntity.ok(createdPackage);
//    }
    @PostMapping("/vaccine-packages")
    public ResponseEntity<VacinePackage> createPackage(@RequestBody VacinePackageRequest vacinePackageRequest) {
        VacinePackage vacinePackage = new VacinePackage();
        vacinePackage.setName(vacinePackageRequest.getName());
        vacinePackage.setDescription(vacinePackageRequest.getDescription());
        vacinePackage.setAvailable(vacinePackageRequest.getAvailable());
        vacinePackage.setPrice(vacinePackageRequest.getPrice());
        List<Vaccine> vaccines = vacinePackageRequest.getVaccineIds().stream()
                .map(vaccineService::getVaccineById)
                .collect(Collectors.toList());
        vacinePackage.setVaccines(vaccines);
        VacinePackage createdPackage = vaccineService.savePackage(vacinePackage);
        return ResponseEntity.ok(createdPackage);
    }


//
//    @PutMapping("/vaccine-packages/{id}")
//    public ResponseEntity<VacinePackage> updatePackage(@PathVariable String id, @RequestBody VacinePackage packageDetails) {
//        VacinePackage updatedPackage = vaccineService.updatePackage(id, packageDetails);
//        return ResponseEntity.ok(updatedPackage);
//    }

    @PutMapping("/vaccine-packages/{id}")
    public ResponseEntity<VacinePackage> updatePackage(@PathVariable String id, @RequestBody VacinePackageRequest vacinePackageRequest) {
        VacinePackage packageDetails = new VacinePackage();
        packageDetails.setName(vacinePackageRequest.getName());
        packageDetails.setDescription(vacinePackageRequest.getDescription());
        packageDetails.setAvailable(vacinePackageRequest.getAvailable());
        packageDetails.setPrice(vacinePackageRequest.getPrice());
        List<Vaccine> vaccines = vacinePackageRequest.getVaccineIds().stream()
                .map(vaccineService::getVaccineById)
                .collect(Collectors.toList());
        packageDetails.setVaccines(vaccines);
        VacinePackage updatedPackage = vaccineService.updatePackage(id, packageDetails);
        return ResponseEntity.ok(updatedPackage);
    }

    @DeleteMapping("/vaccine-packages/{id}")
    public ResponseEntity<Void> deletePackage(@PathVariable String id) {
        vaccineService.deletePackage(id);
        return ResponseEntity.noContent().build();
    }
}