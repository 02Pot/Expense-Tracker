package com.capstone1.automatedpayroll.model;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "earnings")
public class EarningsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long earningId;

    private String earningName;
    private Double earningAmount;
    private boolean stationary;
    @CreationTimestamp
    private LocalDate dateCreated;
    private Long processInPayroll;
    @ManyToOne
    @JoinColumn(name = "userId")
    private EmployeeModel employee;

    public EarningsModel() {}

    public EarningsModel(Long earningId, String earningName, Double earningAmount, boolean stationary,
                        LocalDate dateCreated, EmployeeModel employee) {
        this.earningId = earningId;
        this.earningName = earningName;
        this.earningAmount = earningAmount;
        this.stationary = stationary;
        this.dateCreated = dateCreated;
        this.employee = employee;
    }

    public Long getEarningId() {
        return earningId;
    }

    public void setEarningId(Long earningId) {
        this.earningId = earningId;
    }

    public String getEarningName() {
        return earningName;
    }

    public void setEarningName(String earningName) {
        this.earningName = earningName;
    }

    public Double getEarningAmount() {
        return earningAmount;
    }

    public void setEarningAmount(Double earningAmount) {
        this.earningAmount = earningAmount;
    }

    public boolean isStationary() {
        return stationary;
    }

    public void setStationary(boolean stationary) {
        this.stationary = stationary;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public EmployeeModel getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeModel employee) {
        this.employee = employee;
    }

    public static class Builder {
        private Long earningId;
        private String earningName;
        private Double earningAmount;
        private boolean stationary;
        private LocalDate dateCreated;
        private EmployeeModel employee;

        public Builder earningId(Long earningId) {
            this.earningId = earningId;
            return this;
        }

        public Builder earningName(String earningName) {
            this.earningName = earningName;
            return this;
        }

        public Builder earningAmount(Double earningAmount) {
            this.earningAmount = earningAmount;
            return this;
        }

        public Builder stationary(boolean stationary) {
            this.stationary = stationary;
            return this;
        }

        public Builder dateCreated(LocalDate dateCreated) {
            this.dateCreated = dateCreated;
            return this;
        }

        public Builder employee(EmployeeModel employee) {
            this.employee = employee;
            return this;
        }

        public EarningsModel build() {
            return new EarningsModel(earningId, earningName, earningAmount, stationary, dateCreated, employee);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
