package com.capstone1.automatedpayroll.repository;

import com.capstone1.automatedpayroll.model.EmployeeModel;
import com.capstone1.automatedpayroll.model.PayrollModel;
import com.capstone1.automatedpayroll.model.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<EmployeeModel,Long> {

    @Override
    Optional<EmployeeModel> findById(Long UserId);
    Page<EmployeeModel> findAll(Pageable pageable);
    UserModel findByEmployeeEmail(String userEmail);
    boolean existsByEmployeeEmail(String userEmail);

}
