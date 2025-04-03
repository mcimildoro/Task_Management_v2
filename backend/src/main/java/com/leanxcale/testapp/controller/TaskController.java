package com.leanxcale.testapp.controller;

import com.leanxcale.testapp.model.Task;
import com.leanxcale.testapp.model.User;
import com.leanxcale.testapp.repository.TaskRepository;
import com.leanxcale.testapp.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.*;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskController(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<?> getTasks(Authentication authentication) {
        String username = authentication.getName();
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("error", "User not found"));
        }

        List<Task> tasks = taskRepository.findByUser(user.get());
        return ResponseEntity.ok(tasks);
    }

    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody Map<String, String> body, Authentication authentication) {
        String title = body.get("title");
        if (title == null || title.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Title is required"));
        }

        String username = authentication.getName();
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("error", "User not found"));
        }

        Task task = new Task();
        task.setTitle(title);
        task.setCompleted(false);
        task.setCreatedAt(Instant.now()); // ✅ Corrección aquí
        task.setUser(userOpt.get());

        Task savedTask = taskRepository.save(task);
        return ResponseEntity.ok(savedTask);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> toggleTask(@PathVariable Long id, Authentication authentication) {
        String username = authentication.getName();

        Optional<Task> taskOpt = taskRepository.findById(id);
        if (taskOpt.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("error", "Task not found"));
        }

        Task task = taskOpt.get();
        if (!task.getUser().getUsername().equals(username)) {
            return ResponseEntity.status(403).body(Map.of("error", "Unauthorized"));
        }

        task.setCompleted(!task.isCompleted());
        Task updated = taskRepository.save(task);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id, Authentication authentication) {
        String username = authentication.getName();

        Optional<Task> taskOpt = taskRepository.findById(id);
        if (taskOpt.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("error", "Task not found"));
        }

        Task task = taskOpt.get();
        if (!task.getUser().getUsername().equals(username)) {
            return ResponseEntity.status(403).body(Map.of("error", "Unauthorized"));
        }

        taskRepository.delete(task);
        return ResponseEntity.ok(Map.of("message", "Task deleted"));
    }
}
