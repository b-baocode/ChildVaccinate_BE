package com.swp.ChildrenVaccine.service;

import com.swp.ChildrenVaccine.entities.Vaccine;
import com.swp.ChildrenVaccine.entities.VacinePackage;
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

    public List<Vaccine> getAllVaccines() {
        return vaccineRepository.findAll();
    }

    public List<VacinePackage> getAllPackages() {
        return vacinePackageRepository.findAll();
    }
}
