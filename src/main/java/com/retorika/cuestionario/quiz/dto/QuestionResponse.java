package com.retorika.cuestionario.quiz.dto;

import lombok.Builder;

import java.util.List;
import java.util.Set;

@Builder
public record QuestionResponse (
        Long questionId,
        int order,
        String text,
        List<OptionResponse> options
){ }
