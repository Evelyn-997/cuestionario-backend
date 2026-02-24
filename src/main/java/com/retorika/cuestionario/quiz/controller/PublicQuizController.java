package com.retorika.cuestionario.quiz.controller;

import com.retorika.cuestionario.quiz.dto.QuizPublicResponse;
import com.retorika.cuestionario.quiz.dto.ResultResponse;
import com.retorika.cuestionario.quiz.dto.SubmitQuizRequest;
import com.retorika.cuestionario.quiz.service.QuizQueryService;
import com.retorika.cuestionario.quiz.service.QuizSubmissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public/quizzes")
@RequiredArgsConstructor
public class PublicQuizController {
    private final QuizQueryService quizQueryService;
    private final QuizSubmissionService  quizSubmissionService;

    @GetMapping("/{slug}")
    public QuizPublicResponse getQuiz(@PathVariable String slug) {
        return quizQueryService.getPublicQuizBySlug(slug);
    }

    @PostMapping("/{slug}/submissions")
    public ResultResponse submit(@PathVariable String slug, @Valid @RequestBody SubmitQuizRequest request) {
        return quizSubmissionService.submit(slug, request);
    }

    @GetMapping("/id/{id}")
    public QuizPublicResponse getById(@PathVariable Integer id) {
        return quizQueryService.getPublicQuizBySlug(id.toString());
    }


}
