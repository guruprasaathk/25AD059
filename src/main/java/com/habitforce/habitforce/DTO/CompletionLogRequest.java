package com.habitforce.habitforce.DTO;

import java.time.LocalDate;

public class CompletionLogRequest {

    private LocalDate completionDate;

    private boolean completed;

    public LocalDate getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(LocalDate completionDate) {
        this.completionDate = completionDate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}