package com.capstone1.automatedpayroll.mapper;

import com.capstone1.automatedpayroll.dto.DeductionsDTO;
import com.capstone1.automatedpayroll.dto.EarningsDTO;
import com.capstone1.automatedpayroll.model.DeductionsModel;
import com.capstone1.automatedpayroll.model.EarningsModel;
import com.capstone1.automatedpayroll.model.EmployeeModel;

public class PayMapper {

    public static EarningsDTO mapToEarningsDTO(EarningsModel earningsModel) {
        if (earningsModel == null) return null;

        return new EarningsDTO(
                earningsModel.getEmployee().geteId(),
                earningsModel.getEarningId(),
                earningsModel.isStationary(),
                earningsModel.getEarningName(),
                earningsModel.getEarningAmount()
        );
    }

    public static EarningsModel mapToEarnings(EarningsDTO earningsDTO, EmployeeModel employee) {
        if (earningsDTO == null) return null;

        return EarningsModel.builder()
                .earningId(earningsDTO.getEarningsId())
                .earningName(earningsDTO.getEarningsName())
                .stationary(earningsDTO.isStationary())
                .earningAmount(earningsDTO.getEarningsAmount())
                .employee(employee)
                .build();
    }

    public static DeductionsDTO mapToDeductionsDTO(DeductionsModel deductionsModel) {
        if (deductionsModel == null) return null;

        return new DeductionsDTO(
                deductionsModel.getEmployee().geteId(),
                deductionsModel.getDeductionId(),
                deductionsModel.isStationary(),
                deductionsModel.getDeductionName(),
                deductionsModel.getDeductionPercentage(),
                deductionsModel.getMaxAmount(),
                deductionsModel.getDeductionAmount()
        );
    }

    public static DeductionsModel mapToDeductions(DeductionsDTO deductionsDTO,EmployeeModel employee) {
        if (deductionsDTO == null) return null;

        return DeductionsModel.builder()
                .deductionId(deductionsDTO.getDeductionsId())
                .deductionName(deductionsDTO.getDeductionsName())
                .stationary(deductionsDTO.isStationary())
                .deductionAmount(deductionsDTO.getDeductionsAmount())
                .maxAmount(deductionsDTO.getMaxAmount())
                .employee(employee)
                .build();
    }
}
