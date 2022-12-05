package ru.practicum.ewmmainservice.service.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmmainservice.exception.NotFoundException;
import ru.practicum.ewmmainservice.model.Compilation;
import ru.practicum.ewmmainservice.model.Dto.compilation.CompilationDto;
import ru.practicum.ewmmainservice.model.Dto.compilation.NewCompilationDto;
import ru.practicum.ewmmainservice.model.Event;
import ru.practicum.ewmmainservice.model.mapper.CompilationMapper;
import ru.practicum.ewmmainservice.repository.CompilationRepository;
import ru.practicum.ewmmainservice.repository.EventRepository;

import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class AdminCompilationServiceImpl implements AdminCompilationService {

    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;

    public AdminCompilationServiceImpl(CompilationRepository compilationRepository, EventRepository eventRepository) {
        this.compilationRepository = compilationRepository;
        this.eventRepository = eventRepository;
    }

    @Transactional
    @Override
    public CompilationDto postCompilationAdmin(NewCompilationDto compilationDto) {
        Compilation compilation = CompilationMapper.fromNewCompilationDto(compilationDto);
        List<Integer> eventIdList = compilationDto.getEvents();
        Set<Event> events = eventRepository.findEventsByIds(eventIdList);
        compilation.setEvents(events);
        Compilation inBaseCompilation = compilationRepository.save(compilation);
        log.info("Подборка добавлена");
        return CompilationMapper.fromCompilationToDto(inBaseCompilation);
    }

    @Transactional
    @Override
    public void removeCompilationByIdAdmin(Integer compId) {
        Compilation compilation = compilationRepository.findCompilationById(compId);
        if (compilation == null) {
            log.error("Подборки с айди {} не найдено", compId);
            throw new NotFoundException("Подборки с айди " + compId + " не найдено");
        } else {
            compilationRepository.deleteById(compId);
            log.info("Подборка с айди {} удалена", compId);
        }
    }

    @Transactional
    @Override
    public void removeEventFromCompilationById(Integer compId, Integer eventId) {
        Event event = eventRepository.findEventById(eventId);
        Compilation compilation = compilationRepository.findCompilationById(compId);
        if (event == null) {
            log.error("События с айди {} не найдено", eventId);
            throw new NotFoundException("Подборки с айди " + eventId + " не найдено");
        }
        if (compilation == null) {
            log.error("Подборки с айди {} не найдено", compId);
            throw new NotFoundException("Подборки с айди " + compId + " не найдено");
        }
        Set<Event> events = compilation.getEvents();
        events.remove(event);
        compilation.setEvents(events);
        compilationRepository.save(compilation);
        log.info("Событие с айди = {} удалено из подборки с айди = {}", eventId, compId);
    }

    @Transactional
    @Override
    public void addEventInCompilationById(Integer compId, Integer eventId) {
        Event event = eventRepository.findEventById(eventId);
        Compilation compilation = compilationRepository.findCompilationById(compId);
        if (event == null) {
            log.error("События с айди {} не найдено", eventId);
            throw new NotFoundException("Подборки с айди " + eventId + " не найдено");
        }
        if (compilation == null) {
            log.error("Подборки с айди {} не найдено", compId);
            throw new NotFoundException("Подборки с айди " + compId + " не найдено");
        }
        Set<Event> events = compilation.getEvents();
        events.add(event);
        compilation.setEvents(events);
        compilationRepository.save(compilation);
        log.info("Событие с айди = {} добавлено в подборку с айди = {}", eventId, compId);
    }

    @Transactional
    @Override
    public void removeCompilationFromMainPage(Integer compId) {
        Compilation compilation = compilationRepository.findCompilationById(compId);
        if (compilation == null) {
            log.error("Подборки с айди {} не найдено", compId);
            throw new NotFoundException("Подборки с айди " + compId + " не найдено");
        } else {
            compilation.setPinned(false);
            compilationRepository.save(compilation);
            log.info("Подборка с айди = {} удалена с главной страницы", compId);
        }
    }
    @Transactional
    @Override
    public void addCompilationOnMainPage(Integer compId) {
        Compilation compilation = compilationRepository.findCompilationById(compId);
        if (compilation == null) {
            log.error("Подборки с айди {} не найдено", compId);
            throw new NotFoundException("Подборки с айди " + compId + " не найдено");
        } else {
            compilation.setPinned(true);
            compilationRepository.save(compilation);
            log.info("Подборка с айди = {} добавлена на главную страницу", compId);
        }
    }
}
