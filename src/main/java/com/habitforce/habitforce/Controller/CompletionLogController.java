package com.habitforce.habitforce.Controller;

import com.habitforce.habitforce.DTO.CompletionLogRequest;
import com.habitforce.habitforce.DTO.CompletionLogResponse;
import com.habitforce.habitforce.Service.CompletionLogService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/habits")
public class CompletionLogController {

    private final CompletionLogService completionLogService;

    public CompletionLogController(
            CompletionLogService completionLogService) {

        this.completionLogService = completionLogService;
    }

    // ADD COMPLETION
    @PostMapping("/{habitId}/completion")
    public CompletionLogResponse addCompletion(
            @PathVariable Long habitId,
            @RequestBody CompletionLogRequest request) {

        return completionLogService
                .addCompletion(habitId, request);
    }

    // GET COMPLETION HISTORY
    @GetMapping("/{habitId}/completion")
    public List<CompletionLogResponse> getCompletions(
            @PathVariable Long habitId) {

        return completionLogService
                .getCompletions(habitId);
    }
}