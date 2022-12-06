package ru.practicum.ewmmainservice.controller.publ;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmmainservice.model.Dto.category.CategoryDto;
import ru.practicum.ewmmainservice.service.publ.PublicCategoryService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@Slf4j
@RequestMapping(path = "/categories")
public class PublicCategoryController {

    private final PublicCategoryService publicCategoryService;

    public PublicCategoryController(PublicCategoryService publicCategoryService) {
        this.publicCategoryService = publicCategoryService;
    }

    @GetMapping
    public List<CategoryDto> getCategoriesPublic(@RequestParam(required = false, defaultValue = "0")
                                                 @PositiveOrZero int from,
                                                 @RequestParam(required = false, defaultValue = "10")
                                                 @Positive int size) {
        log.info("Получен публичный запрос на получение списка категорий");
        return publicCategoryService.getCategoryPublic(from, size);
    }

    @GetMapping(path = "/{catId}")
    public CategoryDto getCategoryByIdPublic(@PathVariable Integer catId) {
        log.info("Получен публичный запрос на получение категории с айди = {}", catId);
        return publicCategoryService.getCategoryByIdPublic(catId);
    }
}
