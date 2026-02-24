package com.retorika.cuestionario.quiz.dto;

import lombok.Builder;

@Builder
public record OptionResponse (
        Long optionId,
        String text,
        Long order
){ }
