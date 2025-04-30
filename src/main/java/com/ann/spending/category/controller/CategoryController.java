package com.ann.spending.category.controller;

import com.sun.jdi.event.StepEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @GetMapping
    public String getCategoryName(){
        return "hello";
    }

}
