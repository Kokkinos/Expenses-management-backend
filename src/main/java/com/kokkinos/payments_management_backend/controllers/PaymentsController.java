package com.kokkinos.payments_management_backend.controllers;


import com.kokkinos.payments_management_backend.dtos.ExpenseDTO;
import com.kokkinos.payments_management_backend.services.ExpenseService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class PaymentsController {

    @Autowired
    private ExpenseService expenseService;

    @GetMapping("/")
    public String home() {
        return "Home Page!";
    }


    @GetMapping("/separate_expenses")
    public List<ExpenseDTO> getSeparateExpenses() {
        return expenseService.getSeparateExpenses();
    }

    @GetMapping("/total")
    public ResponseEntity<Double> getTotalFilteredAmount(@RequestParam String filter) {
        try {
            double total = expenseService.getTotalAmount(filter);
            return ResponseEntity.ok(total);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(0.0);
        }
    }

    @PostMapping("/add_expense")
    public void addExpense(@RequestBody ExpenseDTO expenseDTO) {
        expenseService.addExpense(expenseDTO);
    }

    @GetMapping("csrf_token")
    public CsrfToken getCsrfToken(HttpServletRequest request) {
        return (CsrfToken) request.getAttribute("_csrf");
    }


}
