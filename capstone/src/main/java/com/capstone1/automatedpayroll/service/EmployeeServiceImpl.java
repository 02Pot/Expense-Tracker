package com.capstone1.automatedpayroll.service;

import java.util.List;
import java.util.stream.Collectors;

import com.capstone1.automatedpayroll.dto.PayrollDTO;
import com.capstone1.automatedpayroll.mapper.PayrollMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.capstone1.automatedpayroll.dto.EmployeeDTO;
import com.capstone1.automatedpayroll.mapper.EmployeeMapper;
import com.capstone1.automatedpayroll.model.EmployeeModel;
import com.capstone1.automatedpayroll.repository.EmployeeRepository;
import com.capstone1.automatedpayroll.repository.PayrollRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class EmployeeServiceImpl {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private PayrollRepository payrollRepository;

    public Page<EmployeeDTO> getAllEmployee(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return employeeRepository.findAll(pageable).map(employee -> EmployeeMapper.mapToEmployeeDTO(employee,payrollRepository));
    }

    public EmployeeDTO getEmployee(Long employeeId){
        EmployeeModel employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new UsernameNotFoundException("No User with Id of " + employeeId));

        return EmployeeMapper.mapToEmployeeDTO(employee,payrollRepository);
    }

    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO){
        EmployeeModel employee = EmployeeMapper.mapToEmployee(employeeDTO);
        employee.seteId(null);

        EmployeeModel saved = employeeRepository.save(employee);

        return EmployeeMapper.mapToEmployeeDTO(saved,payrollRepository);
    }

    public EmployeeDTO updateEmployee( Long employeeId,EmployeeDTO employeeDTO) {
        EmployeeModel existingEmployee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Earnings not found"));
        if (employeeDTO.getEmployeeFirstName() != null) {
            existingEmployee.setEmployeeFirstName(employeeDTO.getEmployeeFirstName());
        }
        if (employeeDTO.getEmployeeLastName() != null) {
            existingEmployee.setEmployeeLastName(employeeDTO.getEmployeeLastName());
        }
        if (employeeDTO.getEmployeeAddress() != null) {
            existingEmployee.setEmployeeFirstName(employeeDTO.getEmployeeFirstName());
        }
        if (employeeDTO.getEmployeeContactNumber() != null) {
            existingEmployee.setEmployeeContactNumber(employeeDTO.getEmployeeContactNumber());
        }
        if (employeeDTO.getEmployeeEmail() != null) {
            existingEmployee.setEmployeeEmail(employeeDTO.getEmployeeEmail());
        }
        if (employeeDTO.getEmployeeDepartment() != null) {
            existingEmployee.setEmployeeDepartment(employeeDTO.getEmployeeDepartment());
        }
        if (employeeDTO.getDateOfHire() != null) {
            existingEmployee.setDateOfHire(employeeDTO.getDateOfHire());
        }
        if (employeeDTO.getEmployeeEmploymentType() != null) {
            existingEmployee.setEmployeeEmploymentType(employeeDTO.getEmployeeEmploymentType());
        }
        if (employeeDTO.getEmployeeRate() != null) {
            existingEmployee.setEmployeeRate(employeeDTO.getEmployeeRate());
        }

        EmployeeModel updated = employeeRepository.save(existingEmployee);

        return EmployeeMapper.mapToEmployeeDTO(updated,payrollRepository);
    }

    public void deleteEmployee(Long employeeId){
        employeeRepository.deleteById(employeeId);
    }

}
