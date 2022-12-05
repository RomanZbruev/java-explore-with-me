package ru.practicum.ewmmainservice.service.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmmainservice.exception.NotFoundException;
import ru.practicum.ewmmainservice.model.Category;
import ru.practicum.ewmmainservice.model.Dto.category.CategoryDto;
import ru.practicum.ewmmainservice.model.Dto.category.NewCategoryDto;
import ru.practicum.ewmmainservice.model.mapper.CategoryMapper;
import ru.practicum.ewmmainservice.repository.CategoryRepository;

@Service
@Slf4j
public class AdminCategoryServiceImpl implements AdminCategoryService {

    private final CategoryRepository categoryRepository;

    public AdminCategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    @Override
    public CategoryDto updateCategoryAdmin(CategoryDto categoryDto) {
        Category category = categoryRepository.findCategoryById(categoryDto.getId());
        if (category == null) {
            throw new NotFoundException("Невозможно обновить категорию. Категории с айди "
                    + categoryDto.getId() + " не существует");
        } else {
            category.setName(categoryDto.getName());
            log.info("Обновляем название категории");
        }
        Category newCategory = categoryRepository.save(category);
        return CategoryMapper.fromCategory(newCategory);
    }

    @Transactional
    @Override
    public CategoryDto postCategoryAdmin(NewCategoryDto categoryDto) {
        Category category = CategoryMapper.fromNewCategoryDto(categoryDto);
        Category newCategory = categoryRepository.save(category);
        log.info("Новая категория добавлена в хранилище");
        return CategoryMapper.fromCategory(newCategory);
    }

    @Transactional
    @Override
    public void deleteCategoryAdmin(Integer categoryId) {
        Category category = categoryRepository.findCategoryById(categoryId);
        if (category == null) {
            throw new NotFoundException("Невозможно обновить категорию. Категории с айди "
                    + categoryId + " не существует");
        } else {
            log.info("Удаление категории");
            categoryRepository.deleteById(categoryId);
        }
    }
}
