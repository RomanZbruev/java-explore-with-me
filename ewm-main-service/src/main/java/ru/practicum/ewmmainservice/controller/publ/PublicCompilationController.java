package ru.practicum.ewmmainservice.controller.publ;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmmainservice.model.Dto.compilation.CompilationDto;
import ru.practicum.ewmmainservice.service.publ.PublicCompilationService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@Slf4j
@RequestMapping(path = "/compilations")
public class PublicCompilationController {

    private final PublicCompilationService publicCompilationService;

    public PublicCompilationController(PublicCompilationService publicCompilationService) {
        this.publicCompilationService = publicCompilationService;
    }

    @GetMapping
    public List<CompilationDto> getPublicCompilations(@RequestParam(required = false, defaultValue = "false") Boolean pinned,
                                                      @RequestParam(required = false, defaultValue = "0")
                                                      @PositiveOrZero Integer from,
                                                      @RequestParam(required = false, defaultValue = "10")
                                                      @Positive Integer size) {
        log.info("Получен публичный запрос на просмотр подборок");
        return publicCompilationService.getPublicCompilation(pinned, from, size);
    }

    @GetMapping(path = "/{compId}")
    public CompilationDto getPublicCompilationById(@PathVariable Integer compId) {
        log.info("Получен публичный запрос на просмотр подборки с айди = {}", compId);
        return publicCompilationService.getCompilationById(compId);
    }
}
