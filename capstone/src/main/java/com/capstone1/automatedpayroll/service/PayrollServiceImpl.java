package com.capstone1.automatedpayroll.service;

import com.capstone1.automatedpayroll.dto.EarningsDTO;
import com.capstone1.automatedpayroll.dto.PayrollDTO;
import com.capstone1.automatedpayroll.helper.PayrollDateUtils;
import com.capstone1.automatedpayroll.mapper.PayrollMapper;
import com.capstone1.automatedpayroll.model.*;
import com.capstone1.automatedpayroll.model.enums.EmployeeType;
import com.capstone1.automatedpayroll.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PayrollServiceImpl {

    @Autowired
    private AttendanceRepository attendanceRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private EarningsRepository earningsRepository;
    @Autowired
    private DeductionsRepository deductionsRepository;
    @Autowired
    private PayrollDateUtils payrollDateUtils;
    @Autowired
    private EarningsServiceImpl earningsService;
    @Autowired
    private PayrollRepository payrollRepository;

    private double calculateGrossPay(EmployeeModel employee, List<AttendanceModel> attendances, LocalDate startDate, LocalDate endDate) {
        if (employee.getEmployeeEmploymentType() == EmployeeType.Regular) {
            if (attendances == null || attendances.isEmpty()){
                return 0;
            }

            double salaryPerCutOff = employee.getMonthlySalary() / 2;
            double annualSalary = employee.getMonthlySalary() * 12;
            double dailyRate = annualSalary / 261; //261 ay 247 na ordinary work day + 12 holiday + 2 special holiday

            long requiredWorkDays = payrollDateUtils.getWorkingDays(startDate, endDate);
            long daysAttended = attendances.size(); // Number of days they showed up

            long daysAbsent = 0;
            if (daysAttended < requiredWorkDays) {
                daysAbsent = requiredWorkDays - daysAttended;
            }
            double deductionForAbsence = daysAbsent * dailyRate;
            double grossPay = salaryPerCutOff - deductionForAbsence;

            return Math.round(grossPay * 100.0) / 100.0;
        } else {
            double totalHours = attendances.stream()
                    .mapToDouble(AttendanceModel::getHoursWorked)
                    .sum();
            return totalHours * employee.getEmployeeRate();
        }
    }

    private double calculateNetPay(EmployeeModel employee,double grossPay, LocalDate startDate,LocalDate endDate){
        List<EarningsDTO> stationaryEarnings = earningsService.getStationaryEarnings(employee.geteId());
        double stationaryTotal = stationaryEarnings.stream()
                .mapToDouble(EarningsDTO::getEarningsAmount)
                .sum();
        double nonStationaryTotal = calculateNonStationaryEarnings(employee.geteId(), startDate, endDate);
        double totalDeductions = calculateTotalDeductions(employee.geteId(),grossPay);

        double netPay = grossPay + stationaryTotal + nonStationaryTotal - totalDeductions;
        return Math.round(netPay * 100.0) / 100.0;
    }

    private double calculateNonStationaryEarnings(Long employeeId,LocalDate startDate,LocalDate endDate){
        return earningsRepository.findByEmployeeEId(employeeId)
                .stream()
                .filter(e -> !e.isStationary())
                .filter(e -> !e.getDateCreated().isBefore(startDate) && !e.getDateCreated().isAfter(endDate))
                .mapToDouble(EarningsModel::getEarningAmount)
                .sum();
    }

    public double calculateWithHoldingTax(double monthlySalary,double totalStatutoryDeductions){
        double taxableIncome = monthlySalary - totalStatutoryDeductions;

        if (taxableIncome <= 20833.33){
            return 0;
        }
        //Pag above 20,833
        double excess = taxableIncome - 20833.33;
        return excess * 0.15;
    }
    //Per CutOFF
    public double calculatePagIbig(double monthlySalary){
        double rate = 0;
        if (monthlySalary <= 1500){
            rate = 0.01;
        }else {
            rate = 0.02;
        }
        double contribution = monthlySalary * rate;
        double monthlyDeduction = Math.min(contribution,200.0);
        double result = monthlyDeduction / 2;
        return Math.round(result * 100.0)/ 100.0;
    }

    public double calculatePhilHealth(double monthlySalary){
        double salaryBase = Math.max(10000.0, Math.min(monthlySalary, 100000.0));
        double totalMonthlyPremium = salaryBase * 0.05;
        return (totalMonthlyPremium / 2) / 2;
    }

    public double calculateGSIS(double monthlySalary){
        double contribution = monthlySalary * 0.09;
        return contribution / 2;
    }

    private double calculateTotalDeductions(Long employeeId,double grossPay){
        EmployeeModel employee = employeeRepository.findById(employeeId).orElseThrow();
        double total = 0;
        double mbs = employee.getMonthlySalary();

        if (employee.getEmployeeEmploymentType() == EmployeeType.Regular){
            double pagIbig = calculatePagIbig(grossPay);
            double philHealth = calculatePhilHealth(mbs);
            double gsis = calculateGSIS(mbs);

            //TODO wala pa withholding tax para sa mataas na sahod
            total += pagIbig + philHealth + gsis;
        }

        double otherDeductions = deductionsRepository.findByEmployeeEId(employeeId)
                .stream()
                .mapToDouble(DeductionsModel::getDeductionAmount)
                .sum();
        total += otherDeductions;
        return total;
    }

    private void clearNonStationaryEarnings(Long employeeId, LocalDate start, LocalDate end) {
        List<EarningsModel> toDelete = earningsRepository.findByEmployeeEId(employeeId)
                .stream()
                .filter(e -> !e.isStationary())
                .filter(e -> !e.getDateCreated().isBefore(start) && !e.getDateCreated().isAfter(end))
                .toList();
        earningsRepository.deleteAll(toDelete);
    }

    public void runPayrollCurrentCutOff(){
        Map.Entry<LocalDate,LocalDate> cutOff = payrollDateUtils.getCutOffPeriod(LocalDate.now());
        LocalDate startDate = cutOff.getKey();
        LocalDate endDate = cutOff.getValue();

        processPayroll(startDate,endDate);

        //TODO LOG for HISTORY
    }

    public void processPayroll(LocalDate startDate, LocalDate endDate){
        List<EmployeeModel> employees = employeeRepository.findAll();

        for (EmployeeModel employee : employees){
            clearNonStationaryEarnings(employee.geteId(),startDate,endDate);
            List<AttendanceModel> attendances = attendanceRepository
                    .findByEmployee_EIdAndAttendanceDateBetween(employee.geteId(), startDate, endDate);

            double grossPay = calculateGrossPay(employee,attendances,startDate,endDate);

            double totalDeductions = calculateTotalDeductions(employee.geteId(),grossPay);
            double netPay = calculateNetPay(employee,grossPay, startDate, endDate);

            PayrollModel payroll = new PayrollModel();
            payroll.setEmployee(employee);
            payroll.setStartDate(startDate);
            payroll.setEndDate(endDate);
            payroll.setGrossPay(grossPay);
            payroll.setTotalDeductions(totalDeductions);
            payroll.setNetPay(netPay);
            payroll.setDateProcessed(LocalDate.now());
            payrollRepository.save(payroll);
        }
    }

    public List<PayrollDTO> getPayrollByCutoff(){
        Map.Entry<LocalDate,LocalDate> cutOff = payrollDateUtils.getCutOffPeriod(LocalDate.now());
        LocalDate startDate = cutOff.getKey();
        LocalDate endDate = cutOff.getValue();

        List<PayrollModel> payrolls = payrollRepository.findByStartDateAndEndDate(startDate, endDate);
        if (!payrolls.isEmpty()) {
            return payrolls.stream().map(PayrollMapper::mapToPayrollDTO).toList();
        }

        PayrollDTO placeholder = new PayrollDTO();
        placeholder.setStartDate(startDate);
        placeholder.setEndDate(endDate);
        placeholder.setDateProcessed(null);

        return List.of(placeholder);
    }

    public Page<PayrollDTO> getAllPayroll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("dateProcessed").descending());
        return payrollRepository.findAll(pageable).map(PayrollMapper::mapToPayrollDTO);
    }

//    @Scheduled(cron = "0 0 0 * * ?") // runs every day at midnight //test
//    public void runScheduledPayroll() {
//        LocalDate today = LocalDate.now();
//        int day = today.getDayOfMonth();
//        int lastDayOfMonth = today.lengthOfMonth();
//
//        // Only run payroll on cutoff days: 15th or last day
//        if (day == 15 || day == lastDayOfMonth) {
//            System.out.println("Running scheduled payroll for cutoff: " + today);
//            runPayrollCurrentCutOff();
//        } else {
//            System.out.println("Today is not a cutoff. Payroll will not run.");
//        }
//    }

}
