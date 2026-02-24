package com.retorika.cuestionario.quiz.repository;

import com.retorika.cuestionario.quiz.domain.Option;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionRespository extends JpaRepository<Option, Long> {
    Option findById(long id);
}
