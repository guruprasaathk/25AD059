package com.habitforce.habitforce.Controller;

import com.habitforce.habitforce.DTO.StreakResponse;
import com.habitforce.habitforce.Service.StreakService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/habits")
public class StreakController {

    private final StreakService streakService;

    public StreakController(
            StreakService streakService) {

        this.streakService = streakService;
    }

    @GetMapping("/{habitId}/streak")
    public StreakResponse getStreak(
            @PathVariable Long habitId) {

        return streakService.getStreak(habitId);
    }
}