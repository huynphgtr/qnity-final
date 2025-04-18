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
import Util.JWTUtil;
import Util.JsonUtil;
import com.google.gson.Gson;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;

/**
 *
 * @author Asus
 */
@WebServlet(name = "SignupServlet", urlPatterns = {"/signup"})
public class SignupServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CorsUtil.setCors(request, response);
        try {
            BufferedReader reader = request.getReader();
            Gson gson = new Gson();
            User signUpRequest = gson.fromJson(reader, User.class);

            String fullName = signUpRequest.getFullName();
            String email = signUpRequest.getEmail();
            String phone = signUpRequest.getPhone();
            String password = signUpRequest.getPassword();
            String address = signUpRequest.getAddress();

            AuthenService authenService = new AuthenService();
            if (!authenService.checkEmailExist(email)) {
                User user = new User(fullName, email, phone, password, address);
                if (!authenService.signUp(user)) {
                    JsonUtil.writeJson(response, new ApiResponse<>(403, "Sign up failed", null));
                } else {
                    JsonUtil.writeJson(response, new ApiResponse<>(200, "OK", null));
                }
            } else {
                JsonUtil.writeJson(response, new ApiResponse<>(403, "Email is is existed", null));
            }

        } catch (IOException | NumberFormatException e) {
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
