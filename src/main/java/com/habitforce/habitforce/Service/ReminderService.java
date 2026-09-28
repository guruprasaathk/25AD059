package com.habitforce.habitforce.Service;

import com.habitforce.habitforce.DTO.ReminderRequest;
import com.habitforce.habitforce.DTO.ReminderResponse;
import com.habitforce.habitforce.Entity.Habit;
import com.habitforce.habitforce.Entity.Reminder;
import com.habitforce.habitforce.Repository.HabitRepository;
import com.habitforce.habitforce.Repository.ReminderRepository;

import org.springframework.stereotype.Service;

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

    public ReminderResponse createReminder(
            ReminderRequest request) {

        Habit habit = habitRepository
                .findById(request.getHabitId())
                .orElse(null);

        if (habit == null) {
            return null;
        }

        Reminder reminder = new Reminder();

        reminder.setHabit(habit);
        reminder.setReminderTime(
                request.getReminderTime());
        reminder.setEnabled(
                request.isEnabled());

        Reminder savedReminder =
                reminderRepository.save(reminder);

        return convertToResponse(savedReminder);
    }

    public ReminderResponse getReminder(
            Long habitId) {

        Reminder reminder = reminderRepository
                .findByHabitId(habitId)
                .orElse(null);

        if (reminder == null) {
            return null;
        }

        return convertToResponse(reminder);
    }

    public ReminderResponse updateReminder(
            Long habitId,
            ReminderRequest request) {

        Reminder reminder = reminderRepository
                .findByHabitId(habitId)
                .orElse(null);

        if (reminder == null) {
            return null;
        }

        reminder.setReminderTime(
                request.getReminderTime());

        reminder.setEnabled(
                request.isEnabled());

        Reminder updatedReminder =
                reminderRepository.save(reminder);

        return convertToResponse(updatedReminder);
    }

    public void deleteReminder(Long habitId) {

        Reminder reminder = reminderRepository
                .findByHabitId(habitId)
                .orElse(null);

        if (reminder != null) {
            reminderRepository.delete(reminder);
        }
    }

    private ReminderResponse convertToResponse(
            Reminder reminder) {

        return new ReminderResponse(
                reminder.getId(),
                reminder.getHabit().getId(),
                reminder.getReminderTime(),
                reminder.isEnabled()
        );
    }
}