package com.kokkinos.payments_management_backend.repositories;

import com.kokkinos.payments_management_backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
    //make username unique
    User findByUsername(String username);
}
