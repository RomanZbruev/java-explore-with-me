package ru.practicum.ewmmainservice.service.admin;

import ru.practicum.ewmmainservice.model.Dto.compilation.CompilationDto;
import ru.practicum.ewmmainservice.model.Dto.compilation.NewCompilationDto;

public interface AdminCompilationService {
    CompilationDto postCompilationAdmin(NewCompilationDto compilationDto);

    void removeCompilationByIdAdmin(Integer compId);

    void removeEventFromCompilationById(Integer compId, Integer eventId);

    void addEventInCompilationById(Integer compId, Integer eventId);

    void removeCompilationFromMainPage(Integer compId);

    void addCompilationOnMainPage(Integer compId);
}
