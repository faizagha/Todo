package com.scm.todo.controller;

import com.scm.todo.model.TodoModel;
import com.scm.todo.services.TodoServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.Instant;
import java.util.Optional;

@Controller
public class FormController {

    @Autowired
    private TodoServices services;

    @GetMapping("/create-todo")
    public String create(@ModelAttribute("todoModel") TodoModel todoModel) {
        return "create-todo";
    }

    @PostMapping("/todo")
    public String saveTodo(@Valid @ModelAttribute("todoModel") TodoModel todo, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "create-todo";
        }
        todo.setCreatedAt(Instant.now());
        todo.setUpdatedAt(Instant.now());
        services.saveTodo(todo);
        redirectAttributes.addFlashAttribute("message", "Todo item successfully created!");
        return "redirect:/";
    }


    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        Optional<TodoModel> todo = services.getById(id);
        if (todo.isPresent()) {
            model.addAttribute("todoModel", todo.get());
            return "edit-todo";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/update/{id}")
    public String updateTodo(@PathVariable("id") Long id, @Valid @ModelAttribute("todoModel") TodoModel todo, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "edit-todo";
        }
        Optional<TodoModel> existingTodo = services.getById(id);
        if (existingTodo.isPresent()) {
            TodoModel existing = existingTodo.get();
            todo.setId(id);
            todo.setCreatedAt(existing.getCreatedAt());
            todo.setUpdatedAt(Instant.now());
            services.saveTodo(todo);
            redirectAttributes.addFlashAttribute("message", "Todo item successfully updated!");
        }
        return "redirect:/";
    }


    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        if (services.getById(id).isPresent()) {
            services.deleteTodo(id);
            redirectAttributes.addFlashAttribute("message", "Todo item successfully deleted!");
        }
        return "redirect:/";
    }
}
