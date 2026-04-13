package com.capstone1.automatedpayroll.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone1.automatedpayroll.dto.DeductionsDTO;
import com.capstone1.automatedpayroll.service.DeductionsServiceImpl;

@RestController
@RequestMapping("/deductions")
@CrossOrigin("*")
public class DeductionsController {

    @Autowired
    private final DeductionsServiceImpl deductionsService;

    public DeductionsController(DeductionsServiceImpl deductionsService) {
        this.deductionsService = deductionsService;
    }
    @GetMapping("/{employeeId}")
    public List<DeductionsDTO> getDeductions(@PathVariable Long employeeId){
        return deductionsService.getDeductionsByEmployeeId(employeeId);
    }
    @PostMapping("/{employeeId}")
    public DeductionsDTO createDeductions(@PathVariable Long employeeId,@RequestBody DeductionsDTO deductionsDTO){
        return deductionsService.createDeduction(employeeId,deductionsDTO);
    }
    @PutMapping("/{earningsId}")
    public DeductionsDTO updateDeductions(@PathVariable Long deductionsId,@RequestBody DeductionsDTO deductionsDTO) {
        return deductionsService.updateDeduction(deductionsId, deductionsDTO);
    }
    @DeleteMapping("/{earningsId}")
    public void deleteDeduction(@PathVariable Long deductionsId) {
        deductionsService.deleteDeduction(deductionsId);
    }
}
