package com.capstone1.automatedpayroll.dto;


public class DeductionsDTO {

    private Long employeeId;
    private Long deductionsId;
    private boolean stationary;
    private String deductionsName;
    private Double deductionsAmount;
    private double maxAmount;

    private double deductionPercentage;

       public DeductionsDTO() {
    }

    public DeductionsDTO(Long employeeId, Long deductionsId, boolean stationary,
                         String deductionsName, Double deductionsAmount,
                         double maxAmount, double deductionPercentage) {
        this.employeeId = employeeId;
        this.deductionsId = deductionsId;
        this.stationary = stationary;
        this.deductionsName = deductionsName;
        this.deductionsAmount = deductionsAmount;
        this.maxAmount = maxAmount;
        this.deductionPercentage = deductionPercentage;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getDeductionsId() {
        return deductionsId;
    }

    public void setDeductionsId(Long deductionsId) {
        this.deductionsId = deductionsId;
    }

    public boolean isStationary() {
        return stationary;
    }

    public void setStationary(boolean stationary) {
        this.stationary = stationary;
    }

    public String getDeductionsName() {
        return deductionsName;
    }

    public void setDeductionsName(String deductionsName) {
        this.deductionsName = deductionsName;
    }

    public Double getDeductionsAmount() {
        return deductionsAmount;
    }

    public void setDeductionsAmount(Double deductionsAmount) {
        this.deductionsAmount = deductionsAmount;
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

    public static class Builder {
        private Long employeeId;
        private Long deductionsId;
        private boolean stationary;
        private String deductionsName;
        private Double deductionsAmount;
        private double maxAmount;
        private double deductionPercentage;

        public Builder employeeId(Long employeeId) {
            this.employeeId = employeeId;
            return this;
        }

        public Builder deductionsId(Long deductionsId) {
            this.deductionsId = deductionsId;
            return this;
        }

        public Builder stationary(boolean stationary) {
            this.stationary = stationary;
            return this;
        }

        public Builder deductionsName(String deductionsName) {
            this.deductionsName = deductionsName;
            return this;
        }

        public Builder deductionsAmount(Double deductionsAmount) {
            this.deductionsAmount = deductionsAmount;
            return this;
        }

        public Builder maxAmount(double maxAmount) {
            this.maxAmount = maxAmount;
            return this;
        }

        public Builder deductionPercentage(double deductionPercentage) {
            this.deductionPercentage = deductionPercentage;
            return this;
        }

        public DeductionsDTO build() {
            return new DeductionsDTO(
                    employeeId,
                    deductionsId,
                    stationary,
                    deductionsName,
                    deductionsAmount,
                    maxAmount,
                    deductionPercentage
            );
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
