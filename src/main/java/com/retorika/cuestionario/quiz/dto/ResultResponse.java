package com.retorika.cuestionario.quiz.dto;

import com.retorika.cuestionario.quiz.domain.StyleType;
import lombok.Builder;

import java.util.List;
import java.util.Map;

@Builder
public record  ResultResponse (
        Long submissionId,
        Map<StyleType,Double> percentages,
        StyleType dominantStyle,
        List<InfoBlockResponse> infoBlocks
){}
