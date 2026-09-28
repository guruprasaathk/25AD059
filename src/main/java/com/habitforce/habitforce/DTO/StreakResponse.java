package com.habitforce.habitforce.DTO;

import java.time.LocalDate;

public class StreakResponse {

    private Long habitId;

    private int currentStreak;

    private int bestStreak;

    private LocalDate lastCompletedDate;

    public StreakResponse(
            Long habitId,
            int currentStreak,
            int bestStreak,
            LocalDate lastCompletedDate) {

        this.habitId = habitId;
        this.currentStreak = currentStreak;
        this.bestStreak = bestStreak;
        this.lastCompletedDate = lastCompletedDate;
    }

    public Long getHabitId() {
        return habitId;
    }

    public int getCurrentStreak() {
        return currentStreak;
    }

    public int getBestStreak() {
        return bestStreak;
    }

    public LocalDate getLastCompletedDate() {
        return lastCompletedDate;
    }
}