package com.edu.nju.tickets.repository;

import com.edu.nju.tickets.model.Project;
import com.edu.nju.tickets.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat,Long> {

    Seat findBySeatId(Long seatId);

    Seat findByRowAndNumberAndProject(int row, int number,Project project);

    List<Seat> findByProject(Project project);
}
