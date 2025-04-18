package Service;

import Connection.ConnectionDB;
import Model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AuthenService {

    public boolean signUp(User user) {
        try {
            Connection connection = ConnectionDB.getConnection();
            String sql = "insert into users (fullname,email,phone,password,address) values (?,?,?,?,?)";
            PreparedStatement prstm = connection.prepareStatement(sql);
            prstm.setString(1, user.getFullName());
            prstm.setString(2, user.getEmail());
            prstm.setString(3, user.getPhone());
            prstm.setString(4, user.getPassword());
            prstm.setString(5, user.getAddress());
            prstm.execute();
            prstm.close();
            return true;
        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
            Logger.getLogger(AuthenService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public User getUserLogin(String email, String password) {
        User user = null;
        try {
            Connection connection = ConnectionDB.getConnection();
            String sql = "Select * from users where email = ? and password = ?";
            PreparedStatement prstm = connection.prepareStatement(sql);
            prstm.setString(1, email);
            prstm.setString(2, password);
            ResultSet rs = prstm.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt(1));
                user.setFullName(rs.getString(2));
                user.setEmail(rs.getString(3));
            }
            prstm.close();
        } catch (SQLException ex) {
            Logger.getLogger(AuthenService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    }

    public boolean checkEmailExist(String email) {
        try {
            Connection connection = ConnectionDB.getConnection();
            String sql = "Select * from users where email = ?";
            PreparedStatement prstm = connection.prepareStatement(sql);
            prstm.setString(1, email);
            ResultSet rs = prstm.executeQuery();
            if (rs.next()) {
                return true;
            }
            prstm.close();
        } catch (SQLException ex) {
            Logger.getLogger(AuthenService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public int getUserIdByEmail(String email) {
        int id = 0; 
        try {
            Connection connection = ConnectionDB.getConnection();
            String sql = "Select id from users where email = ?";
            PreparedStatement prstm = connection.prepareStatement(sql);
            prstm.setString(1, email);
            ResultSet rs = prstm.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1); 
            }
            prstm.close();
        } catch (SQLException ex) {
            Logger.getLogger(AuthenService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

}
