package com.capstone1.automatedpayroll.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "deductions")
public class DeductionsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deductionId;
    private String deductionName;
    private Double deductionAmount;
    private boolean stationary;
    private double maxAmount;

    private double deductionPercentage;

    @ManyToOne
    @JoinColumn(name = "userId")
    private EmployeeModel employee;

    public DeductionsModel() {}

    public DeductionsModel(Long deductionId, String deductionName, Double deductionAmount, boolean stationary
                        ,double maxAmount,double deductionPercentage ,EmployeeModel employee) {
        this.deductionId = deductionId;
        this.deductionName = deductionName;
        this.deductionAmount = deductionAmount;
        this.stationary = stationary;
        this.maxAmount = maxAmount;
        this.deductionPercentage = deductionPercentage;
        this.employee = employee;
    }

    public Long getDeductionId() {
        return deductionId;
    }

    public void setDeductionId(Long deductionId) {
        this.deductionId = deductionId;
    }

    public String getDeductionName() {
        return deductionName;
    }

    public void setDeductionName(String deductionName) {
        this.deductionName = deductionName;
    }

    public Double getDeductionAmount() {
        return deductionAmount;
    }

    public void setDeductionAmount(Double deductionAmount) {
        this.deductionAmount = deductionAmount;
    }

    public boolean isStationary() {
        return stationary;
    }

    public void setStationary(boolean stationary) {
        this.stationary = stationary;
    }

    public double getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(double maxAmount) {
        this.maxAmount = maxAmount;
    }

    public double getDeductionPercentage() {
        return deductionPercentage;
    }

    public void setDeductionPercentage(double deductionPercentage) {
        this.deductionPercentage = deductionPercentage;
    }

    public EmployeeModel getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeModel employee) {
        this.employee = employee;
    }


    public static class Builder {
        private Long deductionId;
        private String deductionName;
        private Double deductionAmount;
        private boolean stationary;
        private double maxAmount;
        private double deductionPercentage;
        private EmployeeModel employee;


        public Builder deductionId(Long deductionId) { this.deductionId = deductionId; return this; }
        public Builder deductionName(String deductionName) { this.deductionName = deductionName; return this; }
        public Builder deductionAmount(Double deductionAmount) { this.deductionAmount = deductionAmount; return this; }
        public Builder stationary(boolean stationary) { this.stationary = stationary; return this; }
        public Builder maxAmount(double maxAmount) { this.maxAmount = maxAmount; return this; }
        public Builder deductionPercentage(double deductionPercentage) { this.deductionPercentage = deductionPercentage; return this; }
        public Builder employee(EmployeeModel employee) { this.employee = employee; return this; }

        public DeductionsModel build() {
            return new DeductionsModel(
                    deductionId,
                    deductionName,
                    deductionAmount,
                    stationary,
                    maxAmount,
                    deductionPercentage,
                    employee
            );
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
