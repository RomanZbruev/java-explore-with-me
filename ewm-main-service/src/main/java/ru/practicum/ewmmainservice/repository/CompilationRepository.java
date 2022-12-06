package ru.practicum.ewmmainservice.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewmmainservice.model.Compilation;

import java.util.List;


@Repository
public interface CompilationRepository extends JpaRepository<Compilation, Integer> {

    Compilation findCompilationById(Integer id);

    List<Compilation> findCompilationByPinnedEquals(Boolean pinned, Pageable pageable);

}
