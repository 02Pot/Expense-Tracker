package com.capstone1.automatedpayroll.service;

import com.capstone1.automatedpayroll.dto.DeductionsDTO;
import com.capstone1.automatedpayroll.mapper.PayMapper;
import com.capstone1.automatedpayroll.model.DeductionsModel;
import com.capstone1.automatedpayroll.model.EarningsModel;
import com.capstone1.automatedpayroll.model.EmployeeModel;
import com.capstone1.automatedpayroll.repository.DeductionsRepository;
import com.capstone1.automatedpayroll.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeductionsServiceImpl {


    @Autowired
    private DeductionsRepository deductionsRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    public List<DeductionsDTO> getDeductionsByEmployeeId(Long employeeId) {
        EmployeeModel employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee Not Found"));
        return deductionsRepository.findByEmployee(employee)
                .stream()
                .map(e -> {
                    DeductionsDTO dto = new DeductionsDTO();
                    dto.setEmployeeId(e.getEmployee().geteId());
                    dto.setDeductionsId(e.getDeductionId());
                    dto.setDeductionsName(e.getDeductionName());
                    dto.setDeductionsAmount(e.getDeductionAmount());
                    return dto;
                })
                .collect(Collectors.toList());

    }

    public DeductionsDTO createDeduction(Long employeeId,DeductionsDTO deductionsDTO){
        EmployeeModel employee = employeeRepository.findById(employeeId)
                .orElseThrow(()-> new RuntimeException("Employee not found"));

        DeductionsModel deductions = PayMapper.mapToDeductions(deductionsDTO,employee);
        deductions.setDeductionId(null);
        deductions.setEmployee(employee);
        DeductionsModel saved = deductionsRepository.save(deductions);
        return PayMapper.mapToDeductionsDTO(saved);

    }

    public DeductionsDTO updateDeduction( Long deductionsId,DeductionsDTO deductionsDTO) {
        DeductionsModel existingDeduction = deductionsRepository.findById(deductionsId)
                .orElseThrow(() -> new EntityNotFoundException("Earnings not found"));

        if (deductionsDTO.getDeductionsName() != null) {
            existingDeduction.setDeductionName(deductionsDTO.getDeductionsName());
        }if (deductionsDTO.getDeductionsAmount() != null) {
            existingDeduction.setDeductionAmount(deductionsDTO.getDeductionsAmount());
        }
        DeductionsModel updated = deductionsRepository.save(existingDeduction);

        return PayMapper.mapToDeductionsDTO(updated);    }

    public void deleteDeduction(Long deductionId){
        deductionsRepository.deleteById(deductionId);
    }
}
