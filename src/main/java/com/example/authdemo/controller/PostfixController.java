package com.example.authdemo.controller;

import com.example.authdemo.dto.PostfixCalculationResult;
import com.example.authdemo.service.PostfixService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/postfix")
@SecurityRequirement(name = "bearerAuth")
public class PostfixController {

    private final PostfixService postfixService;

    public PostfixController(PostfixService postfixService) {
        this.postfixService = postfixService;
    }

    @PostMapping("/convert")
    public ResponseEntity<String> convertInfixToPostfix(@RequestBody String infixExpression) {
        try {
            String postfix = postfixService.infixToPostfix(infixExpression);
            return ResponseEntity.ok(postfix);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error converting expression: " + e.getMessage());
        }
    }

    @PostMapping("/evaluate")
    public ResponseEntity<String> evaluatePostfixExpression(@RequestBody String postfixExpression) {
        try {
            double result = postfixService.evaluatePostfix(postfixExpression);
            return ResponseEntity.ok(String.valueOf(result));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error evaluating expression: " + e.getMessage());
        }
    }
    
    @PostMapping("/calculate")
    public ResponseEntity<?> calculateFromInfix(@RequestBody String infixExpression) {
        try {
            String postfix = postfixService.infixToPostfix(infixExpression);
            double result = postfixService.evaluatePostfix(postfix);
            return ResponseEntity.ok(new PostfixCalculationResult(postfix, result));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error calculating expression: " + e.getMessage());
        }
    }
}
