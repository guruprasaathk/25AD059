package com.habitforce.habitforce.Repository;

import com.habitforce.habitforce.Entity.CompletionLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface CompletionLogRepository
        extends JpaRepository<CompletionLog, Long> {

    boolean existsByHabit_IdAndCompletionDate(
            Long habitId,
            LocalDate completionDate
    );

    List<CompletionLog> findByHabit_IdOrderByCompletionDateDesc(
            Long habitId
    );

    List<CompletionLog> findByHabit_Id(Long habitId);

    void deleteByHabit_Id(Long habitId);
}