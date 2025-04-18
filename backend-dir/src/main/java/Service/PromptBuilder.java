/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import Model.Accomodation;
import Model.Restaurant;
import Model.Touristplace;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Asus
 */
public class PromptBuilder {

    public static String buildPromptFromData(List<Touristplace> natureList,
            List<Touristplace> cultureList,
            List<Restaurant> resList,
            List<Accomodation> accomList) {

        StringBuilder promptBuilder = new StringBuilder();

        if (natureList != null && !natureList.isEmpty()) {
            promptBuilder.append("Nature places:\n");
            for (Touristplace p : natureList) {
                promptBuilder.append("- ").append(p.getName())
                        .append(" (").append(p.getAddress())
                        .append(", ").append(p.getPrice()).append(" VND)\n");
            }
            promptBuilder.append("\n");
        }

        if (cultureList != null && !cultureList.isEmpty()) {
            promptBuilder.append("Culture places:\n");
            for (Touristplace p : cultureList) {
                promptBuilder.append("- ").append(p.getName())
                        .append(" (").append(p.getAddress())
                        .append(", ").append(p.getPrice()).append(" VND)\n");
            }
            promptBuilder.append("\n");
        }

        if (resList != null && !resList.isEmpty()) {
            promptBuilder.append("Restaurants:\n");
            for (Restaurant r : resList) {
                promptBuilder.append("- ").append(r.getName())
                        .append(" (").append(r.getAddress())
                        .append(", ").append(r.getPrice()).append(" VND)\n");
            }
            promptBuilder.append("\n");
        }

        if (accomList != null && !accomList.isEmpty()) {
            promptBuilder.append("Accommodations:\n");
            for (Accomodation a : accomList) {
                promptBuilder.append("- ").append(a.getName())
                        .append(" (").append(a.getAddress())
                        .append(", ").append(a.getPrice()).append(" VND)\n");
            }
            promptBuilder.append("\n");
        }

        return promptBuilder.toString();
    }

    public static String extractJson(String input) {
        // Regex to match content between ```json and ```
        Pattern pattern = Pattern.compile("```json\\n(.*?)\\n```", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            String json = matcher.group(1);
            //System.out.println("Extracted JSON:\n" + json);
            return json;
        } else {
            System.out.println("No JSON found.");
            return null;

        }
    }

}
