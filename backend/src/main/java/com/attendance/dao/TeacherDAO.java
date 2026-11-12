
package com.attendance.dao;

import com.attendance.model.Teacher;
import com.attendance.util.DBConnection;
import java.sql.*;
import java.util.*;

public class TeacherDAO {

    public boolean addTeacher(Teacher t) throws SQLException {

        String sql = "INSERT INTO teachers(name,email,phone,department) VALUES(?,?,?,?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, t.getName());
            ps.setString(2, t.getEmail());
            ps.setString(3, t.getPhone());
            ps.setString(4, t.getDepartment());

            return ps.executeUpdate() > 0;
        }
    }

    public List<Teacher> getAllTeachers() throws SQLException {

        List<Teacher> list = new ArrayList<>();

        String sql = "SELECT * FROM teachers ORDER BY id DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Teacher(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("department")
                ));
            }
        }

        return list;
    }

    public boolean updateTeacher(Teacher t) throws SQLException {

        String sql = "UPDATE teachers SET name=?,email=?,phone=?,department=? WHERE id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, t.getName());
            ps.setString(2, t.getEmail());
            ps.setString(3, t.getPhone());
            ps.setString(4, t.getDepartment());
            ps.setInt(5, t.getId());

            return ps.executeUpdate() > 0;
        }
    }

    public boolean deleteTeacher(int id) throws SQLException {

        String sql = "DELETE FROM teachers WHERE id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}
