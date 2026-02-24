package com.retorika.cuestionario.quiz.repository;

import com.retorika.cuestionario.quiz.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
