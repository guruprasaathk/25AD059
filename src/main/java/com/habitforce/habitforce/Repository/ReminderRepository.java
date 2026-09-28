package com.habitforce.habitforce.Repository;

import com.habitforce.habitforce.Entity.Reminder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReminderRepository extends JpaRepository<Reminder, Long> {

    List<Reminder> findByHabit_Id(Long habitId);

    void deleteByHabit_Id(Long habitId);
}