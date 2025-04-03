package com.leanxcale.testapp.repository;

import com.leanxcale.testapp.model.Task;
import com.leanxcale.testapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

// REPOSITORIO DE TAREAS
// es una interfaz que le dice a Spring cómo quieres trabajar con la base de datos sin tener que escribir tú las consultas SQL.

public interface TaskRepository extends JpaRepository<Task, Long> { // hereda todo el CRUD de JpaRepository
    List<Task> findByUser(User user); // buscar la tabla tasks por la columna user y devuelve el resultado si existe.
}
