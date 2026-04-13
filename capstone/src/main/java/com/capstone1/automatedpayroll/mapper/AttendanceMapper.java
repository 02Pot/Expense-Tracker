package com.capstone1.automatedpayroll.mapper;

import com.capstone1.automatedpayroll.dto.AttendanceDTO;
import com.capstone1.automatedpayroll.model.AttendanceModel;
import com.capstone1.automatedpayroll.model.EmployeeModel;

public class AttendanceMapper {

    public static AttendanceDTO mapToAttendanceDTO(AttendanceModel attendanceModel) {
        if (attendanceModel == null) return null;

        return new AttendanceDTO(
                attendanceModel.getAttendanceId(),
                attendanceModel.getAttendanceDate(),
                attendanceModel.getTimeIn(),
                attendanceModel.getTimeIn(),
                attendanceModel.getHoursWorked()
        );
    }

    public static AttendanceModel mapToAttendance(AttendanceDTO attendanceDTO, EmployeeModel employee) {
        if (attendanceDTO == null) return null;

        return AttendanceModel.builder()
                .attendanceId(attendanceDTO.getAttendanceId())
                .attendanceDate(attendanceDTO.getAttendanceDate())
                .timeIn(attendanceDTO.getTimeIn())
                .timeOut(attendanceDTO.getTimeOut())
                .hoursWorked(attendanceDTO.getHoursWorked())
                .employee(employee)
                .build();
    }
}
