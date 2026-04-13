package com.capstone1.automatedpayroll.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class AttendanceDTO {

    private Long attendanceId;
    private LocalDate attendanceDate;
    private LocalTime timeIn;
    private LocalTime timeOut;
    private Double hoursWorked;

       public AttendanceDTO() {}

    // All-args constructor
    public AttendanceDTO(Long attendanceId, LocalDate attendanceDate, LocalTime timeIn, LocalTime timeOut, Double hoursWorked) {
        this.attendanceId = attendanceId;
        this.attendanceDate = attendanceDate;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
        this.hoursWorked = hoursWorked;
    }

    // Getters and Setters
    public Long getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(Long attendanceId) {
        this.attendanceId = attendanceId;
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

    // Optional: Builder pattern manually
    public static class Builder {
        private Long attendanceId;
        private LocalDate attendanceDate;
        private LocalTime timeIn;
        private LocalTime timeOut;
        private Double hoursWorked;

        public Builder attendanceId(Long attendanceId) {
            this.attendanceId = attendanceId;
            return this;
        }

        public Builder attendanceDate(LocalDate attendanceDate) {
            this.attendanceDate = attendanceDate;
            return this;
        }

        public Builder timeIn(LocalTime timeIn) {
            this.timeIn = timeIn;
            return this;
        }

        public Builder timeOut(LocalTime timeOut) {
            this.timeOut = timeOut;
            return this;
        }

        public Builder hoursWorked(Double hoursWorked) {
            this.hoursWorked = hoursWorked;
            return this;
        }

        public AttendanceDTO build() {
            return new AttendanceDTO(attendanceId, attendanceDate, timeIn, timeOut, hoursWorked);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
