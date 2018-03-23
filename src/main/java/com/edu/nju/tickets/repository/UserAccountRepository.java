package com.edu.nju.tickets.repository;

import com.edu.nju.tickets.model.User;
import com.edu.nju.tickets.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountRepository extends JpaRepository<UserAccount,Long> {

    UserAccount findByUser(User user);
}
