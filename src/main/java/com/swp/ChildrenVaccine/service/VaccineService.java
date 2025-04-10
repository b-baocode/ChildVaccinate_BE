package com.swp.ChildrenVaccine.service;

import com.swp.ChildrenVaccine.dto.request.VaccineRequest;
import com.swp.ChildrenVaccine.dto.request.VacinePackageRequest;
import com.swp.ChildrenVaccine.entities.VaccinationRelation;
import com.swp.ChildrenVaccine.entities.Vaccine;
import com.swp.ChildrenVaccine.entities.VacinePackage;
import com.swp.ChildrenVaccine.repository.ScheduleRepository;
import com.swp.ChildrenVaccine.repository.VaccinationRelationRepository;
import com.swp.ChildrenVaccine.repository.VaccinePackageRepository;
import com.swp.ChildrenVaccine.repository.VaccineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VaccineService {

    @Autowired
    private VaccineRepository vaccineRepository;

    @Autowired
    private VaccinePackageRepository vacinePackageRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private VaccinationRelationRepository vaccinationRelationRepository;
    @Autowired
    private VaccinePackageRepository vaccinePackageRepository;

    public List<Vaccine> getAllVaccines() {
        List<Vaccine> allVaccines = vaccineRepository.findAll();

        // Set available field of packages containing vaccines with quantity = 0 to 0
        allVaccines.stream()
                .filter(vaccine -> vaccine.getQuantity() == 0)
                .forEach(vaccine -> {
                    List<VacinePackage> packages = getPackageByVaccineId(vaccine.getVaccineId());
                    packages.forEach(pkg -> {
                        pkg.setAvailable(false);
                        vaccinePackageRepository.save(pkg);
                    });
                });

        return allVaccines;
    }


    public void updateVaccineQuantity(Vaccine vaccine, int totalShot) {
        int newQuantity = vaccine.getQuantity() - totalShot;
        vaccine.setQuantity(newQuantity);
        vaccineRepository.save(vaccine);
    }

    public List<Vaccine> getVaccinesByPackageId(String packageId) {
        List<VaccinationRelation> relations = vaccinationRelationRepository.findByPackageId(packageId);
        return relations.stream()
                .map(VaccinationRelation::getVaccine)
                .collect(Collectors.toList());
    }



    public List<VacinePackage> getAllPackages() {
        return vacinePackageRepository.findAll();
    }

    public List<VacinePackage> getPackageByVaccineId(String vaccineId) {
        List<VaccinationRelation> relations = vaccinationRelationRepository.findByVaccineId(vaccineId);
        return relations.stream()
                .map(VaccinationRelation::getAPackage)
                .collect(Collectors.toList());
    }

    public BigDecimal calculateTotalCostByPackageId(String packageId) {
        List<VaccinationRelation> relations = vaccinationRelationRepository.findByPackageId(packageId);
        return relations.stream()
                .map(relation -> relation.getVaccine().getPrice().multiply(BigDecimal.valueOf(relation.getVaccine().getShotNumber())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    //thêm vaccine
    public String generateVaccineId() {
        Optional<Vaccine> lastVaccine = vaccineRepository.findTopByOrderByVaccineIdDesc();

        if (lastVaccine.isPresent()) {
            String lastId = lastVaccine.get().getVaccineId(); // VD: "VAC006"
            int number = Integer.parseInt(lastId.substring(3)) + 1; // Lấy số và +1
            return String.format("VAC%03d", number); // Format lại ID
        }
        return "VAC001";
    }

    public Vaccine addVaccine(VaccineRequest vaccineRequest) {
        Vaccine vaccine = new Vaccine();
        vaccine.setVaccineId(generateVaccineId());
        vaccine.setName(vaccineRequest.getName());
        vaccine.setManufacturer(vaccineRequest.getManufacturer());
        vaccine.setPrice(vaccineRequest.getPrice());
        vaccine.setQuantity(vaccineRequest.getQuantity());
        vaccine.setShotNumber(vaccineRequest.getShotNumber());
        vaccine.setGapDays(vaccineRequest.getGapDays());
        vaccine.setDescription(vaccineRequest.getDescription());
        vaccine.setAgeMonth(vaccineRequest.getAgeMonth());
        return vaccineRepository.save(vaccine);
    }

    public Vaccine getVaccineById(String id) {
        return vaccineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vaccine not found with id " + id));
    }

    public Vaccine updateVaccineInfo(String vaccineId, String description, BigDecimal price, int ageMonth) {
        Vaccine vaccine = getVaccineById(vaccineId);
        vaccine.setDescription(description);
        vaccine.setPrice(price);
        vaccine.setAgeMonth(ageMonth);

        Vaccine updatedVaccine = vaccineRepository.save(vaccine);

        // Get packages containing the updated vaccine
        List<VacinePackage> packages = getPackageByVaccineId(vaccineId);

        // Update the price of each package
        for (VacinePackage vacinePackage : packages) {
            BigDecimal newTotalCost = calculateTotalCostByPackageId(vacinePackage.getPackageId());
            vacinePackage.setPrice(newTotalCost);
            vaccinePackageRepository.save(vacinePackage);
        }

        return updatedVaccine;
    }

    public Vaccine changeVaccineQuantity(String vaccineId, int inputQuantity, boolean method) {
        Vaccine vaccine = getVaccineById(vaccineId);
        int currentQuantity = vaccine.getQuantity();
        if (method) {
            vaccine.setQuantity(currentQuantity + inputQuantity);
        } else {
            vaccine.setQuantity(currentQuantity - inputQuantity);
        }
        return vaccineRepository.save(vaccine);
    }

    public Vaccine getVaccineByName(String name) {
        return vaccineRepository.findByName(name)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Vaccine not found with name " + name));
    }

    //thêm package
    public String generatePackageId() {
        Optional<VacinePackage> lastPackage = vaccinePackageRepository.findTopByOrderByPackageIdDesc();

        if (lastPackage.isPresent()) {
            String lastId = lastPackage.get().getPackageId(); // VD: "VAC006"
            int number = Integer.parseInt(lastId.substring(3)) + 1; // Lấy số và +1
            return String.format("PKG%03d", number); // Format lại ID
        }
        return "PKG001";
    }

    public VacinePackage createPackage(VacinePackageRequest vacinePackageRequest) {
        VacinePackage vacinePackage = new VacinePackage();
        vacinePackage.setPackageId(generatePackageId());
        vacinePackage.setName(vacinePackageRequest.getName());
        vacinePackage.setDescription(vacinePackageRequest.getDescription());
        vacinePackage.setAvailable(true);

        // Calculate total price
        BigDecimal totalPrice = vacinePackageRequest.getVaccineIds().stream()
                .map(vaccineId -> {
                    Vaccine vaccine = getVaccineById(vaccineId);
                    return vaccine.getPrice().multiply(BigDecimal.valueOf(vaccine.getShotNumber()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        vacinePackage.setPrice(totalPrice);

        VacinePackage createdPackage = savePackage(vacinePackage);

        // Create and save VaccinationRelation for each vaccine ID
        createAndSaveVaccinationRelations(createdPackage, vacinePackageRequest.getVaccineIds());

        return createdPackage;
    }

    public VacinePackage updateVaccineInPackage(String packageId, List<String> newVaccineIds, String name, String description) {
        VacinePackage vacinePackage = getPackageById(packageId);
        vacinePackage.setName(name);
        vacinePackage.setDescription(description);

        if (newVaccineIds != null && !newVaccineIds.isEmpty()) {
            List<VaccinationRelation> currentRelations = vaccinationRelationRepository.findByPackageId(packageId);

            // Find vaccines to remove
            List<VaccinationRelation> relationsToRemove = currentRelations.stream()
                    .filter(relation -> !newVaccineIds.contains(relation.getVaccine().getVaccineId()))
                    .collect(Collectors.toList());

            // Find vaccines to add
            List<String> currentVaccineIds = currentRelations.stream()
                    .map(relation -> relation.getVaccine().getVaccineId())
                    .collect(Collectors.toList());
            List<String> vaccineIdsToAdd = newVaccineIds.stream()
                    .filter(vaccineId -> !currentVaccineIds.contains(vaccineId))
                    .collect(Collectors.toList());

            // Remove relations
            vaccinationRelationRepository.deleteAll(relationsToRemove);

            // Add new relations
            createAndSaveVaccinationRelations(vacinePackage, vaccineIdsToAdd);

            // Recalculate total price
            BigDecimal newTotalCost = calculateTotalCostByPackageId(packageId);
            vacinePackage.setPrice(newTotalCost);
        }

        return vaccinePackageRepository.save(vacinePackage);
    }

    public VacinePackage getPackageById(String id) {
        return vacinePackageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Package not found with id " + id));
    }

    public VacinePackage gitsavePackage(VacinePackage vacinePackage) {
        return vacinePackageRepository.save(vacinePackage);
    }


    //================VaccinationRelation=========================

    public void saveVaccinationRelations(List<VaccinationRelation> relations) {
        vaccinationRelationRepository.saveAll(relations);
    }

    public void createAndSaveVaccinationRelations(VacinePackage vacinePackage, List<String> vaccineIds) {
        int startNumber = vaccinationRelationRepository.findTopByOrderByRelationIdDesc()
                .map(last -> Integer.parseInt(last.getRelationId().substring(2)))
                .orElse(0);

        List<VaccinationRelation> relations = new ArrayList<>();

        for (int i = 0; i < vaccineIds.size(); i++) {
            String vaccineId = vaccineIds.get(i);
            Vaccine vaccine = getVaccineById(vaccineId);

            VaccinationRelation relation = new VaccinationRelation();
            relation.setRelationId(String.format("VP%03d", startNumber + i + 1));
            relation.setAPackage(vacinePackage);
            relation.setVaccine(vaccine);

            relations.add(relation);
        }

        saveVaccinationRelations(relations);
    }

}
