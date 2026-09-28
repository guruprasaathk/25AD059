package com.habitforce.habitforce.DTO;

import java.time.LocalTime;

public class ReminderResponse {

    private Long id;

    private Long habitId;

    private LocalTime reminderTime;

    private boolean enabled;

    public ReminderResponse(
            Long id,
            Long habitId,
            LocalTime reminderTime,
            boolean enabled) {

        this.id = id;
        this.habitId = habitId;
        this.reminderTime = reminderTime;
        this.enabled = enabled;
    }

    public Long getId() {
        return id;
    }

    public Long getHabitId() {
        return habitId;
    }

    public LocalTime getReminderTime() {
        return reminderTime;
    }

    public boolean isEnabled() {
        return enabled;
    }
}