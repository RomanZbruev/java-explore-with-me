package ru.practicum.ewmmainservice.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmmainservice.model.Dto.category.CategoryDto;
import ru.practicum.ewmmainservice.model.Dto.category.NewCategoryDto;
import ru.practicum.ewmmainservice.service.admin.AdminCategoryService;

import javax.validation.Valid;

@RestController
@Slf4j
@Validated
@RequestMapping(path = "/admin/categories")
public class AdminCategoryController {

    private final AdminCategoryService adminCategoryService;

    public AdminCategoryController(AdminCategoryService adminCategoryService) {
        this.adminCategoryService = adminCategoryService;
    }

    @PatchMapping
    public CategoryDto updateCategoryAdmin(@Valid @RequestBody CategoryDto categoryDto) {
        log.info("Получен запрос на обновление категории от админа");
        return adminCategoryService.updateCategoryAdmin(categoryDto);
    }

    @PostMapping
    public CategoryDto postCategoryAdmin(@Valid @RequestBody NewCategoryDto categoryDto) {
        log.info("Получен запрос на добавление категории от админа");
        return adminCategoryService.postCategoryAdmin(categoryDto);
    }

    @DeleteMapping("/{catId}")
    public void deleteCategoryAdmin(@PathVariable Integer catId) {
        log.info("Получен запрос на удалении категории от админа");
        adminCategoryService.deleteCategoryAdmin(catId);
    }

}
