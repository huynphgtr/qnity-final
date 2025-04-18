/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.math.BigDecimal;


/**
 *
 * @author Asus
 */
public class Accomodation extends Location{ 

    public Accomodation() {
    }
    
    public Accomodation(int id, String name, String address, BigDecimal price, String interest_name){
        this.id = id; 
        this.name = name; 
        this.address = address; 
        this.price = price; 
        this.interest_name = interest_name; 
    }
}
