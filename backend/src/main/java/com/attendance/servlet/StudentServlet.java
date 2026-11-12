
package com.attendance.servlet;

import com.attendance.dao.StudentDAO;
import com.attendance.model.Student;
import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/api/students")
public class StudentServlet extends HttpServlet {

    private final StudentDAO dao = new StudentDAO();
    private final Gson gson = new Gson();

    private void send(HttpServletResponse response,
                      Object data) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(gson.toJson(data));
    }

    private Map<String, Object> result(boolean success,
                                       String message) {
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
            String id = req.getParameter("id");

            if (id != null) {
                Student student =
                    dao.getStudentById(Integer.parseInt(id));

                if (student == null) {
                    resp.setStatus(404);
                    send(resp, result(false, "Student not found"));
                } else {
                    send(resp, student);
                }
            } else {
                send(resp, dao.getAllStudents());
            }

        } catch (Exception e) {
            resp.setStatus(500);
            send(resp, result(false, "Unable to retrieve students"));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            Student s = new Student();

            s.setRollNo(req.getParameter("rollNo"));
            s.setName(req.getParameter("name"));
            s.setEmail(req.getParameter("email"));
            s.setPhone(req.getParameter("phone"));
            s.setClassName(req.getParameter("className"));

            boolean saved = dao.addStudent(s);

            send(resp, result(saved,
                saved ? "Student added successfully" : "Unable to add student"));

        } catch (Exception e) {
            resp.setStatus(400);
            send(resp, result(false, "Invalid student data or duplicate roll number"));
        }
    }

    @Override
    protected void doPut(HttpServletRequest req,
                         HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            Student s = new Student();

            s.setId(Integer.parseInt(req.getParameter("id")));
            s.setRollNo(req.getParameter("rollNo"));
            s.setName(req.getParameter("name"));
            s.setEmail(req.getParameter("email"));
            s.setPhone(req.getParameter("phone"));
            s.setClassName(req.getParameter("className"));

            boolean updated = dao.updateStudent(s);

            send(resp, result(updated,
                updated ? "Student updated" : "Student not found"));

        } catch (Exception e) {
            resp.setStatus(400);
            send(resp, result(false, "Invalid student data"));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req,
                            HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));

            boolean deleted = dao.deleteStudent(id);

            send(resp, result(deleted,
                deleted ? "Student deleted" : "Student not found"));

        } catch (Exception e) {
            resp.setStatus(400);
            send(resp, result(false, "Invalid student ID"));
        }
    }
}
