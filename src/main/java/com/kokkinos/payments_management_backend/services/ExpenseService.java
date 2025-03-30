package com.kokkinos.payments_management_backend.services;


import com.kokkinos.payments_management_backend.dtos.ExpenseDTO;
import com.kokkinos.payments_management_backend.entities.Expense;
import com.kokkinos.payments_management_backend.repositories.ExpenseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExpenseService {
    @Autowired
    private ExpenseRepo repo;
//    @Autowired
//    private Expense expense;


    public double getTotalAmount(String filter) {
        LocalDate today = LocalDate.now();
        LocalDate tempStartDate = null;

        switch (filter) {
            case "Last_week" -> tempStartDate = today.minusDays(7);
            case "Last_month" -> tempStartDate = today.minusDays(30);
//            default -> startDate = today;
        }

        final LocalDate startDate = tempStartDate;

        List<Expense> expenseList = repo.findAll();

        return  expenseList.stream().filter(expense -> startDate == null ||(expense.getRegistrationDate().isAfter(startDate) || expense.getRegistrationDate().isEqual(startDate)) &&
                (expense.getRegistrationDate().isBefore(today) || expense.getRegistrationDate().isEqual(today))
                ).mapToDouble(Expense::getAmount).sum();

    }

    public void addExpense(ExpenseDTO expenseDTO) {
        Optional<Expense> existingExpense = repo.findByExpenseLabel(expenseDTO.getLabel());
        Expense expense;
        if (existingExpense.isPresent()) {
            expense = existingExpense.get();
            expense.setAmount(expense.getAmount() + expenseDTO.getAmount());
        } else {
            expense = new Expense();
            expense.setExpenseLabel(expenseDTO.getLabel());
            expense.setAmount(expenseDTO.getAmount());
            expense.setRegistrationDate(LocalDate.now());
        }
        repo.save(expense);

    }

    public List<ExpenseDTO> getSeparateExpenses() {
        return repo.findAll().stream()
                .map(expense -> {
                    ExpenseDTO dto = new ExpenseDTO();
                    dto.setLabel(expense.getExpenseLabel());
                    dto.setAmount(expense.getAmount());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
