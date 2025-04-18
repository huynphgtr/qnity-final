/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.Authentication;

import Model.LoginRequest;
import Model.User;
import Response.ApiResponse;
import Service.AuthenService;
import Util.CorsUtil;
import Util.JsonUtil;
import com.google.gson.Gson;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;
import Util.JWTUtil;



/**
 *
 * @author Asus
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CorsUtil.setCors(request, response);
        try {
            BufferedReader reader = request.getReader();
            Gson gson = new Gson();
            LoginRequest loginRequest = gson.fromJson(reader, LoginRequest.class);

            String email = loginRequest.getEmail();
            String password = loginRequest.getPassword();

            AuthenService authenService = new AuthenService();
            User currentUser = authenService.getUserLogin(email, password);
            if (currentUser != null) {
                var token = JWTUtil.generateToken(email);
                Map<String, String> tokenData = new HashMap<>();
                tokenData.put("jwt_token", token);                
                JsonUtil.writeJson(response, new ApiResponse<>(200, "OK", tokenData));
            } else {
                JsonUtil.writeJson(response, new ApiResponse<>(403, "Login failed", null));
            }
        } catch (IOException e) {
            JsonUtil.writeJson(response, new ApiResponse<>(500, "Internal server error", null));
            e.printStackTrace();
        }
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CorsUtil.setCors(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
