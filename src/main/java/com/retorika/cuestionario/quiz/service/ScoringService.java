package com.retorika.cuestionario.quiz.service;

import com.retorika.cuestionario.common.util.PercentageUtil;
import com.retorika.cuestionario.quiz.domain.StyleType;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.Map;

@Service
public class ScoringService {

    public Map<StyleType, Integer> score(Map<StyleType, Integer> accumulator, StyleType style, int weight) {
        accumulator.put(style, accumulator.getOrDefault(style, 0) + weight);
        return accumulator;
    }

    public Map<StyleType, Double> toPercentages(Map<StyleType, Integer> points, int totalQuestions) {
        Map<StyleType, Double> pct = new EnumMap<>(StyleType.class);
        for (StyleType st : StyleType.values()) {
            int value = points.getOrDefault(st, 0);
            double percent = (totalQuestions == 0) ? 0.0 : (value * 100.0) / totalQuestions;
            pct.put(st, PercentageUtil.round(percent, 1));
        }
        // Si quieres forzar suma 100.0 exacta, se puede ajustar aquí.
        return pct;
    }

    public StyleType dominantStyle(Map<StyleType, Double> pct) {
        return pct.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElseThrow()
                .getKey();
    }
}
