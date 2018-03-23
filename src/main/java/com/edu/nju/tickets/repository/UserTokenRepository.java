package com.edu.nju.tickets.repository;

import com.edu.nju.tickets.model.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTokenRepository extends JpaRepository<UserToken,Long> {

    UserToken findByUserName(String userName);

}
