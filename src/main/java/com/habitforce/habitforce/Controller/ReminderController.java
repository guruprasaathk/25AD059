package com.habitforce.habitforce.Controller;

import com.habitforce.habitforce.DTO.ReminderRequest;
import com.habitforce.habitforce.Entity.Reminder;
import com.habitforce.habitforce.Service.ReminderService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reminders")
@CrossOrigin
public class ReminderController {

    private final ReminderService reminderService;

    public ReminderController(ReminderService reminderService) {
        this.reminderService = reminderService;
    }

    @PostMapping
    public Reminder createReminder(
            @RequestBody ReminderRequest request) {

        return reminderService.createReminder(request);
    }

    @GetMapping
    public List<Reminder> getAllReminders() {

        return reminderService.getAllReminders();
    }

    @GetMapping("/{id}")
    public Reminder getReminderById(
            @PathVariable Long id) {

        return reminderService.getReminderById(id);
    }

    @PutMapping("/{id}")
    public Reminder updateReminder(
            @PathVariable Long id,
            @RequestBody ReminderRequest request) {

        return reminderService.updateReminder(id, request);
    }

    @DeleteMapping("/{id}")
    public String deleteReminder(
            @PathVariable Long id) {

        reminderService.deleteReminder(id);

        return "Reminder deleted successfully";
    }
}