package com.capstone1.automatedpayroll.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.capstone1.automatedpayroll.dto.AttendanceDTO;
import com.capstone1.automatedpayroll.service.AttendanceServiceImpl;

@RestController
@RequestMapping("/attendance")
public class UploadController {

    @Autowired
    private AttendanceServiceImpl attendanceService;

    @PostMapping("/import")
    public ResponseEntity<String> importAttendance(@RequestParam("file")MultipartFile file){
        attendanceService.importAttendance(file);

        return ResponseEntity.ok("Attendance imported Success!");
    }

    @GetMapping
    public ResponseEntity<List<AttendanceDTO>> getAllAttendance() {
        List<AttendanceDTO> attendances = attendanceService.findAllAttendance();
        return ResponseEntity.ok(attendances);
    }

}
