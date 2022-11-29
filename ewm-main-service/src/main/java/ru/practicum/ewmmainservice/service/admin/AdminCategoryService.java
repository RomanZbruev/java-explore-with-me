package ru.practicum.ewmmainservice.service.admin;

import ru.practicum.ewmmainservice.model.Dto.category.CategoryDto;
import ru.practicum.ewmmainservice.model.Dto.category.NewCategoryDto;

public interface AdminCategoryService {

    CategoryDto updateCategoryAdmin(CategoryDto categoryDto);

    CategoryDto postCategoryAdmin(NewCategoryDto categoryDto);

    void deleteCategoryAdmin(Integer categoryId);
}
