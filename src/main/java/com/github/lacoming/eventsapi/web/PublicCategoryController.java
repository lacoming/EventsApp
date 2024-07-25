package com.github.lacoming.eventsapi.web;

import com.github.lacoming.eventsapi.mapper.CategoryMapper;
import com.github.lacoming.eventsapi.service.CategoryService;
import com.github.lacoming.eventsapi.web.dto.CategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/public/category")
@RequiredArgsConstructor
public class PublicCategoryController {

    private final CategoryService categoryService;

    private final CategoryMapper categoryMapper;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getCategories() {
        return ResponseEntity.ok(categoryMapper.toDtoList(categoryService.findAll()));
    }

}
