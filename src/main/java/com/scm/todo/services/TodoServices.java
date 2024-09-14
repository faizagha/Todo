package com.scm.todo.services;

import com.scm.todo.model.TodoModel;
import com.scm.todo.repository.Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class TodoServices {

    private final Repo repository;

    @Autowired
    public TodoServices(Repo repository) {
        this.repository = repository;
    }

    public Iterable<TodoModel> getAll() {
        return repository.findAll();
    }

    public Optional<TodoModel> getById(Long id) {
        return repository.findById(id);
    }

    public TodoModel saveTodo(TodoModel todo) {
        // Ensure createdAt is set if not already
        if (todo.getCreatedAt() == null) {
            todo.setCreatedAt(Instant.now());
        }
        // Set default value for isComplete if not provided
        if (todo.getIsComplete() == null) {
            todo.setIsComplete(false);
        }
        return repository.save(todo);
    }

    public String deleteTodo(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return "Todo with id " + id + " has been successfully deleted.";
        } else {
            return "Todo with id " + id + " does not exist.";
        }
    }

    public TodoModel updateTodo(Long id, TodoModel newTodo) {
        Optional<TodoModel> optionalTodo = repository.findById(id);
        if (optionalTodo.isPresent()) {
            TodoModel existingTodo = optionalTodo.get();
            // Update fields
            existingTodo.setDescription(newTodo.getDescription());
            existingTodo.setIsComplete(newTodo.getIsComplete());
            existingTodo.setUpdatedAt(Instant.now());
            return repository.save(existingTodo);
        } else {
            throw new IllegalArgumentException("Todo with id " + id + " does not exist.");
        }
    }
}
