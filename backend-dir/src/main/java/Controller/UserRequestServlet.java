/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import Common.Constant;
import Model.UserRequest;
import Response.ApiResponse;
import Service.AuthenService;
import Service.PlannerService;
import Util.CorsUtil;
import Util.JWTUtil;
import Util.JsonUtil;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.math.BigDecimal;

/**
 *
 * @author Asus
 */
@WebServlet(name = "TripplannerServlet", urlPatterns = {"/user-request"})
public class UserRequestServlet extends HttpServlet {

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
            UserRequest userRequest = gson.fromJson(reader, UserRequest.class);

            // get email và find id khi giải token
            AuthenService authService = new AuthenService();
            String authHeader = request.getHeader("Authorization");
            int id = 0; 
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.replace("Bearer ", "");
                String email = JWTUtil.getEmailFromToken(token); //????
                id = authService.getUserIdByEmail(email);
                if (id == 0) {                
                    JsonUtil.writeJson(response, new ApiResponse<>(403, "Cannot find id", null));
                }
            } else {
                JsonUtil.writeJson(response, new ApiResponse<>(403, "Cannot find user token", null));
            }
            
            //insert into db            
            BigDecimal budget = BigDecimal.valueOf(userRequest.getBudget()).setScale(2);
            PlannerService service = new PlannerService();
            if (!service.addUserRequest(id,userRequest,budget)) {
                JsonUtil.writeJson(response, new ApiResponse<>(403, "Cannot insert", null));
            } else {
                //lấy id_request rồi gửi về FE
                Constant.ID_REQUEST = service.getIdUserRequest(id, userRequest, budget); 
                JsonUtil.writeJson(response, new ApiResponse<>(200, "OK", Constant.ID_REQUEST));
            }
        } catch (JsonIOException | JsonSyntaxException | IOException e) {
            JsonUtil.writeJson(response, new ApiResponse<>(500, "Internal server error", null));
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
