package com.capstone1.automatedpayroll.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PayslipDTO {


    private Long id;
    private String employeeName;
    private LocalDate dateOfJoining;
    private String employeeType;
    private String department;
    private Long workedDays;
    private Double workedHours;
    private double monthlySalary;
    private double employeeRate;
    private LocalDate payrollStartDate;
    private LocalDate payrollEndDate;

    private List<Map<String, Object>> earnings; // name + amount

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public LocalDate getDateOfJoining() {
        return dateOfJoining;
    }

    public void setDateOfJoining(LocalDate dateOfJoining) {
        this.dateOfJoining = dateOfJoining;
    }

    public String getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(String employeeType) {
        this.employeeType = employeeType;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Double getWorkedHours() {
        return workedHours;
    }

    public void setWorkedHours(Double workedHours) {
        this.workedHours = workedHours;
    }

    public Long getWorkedDays() {
        return workedDays;
    }

    public void setWorkedDays(Long workedDays) {
        this.workedDays = workedDays;
    }

    public double getEmployeeRate() {
        return employeeRate;
    }

    public void setMonthlySalary(double monthlySalary) {
        this.monthlySalary = monthlySalary;
    }
    public double getMonthlySalary() {
        return monthlySalary;
    }

    public void setEmployeeRate(double employeeRate) {
        this.employeeRate = employeeRate;
    }
    public LocalDate getPayrollStartDate() {
        return payrollStartDate;
    }

    public void setPayrollStartDate(LocalDate payrollStartDate) {
        this.payrollStartDate = payrollStartDate;
    }

    public LocalDate getPayrollEndDate() {
        return payrollEndDate;
    }

    public void setPayrollEndDate(LocalDate payrollEndDate) {
        this.payrollEndDate = payrollEndDate;
    }

    public List<Map<String, Object>> getEarnings() {
        return earnings;
    }

    public void setEarnings(List<Map<String, Object>> earnings) {
        this.earnings = earnings;
    }

    public List<Map<String, Object>> getDeductions() {
        return deductions;
    }

    public void setDeductions(List<Map<String, Object>> deductions) {
        this.deductions = deductions;
    }

    public double getTotalEarnings() {
        return totalEarnings;
    }

    public void setTotalEarnings(double totalEarnings) {
        this.totalEarnings = totalEarnings;
    }

    public double getTotalDeductions() {
        return totalDeductions;
    }

    public void setTotalDeductions(double totalDeductions) {
        this.totalDeductions = totalDeductions;
    }

    public double getNetPay() {
        return netPay;
    }

    public void setNetPay(double netPay) {
        this.netPay = netPay;
    }

    private List<Map<String, Object>> deductions; // name + amount

    private double totalEarnings;
    private double totalDeductions;
    private double netPay;

}
