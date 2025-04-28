package com.kokkinos.payments_management_backend.services;


import com.kokkinos.payments_management_backend.dtos.ExpenseDTO;
import com.kokkinos.payments_management_backend.entities.Expense;
import com.kokkinos.payments_management_backend.entities.User;
import com.kokkinos.payments_management_backend.repositories.ExpenseRepo;
import com.kokkinos.payments_management_backend.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    @Autowired
    private UserRepo userRepo;

    public double getTotalAmount(String filter) {
        String username = ((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

        User user = userRepo.findByUsername(username);


        LocalDate today = LocalDate.now();
        LocalDate tempStartDate = null;

        switch (filter) {
            case "Last_week" -> tempStartDate = today.minusDays(7);
            case "Last_month" -> tempStartDate = today.minusDays(30);
//            default -> startDate = today;
        }

        final LocalDate startDate = tempStartDate;

        List<Expense> expenseList = repo.findByUser(user);
//        List<Expense> expenseList = repo.findAll();

        return  expenseList.stream().filter(expense -> startDate == null ||(expense.getRegistrationDate().isAfter(startDate) || expense.getRegistrationDate().isEqual(startDate)) &&
                (expense.getRegistrationDate().isBefore(today) || expense.getRegistrationDate().isEqual(today))
                ).mapToDouble(Expense::getAmount).sum();

    }

    public void addExpense(ExpenseDTO expenseDTO) {

        String username = ((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

        User user = userRepo.findByUsername(username);

        Optional<Expense> existingExpense = repo.findByUserAndExpenseLabel(user, expenseDTO.getLabel());
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
        expense.setUser(user);
        repo.save(expense);

    }

    public List<ExpenseDTO> getSeparateExpenses() {
        String username = ((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

        User user = userRepo.findByUsername(username);

        return repo.findByUser(user).stream()
                .map(expense -> {
                    ExpenseDTO dto = new ExpenseDTO();
                    dto.setLabel(expense.getExpenseLabel());
                    dto.setAmount(expense.getAmount());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public List<ExpenseDTO> getFilteredSeparateExpenses(String filter) {
        String username = ((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

        User user = userRepo.findByUsername(username);

        LocalDate today = LocalDate.now();
        LocalDate tempStartDate = null;

        switch (filter) {
            case "Last_week" -> tempStartDate = today.minusDays(7);
            case "Last_month" -> tempStartDate = today.minusDays(30);
        }

        final LocalDate startDate = tempStartDate;

        List<Expense> expenseList = repo.findByUser(user);

        return expenseList.stream()
                .filter(expense -> startDate == null || (
                        (expense.getRegistrationDate().isAfter(startDate) || expense.getRegistrationDate().isEqual(startDate))
                                && (expense.getRegistrationDate().isBefore(today) || expense.getRegistrationDate().isEqual(today))
                ))
                .map(expense -> {
                    ExpenseDTO dto = new ExpenseDTO();
                    dto.setLabel(expense.getExpenseLabel());
                    dto.setAmount(expense.getAmount());
                    return dto;
                })
                .collect(Collectors.toList());


//        return repo.findByUser(user).stream()
//                .map(expense -> {
//                    ExpenseDTO dto = new ExpenseDTO();
//                    dto.setLabel(expense.getExpenseLabel());
//                    dto.setAmount(expense.getAmount());
//                    return dto;
//                })
//                .collect(Collectors.toList());
    }

}
