
package com.college;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/attendancePercentage")
public class AttendancePercentageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        try {
            int roll = Integer.parseInt(
                request.getParameter("roll"));

            String sql = "SELECT COUNT(*) AS total, " +
                "SUM(CASE WHEN status = 'Present' " +
                "THEN 1 ELSE 0 END) AS present " +
                "FROM attendance WHERE roll_no = ?";

            try (Connection con =
                     DBConnection.getConnection()) {

                if (con == null) {
                    throw new Exception("Database connection failed");
                }

                try (PreparedStatement ps =
                         con.prepareStatement(sql)) {

                    ps.setInt(1, roll);

                    try (ResultSet rs = ps.executeQuery()) {

                        if (rs.next()) {
                            int total = rs.getInt("total");
                            int present = rs.getInt("present");

                            double percentage = total > 0
                                ? (present * 100.0) / total
                                : 0.0;

                            var out = response.getWriter();

                            out.println("<h2>Attendance Report</h2>");
                            out.println("<p>Roll Number: " +
                                roll + "</p>");
                            out.println("<p>Total Classes: " +
                                total + "</p>");
                            out.println("<p>Present: " +
                                present + "</p>");
                            out.println(
                                "<p>Attendance Percentage: " +
                                String.format("%.2f", percentage) +
                                "%</p>");
                        }
                    }
                }
            }

        } catch (Exception e) {
            response.getWriter().println(
                "Error: " + e.getMessage());
        }
    }
}
