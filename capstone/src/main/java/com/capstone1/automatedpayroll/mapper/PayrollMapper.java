package com.capstone1.automatedpayroll.mapper;

import com.capstone1.automatedpayroll.dto.PayrollDTO;
import com.capstone1.automatedpayroll.model.EmployeeModel;
import com.capstone1.automatedpayroll.model.PayrollModel;

import java.sql.Date;

public class PayrollMapper {
    public static PayrollDTO mapToPayrollDTO(PayrollModel payrollModel) {
        if (payrollModel == null) return null;
        PayrollDTO dto = new PayrollDTO();

        dto.setId(payrollModel.getId());
        dto.setDateProcessed(payrollModel.getDateProcessed());
        dto.setStartDate(payrollModel.getStartDate());
        dto.setEndDate(payrollModel.getEndDate());
        dto.setGrossPay(payrollModel.getGrossPay());
        dto.setNetPay(payrollModel.getNetPay());
        dto.setTotalDeductions(payrollModel.getTotalDeductions());
        dto.setNonStationaryEarnings(payrollModel.getNonStationaryEarnings());
        dto.setStatus(payrollModel.getDateProcessed() != null ? "Completed" : "Pending");

        if (payrollModel.getEmployee() != null) {
            dto.setUserId(payrollModel.getEmployee().geteId().doubleValue());
        }
        return dto;
    }
    public static PayrollModel mapToPayroll(PayrollDTO payrollDTO, EmployeeModel employee) {
        if (payrollDTO == null) return null;

        return PayrollModel.builder()
                .id(payrollDTO.getId())
                .employee(employee)
                .nonStationaryEarnings(payrollDTO.getNonStationaryEarnings())
                .startDate(payrollDTO.getStartDate())
                .endDate(payrollDTO.getEndDate())
                .grossPay(payrollDTO.getGrossPay())
                .totalDeductions(payrollDTO.getTotalDeductions())
                .netPay(payrollDTO.getNetPay())
                .dateProcessed(payrollDTO.getDateProcessed())
                .status(payrollDTO.getStatus())
                .build();
    }
}
