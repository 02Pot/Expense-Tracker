package com.capstone1.automatedpayroll.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class PayrollModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private EmployeeModel employee;
    private double nonStationaryEarnings;
    private LocalDate startDate;
    private LocalDate endDate;
    private double grossPay;
    private double totalDeductions;
    private double netPay;
    private LocalDate dateProcessed;
    private String status;

    public PayrollModel(){}
    public PayrollModel(Long id, EmployeeModel employee, double nonStationaryEarnings, LocalDate startDate, LocalDate endDate, double grossPay, double totalDeductions, double netPay, LocalDate dateProcessed,String status) {
        this.id = id;
        this.employee = employee;
        this.nonStationaryEarnings = nonStationaryEarnings;
        this.startDate = startDate;
        this.endDate = endDate;
        this.grossPay = grossPay;
        this.totalDeductions = totalDeductions;
        this.netPay = netPay;
        this.dateProcessed = dateProcessed;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EmployeeModel getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeModel employee) {
        this.employee = employee;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public double getGrossPay() {
        return grossPay;
    }

    public void setGrossPay(double grossPay) {
        this.grossPay = grossPay;
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

    public LocalDate getDateProcessed() {
        return dateProcessed;
    }

    public void setDateProcessed(LocalDate dateProcessed) {
        this.dateProcessed = dateProcessed;
    }


    public double getNonStationaryEarnings() {
        return nonStationaryEarnings;
    }

    public void setNonStationaryEarnings(double nonStationaryEarnings) {
        this.nonStationaryEarnings = nonStationaryEarnings;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public static class Builder{
        private Long id;
        private EmployeeModel employee;
        private double nonStationaryEarnings;
        private LocalDate startDate;
        private LocalDate endDate;
        private double grossPay;
        private double totalDeductions;
        private double netPay;
        private LocalDate dateProcessed;
        private String status;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder employee(EmployeeModel employee) { this.employee = employee; return this; }
        public Builder nonStationaryEarnings(double earnings) { this.nonStationaryEarnings = earnings; return this; }
        public Builder startDate(LocalDate start) { this.startDate = start; return this; }
        public Builder endDate(LocalDate end) { this.endDate = end; return this; }
        public Builder grossPay(double gross) { this.grossPay = gross; return this; }
        public Builder totalDeductions(double deductions) { this.totalDeductions = deductions; return this; }
        public Builder netPay(double net) { this.netPay = net; return this; }
        public Builder dateProcessed(LocalDate date) { this.dateProcessed = date; return this; }
        public Builder status(String status){this.status = status;return this;}

        public PayrollModel build() {
            return new PayrollModel(
                    id,
                    employee,
                    nonStationaryEarnings,
                    startDate,
                    endDate,
                    grossPay,
                    totalDeductions,
                    netPay,
                    dateProcessed,
                    status
            );
        }
    }
    public static PayrollModel.Builder builder() {
        return new Builder();
    }
}
