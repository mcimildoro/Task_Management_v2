package com.leanxcale.testapp.service;

import com.leanxcale.testapp.model.Task;
import com.leanxcale.testapp.model.User;
import com.leanxcale.testapp.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    // Obtener todas las tareas del usuario
    public List<Task> getTasksByUser(User user) {
        return taskRepository.findByUser(user);
    }

    // Agregar una nueva tarea
    public Task addTask(String title, User user) {
        Task task = new Task();
        task.setTitle(title);
        task.setCompleted(false);
        task.setUser(user);
        task.setCreatedAt(new Date().toInstant());
        return taskRepository.save(task);
    }

    // Marcar tarea como completada
    public Optional<Task> toggleTaskCompletion(Long taskId, User user) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if (optionalTask.isPresent() && optionalTask.get().getUser().getId().equals(user.getId())) {
            Task task = optionalTask.get();
            task.setCompleted(!task.isCompleted());
            return Optional.of(taskRepository.save(task));
        }
        return Optional.empty();
    }

    // Eliminar una tarea
    public boolean deleteTask(Long taskId, User user) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if (optionalTask.isPresent() && optionalTask.get().getUser().getId().equals(user.getId())) {
            taskRepository.delete(optionalTask.get());
            return true;
        }
        return false;
    }
}
