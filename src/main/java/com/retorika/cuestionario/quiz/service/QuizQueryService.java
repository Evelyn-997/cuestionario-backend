package com.retorika.cuestionario.quiz.service;

import com.retorika.cuestionario.common.exception.NotFoundException;
import com.retorika.cuestionario.quiz.domain.Option;
import com.retorika.cuestionario.quiz.domain.Question;
import com.retorika.cuestionario.quiz.domain.Quiz;
import com.retorika.cuestionario.quiz.dto.OptionResponse;
import com.retorika.cuestionario.quiz.dto.QuestionResponse;
import com.retorika.cuestionario.quiz.dto.QuizPublicResponse;
import com.retorika.cuestionario.quiz.repository.QuizRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuizQueryService {
    private final QuizRespository quizRespository;

    public QuizPublicResponse getPublicQuizBySlug(String slug){

        Quiz quiz = quizRespository.findWithQuestionsBySlugAndActiveTrue(slug)
                .orElseThrow(()-> new NotFoundException("Quiz no encontrado o inactivo: "+ slug));

        List<QuestionResponse> questions = quiz.getQuestions().stream()
                .collect(Collectors.toMap(
                        Question::getId,
                        q -> q,
                        (a,b) -> a,           // si viene duplicada, te quedas con la primera
                        LinkedHashMap::new))
                .values().stream()
                .sorted(Comparator.comparingInt(q -> q.getOrderIndex() == null ?0: q.getOrderIndex()))
                .map(q-> QuestionResponse.builder()
                        .questionId(q.getId())
                        .order(q.getOrderIndex())
                        .text(q.getText())
                        .options(
                                q.getOptions().stream()
                               .sorted(Comparator.comparingInt(o -> o.getOrderIndex() == null ?0: o.getOrderIndex()))
                                .map(o -> OptionResponse.builder()
                                        .optionId(o.getId())
                                        .text(o.getText())
                                        .order(Long.valueOf(o.getOrderIndex()))
                                        .build())
                                .toList())
                        .build())
                .toList();

        return QuizPublicResponse.builder()
                .quizSlug(quiz.getSlug())
                .title(quiz.getTitle())
                .description(quiz.getDescription())
                .totalQuestions(questions.size())
                .questions(questions)
                .build();
    }
}
