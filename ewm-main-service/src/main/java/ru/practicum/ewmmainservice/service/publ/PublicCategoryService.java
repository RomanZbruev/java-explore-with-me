package ru.practicum.ewmmainservice.service.publ;

import ru.practicum.ewmmainservice.model.Dto.category.CategoryDto;

import java.util.List;

public interface PublicCategoryService {

    List<CategoryDto> getCategoryPublic(Integer from, Integer size);

    CategoryDto getCategoryByIdPublic(Integer catId);
}
