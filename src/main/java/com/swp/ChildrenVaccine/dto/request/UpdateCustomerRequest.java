package com.swp.ChildrenVaccine.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class UpdateCustomerRequest {
    private String phone;
    private String address;
}
