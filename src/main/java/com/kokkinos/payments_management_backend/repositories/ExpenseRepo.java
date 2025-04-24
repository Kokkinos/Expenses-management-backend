package com.kokkinos.payments_management_backend.repositories;

import com.kokkinos.payments_management_backend.entities.Expense;
import com.kokkinos.payments_management_backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseRepo extends JpaRepository<Expense, Long> {
    List<Expense> findByUser(User user);
    Optional<Expense> findByExpenseLabel(String expenseLabel);
    Optional<Expense> findByUserAndExpenseLabel(User user, String expenseLabel);
}
