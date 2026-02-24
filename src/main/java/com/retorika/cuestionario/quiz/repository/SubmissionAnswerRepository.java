package com.retorika.cuestionario.quiz.repository;

import com.retorika.cuestionario.quiz.domain.SubmissionAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubmissionAnswerRepository extends JpaRepository<SubmissionAnswer, Long> {
}
