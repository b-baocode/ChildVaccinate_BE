package com.swp.ChildrenVaccine.controller;

import com.swp.ChildrenVaccine.dto.request.ChangeVaccineQuantityRequest;
import com.swp.ChildrenVaccine.dto.request.VaccineRequest;
import com.swp.ChildrenVaccine.dto.request.VaccineUpdateRequest;
import com.swp.ChildrenVaccine.entities.Vaccine;
import com.swp.ChildrenVaccine.entities.VacinePackage;
import com.swp.ChildrenVaccine.service.VaccineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/vaccine")
@CrossOrigin(origins = "http://localhost:3000") // Port của React
public class VaccineController {

    @Autowired
    private VaccineService vaccineService;

    //get all vaccines
    @GetMapping("/vaccines")
    public ResponseEntity<List<Vaccine>> getAllVaccines() {
        List<Vaccine> vaccines = vaccineService.getAllVaccines();
        return ResponseEntity.ok(vaccines);
    }

    //get all packages
    @GetMapping("/vaccine-packages")
    public ResponseEntity<List<VacinePackage>> getAllPackages() {
        List<VacinePackage> packages = vaccineService.getAllPackages();
        return ResponseEntity.ok(packages);
    }

    @GetMapping("/vaccines/{id}")
    public ResponseEntity<Vaccine> getVaccineById(@PathVariable String id) {
        Vaccine vaccine = vaccineService.getVaccineById(id);
        return ResponseEntity.ok(vaccine);
    }

    //get vaccine by packageId
    @GetMapping("/vaccines-by-package/{packageId}")
    public ResponseEntity<List<Vaccine>> getVaccinesByPackageId(@PathVariable String packageId) {
        List<Vaccine> vaccines = vaccineService.getVaccinesByPackageId(packageId);
        return ResponseEntity.ok(vaccines);
    }


    //get total cost by packageId -test
    @GetMapping("/total-cost-by-package/{packageId}")
    public ResponseEntity<BigDecimal> calculateTotalCostByPackageId(@PathVariable String packageId) {
        BigDecimal totalCost = vaccineService.calculateTotalCostByPackageId(packageId);
        return ResponseEntity.ok(totalCost);
    }

    // create
    @PostMapping("/add")
    public ResponseEntity<Vaccine> addVaccine(@RequestBody VaccineRequest vaccineRequest) {
        Vaccine newVaccine = vaccineService.addVaccine(vaccineRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(newVaccine);
    }

    @PutMapping("/update/{vaccineId}")
    public ResponseEntity<Vaccine> updateVaccineInfo(
            @PathVariable String vaccineId,
            @RequestBody VaccineUpdateRequest vaccineUpdateRequest) {
        Vaccine updatedVaccine = vaccineService.updateVaccineInfo(
                vaccineId,
                vaccineUpdateRequest.getDescription(),
                vaccineUpdateRequest.getPrice(),
                vaccineUpdateRequest.getAgeMonth());
        return ResponseEntity.ok(updatedVaccine);
    }

    @PutMapping("/change-quantity/{vaccineId}")
    public ResponseEntity<Vaccine> changeVaccineQuantity(
            @PathVariable String vaccineId,
            @RequestBody ChangeVaccineQuantityRequest changeVaccineQuantityRequest) {
        Vaccine updatedVaccine = vaccineService.changeVaccineQuantity(
                vaccineId,
                changeVaccineQuantityRequest.getInputQuantity(),
                changeVaccineQuantityRequest.isMethod());
        return ResponseEntity.ok(updatedVaccine);
    }

    @GetMapping("/vaccines-by-name/{name}")
    public ResponseEntity<Vaccine> getVaccineByName(@PathVariable String name) {
        Vaccine vaccine = vaccineService.getVaccineByName(name);
        return ResponseEntity.ok(vaccine);
    }

}
