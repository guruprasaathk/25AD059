package com.habitforce.habitforce.Repository;

import com.habitforce.habitforce.Entity.Habit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HabitRepository
        extends JpaRepository<Habit, Long> {

}