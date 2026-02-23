package com.sankar.sbaiollamalang;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UiController {

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("message", "Welcome to the Spring Boot Application!");
        return "index";
    }

    @GetMapping("/ollama-status")
    public String getOllamaStatus(Model model) {
        try {
            URL url = new URL("http://localhost:11434");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();

            if (responseCode == 200) {
                model.addAttribute("message", "Ollama is running on localhost:11434");
            } else {
                model.addAttribute("message", "Ollama is not running. Response code: " + responseCode);
            }
        } catch (Exception e) {
            model.addAttribute("message", "Error checking Ollama status: " + e.getMessage());
        }
        return "index";
    }

    @GetMapping("/models")
    @ResponseBody
    public List<String> getModels() {
        List<String> models = new ArrayList<>();
        try {
            URL url = new URL("http://localhost:11434/api/tags"); // Replace with actual Ollama endpoint
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            if (connection.getResponseCode() == 200) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        models.add(line);
                    }
                }
            }
        } catch (Exception e) {
            models.add("Error fetching models: " + e.getMessage());
        }
        return models;
    }
}