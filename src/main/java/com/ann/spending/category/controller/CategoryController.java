package com.ann.spending.category.controller;

import com.ann.spending.authorization.entity.User;
import com.ann.spending.category.entity.Category;
import com.ann.spending.category.view.CategoryDTO;
import com.sun.jdi.event.StepEvent;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @GetMapping
    public String getCategoryName(){

        // todo: implement cruds for category
        // todo: implement sorting
        return "hello";
    }

    @PatchMapping("/reorder")
    public ResponseEntity<Void> reorderCategories(@RequestAttribute("user") User user, List<CategoryDTO> categoryDTOS){

        return ResponseEntity.ok().build();
    }

}
