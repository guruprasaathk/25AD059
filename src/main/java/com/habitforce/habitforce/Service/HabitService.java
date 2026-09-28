package com.habitforce.habitforce.Service;

import com.habitforce.habitforce.DTO.HabitRequest;
import com.habitforce.habitforce.DTO.HabitResponse;
import com.habitforce.habitforce.Entity.Habit;
import com.habitforce.habitforce.Repository.CompletionLogRepository;
import com.habitforce.habitforce.Repository.HabitRepository;
import com.habitforce.habitforce.Repository.ReminderRepository;
import com.habitforce.habitforce.Repository.StreakRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class HabitService {

    private final HabitRepository habitRepository;
    private final CompletionLogRepository completionLogRepository;
    private final StreakRepository streakRepository;
    private final ReminderRepository reminderRepository;

    public HabitService(
            HabitRepository habitRepository,
            CompletionLogRepository completionLogRepository,
            StreakRepository streakRepository,
            ReminderRepository reminderRepository) {

        this.habitRepository = habitRepository;
        this.completionLogRepository = completionLogRepository;
        this.streakRepository = streakRepository;
        this.reminderRepository = reminderRepository;
    }

    // CREATE HABIT
    public HabitResponse createHabit(HabitRequest request) {

        Habit habit = new Habit();

        habit.setName(request.getName());
        habit.setDescription(request.getDescription());
        habit.setFrequency(request.getFrequency());
        habit.setActive(true);

        Habit savedHabit = habitRepository.save(habit);

        return convertToResponse(savedHabit);
    }

    // GET ALL HABITS
    public Page<HabitResponse> getAllHabits(
            int page,
            int size,
            String sortBy) {

        PageRequest pageRequest = PageRequest.of(
                page,
                size,
                Sort.by(sortBy).ascending()
        );

        return habitRepository
                .findAll(pageRequest)
                .map(this::convertToResponse);
    }

    // GET HABIT BY ID
    public HabitResponse getHabitById(Long id) {

        Habit habit = habitRepository
                .findById(id)
                .orElse(null);

        if (habit == null) {
            return null;
        }

        return convertToResponse(habit);
    }

    // UPDATE HABIT
    public HabitResponse updateHabit(
            Long id,
            HabitRequest request) {

        Habit habit = habitRepository
                .findById(id)
                .orElse(null);

        if (habit == null) {
            return null;
        }

        habit.setName(request.getName());
        habit.setDescription(request.getDescription());
        habit.setFrequency(request.getFrequency());

        Habit updatedHabit = habitRepository.save(habit);

        return convertToResponse(updatedHabit);
    }

    @Transactional
    public void deleteHabit(Long id) {

        if (!habitRepository.existsById(id)) {
            return;
        }

        // Delete completion logs
        completionLogRepository.deleteByHabit_Id(id);

        // Delete streak
        streakRepository.deleteByHabit_Id(id);

        // Delete reminders
        reminderRepository.deleteByHabit_Id(id);

        // Finally delete habit
        habitRepository.deleteById(id);
    }
    // ENTITY TO RESPONSE
    private HabitResponse convertToResponse(Habit habit) {

        return new HabitResponse(
                habit.getId(),
                habit.getName(),
                habit.getDescription(),
                habit.getFrequency(),
                habit.isActive(),
                LocalDate.now()
        );
    }
}