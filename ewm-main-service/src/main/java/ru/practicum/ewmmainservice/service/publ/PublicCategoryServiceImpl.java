package ru.practicum.ewmmainservice.service.publ;


import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewmmainservice.exception.NotFoundException;
import ru.practicum.ewmmainservice.model.Category;
import ru.practicum.ewmmainservice.model.Dto.category.CategoryDto;
import ru.practicum.ewmmainservice.model.mapper.CategoryMapper;
import ru.practicum.ewmmainservice.repository.CategoryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PublicCategoryServiceImpl implements PublicCategoryService {

    private final CategoryRepository categoryRepository;

    public PublicCategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryDto> getCategoryPublic(Integer from, Integer size) {
        int fromPage = from / size;
        Pageable pageable = PageRequest.of(fromPage, size);
        List<Category> category = categoryRepository.findAll(pageable).getContent();
        log.info("Получен список категорий");
        return category.stream()
                .map(CategoryMapper::fromCategory)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto getCategoryByIdPublic(Integer catId) {
        Category category = categoryRepository.findCategoryById(catId);
        if (category == null) {
            throw new NotFoundException("Категория с айди = " + catId + " не найдено");
        } else {
            return CategoryMapper.fromCategory(category);
        }
    }
}
