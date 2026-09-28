package com.habitforce.habitforce.Entity;

import jakarta.persistence.*;

import java.time.LocalTime;

@Entity
@Table(name = "reminders")
public class Reminder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalTime reminderTime;

    private boolean enabled;

    @OneToOne
    @JoinColumn(name = "habit_id")
    private Habit habit;

    public Long getId() {
        return id;
    }

    public LocalTime getReminderTime() {
        return reminderTime;
    }

    public void setReminderTime(LocalTime reminderTime) {
        this.reminderTime = reminderTime;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Habit getHabit() {
        return habit;
    }

    public void setHabit(Habit habit) {
        this.habit = habit;
    }
}