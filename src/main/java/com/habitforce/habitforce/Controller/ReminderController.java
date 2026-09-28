package com.habitforce.habitforce.Controller;

import com.habitforce.habitforce.DTO.ReminderRequest;
import com.habitforce.habitforce.DTO.ReminderResponse;
import com.habitforce.habitforce.Service.ReminderService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reminders")
public class ReminderController {

    private final ReminderService reminderService;

    public ReminderController(
            ReminderService reminderService) {

        this.reminderService = reminderService;
    }

    @PostMapping
    public ReminderResponse createReminder(
            @RequestBody ReminderRequest request) {

        return reminderService.createReminder(request);
    }

    @GetMapping("/habit/{habitId}")
    public ReminderResponse getReminder(
            @PathVariable Long habitId) {

        return reminderService.getReminder(habitId);
    }

    @PutMapping("/habit/{habitId}")
    public ReminderResponse updateReminder(
            @PathVariable Long habitId,
            @RequestBody ReminderRequest request) {

        return reminderService.updateReminder(
                habitId, request);
    }

    @DeleteMapping("/habit/{habitId}")
    public String deleteReminder(
            @PathVariable Long habitId) {

        reminderService.deleteReminder(habitId);

        return "Reminder deleted successfully";
    }
}