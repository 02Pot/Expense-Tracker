package com.capstone1.automatedpayroll.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.capstone1.automatedpayroll.model.EmployeeModel;
import com.capstone1.automatedpayroll.model.PayrollModel;

public interface PayrollRepository extends JpaRepository<PayrollModel,Long> {

    List<PayrollModel> findByEmployee(EmployeeModel employee);
    Page<PayrollModel> findAll(Pageable pageable);
    List<PayrollModel> findByStartDateAndEndDate(LocalDate startDate, LocalDate endDate);
    Optional<PayrollModel> findTopByEmployee_EIdOrderByDateProcessedDesc(Long aLong);
}
