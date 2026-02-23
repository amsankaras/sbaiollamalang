package com.sankar.sbaiollamalang;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.net.HttpURLConnection;
import java.net.URL;

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
}