
package com.college;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Date;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/markAttendance")
public class MarkAttendanceServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");

        try {
            int roll = Integer.parseInt(
                request.getParameter("roll"));

            String date = request.getParameter("date");
            String status = request.getParameter("status");

            if (!"Present".equals(status) &&
                !"Absent".equals(status)) {
                throw new IllegalArgumentException(
                    "Invalid attendance status");
            }

            String sql = "INSERT INTO attendance " +
                "(roll_no, attendance_date, status) " +
                "VALUES (?, ?, ?)";

            try (Connection con =
                     DBConnection.getConnection()) {

                if (con == null) {
                    throw new Exception("Database connection failed");
                }

                try (PreparedStatement ps =
                         con.prepareStatement(sql)) {

                    ps.setInt(1, roll);
                    ps.setDate(2, Date.valueOf(date));
                    ps.setString(3, status);

                    ps.executeUpdate();
                }
            }

            response.getWriter().println(
                "<h2>Attendance Marked Successfully!</h2>");

        } catch (Exception e) {
            response.getWriter().println(
                "Error: " + e.getMessage());
        }
    }
}
