package com.capstone1.automatedpayroll.model;

import com.capstone1.automatedpayroll.model.enums.EmployeeType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import com.capstone1.automatedpayroll.model.enums.Gender;

import java.time.LocalDate;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "employee")
public class EmployeeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eId;
    private String employeeEmail;
    private String employeeFirstName;
    private String employeeLastName;
    private Long employeeContactNumber;
    private String employeeAddress;
    @Enumerated(EnumType.STRING)
    private Gender employeeGender;

    @Enumerated(EnumType.STRING)
    @Column(name = "employee_employment_type")
    private EmployeeType employmentType;

    private LocalDate dateOfHire;
    private String employeeDepartment;

    private Long employeeRate;
    private double monthlySalary;

    @OneToMany(mappedBy = "employee",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<EarningsModel> earningsModels;
    @OneToMany(mappedBy = "employee",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<DeductionsModel> deductionsModels;
    @OneToMany(mappedBy = "employee",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<AttendanceModel> attendanceModels;
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<PayrollModel> payrollModel;

    public EmployeeModel(Long eId, String employeeEmail, String employeeFirstName, String employeeLastName, long employeeContactNumber,
                         String employeeAddress, Gender employeeGender, EmployeeType employmentType, LocalDate dateOfHire, String employeeDepartment, Long employeeRate
                         , double monthlySalary, List<DeductionsModel> deductionsModel, List<EarningsModel> earningsModel
                         ) {
        this.eId = eId;
        this.employeeEmail = employeeEmail;
        this.employeeFirstName = employeeFirstName;
        this.employeeLastName = employeeLastName;
        this.employeeContactNumber = employeeContactNumber;
        this.employeeAddress = employeeAddress;
        this.employeeGender = employeeGender;
        this.employmentType = employmentType;
        this.dateOfHire = dateOfHire;
        this.employeeDepartment = employeeDepartment;
        this.employeeRate = employeeRate;
        this.deductionsModels = deductionsModel;
        this.earningsModels = earningsModel;
    }

    public Long getEmployeeRate() {return employeeRate;}
    public void setEmployeeRate(Long employeeRate) {this.employeeRate = employeeRate;}
    public double getMonthlySalary() {return monthlySalary;}
    public void setMonthlySalary(double monthlySalary) {this.monthlySalary = monthlySalary;}
    public String getEmployeeEmail() {return employeeEmail;}
    public void setEmployeeEmail(String employeeEmail) {this.employeeEmail = employeeEmail;}
    public String getEmployeeFirstName() {return employeeFirstName;}
    public void setEmployeeFirstName(String employeeFirstName) {this.employeeFirstName = employeeFirstName;}
    public String getEmployeeLastName() {return employeeLastName;}
    public void setEmployeeLastName(String employeeLastName) {this.employeeLastName = employeeLastName;}
    public Long getEmployeeContactNumber() {return employeeContactNumber;}
    public void setEmployeeContactNumber(Long employeeContactNumber) {this.employeeContactNumber = employeeContactNumber;}
    public String getEmployeeAddress() {return employeeAddress;}
    public void setEmployeeAddress(String employeeAddress) {this.employeeAddress = employeeAddress;}
    public void setEmployeeGender(Gender employeeGender) {this.employeeGender = employeeGender;}
    public Gender getEmployeeGender(){return employeeGender;}
    public EmployeeType getEmployeeEmploymentType() {return employmentType;}
    public void setEmployeeEmploymentType(EmployeeType employeeType) {this.employmentType = employmentType;}
    public LocalDate getDateOfHire() {return dateOfHire;}
    public void setDateOfHire(LocalDate dateOfHire) {this.dateOfHire = dateOfHire;}
    public String getEmployeeDepartment() {return employeeDepartment;}
    public void setEmployeeDepartment(String employeeDepartment) {this.employeeDepartment = employeeDepartment;}
    public Long geteId() {return eId;}
    public void seteId(Long eId) {this.eId = eId;}
    public void setEarningsModels (List<EarningsModel> earningsModels) {this.earningsModels = earningsModels;}
    public List<EarningsModel> getEarningsModel(){return earningsModels;}
    public void setDeductionsModels(List<DeductionsModel> deductionsModels) {this.deductionsModels = deductionsModels;}
    public List<DeductionsModel> getDeductionsModel(){return deductionsModels;}
    public void setPayrollModel(List<PayrollModel> payrollModel) {this.payrollModel = payrollModel;}
    public List<PayrollModel> getPayrollModel() {return payrollModel;}
}
