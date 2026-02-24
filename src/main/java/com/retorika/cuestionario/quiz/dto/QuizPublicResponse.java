package com.retorika.cuestionario.quiz.dto;

import lombok.Builder;
import java.util.List;


@Builder
public record QuizPublicResponse(
        String quizSlug,
        String title,
        String description,
        int totalQuestions,
        List<QuestionResponse> questions
) { }
