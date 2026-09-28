package com.habitforce.habitforce.Service;

import com.habitforce.habitforce.DTO.CompletionLogRequest;
import com.habitforce.habitforce.DTO.CompletionLogResponse;
import com.habitforce.habitforce.Entity.CompletionLog;
import com.habitforce.habitforce.Entity.Habit;
import com.habitforce.habitforce.Repository.CompletionLogRepository;
import com.habitforce.habitforce.Repository.HabitRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompletionLogService {

    private final CompletionLogRepository completionLogRepository;
    private final HabitRepository habitRepository;

    public CompletionLogService(
            CompletionLogRepository completionLogRepository,
            HabitRepository habitRepository) {

        this.completionLogRepository = completionLogRepository;
        this.habitRepository = habitRepository;
    }

    public CompletionLogResponse addCompletion(
            Long habitId,
            CompletionLogRequest request) {

        Habit habit = habitRepository
                .findById(habitId)
                .orElse(null);

        if (habit == null) {
            return null;
        }

        boolean alreadyExists =
                completionLogRepository
                        .existsByHabitIdAndCompletionDate(
                                habitId,
                                request.getCompletionDate());

        if (alreadyExists) {
            return null;
        }

        CompletionLog log = new CompletionLog();

        log.setHabit(habit);
        log.setCompletionDate(
                request.getCompletionDate());
        log.setCompleted(
                request.isCompleted());

        CompletionLog savedLog =
                completionLogRepository.save(log);

        return convertToResponse(savedLog);
    }

    public List<CompletionLogResponse> getCompletions(
            Long habitId) {

        return completionLogRepository
                .findByHabitIdOrderByCompletionDateDesc(habitId)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    private CompletionLogResponse convertToResponse(
            CompletionLog log) {

        return new CompletionLogResponse(
                log.getId(),
                log.getHabit().getId(),
                log.getCompletionDate(),
                log.isCompleted()
        );
    }
}