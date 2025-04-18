package Service;

import Connection.ConnectionDB;
import Model.Accomodation;
import Model.Restaurant;
import Model.Touristplace;
import Model.UserRequest;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlannerService {

    public boolean addUserRequest(int id, UserRequest userRequest, BigDecimal budget) {
        try {
            Connection connection = ConnectionDB.getConnection();
            String sql = "insert into userrequest (user_id, start_date,end_date,budget,num_people) values (?,?,?,?,?)";
            PreparedStatement prstm = connection.prepareStatement(sql);
            prstm.setInt(1, id);
            prstm.setString(2, userRequest.getStartdate());
            prstm.setString(3, userRequest.getEnddate());
            prstm.setBigDecimal(4, budget);
            prstm.setInt(5, userRequest.getPeople());
            prstm.execute();
            return true;
        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
            Logger.getLogger(PlannerService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public int getIdUserRequest(int id, UserRequest userRequest, BigDecimal budget) {
        int request_id = 0;
        try {
            Connection connection = ConnectionDB.getConnection();
            String sql = "select id from userrequest where user_id = ?\n"
                    + "and start_date = ? \n"
                    + "and end_date = ? \n"
                    + "and budget = ? \n"
                    + "and num_people = ? \n"
                    + "and status = 'Pending'\n"
                    + "order by id desc \n"
                    + "limit 1; "; // đổi câu query
            PreparedStatement prstm = connection.prepareStatement(sql);
            prstm.setInt(1, id);
            prstm.setString(2, userRequest.getStartdate());
            prstm.setString(3, userRequest.getEnddate());
            prstm.setBigDecimal(4, budget);
            prstm.setInt(5, userRequest.getPeople());
            ResultSet rs = prstm.executeQuery();
            if (rs.next()) {
                request_id = rs.getInt(1);
            }
        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
            Logger.getLogger(PlannerService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return request_id;
    }

    public List<Restaurant> getRestaurant(int id_interest) { // 3(cheap) 4 (expensive)
        Restaurant restaurant;
        List<Restaurant> list = new ArrayList<>();
        try {
            Connection connection = ConnectionDB.getConnection();
            String sql = "select r.id,restaurant_name,address,(min_price + max_price)/ 2* 0.9 as price\n"
                    + "from restaurant r\n"
                    + "join interest i\n"
                    + "on r.interest_id = i.id\n"
                    + "where r.interest_id = ? ;"; // đổi câu query
            PreparedStatement prstm = connection.prepareStatement(sql);
            prstm.setInt(1, id_interest);
            ResultSet rs = prstm.executeQuery();
            while (rs.next()) {
                restaurant = new Restaurant();
                restaurant.setId(rs.getInt(1));
                restaurant.setName(rs.getString(2));
                restaurant.setAddress(rs.getString(3));
                restaurant.setPrice(rs.getBigDecimal(4));
                list.add(restaurant);
            }
            return list;
        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
            Logger.getLogger(PlannerService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public List<Accomodation> getAccomodation() {
        Accomodation accomodation;
        List<Accomodation> list = new ArrayList<>();
        try {
            Connection connection = ConnectionDB.getConnection();
            String sql = "select id, accommodation_name, address, (min_price + max_price)/ 2* 0.9 as price\n"
                    + "from accommodation ;";
            PreparedStatement prstm = connection.prepareStatement(sql);
            ResultSet rs = prstm.executeQuery();
            while (rs.next()) {
                accomodation = new Accomodation();
                accomodation.setId(rs.getInt(1));
                accomodation.setName(rs.getString(2));
                accomodation.setAddress(rs.getString(3));
                accomodation.setPrice(rs.getBigDecimal(4));
                list.add(accomodation);
            }
            return list;
        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
            Logger.getLogger(PlannerService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public List<Touristplace> getTouristplace(int id_interest) { // 1(nature) 2(culture)
        Touristplace touristplace;
        List<Touristplace> list = new ArrayList<>();
        try {
            Connection connection = ConnectionDB.getConnection();
            String sql = "select t.id, place_name, address, ticket_price as price\n"
                    + "from touristplace t\n"
                    + "join interest i\n"
                    + "on t.interest_id = i.id\n"
                    + "where t.interest_id = ?;"; // đổi câu query
            PreparedStatement prstm = connection.prepareStatement(sql);
            prstm.setInt(1, id_interest);
            ResultSet rs = prstm.executeQuery();
            while (rs.next()) {
                touristplace = new Touristplace();
                touristplace.setId(rs.getInt(1));
                touristplace.setName(rs.getString(2));
                touristplace.setAddress(rs.getString(3));
                touristplace.setPrice(rs.getBigDecimal(4));
                list.add(touristplace);
            }
            return list;
        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
            Logger.getLogger(PlannerService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    public boolean insertJson( int id, String plan ){
        try {
            Connection connection = ConnectionDB.getConnection();
            String sql = "insert into plan (user_id, plan) values(?, ?)";
            PreparedStatement prstm = connection.prepareStatement(sql);
            prstm.setInt(1, id);
            prstm.setString(2, plan); 
            prstm.execute();   
            prstm.close();
            return true; 
        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
            Logger.getLogger(PlannerService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
        
    }
    
    public String getJson(int id){
        String plan = null; 
        try {
            Connection connection = ConnectionDB.getConnection();
            String sql = "select plan from plan where user_id = ?"; // đổi câu query
            PreparedStatement prstm = connection.prepareStatement(sql);
            prstm.setInt(1, id);
            ResultSet rs = prstm.executeQuery();
            while (rs.next()) {
                plan = rs.getString(1);
            }
            prstm.close();
        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
            Logger.getLogger(PlannerService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return plan;  
    }
    
    public int getId(int id_request){
        int id = 0; 
        try {
            Connection connection = ConnectionDB.getConnection();
            String sql = "select user_id from userrequest where id = ? "; // đổi câu query
            PreparedStatement prstm = connection.prepareStatement(sql);
            prstm.setInt(1, id_request);
            ResultSet rs = prstm.executeQuery();
            while (rs.next()) {
                id = rs.getInt(1); 
            }
            prstm.close();
        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
            Logger.getLogger(PlannerService.class.getName()).log(Level.SEVERE, null, ex);
        }       
        return id; 
    }

}
