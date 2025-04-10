package com.swp.ChildrenVaccine.dto.request;

public class ChangeVaccineQuantityRequest {
    private int inputQuantity;
    private boolean method;

    // Getters and Setters
    public int getInputQuantity() {
        return inputQuantity;
    }

    public void setInputQuantity(int inputQuantity) {
        this.inputQuantity = inputQuantity;
    }

    public boolean isMethod() {
        return method;
    }

    public void setMethod(boolean method) {
        this.method = method;
    }
}