package com.habitforce.habitforce.Repository;

import com.habitforce.habitforce.Entity.CompletionLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface CompletionLogRepository
        extends JpaRepository<CompletionLog, Long> {

    List<CompletionLog> findByHabitIdOrderByCompletionDateDesc(
            Long habitId);

    boolean existsByHabitIdAndCompletionDate(
            Long habitId,
            LocalDate completionDate);
}