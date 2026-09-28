package com.habitforce.habitforce.Service;

import com.habitforce.habitforce.DTO.ReminderRequest;
import com.habitforce.habitforce.Entity.Habit;
import com.habitforce.habitforce.Entity.Reminder;
import com.habitforce.habitforce.Repository.HabitRepository;
import com.habitforce.habitforce.Repository.ReminderRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReminderService {

    private final ReminderRepository reminderRepository;
    private final HabitRepository habitRepository;

    public ReminderService(
            ReminderRepository reminderRepository,
            HabitRepository habitRepository) {

        this.reminderRepository = reminderRepository;
        this.habitRepository = habitRepository;
    }

    // CREATE REMINDER
    public Reminder createReminder(ReminderRequest request) {

        Habit habit = habitRepository
                .findById(request.getHabitId())
                .orElse(null);

        if (habit == null) {
            return null;
        }

        Reminder reminder = new Reminder();

        reminder.setHabit(habit);
        reminder.setReminderTime(request.getReminderTime());
        reminder.setEnabled(request.isEnabled());

        return reminderRepository.save(reminder);
    }

    // GET ALL REMINDERS
    public List<Reminder> getAllReminders() {

        return reminderRepository.findAll();
    }

    // GET REMINDER BY ID
    public Reminder getReminderById(Long id) {

        return reminderRepository
                .findById(id)
                .orElse(null);
    }

    // UPDATE REMINDER
    public Reminder updateReminder(
            Long id,
            ReminderRequest request) {

        Reminder reminder = reminderRepository
                .findById(id)
                .orElse(null);

        if (reminder == null) {
            return null;
        }

        Habit habit = habitRepository
                .findById(request.getHabitId())
                .orElse(null);

        if (habit == null) {
            return null;
        }

        reminder.setHabit(habit);
        reminder.setReminderTime(request.getReminderTime());
        reminder.setEnabled(request.isEnabled());

        return reminderRepository.save(reminder);
    }

    // DELETE REMINDER
    public void deleteReminder(Long id) {

        if (reminderRepository.existsById(id)) {
            reminderRepository.deleteById(id);
        }
    }
}