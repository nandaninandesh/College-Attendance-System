
package com.attendance.dao;

import com.attendance.util.DBConnection;
import java.sql.*;
import java.util.*;

public class ReportDAO {

    public int getTotalStudents() throws SQLException {
        return getCount("SELECT COUNT(*) FROM students");
    }

    public int getTotalTeachers() throws SQLException {
        return getCount("SELECT COUNT(*) FROM teachers");
    }

    public int getTotalSubjects() throws SQLException {
        return getCount("SELECT COUNT(*) FROM subjects");
    }

    public int getPresentCount() throws SQLException {
        return getCount(
            "SELECT COUNT(*) FROM attendance WHERE status='PRESENT'"
        );
    }

    public int getAbsentCount() throws SQLException {
        return getCount(
            "SELECT COUNT(*) FROM attendance WHERE status='ABSENT'"
        );
    }

    public int getTodayAttendanceCount() throws SQLException {
        return getCount(
            "SELECT COUNT(*) FROM attendance WHERE attendance_date=CURRENT_DATE()"
        );
    }

    private int getCount(String sql) throws SQLException {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        }

        return 0;
    }

    public List<Map<String, Object>> getStudentReport()
            throws SQLException {

        List<Map<String, Object>> report = new ArrayList<>();

        String sql = "SELECT s.id, s.roll_no, s.name, " +
                     "COUNT(a.id) AS total_classes, " +
                     "SUM(CASE WHEN a.status='PRESENT' THEN 1 ELSE 0 END) AS present_count, " +
                     "SUM(CASE WHEN a.status='ABSENT' THEN 1 ELSE 0 END) AS absent_count " +
                     "FROM students s LEFT JOIN attendance a " +
                     "ON s.id=a.student_id GROUP BY s.id, s.roll_no, s.name";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Map<String, Object> row = new LinkedHashMap<>();

                row.put("id", rs.getInt("id"));
                row.put("rollNo", rs.getString("roll_no"));
                row.put("name", rs.getString("name"));
                row.put("totalClasses", rs.getInt("total_classes"));
                row.put("present", rs.getInt("present_count"));
                row.put("absent", rs.getInt("absent_count"));

                int total = rs.getInt("total_classes");
                int present = rs.getInt("present_count");

                double percentage = total == 0
                    ? 0
                    : (present * 100.0 / total);

                row.put("percentage", percentage);

                report.add(row);
            }
        }

        return report;
    }
}
