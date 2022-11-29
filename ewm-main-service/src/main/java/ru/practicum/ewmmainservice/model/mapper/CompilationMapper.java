package ru.practicum.ewmmainservice.model.mapper;


import org.springframework.stereotype.Component;
import ru.practicum.ewmmainservice.model.Compilation;
import ru.practicum.ewmmainservice.model.Dto.compilation.CompilationDto;
import ru.practicum.ewmmainservice.model.Dto.compilation.NewCompilationDto;

import java.util.ArrayList;

@Component
public class CompilationMapper {

    public static Compilation fromNewCompilationDto(NewCompilationDto newCompilationDto) {
        return Compilation.builder()
                .title(newCompilationDto.getTitle())
                .pinned(newCompilationDto.getPinned())
                .build();
    }

    public static CompilationDto fromCompilationToDto(Compilation compilation) {
        return CompilationDto.builder()
                .id(compilation.getId())
                .pinned(compilation.getPinned())
                .title(compilation.getTitle())
                .events(new ArrayList<>(EventMapper.fromEventToShortDto(compilation.getEvents())))
                .build();
    }

}
