package com.capstone1.automatedpayroll.repository;

import com.capstone1.automatedpayroll.model.DeductionsModel;
import com.capstone1.automatedpayroll.model.EarningsModel;
import com.capstone1.automatedpayroll.model.EmployeeModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeductionsRepository extends JpaRepository<DeductionsModel, Long> {

    List<DeductionsModel> findByEmployee(EmployeeModel employee);
    List<DeductionsModel> findByEmployeeEId(Long employeeId);
    List<DeductionsModel> findByEmployeeEIdAndStationaryTrue(Long employeeId);

}
