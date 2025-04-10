package com.swp.ChildrenVaccine.controller;

import com.swp.ChildrenVaccine.dto.request.UpdateVaccineInPackageRequest;
import com.swp.ChildrenVaccine.dto.request.VacinePackageRequest;
import com.swp.ChildrenVaccine.entities.VaccinationRelation;
import com.swp.ChildrenVaccine.entities.Vaccine;
import com.swp.ChildrenVaccine.entities.VacinePackage;
import com.swp.ChildrenVaccine.service.VaccineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/vaccine-package")
@CrossOrigin(origins = "http://localhost:3000") // Port của React
public class VaccinePackageController {

    @Autowired
    private VaccineService vaccineService;

    @GetMapping("/package/{id}")
    public ResponseEntity<VacinePackage> getPackageById(@PathVariable String id) {
        VacinePackage vacinePackage = vaccineService.getPackageById(id);
        return ResponseEntity.ok(vacinePackage);
    }

    @PostMapping("/create-package")
    public ResponseEntity<VacinePackage> createPackage(@RequestBody VacinePackageRequest vacinePackageRequest) {
        VacinePackage createdPackage = vaccineService.createPackage(vacinePackageRequest);
        return ResponseEntity.ok(createdPackage);
    }

    @GetMapping("/packages-by-vaccine/{vaccineId}")
    public ResponseEntity<List<VacinePackage>> getPackageByVaccineId(@PathVariable String vaccineId) {
        List<VacinePackage> packages = vaccineService.getPackageByVaccineId(vaccineId);
        return ResponseEntity.ok(packages);
    }

    /**
     * update vaccine in package
     * @param packageId
     * @param updateVaccineInPackageRequest
     * @return
     */

    @PutMapping("/update-vaccines/{packageId}")
    public ResponseEntity<VacinePackage> updateVaccineInPackage(
            @PathVariable String packageId,
            @RequestBody UpdateVaccineInPackageRequest updateVaccineInPackageRequest) {
        VacinePackage updatedPackage = vaccineService.updateVaccineInPackage(
                packageId,
                updateVaccineInPackageRequest.getVaccineIds(),
                updateVaccineInPackageRequest.getName(),
                updateVaccineInPackageRequest.getDescription());
        return ResponseEntity.ok(updatedPackage);
    }
}