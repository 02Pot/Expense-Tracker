package com.capstone1.automatedpayroll.controller;


import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.capstone1.automatedpayroll.dto.PayrollDTO;
import com.capstone1.automatedpayroll.model.PayrollModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.capstone1.automatedpayroll.dto.PayslipDTO;
import com.capstone1.automatedpayroll.service.PayrollServiceImpl;
import com.capstone1.automatedpayroll.service.PayslipServiceImpl;

@RestController
@RequestMapping("/payroll")
@CrossOrigin("*")
public class PayrollController {

    @Autowired
    private PayrollServiceImpl payrollService;
    @Autowired
    private PayslipServiceImpl payslipService;


    @PostMapping("/run-cutoff")
    public Map<String, Object> runPayroll(){
        payrollService.runPayrollCurrentCutOff();

        Map<String,Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Payroll succesfully Processed");

        return response;
    }

    @GetMapping("/cutoff")
    public List<PayrollDTO> getPayrollByCutoff(){
        return payrollService.getPayrollByCutoff();
    }

    @GetMapping("/all")
    public Page<PayrollDTO> getAllPayroll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return payrollService.getAllPayroll(page,size);
    }

    @GetMapping("/{payrollId}")
    public PayslipDTO getPayslip(@PathVariable Long payrollId){
        return payslipService.generatePayslip(payrollId);
    }

}
