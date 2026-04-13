package com.capstone1.automatedpayroll.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone1.automatedpayroll.model.MailModel;
import com.capstone1.automatedpayroll.service.MailService;

import jakarta.mail.MessagingException;


@RestController
@RequestMapping("/mail")
public class MailController {

    @Autowired
    private MailService mailService;

    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping("/send/{employeeId}")
    public String sendEmail(@PathVariable Long employeeId) throws MessagingException {
        mailService.sendMail(employeeId);
        return "Success";
    }

    @PostMapping("/send-all")
    public ResponseEntity<String> sendAllPayslip() {
        mailService.sendAllMail();
        return ResponseEntity.ok("Payslip emails dispatched.");
    }

}