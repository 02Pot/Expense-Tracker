package com.capstone1.automatedpayroll.mapper;

import com.capstone1.automatedpayroll.dto.EmployeeDTO;
import com.capstone1.automatedpayroll.model.EmployeeModel;
import com.capstone1.automatedpayroll.model.PayrollModel;
import com.capstone1.automatedpayroll.repository.PayrollRepository;

public class EmployeeMapper {

    public static EmployeeDTO mapToEmployeeDTO(EmployeeModel employeeModel, PayrollRepository payrollRepository) {
        if (employeeModel == null) return null;
//        System.out.println("DB: " + employeeModel.getEmployeeEmploymentType());
        Long latestPayroll = payrollRepository
                .findTopByEmployee_EIdOrderByDateProcessedDesc(employeeModel.geteId())
                .map(PayrollModel::getId)
                .orElse(null);

        return new EmployeeDTO(
                employeeModel.geteId(),
                employeeModel.getEmployeeFirstName(),
                employeeModel.getEmployeeLastName(),
                employeeModel.getEmployeeAddress(),
                employeeModel.getEmployeeContactNumber(),
                employeeModel.getEmployeeEmail(),
                employeeModel.getEmployeeDepartment(),
                employeeModel.getDateOfHire(),
                employeeModel.getEmployeeGender(),
                employeeModel.getEmployeeEmploymentType(),
                employeeModel.getEmployeeRate(),
                employeeModel.getMonthlySalary(),
                latestPayroll

        );
    }

    public static EmployeeModel mapToEmployee(EmployeeDTO employeeDTO){
        if (employeeDTO == null) return null;

        return EmployeeModel.builder()
                .eId(employeeDTO.getEmployeeId())
                .employeeFirstName(employeeDTO.getEmployeeFirstName())
                .employeeLastName(employeeDTO.getEmployeeLastName())
                .employeeAddress(employeeDTO.getEmployeeAddress())
                .employeeContactNumber(employeeDTO.getEmployeeContactNumber())
                .employeeEmail(employeeDTO.getEmployeeEmail())
                .employeeDepartment(employeeDTO.getEmployeeDepartment())
                .dateOfHire(employeeDTO.getDateOfHire())
                .employeeGender(employeeDTO.getEmployeeGender())
                .employmentType(employeeDTO.getEmployeeEmploymentType())
                .employeeRate(employeeDTO.getEmployeeRate())
                .monthlySalary(employeeDTO.getMonthlySalary())
                .build();
    }
}
