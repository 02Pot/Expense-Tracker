package com.capstone1.automatedpayroll.repository;

import com.capstone1.automatedpayroll.model.AttendanceModel;
import com.capstone1.automatedpayroll.model.DeductionsModel;
import com.capstone1.automatedpayroll.model.EmployeeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<AttendanceModel,Long> {

    boolean existsByEmployee_EIdAndAttendanceDate(Long employeeId, LocalDate attendanceDate);
    List<AttendanceModel> findByEmployee_EIdAndAttendanceDateBetween(Long employeeId,LocalDate startDate,LocalDate endDate);
    List<AttendanceModel> findByEmployee(EmployeeModel employee);
    List<AttendanceModel> findByEmployee_EId(Long employeeId);

}
