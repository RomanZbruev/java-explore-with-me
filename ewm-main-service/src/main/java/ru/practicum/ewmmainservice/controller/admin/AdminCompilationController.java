package ru.practicum.ewmmainservice.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmmainservice.model.Dto.compilation.CompilationDto;
import ru.practicum.ewmmainservice.model.Dto.compilation.NewCompilationDto;
import ru.practicum.ewmmainservice.service.admin.AdminCompilationService;

import javax.validation.Valid;

@RestController
@Slf4j
@Validated
@RequestMapping(path = "/admin/compilations")
public class AdminCompilationController {

    private final AdminCompilationService adminCompilationService;

    public AdminCompilationController(AdminCompilationService adminCompilationService) {
        this.adminCompilationService = adminCompilationService;
    }

    @PostMapping
    public CompilationDto postCompilationAdmin(@Valid @RequestBody NewCompilationDto compilationDto) {
        log.info("Получен запрос на создание подборки от админа");
        return adminCompilationService.postCompilationAdmin(compilationDto);
    }

    @DeleteMapping(path = "/{compId}")
    public void deleteCompilationByIdAdmin(@PathVariable Integer compId) {
        log.info("Получен запрос на удаление компиляции с айди: {} от админа", compId);
        adminCompilationService.removeCompilationByIdAdmin(compId);
    }

    @DeleteMapping(path = "/{compId}/events/{eventId}")
    public void deleteEventFromCompilationByIdAdmin(@PathVariable Integer compId, @PathVariable Integer eventId) {
        log.info("Получен запрос на удаление из компиляции с айди = {} события с айди = {}  от админа",
                compId, eventId);
        adminCompilationService.removeEventFromCompilationById(compId, eventId);
    }

    @PatchMapping(path = "/{compId}/events/{eventId}")
    public void addEventInCompilationByIdAdmin(@PathVariable Integer compId, @PathVariable Integer eventId) {
        log.info("Получен запрос на добавление в компиляцию с айди = {} события с айди = {}  от админа",
                compId, eventId);
        adminCompilationService.addEventInCompilationById(compId, eventId);
    }

    @DeleteMapping(path = "/{compId}/pin")
    public void deleteCompilationFromMainPage(@PathVariable Integer compId) {
        log.info("Получен запрос на открепление компиляции с айди: {} с главной страницы от админа", compId);
        adminCompilationService.removeCompilationFromMainPage(compId);
    }

    @PatchMapping(path = "/{compId}/pin")
    public void addCompilationOnMainPage(@PathVariable Integer compId) {
        log.info("Получен запрос на добавление компиляции с айди: {} на главную страницу от админа", compId);
        adminCompilationService.addCompilationOnMainPage(compId);
    }


}
