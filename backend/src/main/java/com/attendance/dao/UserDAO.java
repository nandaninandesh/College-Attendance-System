
package com.attendance.dao;

import com.attendance.model.User;
import com.attendance.util.DBConnection;
import java.sql.*;

public class UserDAO {

    public User login(String username, String password)
            throws SQLException {

        String sql = "SELECT * FROM users WHERE username=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String hashedPassword = rs.getString("password");

                    if (org.mindrot.jbcrypt.BCrypt.checkpw(
                            password, hashedPassword)) {

                        return new User(
                            rs.getInt("id"),
                            rs.getString("username"),
                            hashedPassword,
                            rs.getString("role")
                        );
                    }
                }
            }
        }

        return null;
    }

    public boolean addUser(User user) throws SQLException {

        String sql = "INSERT INTO users(username,password,role) VALUES(?,?,?)";

        String hashedPassword =
            org.mindrot.jbcrypt.BCrypt.hashpw(
                user.getPassword(),
                org.mindrot.jbcrypt.BCrypt.gensalt()
            );

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, hashedPassword);
            ps.setString(3, user.getRole());

            return ps.executeUpdate() > 0;
        }
    }
}
