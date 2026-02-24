package com.retorika.cuestionario.quiz.repository;

import com.retorika.cuestionario.quiz.domain.Submission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {
}
