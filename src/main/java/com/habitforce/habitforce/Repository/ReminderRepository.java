package com.habitforce.habitforce.Repository;

import com.habitforce.habitforce.Entity.Reminder;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReminderRepository
        extends JpaRepository<Reminder, Long> {

    Optional<Reminder> findByHabitId(Long habitId);
}