
package com.attendance.dao;

import com.attendance.model.Student;
import com.attendance.util.DBConnection;
import java.sql.*;
import java.util.*;

public class StudentDAO {

    public boolean addStudent(Student s) throws SQLException {

        String sql = "INSERT INTO students(roll_no,name,email,phone,class_name) VALUES(?,?,?,?,?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, s.getRollNo());
            ps.setString(2, s.getName());
            ps.setString(3, s.getEmail());
            ps.setString(4, s.getPhone());
            ps.setString(5, s.getClassName());

            return ps.executeUpdate() > 0;
        }
    }

    public List<Student> getAllStudents() throws SQLException {

        List<Student> list = new ArrayList<>();

        String sql = "SELECT * FROM students ORDER BY id DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Student(
                    rs.getInt("id"),
                    rs.getString("roll_no"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("class_name")
                ));
            }
        }

        return list;
    }

    public boolean updateStudent(Student s) throws SQLException {

        String sql = "UPDATE students SET roll_no=?,name=?,email=?,phone=?,class_name=? WHERE id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, s.getRollNo());
            ps.setString(2, s.getName());
            ps.setString(3, s.getEmail());
            ps.setString(4, s.getPhone());
            ps.setString(5, s.getClassName());
            ps.setInt(6, s.getId());

            return ps.executeUpdate() > 0;
        }
    }

    public boolean deleteStudent(int id) throws SQLException {

        String sql = "DELETE FROM students WHERE id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    public Student getStudentById(int id) throws SQLException {

        String sql = "SELECT * FROM students WHERE id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Student(
                        rs.getInt("id"),
                        rs.getString("roll_no"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("class_name")
                    );
                }
            }
        }

        return null;
    }
}
