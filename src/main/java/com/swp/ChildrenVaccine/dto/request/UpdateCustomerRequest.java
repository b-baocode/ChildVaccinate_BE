package com.swp.ChildrenVaccine.dto.request;

import lombok.*;

@Getter
@Setter
@Data
public class UpdateCustomerRequest {
    private String phone;
    private String address;
}
