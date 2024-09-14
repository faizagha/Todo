package com.scm.todo.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;

@Data
@Entity
@Table(name = "todo_items")
public class TodoModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String description;

    private Boolean isComplete;

    private Instant createdAt;
    private Instant updatedAt;

    @Override
    public String toString() {
        return String.format("TodoItem{id=%d, description='%s', isComplete=%s, createdAt=%s, updatedAt=%s}",
                id, description, isComplete, createdAt, updatedAt);
    }
}
