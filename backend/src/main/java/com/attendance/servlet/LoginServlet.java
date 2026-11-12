
package com.attendance.servlet;

import com.attendance.dao.UserDAO;
import com.attendance.model.User;
import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/api/login")
public class LoginServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Map<String, Object> result = new HashMap<>();

        try {
            if (username == null || password == null ||
                username.isBlank() || password.isBlank()) {

                response.setStatus(400);
                result.put("success", false);
                result.put("message", "Username and password required");

            } else {
                User user = userDAO.login(username, password);

                if (user != null) {

                    HttpSession session = request.getSession(true);
                    session.setAttribute("userId", user.getId());
                    session.setAttribute("username", user.getUsername());
                    session.setAttribute("role", user.getRole());

                    result.put("success", true);
                    result.put("message", "Login successful");
                    result.put("role", user.getRole());
                    result.put("username", user.getUsername());

                } else {
                    response.setStatus(401);
                    result.put("success", false);
                    result.put("message", "Invalid username or password");
                }
            }

        } catch (Exception e) {
            response.setStatus(500);
            result.put("success", false);
            result.put("message", "Login failed");
        }

        response.getWriter().write(gson.toJson(result));
    }
}
