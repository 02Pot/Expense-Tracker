package com.capstone1.automatedpayroll.dto;

import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.Date;

public class PayrollDTO {

    private Long id;
    private LocalDate dateProcessed;
    private LocalDate startDate;
    private LocalDate endDate;
    private double grossPay;
    private double netPay;
    private double totalDeductions;
    private double nonStationaryEarnings;
    private double userId;
    private String status;

    public PayrollDTO(){}

    public PayrollDTO(double userId, double nonStationaryEarnings, double totalDeductions, double netPay, double grossPay, LocalDate endDate, LocalDate startDate, LocalDate dateProcessed, Long id,String status) {
        this.userId = userId;
        this.nonStationaryEarnings = nonStationaryEarnings;
        this.totalDeductions = totalDeductions;
        this.netPay = netPay;
        this.grossPay = grossPay;
        this.endDate = endDate;
        this.startDate = startDate;
        this.dateProcessed = dateProcessed;
        this.id = id;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateProcessed() {
        return dateProcessed;
    }

    public void setDateProcessed(LocalDate dateProcessed) {
        this.dateProcessed = dateProcessed;
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

    public double getNetPay() {
        return netPay;
    }

    public void setNetPay(double netPay) {
        this.netPay = netPay;
    }

    public double getTotalDeductions() {
        return totalDeductions;
    }

    public void setTotalDeductions(double totalDeductions) {
        this.totalDeductions = totalDeductions;
    }

    public double getNonStationaryEarnings() {
        return nonStationaryEarnings;
    }

    public void setNonStationaryEarnings(double nonStationaryEarnings) {
        this.nonStationaryEarnings = nonStationaryEarnings;
    }

    public double getUserId() {
        return userId;
    }

    public void setUserId(double userId) {
        this.userId = userId;
    }
    public String getStatus(){
        return status;
    }
    public void setStatus(String status){
        this.status = status;
    }

}
