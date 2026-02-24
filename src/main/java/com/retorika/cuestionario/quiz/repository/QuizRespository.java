package com.retorika.cuestionario.quiz.repository;

import com.retorika.cuestionario.quiz.domain.Quiz;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface QuizRespository extends JpaRepository<Quiz, Long> {
    Optional<Quiz> findBySlugAndActiveTrue(String slug);

   // @EntityGraph(attributePaths = {"questions","questions.options"})
   @Query("""
      select distinct q
      from Quiz q
      left join fetch q.questions qu
      left join fetch qu.options o
      where q.slug = :slug and q.active = true
    """)
   Optional<Quiz> findWithQuestionsBySlugAndActiveTrue( @Param("slug") String slug);

}
