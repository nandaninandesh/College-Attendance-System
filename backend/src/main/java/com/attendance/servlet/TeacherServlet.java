
package com.attendance.servlet;

import com.attendance.dao.TeacherDAO;
import com.attendance.model.Teacher;
import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/api/teachers")
public class TeacherServlet extends HttpServlet {

    private final TeacherDAO dao = new TeacherDAO();
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
            send(resp, dao.getAllTeachers());
        } catch (Exception e) {
            resp.setStatus(500);
            send(resp, result(false, "Unable to retrieve teachers"));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            Teacher t = new Teacher();

            t.setName(req.getParameter("name"));
            t.setEmail(req.getParameter("email"));
            t.setPhone(req.getParameter("phone"));
            t.setDepartment(req.getParameter("department"));

            boolean saved = dao.addTeacher(t);

            send(resp, result(saved,
                saved ? "Teacher added successfully" : "Unable to add teacher"));

        } catch (Exception e) {
            resp.setStatus(400);
            send(resp, result(false, "Invalid teacher data"));
        }
    }

    @Override
    protected void doPut(HttpServletRequest req,
                         HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            Teacher t = new Teacher();

            t.setId(Integer.parseInt(req.getParameter("id")));
            t.setName(req.getParameter("name"));
            t.setEmail(req.getParameter("email"));
            t.setPhone(req.getParameter("phone"));
            t.setDepartment(req.getParameter("department"));

            boolean updated = dao.updateTeacher(t);

            send(resp, result(updated,
                updated ? "Teacher updated" : "Teacher not found"));

        } catch (Exception e) {
            resp.setStatus(400);
            send(resp, result(false, "Invalid teacher data"));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req,
                            HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));

            boolean deleted = dao.deleteTeacher(id);

            send(resp, result(deleted,
                deleted ? "Teacher deleted" : "Teacher not found"));

        } catch (Exception e) {
            resp.setStatus(400);
            send(resp, result(false, "Invalid teacher ID"));
        }
    }
}
