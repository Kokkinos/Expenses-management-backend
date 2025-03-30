package com.kokkinos.payments_management_backend.repositories;

import com.kokkinos.payments_management_backend.entities.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExpenseRepo extends JpaRepository<Expense, Long> {

    Optional<Expense> findByExpenseLabel(String expenseLabel);
}
