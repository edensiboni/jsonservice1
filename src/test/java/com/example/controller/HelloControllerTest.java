package com.example.controller;

import com.example.model.Message;
import com.example.controller.HelloController;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HelloController.class)  // Load only the controller
@ContextConfiguration(classes = {HelloController.class})
public class HelloControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        // Initialize any test data or configurations here if needed
    }

    @Test
    public void testEchoMessage() throws Exception {
        // Prepare the request payload as JSON
        String requestContent = "{\"content\":\"Hello, world!\"}";

        // Perform the POST request and verify the response
        mockMvc.perform(post("/api/echo")
                .contentType("application/json")  // Use application/json directly
                .content(requestContent))
                .andExpect(status().isOk())  // Verify 200 OK status
                .andExpect(jsonPath("$.content", is("Hello, world!\nI did it")))  // Check response content
                .andExpect(jsonPath("$.statusCode", is("200")));  // Check status code in the response
    }

    @Test
    public void testEchoMessageBadRequest() throws Exception {
        // Prepare the request payload as an empty content (invalid input)
        String requestContent = "{\"content\":\"\"}";

        // Perform the POST request and verify the response
        mockMvc.perform(post("/api/echo")
                .contentType("application/json")  // Use application/json directly
                .content(requestContent))
                .andExpect(status().isBadRequest())  // Verify 400 Bad Request status
                .andExpect(jsonPath("$.content", is("Please provide 'content' in the request.")))  // Error message
                .andExpect(jsonPath("$.statusCode", is("400")));  // Check status code in the response
    }
}
