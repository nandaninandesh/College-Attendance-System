
package com.college;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/addStudent")
public class AddStudentServlet extends HttpServlet {

    protected void doPost(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws ServletException, IOException {

        int roll = Integer.parseInt(
            request.getParameter("roll")
        );

        String name = request.getParameter("name");

        String department =
            request.getParameter("department");

        int semester = Integer.parseInt(
            request.getParameter("semester")
        );

        String sql =
            "INSERT INTO students VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, roll);
            ps.setString(2, name);
            ps.setString(3, department);
            ps.setInt(4, semester);

            int result = ps.executeUpdate();

            response.setContentType("text/html");

            if (result > 0) {
                response.getWriter().println(
                    "Student Added Successfully!"
                );
            }

        } catch (Exception e) {
            response.getWriter().println(
                "Error: " + e.getMessage()
            );
        }
    }
}
