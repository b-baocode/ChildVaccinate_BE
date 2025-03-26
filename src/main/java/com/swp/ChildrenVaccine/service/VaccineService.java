package com.swp.ChildrenVaccine.service;

import com.swp.ChildrenVaccine.entities.VaccinationRelation;
import com.swp.ChildrenVaccine.entities.Vaccine;
import com.swp.ChildrenVaccine.entities.VacinePackage;
import com.swp.ChildrenVaccine.repository.ScheduleRepository;
import com.swp.ChildrenVaccine.repository.VaccinationRelationRepository;
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

    @Autowired
    private VaccinationRelationRepository vaccinationRelationRepository;

    public List<Vaccine> getAllVaccines() {
        return vaccineRepository.findAll();
    }

    public void updateVaccineQuantity(Vaccine vaccine, int totalShot) {
        int newQuantity = vaccine.getQuantity() - totalShot;
        vaccine.setQuantity(newQuantity);
//save vaccine and service
    // Lấy danh sách packageId từ repository
    List<String> packageIds = vaccineRepository.findPackageIdsByVaccineId(vaccine.getVaccineId());
    // Cập nhật từng package
    for (String packageId : packageIds) {
        updatePackageAvailability(packageId);
    }
    }

    public Vaccine saveVaccine(Vaccine vaccine) {
        Vaccine savedVaccine = vaccineRepository.save(vaccine);
        List<String> packageIds = vaccineRepository.findPackageIdsByVaccineId(savedVaccine.getVaccineId());
    for (String packageId : packageIds) {
        updatePackageAvailability(packageId);
    }
    return savedVaccine;
    }


    public Vaccine getVaccineById(String id) {
        return vaccineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vaccine not found with id " + id));
    }
    // update all of vaccin
//    public Vaccine updateVaccine(String id, Vaccine vaccineDetails) {
//        Vaccine vaccine = getVaccineById(id);
//        vaccine.setName(vaccineDetails.getName());
//        vaccine.setQuantity(vaccineDetails.getQuantity());
//        return vaccineRepository.save(vaccine);
//    }

   public Vaccine updateVaccine(String id, Vaccine vaccineDetails) {
    Vaccine vaccine = getVaccineById(id);
    vaccine.setName(vaccineDetails.getName());
    vaccine.setDescription(vaccineDetails.getDescription());
    vaccine.setManufacturer(vaccineDetails.getManufacturer());
    vaccine.setShotNumber(vaccineDetails.getShotNumber());
    vaccine.setQuantity(vaccineDetails.getQuantity());
    vaccine.setPrice(vaccineDetails.getPrice());
    vaccine.setGapDays(vaccineDetails.getGapDays());
    Vaccine updatedVaccine = vaccineRepository.save(vaccine);
    
    // Lấy danh sách packageIds từ repository
    List<String> packageIds = vaccineRepository.findPackageIdsByVaccineId(updatedVaccine.getVaccineId());
    for (String packageId : packageIds) {
        updatePackageAvailability(packageId);
    }
    return updatedVaccine;
}
public void deleteVaccine(String id) {
    Vaccine vaccine = getVaccineById(id);
    // Lấy danh sách packageIds trước khi xóa
    List<String> packageIds = vaccineRepository.findPackageIdsByVaccineId(vaccine.getVaccineId());
    vaccineRepository.delete(vaccine);
    // Cập nhật trạng thái các package sau khi xóa
    for (String packageId : packageIds) {
        updatePackageAvailability(packageId);
    }
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

    private void updatePackageAvailability(String packageId) {
        VacinePackage vacinePackage = vacinePackageRepository.findById(packageId)
                .orElseThrow(() -> new RuntimeException("Package not found with id " + packageId));
                
        // Kiểm tra số lượng vaccine trong package
        List<VaccinationRelation> relations = vaccinationRelationRepository.findByPackageId(packageId);
        boolean hasAvailableVaccines = relations.stream()
                .allMatch(relation -> relation.getVaccine().getQuantity() > 0);
                
        vacinePackage.setAvailable(hasAvailableVaccines ? 1 : 0);
        vacinePackageRepository.save(vacinePackage);
    }


}
