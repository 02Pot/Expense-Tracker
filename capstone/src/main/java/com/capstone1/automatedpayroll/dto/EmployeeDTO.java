package com.capstone1.automatedpayroll.dto;

import java.time.LocalDate;

import com.capstone1.automatedpayroll.model.enums.EmployeeType;
import com.capstone1.automatedpayroll.model.enums.Gender;

public class EmployeeDTO {

    private Long employeeId;
    private String employeeFirstName;
    private String employeeLastName;
    private String employeeAddress;
    private Long employeeContactNumber;
    private String employeeEmail;
    private String employeeDepartment;
    private LocalDate dateOfHire;
    private Gender employeeGender;
    private EmployeeType employeeEmploymentType;
    private Long employeeRate;
    private double monthlySalary;
    private Long latestPayrollId;

    public EmployeeDTO() {}

    public EmployeeDTO(Long employeeId, String employeeFirstName, String employeeLastName,
                       String employeeAddress, Long employeeContactNumber, String employeeEmail,
                       String employeeDepartment, LocalDate dateOfHire, Gender employeeGender,
                       EmployeeType employeeEmploymentType, Long employeeRate, double monthlySalary,
                       Long latestPayrollId) {

        this.employeeId = employeeId;
        this.employeeFirstName = employeeFirstName;
        this.employeeLastName = employeeLastName;
        this.employeeAddress = employeeAddress;
        this.employeeContactNumber = employeeContactNumber;
        this.employeeEmail = employeeEmail;
        this.employeeDepartment = employeeDepartment;
        this.dateOfHire = dateOfHire;
        this.employeeGender = employeeGender;
        this.employeeEmploymentType = employeeEmploymentType;
        this.employeeRate = employeeRate;
        this.monthlySalary = monthlySalary;
        this.latestPayrollId = latestPayrollId;
    }

    public Long getLatestPayrollId() {
        return latestPayrollId;
    }

    public void setLatestPayrollId(Long latestPayrollId) {
        this.latestPayrollId = latestPayrollId;
    }

    public Long getEmployeeRate() {
        return employeeRate;
    }

    public void setMonthlySalary(double monthlySalary) {
        this.monthlySalary = monthlySalary;
    }
    public double getMonthlySalary() {
        return monthlySalary;
    }

    public void setEmployeeRate(Long employeeRate) {
        this.employeeRate = employeeRate;
    }
    public EmployeeType getEmployeeEmploymentType() {
        return employeeEmploymentType;
    }

    public void setEmployeeEmploymentType(EmployeeType employeeEmploymentType) {
        this.employeeEmploymentType = employeeEmploymentType;
    }

    public Gender getEmployeeGender() {
        return employeeGender;
    }

    public void setEmployeeGender(Gender employeeGender) {
        this.employeeGender = employeeGender;
    }

    public LocalDate getDateOfHire() {
        return dateOfHire;
    }

    public void setDateOfHire(LocalDate dateOfHire) {
        this.dateOfHire = dateOfHire;
    }

    public String getEmployeeDepartment() {
        return employeeDepartment;
    }

    public void setEmployeeDepartment(String employeeDepartment) {
        this.employeeDepartment = employeeDepartment;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public Long getEmployeeContactNumber() {
        return employeeContactNumber;
    }

    public void setEmployeeContactNumber(Long employeeContactNumber) {
        this.employeeContactNumber = employeeContactNumber;
    }

    public String getEmployeeAddress() {
        return employeeAddress;
    }

    public void setEmployeeAddress(String employeeAddress) {
        this.employeeAddress = employeeAddress;
    }

    public String getEmployeeLastName() {
        return employeeLastName;
    }

    public void setEmployeeLastName(String employeeLastName) {
        this.employeeLastName = employeeLastName;
    }

    public String getEmployeeFirstName() {
        return employeeFirstName;
    }

    public void setEmployeeFirstName(String employeeFirstName) {
        this.employeeFirstName = employeeFirstName;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

     public static class Builder {
        private Long employeeId;
        private String employeeFirstName;
        private String employeeLastName;
        private String employeeAddress;
        private Long employeeContactNumber;
        private String employeeEmail;
        private String employeeDepartment;
        private LocalDate dateOfHire;
        private Gender employeeGender;
        private EmployeeType employeeEmploymentType;
        private Long employeeRate;
        private double monthlySalary;
        private Long latestPayrollId;

        public Builder employeeId(Long employeeId) { this.employeeId = employeeId; return this; }
        public Builder employeeFirstName(String employeeFirstName) { this.employeeFirstName = employeeFirstName; return this; }
        public Builder employeeLastName(String employeeLastName) { this.employeeLastName = employeeLastName; return this; }
        public Builder employeeAddress(String employeeAddress) { this.employeeAddress = employeeAddress; return this; }
        public Builder employeeContactNumber(Long employeeContactNumber) { this.employeeContactNumber = employeeContactNumber; return this; }
        public Builder employeeEmail(String employeeEmail) { this.employeeEmail = employeeEmail; return this; }
        public Builder employeeDepartment(String employeeDepartment) { this.employeeDepartment = employeeDepartment; return this; }
        public Builder dateOfHire(LocalDate dateOfHire) { this.dateOfHire = dateOfHire; return this; }
        public Builder employeeGender(Gender employeeGender) { this.employeeGender = employeeGender; return this; }
        public Builder employeeEmploymentType(EmployeeType employeeEmploymentType) { this.employeeEmploymentType = employeeEmploymentType; return this; }
        public Builder employeeRate(Long employeeRate) { this.employeeRate = employeeRate; return this; }
        public Builder monthlySalary(double monthlySalary) { this.monthlySalary = monthlySalary; return this; }
        public Builder latestPayrollId(Long latestPayrollId) { this.latestPayrollId = latestPayrollId; return this; }

        public EmployeeDTO build() {
            return new EmployeeDTO(employeeId, employeeFirstName, employeeLastName, employeeAddress,
                                   employeeContactNumber, employeeEmail, employeeDepartment, dateOfHire,
                                   employeeGender, employeeEmploymentType, employeeRate, monthlySalary, latestPayrollId);
        }
    }

    public static Builder builder() { return new Builder(); }
}
