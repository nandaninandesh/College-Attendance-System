
package com.attendance.servlet;

import com.attendance.dao.ReportDAO;
import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/api/reports")
public class ReportServlet extends HttpServlet {

    private final ReportDAO dao = new ReportDAO();
    private final Gson gson = new Gson();

    private void send(HttpServletResponse resp, Object data)
            throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(gson.toJson(data));
    }

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            Map<String, Object> report = new HashMap<>();

            report.put("totalStudents", dao.getTotalStudents());
            report.put("totalTeachers", dao.getTotalTeachers());
            report.put("totalSubjects", dao.getTotalSubjects());
            report.put("presentCount", dao.getPresentCount());
            report.put("absentCount", dao.getAbsentCount());
            report.put("todayAttendance",
                       dao.getTodayAttendanceCount());

            report.put("studentReport", dao.getStudentReport());

            send(resp, report);

        } catch (Exception e) {
            resp.setStatus(500);

            Map<String, String> error = new HashMap<>();
            error.put("success", "false");
            error.put("message", "Unable to generate report");

            send(resp, error);
        }
    }
}
