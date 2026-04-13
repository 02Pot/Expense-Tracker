package com.capstone1.automatedpayroll.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.capstone1.automatedpayroll.dto.PayslipDTO;
import com.capstone1.automatedpayroll.model.EmployeeModel;
import com.capstone1.automatedpayroll.model.MailModel;
import com.capstone1.automatedpayroll.model.PayrollModel;
import com.capstone1.automatedpayroll.repository.EmployeeRepository;
import com.capstone1.automatedpayroll.repository.PayrollRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromMail;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private PayrollRepository payrollRepository;
    @Autowired
    private PayslipServiceImpl payslipService;

    public void sendAllMail(){
        List<EmployeeModel> employees = employeeRepository.findAll();
        List<String> failed = new ArrayList<>();

        for(EmployeeModel employee : employees){

            if(employee.getEmployeeEmail() == null || employee.getEmployeeEmail().isBlank()){
                failed.add("ID " + employee.geteId() + " (noemail)");
                continue;
            }
            boolean hasPayroll = payrollRepository.findTopByEmployee_EIdOrderByDateProcessedDesc(employee.geteId()).isPresent();
            if(!hasPayroll){
                failed.add(employee.getEmployeeFirstName() + employee.getEmployeeLastName() + " (no payroll record)");
                continue;
            }

            try {
            sendMail(employee.geteId());
            } catch (MessagingException e) {
                failed.add(employee.getEmployeeLastName() + " (mail error: " + e.getMessage() + ")");
            } catch (Exception e) {
                failed.add(employee.getEmployeeLastName() + " (error: " + e.getMessage() + ")");
            }
        }
        if (!failed.isEmpty()) {
            System.err.println("Failed to send payslip to: " + failed);
        }
    }

    public void sendMail(Long employeeId) throws MessagingException {
        EmployeeModel employee = employeeRepository.findById(employeeId).orElseThrow();
        PayrollModel payroll = payrollRepository.findTopByEmployee_EIdOrderByDateProcessedDesc(employeeId).orElseThrow();

        PayslipDTO payslip = payslipService.generatePayslip(payroll.getId());

        StringBuilder tableRows = new StringBuilder();
        int maxSize = Math.max(payslip.getEarnings().size(),payslip.getDeductions().size());

        for(int i = 0; i< maxSize; i++){

            String earningName = "";
            String earningAmount = "";
            String deductionName = "";
            String deductionAmount = "";

            if (i < payslip.getEarnings().size()) {
                Map<String, Object> e = payslip.getEarnings().get(i);
                earningName = (String) e.get("name");
                earningAmount = String.format("₱ %.2f", (double) e.get("amount"));
            }

            if (i < payslip.getDeductions().size()) {
                Map<String, Object> d = payslip.getDeductions().get(i);
                deductionName = (String) d.get("name");
                deductionAmount = String.format("₱ %.2f", (double) d.get("amount"));
            }

            tableRows.append(String.format("""
            <tr>
                <td style="padding: 8px; border: 1px solid #ddd;">%s</td>
                <td style="text-align: right; padding: 8px; border: 1px solid #ddd;">%s</td>
                <td style="padding: 8px; border: 1px solid #ddd;">%s</td>
                <td style="text-align: right; padding: 8px; border: 1px solid #ddd;">%s</td>
            </tr>
            """, earningName, earningAmount, deductionName, deductionAmount));
        }

        payroll.setEmployee(employee);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true);
        helper.setFrom(fromMail);

        helper.setTo(employee.getEmployeeEmail());
        helper.setSubject("Payslip Email - " + employee.getEmployeeLastName() + " " + employee.getEmployeeFirstName());

        EmployeeModel emp = employee;

        String htmlContent = """
             <html>
               <body style="font-family: Arial, sans-serif; color: #333;">
                   <div style="max-width: 600px; margin: auto; border: 1px solid #ddd; padding: 24px; border-radius: 8px;">
                       <h2 style="text-align: center;">Pateros Technological College</h2>
                       <h3 style="text-align: center;">Payslip</h3>

                       <table width="100%%" style="margin-bottom: 16px;">
                           <tr>
                               <td><strong>Employee Name:</strong> %s</td>
                               <td><strong>Employee Type:</strong> %s</td>
                           </tr>
                           <tr>
                               <td><strong>Department:</strong> %s</td>
                               <td><strong>Pay Period:</strong> %s to %s</td>
                           </tr>
                           <tr>
                               <td><strong>Date Processed:</strong> %s</td>
                           </tr>
                       </table>

                       <table width="100%%" style="border-collapse: collapse; margin-bottom: 16px;">
                           <tr style="background-color: #f0f0f0;">
                               <th style="text-align: left; padding: 8px; border: 1px solid #ddd;">Earnings</th>
                               <th style="text-align: right; padding: 8px; border: 1px solid #ddd;">Amount</th>
                               <th style="text-align: left; padding: 8px; border: 1px solid #ddd;">Deductions</th>
                               <th style="text-align: right; padding: 8px; border: 1px solid #ddd;">Amount</th>
                           </tr>
                           %s
                           <tr>
                               <td style="padding: 8px; border: 1px solid #ddd;">Gross Pay</td>
                               <td style="text-align: right; padding: 8px; border: 1px solid #ddd;">₱ %.2f</td>
                                <td style="padding: 8px; border: 1px solid #ddd;">Total Deductions</td>
                               <td style="text-align: right; padding: 8px; border: 1px solid #ddd; color: red;">- ₱ %.2f</td>
                           </tr>
                           <tr>
                               <td></td>
                               <td></td>
                               <td></td>
                               <td></td>
                           </tr>
                           <tr style="background-color: #e8f5e9;">
                               <td style="padding: 8px; border: 1px solid #ddd;"><strong>Net Pay</strong></td>
                               <td style="text-align: right; padding: 8px; border: 1px solid #ddd;"><strong>₱ %.2f</strong></td>
                           </tr>
                       </table>
                       <p
                       <p style="text-align: center; color: #888; font-size: 12px;">
                           This is a system-generated payslip. Please do not reply to this email.
                       </p>
                   </div>
               </body>
           </html>
        """.formatted(
                emp.getEmployeeLastName()+ " " + emp.getEmployeeFirstName(),
                emp.getEmployeeEmploymentType(),
                emp.getEmployeeDepartment(),
                payroll.getStartDate(),
                payroll.getEndDate(),
                payroll.getDateProcessed(),
                tableRows.toString(),
                payroll.getGrossPay(),
                payroll.getTotalDeductions(),
                payroll.getNetPay()
        );

        helper.setText(htmlContent, true);
        mailSender.send(message);

    }

}
