package com.capstone1.automatedpayroll.controller;


import com.capstone1.automatedpayroll.service.AttendanceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/attendance")
@CrossOrigin("*")
public class AttendanceController {

    @Autowired
    private final AttendanceServiceImpl attendanceService;

    public AttendanceController(AttendanceServiceImpl attendanceService) {
        this.attendanceService = attendanceService;
    }
    @GetMapping("/{employeeId}")
    public double getAttendance(@PathVariable Long employeeId) {
        return attendanceService.getTotalHoursWorkedByEmployee(employeeId);
    }

}
