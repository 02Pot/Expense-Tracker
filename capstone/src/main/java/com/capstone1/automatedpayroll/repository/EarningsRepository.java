package com.capstone1.automatedpayroll.repository;

import com.capstone1.automatedpayroll.model.AttendanceModel;
import com.capstone1.automatedpayroll.model.EarningsModel;
import com.capstone1.automatedpayroll.model.EmployeeModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface EarningsRepository extends JpaRepository<EarningsModel, Long> {

    List<EarningsModel> findByEmployee(EmployeeModel employee);
    List<EarningsModel> findByEmployeeEId(Long employeeId);
    List<EarningsModel> findByEmployeeEIdAndStationaryTrue(Long employeeId);
    boolean existsByEmployeeEIdAndEarningNameAndDateCreatedBetween(
            Long employeeId, String earningName, LocalDate start, LocalDate end
    );
}
