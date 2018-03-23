package com.edu.nju.tickets.repository;

import com.edu.nju.tickets.model.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRepository extends JpaRepository<Manager,String> {

    Manager findByManagerId(String managerId);
}
