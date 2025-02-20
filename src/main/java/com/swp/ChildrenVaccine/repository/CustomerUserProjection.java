package com.swp.ChildrenVaccine.repository;

import java.time.LocalDate;

public interface CustomerUserProjection {
    String getCusId();
    String getUserId();
    String getAddress();
    LocalDate getDateOfBirth();
    String getGender();
    String getEmail();
    String getFullName();
    String getPhone();
}
