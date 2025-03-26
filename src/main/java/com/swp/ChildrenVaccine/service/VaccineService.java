package com.swp.ChildrenVaccine.service;

import com.swp.ChildrenVaccine.entities.Vaccine;
import com.swp.ChildrenVaccine.entities.VacinePackage;
import com.swp.ChildrenVaccine.repository.ScheduleRepository;
import com.swp.ChildrenVaccine.repository.VaccinePackageRepository;
import com.swp.ChildrenVaccine.repository.VaccineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VaccineService {

    @Autowired
    private VaccineRepository vaccineRepository;

    @Autowired
    private VaccinePackageRepository vacinePackageRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;

    public List<Vaccine> getAllVaccines() {
        return vaccineRepository.findAll();
    }

    public void updateVaccineQuantity(Vaccine vaccine, int totalShot) {
        int newQuantity = vaccine.getQuantity() - totalShot;
        vaccine.setQuantity(newQuantity);

        vaccineRepository.save(vaccine);
    }
    public Vaccine saveVaccine(Vaccine vaccine) {
        return vaccineRepository.save(vaccine);
    }
    public Vaccine getVaccineById(String id) {
        return vaccineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vaccine not found with id " + id));
    }
    // update all of vaccin
    public Vaccine updateVaccine(String id, Vaccine vaccineDetails) {
        Vaccine vaccine = getVaccineById(id);
        vaccine.setName(vaccineDetails.getName());
        vaccine.setQuantity(vaccineDetails.getQuantity());
        return vaccineRepository.save(vaccine);
    }
    public void deleteVaccine(String id) {
        Vaccine vaccine = getVaccineById(id);
        vaccineRepository.delete(vaccine);
    }

    //PACKAGE

    public VacinePackage getPackageById(String id) {
        return vacinePackageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Package not found with id " + id));
    }

    public VacinePackage savePackage(VacinePackage vacinePackage) {
        return vacinePackageRepository.save(vacinePackage);
    }

    public VacinePackage updatePackage(String id, VacinePackage packageDetails) {
        VacinePackage vacinePackage = getPackageById(id);
        vacinePackage.setName(packageDetails.getName());
        vacinePackage.setVaccines(packageDetails.getVaccines());
        return vacinePackageRepository.save(vacinePackage);
    }

    public void deletePackage(String id) {
        VacinePackage vacinePackage = getPackageById(id);
        vacinePackageRepository.delete(vacinePackage);
    }

    public List<VacinePackage> getAllPackages() {
        return vacinePackageRepository.findAll();
    }




}
