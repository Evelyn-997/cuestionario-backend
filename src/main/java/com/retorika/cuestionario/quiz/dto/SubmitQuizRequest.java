package com.retorika.cuestionario.quiz.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

import java.util.List;

@Builder
public record SubmitQuizRequest (
        @NotEmpty @Valid
        List<SubmitAnswerRequest> answers
){ }
