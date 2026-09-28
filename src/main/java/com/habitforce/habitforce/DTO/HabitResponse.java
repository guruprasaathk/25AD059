package com.habitforce.habitforce.DTO;

import java.time.LocalDate;

public class HabitResponse {

    private Long id;
    private String name;
    private String description;
    private String frequency;
    private boolean active;
    private LocalDate createdDate;

    public HabitResponse(
            Long id,
            String name,
            String description,
            String frequency,
            boolean active,
            LocalDate createdDate) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.frequency = frequency;
        this.active = active;
        this.createdDate = createdDate;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getFrequency() {
        return frequency;
    }

    public boolean isActive() {
        return active;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }
}