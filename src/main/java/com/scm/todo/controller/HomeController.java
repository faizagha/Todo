package com.scm.todo.controller;

import com.scm.todo.services.TodoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
    @Autowired
    private TodoServices services;

    @GetMapping(path = "/")
    public ModelAndView todos()
    {
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("todoItems", services.getAll());
        return modelAndView;
    }

}
