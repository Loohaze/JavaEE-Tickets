package com.edu.nju.tickets.repository;

import com.edu.nju.tickets.model.Order;
import com.edu.nju.tickets.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {

    Transaction findByOrder(Order order);
}
