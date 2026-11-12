
package com.attendance.servlet;

import com.attendance.dao.AttendanceDAO;
import com.attendance.model.Attendance;
import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/api/attendance")
public class AttendanceServlet extends HttpServlet {

    private final AttendanceDAO dao = new AttendanceDAO();
    private final Gson gson = new Gson();

    private void send(HttpServletResponse resp, Object data)
            throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(gson.toJson(data));
    }

    private Map<String, Object> result(boolean success, String message) {
        Map<String, Object> map = new HashMap<>();
        map.put("success", success);
        map.put("message", message);
        return map;
    }

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            String studentId = req.getParameter("studentId");

            if (studentId != null && !studentId.isBlank()) {
                send(resp, dao.getAttendanceByStudent(
                    Integer.parseInt(studentId)
                ));
            } else {
                send(resp, dao.getAllAttendance());
            }

        } catch (Exception e) {
            resp.setStatus(500);
            send(resp, result(false, "Unable to retrieve attendance"));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            Attendance a = new Attendance();

            a.setStudentId(
                Integer.parseInt(req.getParameter("studentId"))
            );

            a.setSubjectId(
                Integer.parseInt(req.getParameter("subjectId"))
            );

            a.setAttendanceDate(
                Date.valueOf(req.getParameter("attendanceDate"))
            );

            String status = req.getParameter("status");

            if (!"PRESENT".equals(status)
                    && !"ABSENT".equals(status)
                    && !"LATE".equals(status)) {
                resp.setStatus(400);
                send(resp, result(false, "Invalid attendance status"));
                return;
            }

            a.setStatus(status);

            boolean saved = dao.markAttendance(a);

            send(resp, result(saved,
                saved ? "Attendance saved successfully"
                      : "Unable to save attendance"));

        } catch (Exception e) {
            resp.setStatus(400);
            send(resp, result(false, "Invalid attendance data"));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req,
                            HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));

            boolean deleted = dao.deleteAttendance(id);

            send(resp, result(deleted,
                deleted ? "Attendance deleted" : "Attendance not found"));

        } catch (Exception e) {
            resp.setStatus(400);
            send(resp, result(false, "Invalid attendance ID"));
        }
    }
}
