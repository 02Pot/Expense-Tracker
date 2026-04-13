package com.capstone1.automatedpayroll.model;

import java.time.LocalDate;
import java.time.LocalTime;

import org.apache.poi.ss.formula.functions.T;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "attendance")
public class AttendanceModel {

    public static double getHoursWorked(T value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long attendanceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private EmployeeModel employee;

    private LocalDate attendanceDate;
    private LocalTime timeIn;
    private LocalTime timeOut;

    private Double hoursWorked;

    public AttendanceModel() {}

    public AttendanceModel(Long attendanceId, EmployeeModel employee, LocalDate attendanceDate,
                        LocalTime timeIn, LocalTime timeOut, Double hoursWorked) {
        this.attendanceId = attendanceId;
        this.employee = employee;
        this.attendanceDate = attendanceDate;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
        this.hoursWorked = hoursWorked;
    }

    public Long getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(Long attendanceId) {
        this.attendanceId = attendanceId;
    }

    public EmployeeModel getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeModel employee) {
        this.employee = employee;
    }

    public LocalDate getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(LocalDate attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public LocalTime getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(LocalTime timeIn) {
        this.timeIn = timeIn;
    }

    public LocalTime getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(LocalTime timeOut) {
        this.timeOut = timeOut;
    }

    public Double getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(Double hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    public static class Builder {
        private Long attendanceId;
        private EmployeeModel employee;
        private LocalDate attendanceDate;
        private LocalTime timeIn;
        private LocalTime timeOut;
        private Double hoursWorked;

        public Builder attendanceId(Long attendanceId) { this.attendanceId = attendanceId; return this; }
        public Builder employee(EmployeeModel employee) { this.employee = employee; return this; }
        public Builder attendanceDate(LocalDate attendanceDate) { this.attendanceDate = attendanceDate; return this; }
        public Builder timeIn(LocalTime timeIn) { this.timeIn = timeIn; return this; }
        public Builder timeOut(LocalTime timeOut) { this.timeOut = timeOut; return this; }
        public Builder hoursWorked(Double hoursWorked) { this.hoursWorked = hoursWorked; return this; }

        public AttendanceModel build() {
            return new AttendanceModel(attendanceId, employee, attendanceDate, timeIn, timeOut, hoursWorked);
        }
    }

    public static Builder builder() { return new Builder(); }

    public static double getHoursWorked(LocalTime timeIn, LocalTime timeOut) {
        if (timeIn == null || timeOut == null) return 0.0;
        return java.time.Duration.between(timeIn, timeOut).toHours() +
            java.time.Duration.between(timeIn, timeOut).toMinutesPart() / 60.0;
    }

}
