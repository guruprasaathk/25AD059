package com.habitforce.habitforce.Repository;

import com.habitforce.habitforce.Entity.Streak;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StreakRepository
        extends JpaRepository<Streak, Long> {

    Optional<Streak> findByHabit_Id(Long habitId);

    void deleteByHabit_Id(Long habitId);
}