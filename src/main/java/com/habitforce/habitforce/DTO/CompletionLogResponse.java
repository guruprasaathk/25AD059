package com.habitforce.habitforce.DTO;

import java.time.LocalDate;

public class CompletionLogResponse {

    private Long id;
    private Long habitId;
    private LocalDate completionDate;
    private boolean completed;

    public CompletionLogResponse(
            Long id,
            Long habitId,
            LocalDate completionDate,
            boolean completed) {

        this.id = id;
        this.habitId = habitId;
        this.completionDate = completionDate;
        this.completed = completed;
    }

    public Long getId() {
        return id;
    }

    public Long getHabitId() {
        return habitId;
    }

    public LocalDate getCompletionDate() {
        return completionDate;
    }

    public boolean isCompleted() {
        return completed;
    }
}