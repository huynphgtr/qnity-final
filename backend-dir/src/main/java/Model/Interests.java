/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Asus
 */
public class Interests {
    public static List<Integer> getInterestMap(List<String> interests) {
        Map<String, Integer> interestMap = new HashMap<>();
        interestMap.put("Nature", 1);
        interestMap.put("Culture", 2);
        interestMap.put("Food", 3);
        
        List<Integer> interestCodes = new ArrayList<>();
        for (String interest : interests) {
            if (interestMap.containsKey(interest)) {
                interestCodes.add(interestMap.get(interest));
            }
        }
        return interestCodes; 
    }
}
