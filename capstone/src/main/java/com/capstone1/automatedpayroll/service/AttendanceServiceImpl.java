package com.capstone1.automatedpayroll.service;

import java.io.InputStream;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.capstone1.automatedpayroll.dto.AttendanceDTO;
import com.capstone1.automatedpayroll.mapper.AttendanceMapper;
import com.capstone1.automatedpayroll.model.AttendanceModel;
import com.capstone1.automatedpayroll.model.EmployeeModel;
import com.capstone1.automatedpayroll.repository.AttendanceRepository;
import com.capstone1.automatedpayroll.repository.EmployeeRepository;

@Service
public class AttendanceServiceImpl {

    @Autowired
    private AttendanceRepository attendanceRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    public void importAttendance(MultipartFile file) {
        try (InputStream is = file.getInputStream();
            Workbook workbook = WorkbookFactory.create(is)) {

            Sheet sheet = workbook.getSheetAt(0);

            for (int rowIndex = 1; rowIndex < sheet.getPhysicalNumberOfRows(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row == null) break;

                Cell idCell = row.getCell(0);
                if (idCell == null || idCell.getCellType() == CellType.BLANK) break;

                Long employeeId = (long) row.getCell(0).getNumericCellValue();
                LocalDate attendanceDate = getDate(row.getCell(1));
                LocalTime timeIn = getTime(row.getCell(2));
                LocalTime timeOut = getTime(row.getCell(3));

                EmployeeModel employee = employeeRepository.findById(employeeId)
                        .orElseThrow(() -> new RuntimeException("Employee not found: " + employeeId));

                if (attendanceDate == null || timeIn == null || timeOut == null) {
                    System.out.println("Skipping row " + rowIndex + " because of missing entry");
                    continue;
                }

                boolean exists = attendanceRepository.existsByEmployee_EIdAndAttendanceDate(employeeId,attendanceDate);

                if (exists){
                    System.out.println("Duplicate Entry skipping");
                    continue;
                }

                double hoursWorked = calculateHours(timeIn,timeOut);

                AttendanceModel attendance = new AttendanceModel();
                attendance.setEmployee(employee);
                attendance.setAttendanceDate(attendanceDate);
                attendance.setTimeIn(timeIn);
                attendance.setTimeOut(timeOut);
                attendance.setHoursWorked(hoursWorked);

                attendanceRepository.save(attendance);

            }
        } catch (Exception e) {
            throw new RuntimeException("Error processing Excel file: " + e.getMessage());
        }
    }

    private double calculateHours(LocalTime timeIn, LocalTime timeOut) {
        Duration duration = Duration.between(timeIn, timeOut);
        double hours = duration.toMinutes() / 60.0;
        return Math.round(hours * 100.0) / 100.0;
    }

    private LocalDate getDate(Cell cell) {
        if (cell == null || cell.getCellType() == CellType.BLANK) return null;
        return cell.getLocalDateTimeCellValue().toLocalDate();
    }

    private LocalTime getTime(Cell cell) {
        if (cell == null || cell.getCellType() == CellType.BLANK) return null;
        return cell.getLocalDateTimeCellValue().toLocalTime();
    }

    public List<AttendanceDTO> findAllAttendance(){
        return attendanceRepository.findAll()
                .stream()
                .map(AttendanceMapper::mapToAttendanceDTO)
                .collect(Collectors.toList());
    }

    public List<AttendanceDTO> getAttendanceByEmployeeId(Long employeeId) {
        EmployeeModel employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee Not Found"));
        return attendanceRepository.findByEmployee(employee)
                .stream()
                .map(attendace -> {
                    AttendanceDTO dto = new AttendanceDTO();
                    dto.setAttendanceId(attendace.getAttendanceId());
                    dto.setAttendanceDate(attendace.getAttendanceDate());
                    dto.setTimeIn(attendace.getTimeIn());
                    dto.setTimeOut(attendace.getTimeOut());
                    dto.setHoursWorked(attendace.getHoursWorked());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public double getTotalHoursWorked() {
        return attendanceRepository.findAll()
                .stream()
                .mapToDouble(AttendanceModel::getHoursWorked)
                .sum();
    }

    public double getTotalHoursWorkedByEmployee(Long employeeId) {
        return attendanceRepository.findByEmployee_EId(employeeId)
                .stream()
                .mapToDouble(AttendanceModel::getHoursWorked)
                .sum();
    }

    // Total hours worked for a specific employee within a date range (per cutoff)
    public double getTotalHoursWorkedByEmployee(Long employeeId, LocalDate startDate, LocalDate endDate) {
        return attendanceRepository.findByEmployee_EIdAndAttendanceDateBetween(employeeId, startDate, endDate)
                .stream()
                .mapToDouble(AttendanceModel::getHoursWorked)
                .sum();
    }

}
