package com.habitforce.habitforce.Service;

import com.habitforce.habitforce.DTO.StreakResponse;
import com.habitforce.habitforce.Entity.CompletionLog;
import com.habitforce.habitforce.Entity.Habit;
import com.habitforce.habitforce.Entity.Streak;
import com.habitforce.habitforce.Repository.CompletionLogRepository;
import com.habitforce.habitforce.Repository.HabitRepository;
import com.habitforce.habitforce.Repository.StreakRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class StreakService {

    private final StreakRepository streakRepository;
    private final CompletionLogRepository completionLogRepository;
    private final HabitRepository habitRepository;

    public StreakService(
            StreakRepository streakRepository,
            CompletionLogRepository completionLogRepository,
            HabitRepository habitRepository) {

        this.streakRepository = streakRepository;
        this.completionLogRepository = completionLogRepository;
        this.habitRepository = habitRepository;
    }

    public StreakResponse getStreak(Long habitId) {

        Habit habit = habitRepository
                .findById(habitId)
                .orElse(null);

        if (habit == null) {
            return null;
        }

        List<CompletionLog> logs =
                completionLogRepository
                        .findByHabit_IdOrderByCompletionDateDesc(habitId);

        int currentStreak = calculateCurrentStreak(logs);

        int bestStreak = calculateBestStreak(logs);

        LocalDate lastCompletedDate = null;

        if (!logs.isEmpty()) {
            lastCompletedDate =
                    logs.get(0).getCompletionDate();
        }

        Streak streak = streakRepository
                .findByHabit_Id(habitId)
                .orElse(new Streak());

         streak.setHabit(habit);
        streak.setCurrentStreak(currentStreak);
        streak.setBestStreak(bestStreak);
        streak.setLastCompletedDate(lastCompletedDate);

         streakRepository.save(streak);

        return new StreakResponse(
                habitId,
                currentStreak,
                bestStreak,
                lastCompletedDate
        );
    }

    private int calculateCurrentStreak(
            List<CompletionLog> logs) {

        if (logs.isEmpty()) {
            return 0;
        }

        LocalDate expectedDate = LocalDate.now();

        int streak = 0;

        for (CompletionLog log : logs) {

            if (!log.isCompleted()) {
                continue;
            }

            LocalDate date =
                    log.getCompletionDate();

            if (date.equals(expectedDate)) {

                streak++;

                expectedDate =
                        expectedDate.minusDays(1);

            } else if (date.isBefore(expectedDate)) {

                break;
            }
        }

        return streak;
    }

    private int calculateBestStreak(
            List<CompletionLog> logs) {

        int best = 0;
        int current = 0;

        LocalDate previousDate = null;

        for (int i = logs.size() - 1; i >= 0; i--) {

            CompletionLog log = logs.get(i);

            if (!log.isCompleted()) {
                current = 0;
                previousDate = null;
                continue;
            }

            LocalDate date =
                    log.getCompletionDate();

            if (previousDate == null) {

                current = 1;

            } else if (date.equals(
                    previousDate.plusDays(1))) {

                current++;

            } else {

                current = 1;
            }

            if (current > best) {
                best = current;
            }

            previousDate = date;
        }

        return best;
    }
}