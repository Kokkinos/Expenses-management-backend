package com.kokkinos.payments_management_backend.dtos;

public class ExpenseDTO {

    private String label;
    private double amount;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
