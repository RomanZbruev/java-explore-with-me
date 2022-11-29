package ru.practicum.ewmmainservice.model.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.ewmmainservice.model.Category;
import ru.practicum.ewmmainservice.model.Dto.category.CategoryDto;
import ru.practicum.ewmmainservice.model.Dto.category.NewCategoryDto;

@Component
public class CategoryMapper {

    public static Category fromNewCategoryDto(NewCategoryDto newCategoryDto) {
        return Category.builder()
                .name(newCategoryDto.getName())
                .build();
    }

    public static Category fromCategoryDto(CategoryDto categoryDto) {
        return Category.builder()
                .id(categoryDto.getId())
                .name(categoryDto.getName())
                .build();
    }


    public static CategoryDto fromCategory(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
