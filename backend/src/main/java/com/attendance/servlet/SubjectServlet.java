
package com.attendance.servlet;

import com.attendance.dao.SubjectDAO;
import com.attendance.model.Subject;
import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/api/subjects")
public class SubjectServlet extends HttpServlet {

    private final SubjectDAO dao = new SubjectDAO();
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
            send(resp, dao.getAllSubjects());
        } catch (Exception e) {
            resp.setStatus(500);
            send(resp, result(false, "Unable to retrieve subjects"));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            Subject s = new Subject();

            s.setSubjectName(req.getParameter("subjectName"));
            s.setSubjectCode(req.getParameter("subjectCode"));

            String teacherId = req.getParameter("teacherId");
            s.setTeacherId(
                teacherId == null || teacherId.isBlank()
                ? 0 : Integer.parseInt(teacherId)
            );

            boolean saved = dao.addSubject(s);

            send(resp, result(saved,
                saved ? "Subject added successfully" : "Unable to add subject"));

        } catch (Exception e) {
            resp.setStatus(400);
            send(resp, result(false, "Invalid subject data"));
        }
    }

    @Override
    protected void doPut(HttpServletRequest req,
                         HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            Subject s = new Subject();

            s.setId(Integer.parseInt(req.getParameter("id")));
            s.setSubjectName(req.getParameter("subjectName"));
            s.setSubjectCode(req.getParameter("subjectCode"));

            String teacherId = req.getParameter("teacherId");
            s.setTeacherId(
                teacherId == null || teacherId.isBlank()
                ? 0 : Integer.parseInt(teacherId)
            );

            boolean updated = dao.updateSubject(s);

            send(resp, result(updated,
                updated ? "Subject updated" : "Subject not found"));

        } catch (Exception e) {
            resp.setStatus(400);
            send(resp, result(false, "Invalid subject data"));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req,
                            HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));

            boolean deleted = dao.deleteSubject(id);

            send(resp, result(deleted,
                deleted ? "Subject deleted" : "Subject not found"));

        } catch (Exception e) {
            resp.setStatus(400);
            send(resp, result(false, "Invalid subject ID"));
        }
    }
}
