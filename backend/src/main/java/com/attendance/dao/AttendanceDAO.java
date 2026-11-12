
package com.attendance.dao;

import com.attendance.model.Attendance;
import com.attendance.util.DBConnection;
import java.sql.*;
import java.util.*;

public class AttendanceDAO {

    public boolean markAttendance(Attendance a)
            throws SQLException {

        String sql = "INSERT INTO attendance(student_id,subject_id,attendance_date,status) VALUES(?,?,?,?) " +
                     "ON DUPLICATE KEY UPDATE status=VALUES(status)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, a.getStudentId());
            ps.setInt(2, a.getSubjectId());
            ps.setDate(3, a.getAttendanceDate());
            ps.setString(4, a.getStatus());

            return ps.executeUpdate() > 0;
        }
    }

    public List<Attendance> getAllAttendance()
            throws SQLException {

        List<Attendance> list = new ArrayList<>();

        String sql = "SELECT * FROM attendance ORDER BY attendance_date DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Attendance(
                    rs.getInt("id"),
                    rs.getInt("student_id"),
                    rs.getInt("subject_id"),
                    rs.getDate("attendance_date"),
                    rs.getString("status")
                ));
            }
        }

        return list;
    }

    public List<Attendance> getAttendanceByStudent(int studentId)
            throws SQLException {

        List<Attendance> list = new ArrayList<>();

        String sql = "SELECT * FROM attendance WHERE student_id=? ORDER BY attendance_date DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, studentId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Attendance(
                        rs.getInt("id"),
                        rs.getInt("student_id"),
                        rs.getInt("subject_id"),
                        rs.getDate("attendance_date"),
                        rs.getString("status")
                    ));
                }
            }
        }

        return list;
    }

    public boolean deleteAttendance(int id)
            throws SQLException {

        String sql = "DELETE FROM attendance WHERE id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}
