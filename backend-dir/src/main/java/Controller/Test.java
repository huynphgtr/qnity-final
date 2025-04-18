/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import Model.Accomodation;
import Model.Restaurant;
import Model.Touristplace;
import Service.PlannerService;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.PrintStream;
import java.util.List;

/**
 *
 * @author Asus
 */
@WebServlet(name = "Test", urlPatterns = {"/Test"})
public class Test extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        PrintStream out = new PrintStream(System.out, true, "UTF-8");
//        PlannerService service = new PlannerService();
//        List<Restaurant> resList = service.getRestaurant(3);
//        if (resList.isEmpty()) {
//            System.out.println("No list");
//        } else {
//            for (Restaurant r : resList) {
//                out.println(r.getName() + " " + r.getPrice());
//            }
//        }
//        List<Accomodation> accList = service.getAccomodation();
//        if (accList.isEmpty()) {
//            System.out.println("No list");
//        } else {
//            for (Accomodation r : accList) {
//                out.println(r.getName() + " " + r.getPrice());
//            }
//        }
//        List<Touristplace> placeList = service.getTouristplace(1);
//        if (placeList.isEmpty()) {
//            System.out.println("No list");
//        } else {
//            for (Touristplace r : placeList) {
//                out.println(r.getName() + " " + r.getPrice());
//            }
//        }
         PlannerService service = new PlannerService(); 
         String plan = service.getJson(1); 
         System.out.println(plan); 
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
