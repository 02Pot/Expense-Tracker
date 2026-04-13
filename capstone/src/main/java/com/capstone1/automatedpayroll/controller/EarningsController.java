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

import com.capstone1.automatedpayroll.dto.EarningsDTO;
import com.capstone1.automatedpayroll.service.EarningsServiceImpl;

@RestController
@RequestMapping("/earnings")
@CrossOrigin("*")
public class EarningsController {

    @Autowired
    private final EarningsServiceImpl earningsService;

    public EarningsController(EarningsServiceImpl earningsService) {
        this.earningsService = earningsService;
    }
    @GetMapping("/{employeeId}")
    public List<EarningsDTO> getEarnings(@PathVariable Long employeeId){
        return earningsService.getEarningsByEmployeeId(employeeId);
    }
    @PostMapping("/{employeeId}")
    public EarningsDTO createEarnings(@PathVariable Long employeeId,@RequestBody EarningsDTO earningsDTO){
        return earningsService.createEarnings(employeeId,earningsDTO);
    }
    @PutMapping("/{earningsId}")
    public EarningsDTO updateEarnings(@PathVariable Long earningsId,@RequestBody EarningsDTO earningsDTO) {
        return earningsService.updateEarning(earningsId, earningsDTO);
    }
    @DeleteMapping("/{earningsId}")
    public void deleteEarnings(@PathVariable Long earningsId) {
        earningsService.deleteEarning(earningsId);
    }
}
