package ru.practicum.ewmmainservice.service.publ;

import ru.practicum.ewmmainservice.model.Dto.compilation.CompilationDto;

import java.util.List;

public interface PublicCompilationService {

    List<CompilationDto> getPublicCompilation(Boolean pinned, Integer from, Integer size);

    CompilationDto getCompilationById(Integer compId);
}
