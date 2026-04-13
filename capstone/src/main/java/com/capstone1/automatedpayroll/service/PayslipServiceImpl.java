package com.capstone1.automatedpayroll.service;

import com.capstone1.automatedpayroll.dto.EarningsDTO;
import com.capstone1.automatedpayroll.dto.PayslipDTO;
import com.capstone1.automatedpayroll.model.*;
import com.capstone1.automatedpayroll.model.enums.EmployeeType;
import com.capstone1.automatedpayroll.repository.AttendanceRepository;
import com.capstone1.automatedpayroll.repository.DeductionsRepository;
import com.capstone1.automatedpayroll.repository.EarningsRepository;
import com.capstone1.automatedpayroll.repository.PayrollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PayslipServiceImpl {

    @Autowired
    PayrollRepository payrollRepository;
    @Autowired
    AttendanceRepository attendanceRepository;
    @Autowired
    EarningsRepository earningsRepository;
    @Autowired
    EarningsServiceImpl earningsService;
    @Autowired
    DeductionsRepository deductionsRepository;
    @Autowired
    PayrollServiceImpl payrollService;

    public PayslipDTO generatePayslip(Long payrollId){
        PayrollModel payroll = payrollRepository.findById(payrollId)
                .orElseThrow(() -> new RuntimeException("Payroll not found"));

        EmployeeModel emp = payroll.getEmployee();

        List<AttendanceModel> attendances = attendanceRepository
                .findByEmployee_EIdAndAttendanceDateBetween(emp.geteId(),
                        payroll.getStartDate(), payroll.getEndDate());
        double workedHours = attendances.stream()
                .mapToDouble(AttendanceModel::getHoursWorked)
                .sum();
        long workedDays = attendances.size();
        List<EarningsDTO> stationaryEarnings = earningsService.getStationaryEarnings(emp.geteId());
        double stationaryTotal = stationaryEarnings.stream()
                .mapToDouble(EarningsDTO::getEarningsAmount)
                .sum();

        double nonStationaryTotal = payroll.getNonStationaryEarnings();
        List<Map<String, Object>> earningsList = new java.util.ArrayList<>();

        switch (emp.getEmployeeEmploymentType()) {
            case Regular -> earningsList.add(Map.of("name", "Basic Pay", "amount", emp.getMonthlySalary()));
            case Part_Time -> earningsList.add(Map.of("name", "Basic Pay", "amount", emp.getEmployeeRate()));
        }

        stationaryEarnings.forEach(e -> earningsList.add(Map.of(
                "name", e.getEarningsName(),
                "amount", e.getEarningsAmount()
        )));

        if (nonStationaryTotal > 0) {
            earningsList.add(Map.of("name", "Non-Stationary Earnings", "amount", nonStationaryTotal));
        }

        double totalEarnings = payroll.getGrossPay() + stationaryTotal + nonStationaryTotal;

        // Deductions
        List<DeductionsModel> otherDeductions = deductionsRepository.findByEmployeeEId(emp.geteId());
        List<Map<String,Object>> deductionsList = new java.util.ArrayList<>();
        double totalDeductions = 0;

        if (emp.getEmployeeEmploymentType() == EmployeeType.Regular){
            double pagIbig = payrollService.calculatePagIbig(payroll.getGrossPay());
            double philHealth = payrollService.calculatePhilHealth(emp.getMonthlySalary());
            double gsis = payrollService.calculateGSIS(emp.getMonthlySalary());

            deductionsList.add(Map.of("name","Pag-IBIG","amount",pagIbig));
            deductionsList.add(Map.of("name","PhilHealth","amount",philHealth));
            deductionsList.add(Map.of("name","GSIS","amount",gsis));

            totalDeductions += pagIbig + philHealth + gsis;
        }else if(emp.getEmployeeEmploymentType() == EmployeeType.Part_Time){
            double partTimeDeduction = payroll.getGrossPay() * 0.10;
            deductionsList.add(Map.of("name","Part-Time Deduction - 10%","amount",partTimeDeduction));
            totalDeductions += partTimeDeduction;
        }
        // Mandatory deductions

        otherDeductions.forEach(d -> deductionsList.add(Map.of(
                "name", d.getDeductionName(),
                "amount", d.getDeductionAmount()
        )));

        totalDeductions += otherDeductions.stream()
            .mapToDouble(DeductionsModel::getDeductionAmount)
            .sum();

        PayslipDTO dto = new PayslipDTO();
        dto.setId(emp.geteId());
        dto.setEmployeeName(emp.getEmployeeFirstName()+emp.getEmployeeLastName());
        dto.setDateOfJoining(emp.getDateOfHire());
        dto.setEmployeeType(emp.getEmployeeEmploymentType().name());
        dto.setDepartment(emp.getEmployeeDepartment());
        if (emp.getEmployeeEmploymentType() == EmployeeType.Regular){
            dto.setWorkedDays(workedDays);
            dto.setWorkedHours(null);
        }else {
            dto.setWorkedHours(workedHours);
            dto.setWorkedDays(null);
        }
        if (emp.getEmployeeEmploymentType() == EmployeeType.Regular){
            dto.setEmployeeRate(0);
            dto.setMonthlySalary(emp.getMonthlySalary());
        }else {
            dto.setMonthlySalary(0);
            dto.setEmployeeRate(emp.getEmployeeRate());
        }
        dto.setPayrollStartDate(payroll.getStartDate());
        dto.setPayrollEndDate(payroll.getEndDate());
        dto.setEarnings(earningsList);
        dto.setDeductions(deductionsList);
        dto.setTotalEarnings(totalEarnings);
        dto.setTotalDeductions(totalDeductions);
        dto.setNetPay(totalEarnings - totalDeductions);

        return dto;
    }

}
