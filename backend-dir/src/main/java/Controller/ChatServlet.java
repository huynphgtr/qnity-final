/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import Common.Constant;
import Response.ApiResponse;
import Service.PlannerService;
import Service.PromptBuilder;
import Util.CorsUtil;
import Util.JsonUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
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

/**
 *
 * @author Asus
 */
@WebServlet(name = "ChatServlet", urlPatterns = {"/chat-servlet"})
public class ChatServlet extends HttpServlet {

    private static final String API_KEY = "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"; // Thay bằng key thật
    private static final String API_URL = "https://api.groq.com/openai/v1/chat/completions";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
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

        // 1. Nhận input từ client
        BufferedReader reader = request.getReader();
        Gson gson = new Gson();
        JsonObject req = gson.fromJson(reader, JsonObject.class);
        String msg = req.get("message").getAsString();

        // 2. Lấy data lịch trình để gợi ý (tuỳ chọn)
        PlannerService service = new PlannerService();
        String data = service.getJson(Constant.ID_USER);
        
        String systemPrompt = "You are a friendly and knowledgeable travel assistant who helps users understand their travel itinerary and answer questions about it. The itinerary is in JSON format. Use the data to provide helpful, concise, and accurate responses in a conversational tone. Answer in English, no matter what language the input is.";
        String userPrompt1 = "Here is the travel itinerary data: " + escape(data);
        String userPrompt2 = msg; 

        String jsonRequest = "{"
                + "\"model\": \"llama-3.3-70b-versatile\","
                + "\"messages\": ["
                + "{\"role\": \"system\", \"content\": \"" + escape(systemPrompt) + "\"},"
                + "{\"role\": \"user\", \"content\": \"" + escape(userPrompt1) + "\"},"
                + "{\"role\": \"user\", \"content\": \"" + escape(userPrompt2) + "\"}"
                + "],"
                + "\"temperature\": 0.7"
                + "}";

        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) new URL(API_URL).openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + API_KEY);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");
            conn.setDoOutput(true);

            // 4. Gửi request
            try (OutputStream os = conn.getOutputStream()) {
                os.write(jsonRequest.getBytes("utf-8"));
            }

            // 5. Đọc phản hồi từ AI
            StringBuilder result = new StringBuilder();
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    result.append(line.trim());
                }
            }

            // 6. Parse JSON từ Groq API
            String jsonResponse = result.toString();
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(jsonResponse);
            String aiReply = rootNode.path("choices").get(0).path("message").path("content").asText();

            // 7. Trả JSON cho client
            JsonObject json = new JsonObject();
            json.addProperty("reply", aiReply);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(gson.toJson(json));

        } catch (IOException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JsonObject error = new JsonObject();
            error.addProperty("reply", "Xin lỗi, hệ thống đang gặp lỗi. Vui lòng thử lại sau.");
            response.getWriter().write(error.toString());
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

    private static String escape(String str) {
        return str.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n");
    }

}
