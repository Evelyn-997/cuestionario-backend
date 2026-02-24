package com.retorika.cuestionario.quiz.dto;

import lombok.Builder;

@Builder
public record InfoBlockResponse(
        String title,
        String text
) { }
