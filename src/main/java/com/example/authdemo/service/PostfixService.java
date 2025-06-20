package com.example.authdemo.service;

import org.springframework.stereotype.Service;
import java.util.Stack;

@Service
public class PostfixService {

    /**
     * Helper to determine operator precedence.
     */
    private int precedence(char operator) {
        if (operator == '+' || operator == '-') return 1;
        if (operator == '*' || operator == '/' || operator == '%') return 2;
        return -1; // Not an operator
    }

    /**
     * Converts an infix mathematical expression to a postfix expression.
     * This version handles multi-digit numbers and produces a space-separated postfix string.
     * @param infix The infix expression string.
     * @return A space-separated postfix expression string.
     */
    public String infixToPostfix(String infix) {
        StringBuilder postfix = new StringBuilder();
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < infix.length(); ++i) {
            char token = infix.charAt(i);

            if (Character.isWhitespace(token)) continue;

            // If the token is a number (could be multi-digit)
            if (Character.isDigit(token) || token == '.') {
                StringBuilder numBuilder = new StringBuilder();
                while (i < infix.length() && (Character.isDigit(infix.charAt(i)) || infix.charAt(i) == '.')) {
                    numBuilder.append(infix.charAt(i++));
                }
                i--; // Adjust for outer loop's increment
                postfix.append(numBuilder.toString()).append(" ");
            }
            // If the token is an '(', push it to the stack.
            else if (token == '(') {
                stack.push(token);
            }
            // If the token is an ')', pop and append from the stack until '(' is found
            else if (token == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    postfix.append(stack.pop()).append(" ");
                }
                stack.pop(); // Pop the opening parenthesis
            }
            // If an operator is encountered
            else {
                while (!stack.isEmpty() && stack.peek() != '(' && precedence(token) <= precedence(stack.peek())) {
                    postfix.append(stack.pop()).append(" ");
                }
                stack.push(token);
            }
        }

        // Pop all remaining operators from the stack
        while (!stack.isEmpty()) {
            if (stack.peek() == '(') throw new IllegalArgumentException("Mismatched parentheses in expression");
            postfix.append(stack.pop()).append(" ");
        }

        return postfix.toString().trim();
    }

    /**
     * Evaluates a space-separated postfix expression.
     * @param postfix The postfix expression string.
     * @return The result of the calculation.
     */
    public double evaluatePostfix(String postfix) {
        Stack<Double> stack = new Stack<>();
        String[] tokens = postfix.split("\\s+");

        for (String token : tokens) {
            if (token.isEmpty()) continue;

            try {
                // If the token is a number, push it to the stack
                double num = Double.parseDouble(token);
                stack.push(num);
            } catch (NumberFormatException e) {
                // If the token is an operator, pop two operands, calculate, and push result
                if (stack.size() < 2) throw new IllegalArgumentException("Invalid postfix expression: insufficient operands.");
                
                double val2 = stack.pop();
                double val1 = stack.pop();
                
                switch (token.charAt(0)) {
                    case '+': stack.push(val1 + val2); break;
                    case '-': stack.push(val1 - val2); break;
                    case '*': stack.push(val1 * val2); break;
                    case '/': 
                        if (val2 == 0) throw new ArithmeticException("Cannot divide by zero.");
                        stack.push(val1 / val2); break;
                    case '%':
                        if (val2 == 0) throw new ArithmeticException("Cannot divide by zero.");
                        stack.push(val1 % val2); break;
                    default: throw new IllegalArgumentException("Invalid operator: " + token);
                }
            }
        }

        if (stack.size() != 1) throw new IllegalArgumentException("Invalid postfix expression: too many operands.");
        return stack.pop();
    }
}
