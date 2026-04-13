package com.capstone1.automatedpayroll.dto;

public class EarningsDTO {

    private Long employeeId;
    private Long earningsId;
    private boolean stationary;
    private String earningsName;
    private Double earningsAmount;
    
    public EarningsDTO() {}

    public EarningsDTO(Long employeeId, Long earningsId, boolean stationary, String earningsName, Double earningsAmount) {
        this.employeeId = employeeId;
        this.earningsId = earningsId;
        this.stationary = stationary;
        this.earningsName = earningsName;
        this.earningsAmount = earningsAmount;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getEarningsId() {
        return earningsId;
    }

    public void setEarningsId(Long earningsId) {
        this.earningsId = earningsId;
    }

    public boolean isStationary() {
        return stationary;
    }

    public void setStationary(boolean stationary) {
        this.stationary = stationary;
    }

    public String getEarningsName() {
        return earningsName;
    }

    public void setEarningsName(String earningsName) {
        this.earningsName = earningsName;
    }

    public Double getEarningsAmount() {
        return earningsAmount;
    }

    public void setEarningsAmount(Double earningsAmount) {
        this.earningsAmount = earningsAmount;
    }

    public static class Builder {
        private Long employeeId;
        private Long earningsId;
        private boolean stationary;
        private String earningsName;
        private Double earningsAmount;

        public Builder employeeId(Long employeeId) {
            this.employeeId = employeeId;
            return this;
        }

        public Builder earningsId(Long earningsId) {
            this.earningsId = earningsId;
            return this;
        }

        public Builder stationary(boolean stationary) {
            this.stationary = stationary;
            return this;
        }

        public Builder earningsName(String earningsName) {
            this.earningsName = earningsName;
            return this;
        }

        public Builder earningsAmount(Double earningsAmount) {
            this.earningsAmount = earningsAmount;
            return this;
        }

        public EarningsDTO build() {
            return new EarningsDTO(employeeId, earningsId, stationary, earningsName, earningsAmount);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

}
