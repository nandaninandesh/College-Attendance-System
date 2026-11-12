
package com.attendance.dao;

import com.attendance.model.Subject;
import com.attendance.util.DBConnection;
import java.sql.*;
import java.util.*;

public class SubjectDAO {

    public boolean addSubject(Subject s) throws SQLException {

        String sql = "INSERT INTO subjects(subject_name,subject_code,teacher_id) VALUES(?,?,?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, s.getSubjectName());
            ps.setString(2, s.getSubjectCode());

            if (s.getTeacherId() > 0)
                ps.setInt(3, s.getTeacherId());
            else
                ps.setNull(3, Types.INTEGER);

            return ps.executeUpdate() > 0;
        }
    }

    public List<Subject> getAllSubjects() throws SQLException {

        List<Subject> list = new ArrayList<>();

        String sql = "SELECT * FROM subjects ORDER BY id DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Subject(
                    rs.getInt("id"),
                    rs.getString("subject_name"),
                    rs.getString("subject_code"),
                    rs.getInt("teacher_id")
                ));
            }
        }

        return list;
    }

    public boolean updateSubject(Subject s) throws SQLException {

        String sql = "UPDATE subjects SET subject_name=?,subject_code=?,teacher_id=? WHERE id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, s.getSubjectName());
            ps.setString(2, s.getSubjectCode());

            if (s.getTeacherId() > 0)
                ps.setInt(3, s.getTeacherId());
            else
                ps.setNull(3, Types.INTEGER);

            ps.setInt(4, s.getId());

            return ps.executeUpdate() > 0;
        }
    }

    public boolean deleteSubject(int id) throws SQLException {

        String sql = "DELETE FROM subjects WHERE id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}
