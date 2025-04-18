/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import Common.Constant;
import Model.Accomodation;
import Model.Interests;
import Model.Restaurant;
import Model.Touristplace;
import Model.UserRequest;
import Response.ApiResponse;
import Service.PlannerService;
import Service.PromptBuilder;
import Util.CorsUtil;
import Util.JsonUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Asus
 */
@WebServlet(name = "AIGenerateServlet", urlPatterns = {"/generate-itinerary"})
public class AIGenerateServlet extends HttpServlet {

    private static final String API_KEY = "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"; // Thay bằng key thật
    private static final String API_URL = "https://api.groq.com/openai/v1/chat/completions";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CorsUtil.setCors(request, response);

        //lấy request        
        BufferedReader reader = request.getReader();
        Gson gson = new Gson();
        UserRequest userRequest = gson.fromJson(reader, UserRequest.class);
        if (userRequest == null) {
            throw new IllegalArgumentException("Invalid input: can't parse UserRequest");
        }
//        System.out.println("ID = " + Constant.ID_REQUEST); 
        String startdate = userRequest.getStartdate();
        String enddate = userRequest.getEnddate();
        long budget = userRequest.getBudget();
        int people = userRequest.getPeople();
        List<String> interests = userRequest.getInterests();

        //lấy data
        List<Touristplace> natureList = new ArrayList<>();
        List<Touristplace> cultureList = new ArrayList<>();
        List<Restaurant> resList = new ArrayList<>();

        PlannerService service = new PlannerService();
        List<Integer> listInterest = Interests.getInterestMap(interests);
        if (listInterest.contains(1)) {
            natureList = service.getTouristplace(1);
        }
        if (listInterest.contains(2)) {
            cultureList = service.getTouristplace(2);
        }
        if (listInterest.contains(3)) {
            resList = service.getRestaurant(3);
        }else{
            resList = service.getRestaurant(4);
        }
       
        List<Accomodation> accomList = service.getAccomodation();

        //Tạo prompt
        String locationInfo = PromptBuilder.buildPromptFromData(natureList, cultureList, resList, accomList);

        String prompt = String.format(
                "You are a travel planner.\n"
                + "Create a personalized travel itinerary for Quy Nhon based on these details:\n"
                + "- Travel Dates: %1$s to %2$s\n" // 1$ là tham số đầu tiên, 2$ là tham số thứ hai, v.v.
                + "- Number of People: %3$d\n"
                + "- Budget: %4$d VND\n"
                + "- Interests: %5$s\n"
                + "Available locations:%6$s\n"
                + "**Note: Prices shown for accommodations are estimated for one day only.**\n"
                + "Please:\n"
                + "- Predict the weather in Quy Nhon during the trip.\n"
                + "- If rainy, prioritize indoor activities.\n"
                + "- If sunny, suggest beach or outdoor activities.\n"
                + "Return the itinerary in JSON format, grouped by day. Each day object should include the following fields:\n"
                + "- time (HH:mm)\n"
                + "- location\n"
                + "- activity description\n"
                + "- estimated cost (VND)\n"
                + "Now, generate the itinerary in this JSON format based on the input details.",
                startdate, enddate, people, budget, String.join(", ", interests), locationInfo
        );

        String escapedPrompt = prompt.replace("\"", "\\\""); // escape dấu "
        escapedPrompt = escapedPrompt.replace("\n", "\\n");  // escape xuống dòng

        String jsonRequest = "{"
                + "\"model\": \"llama-3.3-70b-versatile\","
                + "\"messages\": ["
                + "  {"
                + "    \"role\": \"user\","
                + "    \"content\": \"" + escapedPrompt + "\""
                + "  }"
                + "],"
                + "\"temperature\": 0.7"
                + "}";
        //System.out.println("Request: " + jsonRequest);

        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) new URL(API_URL).openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + API_KEY);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0"); // Thêm dòng này
            conn.setDoOutput(true);

            // 2. Gửi JSON request body
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonRequest.getBytes("utf-8");
                os.write(input, 0, input.length);
                if (input == null) {
                    System.out.println("Error");
                }
            }

            // 3. Đọc phản hồi từ API
            StringBuilder result = new StringBuilder();
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    //System.out.println("Line read: " + line); // debug
                    result.append(line.trim());
                }
            } catch (IOException e) {
                e.printStackTrace(); // hoặc log lỗi rõ hơn
            }

            // 4. In mã phản hồi để debug (có thể xóa sau khi ổn định)
            //System.out.println("Response Code: " + conn.getResponseCode());
            // Parse JSON lấy content
            String jsonResponse = result.toString();
            // Parse JSON lấy content bằng Jackson
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(jsonResponse);

            // Lấy nội dung content ở choices[0].message.content
            JsonNode contentNode = rootNode.path("choices").get(0).path("message").path("content");
            String contentJson = contentNode.asText(); // Trả về chuỗi bình thường

            System.out.println("== Groq JSON Response ==");
            String jsonStruct = PromptBuilder.extractJson(contentJson);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");            
            int id = service.getId(Constant.ID_REQUEST);
            Constant.ID_USER = id; 
            if(service.insertJson(Constant.ID_USER, jsonStruct)){
                System.out.println("Insert ok");
            } else{
                System.out.println("cannot insert");
            }

            response.getWriter().write(jsonStruct);

        } catch (IOException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json");
            JsonUtil.writeJson(response, new ApiResponse<>(500, "Internal server error", null));
        }
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CorsUtil.setCors(request, response);
    }

    @Override
    public String getServletInfo() {
        return "AI Itinerary Generator Servlet";
    }
}
