package com.example.controller;

import com.example.model.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class HelloController {

    @PostMapping("/echo")
    public ResponseEntity<Message> echoMessage(@RequestBody Message input) {
        String userInputString = input.getContent();
        String statusCode = "200";

        if (userInputString == null || userInputString.trim().isEmpty()) {
            statusCode = "400";
            return ResponseEntity.badRequest().body(new Message("Please provide 'content' in the request.", statusCode));
        }

        String modifiedContent = input.getContent() + "\nI did it";
        return ResponseEntity.ok(new Message(modifiedContent, statusCode));
    }
}
