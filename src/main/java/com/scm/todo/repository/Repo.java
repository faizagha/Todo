package com.scm.todo.repository;

import com.scm.todo.model.TodoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Repo extends JpaRepository<TodoModel, Long> {
}
