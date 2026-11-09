
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

@WebServlet("/viewAttendance")
public class ViewAttendanceServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        try {
            int roll = Integer.parseInt(
                request.getParameter("roll"));

            String sql = "SELECT attendance_date, status " +
                "FROM attendance WHERE roll_no = ? " +
                "ORDER BY attendance_date DESC";

            try (Connection con =
                     DBConnection.getConnection()) {

                if (con == null) {
                    throw new Exception("Database connection failed");
                }

                try (PreparedStatement ps =
                         con.prepareStatement(sql)) {

                    ps.setInt(1, roll);

                    try (ResultSet rs = ps.executeQuery()) {

                        var out = response.getWriter();

                        out.println("<h2>Attendance Records</h2>");
                        out.println("<table border='1'>");
                        out.println(
                            "<tr><th>Date</th><th>Status</th></tr>");

                        while (rs.next()) {
                            out.println("<tr><td>" +
                                rs.getDate("attendance_date") +
                                "</td><td>" +
                                rs.getString("status") +
                                "</td></tr>");
                        }

                        out.println("</table>");
                    }
                }
            }

        } catch (Exception e) {
            response.getWriter().println(
                "Error: " + e.getMessage());
        }
    }
}
