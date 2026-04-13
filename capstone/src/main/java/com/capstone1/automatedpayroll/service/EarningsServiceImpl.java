package com.capstone1.automatedpayroll.service;

import com.capstone1.automatedpayroll.dto.EarningsDTO;
import com.capstone1.automatedpayroll.dto.EmployeeDTO;
import com.capstone1.automatedpayroll.mapper.EmployeeMapper;
import com.capstone1.automatedpayroll.mapper.PayMapper;
import com.capstone1.automatedpayroll.model.EarningsModel;
import com.capstone1.automatedpayroll.model.EmployeeModel;
import com.capstone1.automatedpayroll.repository.EarningsRepository;
import com.capstone1.automatedpayroll.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EarningsServiceImpl {

    @Autowired
    private EarningsRepository earningsRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    public List<EarningsDTO> getEarningsByEmployeeId(Long employeeId) {
        EmployeeModel employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee Not Found"));
        return earningsRepository.findByEmployee(employee)
                .stream()
                .map(e -> {
                    EarningsDTO dto = new EarningsDTO();
                    dto.setEmployeeId(e.getEmployee().geteId());
                    dto.setEarningsId(e.getEarningId());
                    dto.setEarningsName(e.getEarningName());
                    dto.setEarningsAmount(e.getEarningAmount());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public List<EarningsDTO> getStationaryEarnings(Long employeeId) {
        EmployeeModel employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee Not Found"));
        return earningsRepository.findByEmployeeEIdAndStationaryTrue(employeeId)
                .stream()
                .map(e -> {
                    EarningsDTO dto = new EarningsDTO();
                    dto.setEmployeeId(e.getEmployee().geteId());
                    dto.setEarningsId(e.getEarningId());
                    dto.setEarningsName(e.getEarningName());
                    dto.setEarningsAmount(e.getEarningAmount());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public EarningsDTO createEarnings(Long employeeId,EarningsDTO earningsDTO){
        EmployeeModel employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        EarningsModel earnings = PayMapper.mapToEarnings(earningsDTO,employee);
        earnings.setEarningId(null);
        earnings.setStationary(earningsDTO.isStationary());
        earnings.setEmployee(employee);
        EarningsModel saved = earningsRepository.save(earnings);
        return PayMapper.mapToEarningsDTO(saved);
    }

    public EarningsDTO updateEarning( Long earningsId,EarningsDTO earningsDTO) {
        EarningsModel existingEarnings = earningsRepository.findById(earningsId)
                .orElseThrow(() -> new EntityNotFoundException("Earnings not found"));
        if (earningsDTO.getEarningsName() != null) {
            existingEarnings.setEarningName(earningsDTO.getEarningsName());
        }if (earningsDTO.getEarningsAmount() != null) {
            existingEarnings.setEarningAmount(earningsDTO.getEarningsAmount());
        }
        EarningsModel updated = earningsRepository.save(existingEarnings);

        return PayMapper.mapToEarningsDTO(updated);
    }

    public void deleteEarning(Long earningsId){
        earningsRepository.deleteById(earningsId);
    }


}
