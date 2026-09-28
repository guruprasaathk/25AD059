package com.habitforce.habitforce.Controller;

import com.habitforce.habitforce.DTO.HabitRequest;
import com.habitforce.habitforce.DTO.HabitResponse;
import com.habitforce.habitforce.Service.HabitService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/habits")
public class HabitController {

    private final HabitService habitService;

    public HabitController(HabitService habitService) {
        this.habitService = habitService;
    }

    // CREATE HABIT
    @PostMapping
    public HabitResponse createHabit(
            @RequestBody HabitRequest request) {

        return habitService.createHabit(request);
    }

    // GET ALL HABITS
    @GetMapping
    public List<HabitResponse> getAllHabits() {

        return habitService
                .getAllHabits(0, 100, "id")
                .getContent();
    }

    // GET HABIT BY ID
    @GetMapping("/{id}")
    public HabitResponse getHabitById(
            @PathVariable Long id) {

        return habitService.getHabitById(id);
    }

    // UPDATE HABIT
    @PutMapping("/{id}")
    public HabitResponse updateHabit(
            @PathVariable Long id,
            @RequestBody HabitRequest request) {

        return habitService.updateHabit(id, request);
    }

    @DeleteMapping("/{id}")
    public String deleteHabit(@PathVariable Long id) {

        habitService.deleteHabit(id);

        return "Habit deleted successfully";
    }
}