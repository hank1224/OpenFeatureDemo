package com.example.openfeaturedemo.controller;

import com.example.openfeaturedemo.entity.LogEntry;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/logs")
@Tag(name = "Log Receiver API", description = "Receive logs from Case5 Web clients.")
public class LogController {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/")
    public ResponseEntity<?> receiveLogs(@RequestBody List<LogEntry> logEntries) {
        try {
            // 将接收到的对象转换回 JSON 格式以便打印和检查
            String rawJson = objectMapper.writeValueAsString(logEntries);
            System.out.println("Received log entries as JSON: " + rawJson);

            logEntries.forEach(entry -> System.out.println("Received log: " + entry));
            return ResponseEntity.ok().build();
        } catch (JsonProcessingException e) {
            System.out.println("Error converting received objects to JSON: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing logs");
        } catch (Exception e) {
            System.out.println("Error processing logs: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error processing logs");
        }
    }
}