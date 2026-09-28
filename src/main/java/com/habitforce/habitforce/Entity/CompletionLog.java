package com.habitforce.habitforce.Entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "completion_logs")
public class CompletionLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate completionDate;

    private boolean completed;

    @ManyToOne
    @JoinColumn(name = "habit_id")
    private Habit habit;

    public Long getId() {
        return id;
    }

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

    public Habit getHabit() {
        return habit;
    }

    public void setHabit(Habit habit) {
        this.habit = habit;
    }
}