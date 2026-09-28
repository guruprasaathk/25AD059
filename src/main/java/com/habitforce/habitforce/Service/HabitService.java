package com.habitforce.habitforce.Service;

import com.habitforce.habitforce.DTO.HabitRequest;
import com.habitforce.habitforce.DTO.HabitResponse;
import com.habitforce.habitforce.Entity.Habit;
import com.habitforce.habitforce.Repository.HabitRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HabitService {

    private final HabitRepository habitRepository;

    public HabitService(HabitRepository habitRepository) {
        this.habitRepository = habitRepository;
    }

    public HabitResponse createHabit(HabitRequest request) {

        Habit habit = new Habit();

        habit.setName(request.getName());
        habit.setDescription(request.getDescription());
        habit.setFrequency(request.getFrequency());

        Habit savedHabit = habitRepository.save(habit);

        return convertToResponse(savedHabit);
    }

    public List<HabitResponse> getAllHabits() {

        return habitRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    public HabitResponse getHabitById(Long id) {

        Habit habit = habitRepository
                .findById(id)
                .orElse(null);

        if (habit == null) {
            return null;
        }

        return convertToResponse(habit);
    }

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

    public void deleteHabit(Long id) {

        habitRepository.deleteById(id);
    }

    private HabitResponse convertToResponse(Habit habit) {

        return new HabitResponse(
                habit.getId(),
                habit.getName(),
                habit.getDescription(),
                habit.getFrequency(),
                habit.isActive(),
                habit.getCreatedDate()
        );
    }
}