package ru.practicum.ewmmainservice.service.publ;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewmmainservice.exception.NotFoundException;
import ru.practicum.ewmmainservice.model.Compilation;
import ru.practicum.ewmmainservice.model.Dto.compilation.CompilationDto;
import ru.practicum.ewmmainservice.model.mapper.CompilationMapper;
import ru.practicum.ewmmainservice.repository.CompilationRepository;


import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PublicCompilationServiceImpl implements PublicCompilationService {

    private final CompilationRepository compilationRepository;

    public PublicCompilationServiceImpl(CompilationRepository compilationRepository) {
        this.compilationRepository = compilationRepository;
    }

    @Override
    public List<CompilationDto> getPublicCompilation(Boolean pinned, Integer from, Integer size) {
        int fromPage = from / size;
        Pageable pageable = PageRequest.of(fromPage, size);
        List<Compilation> compilations = compilationRepository.findCompilationByPinnedEquals(pinned, pageable);
        log.info("Получен список подборок по условиям: pinned = {} , from = {} , size = {}", pinned,
                from, size);
        return compilations.stream()
                .map(CompilationMapper::fromCompilationToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CompilationDto getCompilationById(Integer compId) {
        Compilation compilation = compilationRepository.findCompilationById(compId);
        if (compilation == null) {
            log.info("Подборка с айди = {} не найдена", compId);
            throw new NotFoundException("Подборка с айди = " + compId + " не найдена");
        } else {
            return CompilationMapper.fromCompilationToDto(compilation);
        }
    }
}
