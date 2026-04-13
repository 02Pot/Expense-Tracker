package com.capstone1.automatedpayroll.dto;

import java.time.LocalDate;

public class PayrollPeriodDTO {
    private LocalDate startDate;
    private LocalDate endDate;

    public PayrollPeriodDTO(LocalDate startDate,LocalDate endDate){
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }


}
