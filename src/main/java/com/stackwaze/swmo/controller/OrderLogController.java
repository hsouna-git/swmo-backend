package com.stackwaze.swmo.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class OrderLogController {

    private static final String LOG_FILE_NAME = "commandes.log";

    @GetMapping(value = "/api/orders/logs", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> getOrdersLog() throws IOException {
        Path logPath = Paths.get(System.getProperty("user.dir"), "logs", LOG_FILE_NAME).toAbsolutePath().normalize();

        if (!Files.exists(logPath)) {
            return ResponseEntity.ok("");
        }

        String content = Files.readString(logPath, StandardCharsets.UTF_8);
        return ResponseEntity.ok(content);
    }
}
