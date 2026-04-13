package com.capstone1.automatedpayroll.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MailModel {
    private EmployeeModel employee;
    private PayrollModel payrollModel;
    private String subject;
    private String message;
}
