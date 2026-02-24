package com.retorika.cuestionario.quiz.service;


import com.retorika.cuestionario.common.exception.BadRequestException;
import com.retorika.cuestionario.quiz.domain.*;
import com.retorika.cuestionario.quiz.dto.ResultResponse;
import com.retorika.cuestionario.quiz.dto.SubmitQuizRequest;
import com.retorika.cuestionario.quiz.repository.OptionRespository;
import com.retorika.cuestionario.quiz.repository.QuizRespository;
import com.retorika.cuestionario.quiz.repository.SubmissionAnswerRepository;
import com.retorika.cuestionario.quiz.repository.SubmissionRepository;

import lombok.RequiredArgsConstructor;
//import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class QuizSubmissionService {
    private final QuizRespository quizRespository;
    private final OptionRespository optionRespository;
    private final SubmissionRepository submissionRepository;
    private final SubmissionAnswerRepository submissionAnswerRepository;

    private final ScoringService scoringService;
    private final ResultInfoService resultInfoService;

    @Transactional
    public ResultResponse submit(String quizSlug, SubmitQuizRequest request){
        Quiz quiz = quizRespository.findWithQuestionsBySlugAndActiveTrue(quizSlug)
                .orElseThrow(() -> new IllegalArgumentException("Quiz no encontrado o inactivo: " + quizSlug));

        int totalQuestions = quiz.getQuestions().size();
        if(request.answers().size() != totalQuestions){
            throw new IllegalArgumentException("Debes responder  " + totalQuestions+ " preguntas. Recibidas: "+ request.answers().size());
        }
        //Validar duplicados de questionId
        Set<Long> seenQuestions = new HashSet<>();
        request.answers().forEach(answer -> {
            if(!seenQuestions.add(answer.questionId())){
                throw new BadRequestException("Pregunta ducplicada: questionId = "+answer.questionId());
            }
        });

        Submission submission = Submission.builder()
                .quiz(quiz)
                .build();
        submission = submissionRepository.save(submission);

        Map<StyleType, Integer> points = new EnumMap<>(StyleType.class);

        for(var ans: request.answers()){
            Option option = optionRespository.findById(ans.optionId())
                    .orElseThrow();

            // Validar que esa opción pertenece al quiz (vía pregunta->quiz)
            Long optionQuizId = Long.valueOf(option.getQuestion().getQuiz().getId());
            if (!optionQuizId.equals(quiz.getId())) {
                throw new BadRequestException("La opción " + option.getId() + " no pertenece a este cuestionario.");
            }
            scoringService.score(points, option.getStyleType(), option.getWeight());

            SubmissionAnswer sa = SubmissionAnswer.builder()
                    .submission(submission)
                    .question(option.getQuestion())
                    .option(option)
                    .build();
            submissionAnswerRepository.save(sa);
        }

        Map<StyleType, Double> pct = scoringService.toPercentages(points, totalQuestions);
        StyleType dominant = scoringService.dominantStyle(pct);

        // guardar snapshot
        submission.setPctAgresivo(pct.getOrDefault(StyleType.AGRESIVO, 0.0));
        submission.setPctAsertivo(pct.getOrDefault(StyleType.ASERTIVO, 0.0));
        submission.setPctPasivo(pct.getOrDefault(StyleType.PASIVO, 0.0));
        submission.setDominantStyle(dominant);
        submissionRepository.save(submission);

        return ResultResponse.builder()
                .submissionId(submission.getId())
                .percentages(pct)
                .dominantStyle(dominant)
                .infoBlocks(resultInfoService.buildInfoBlocks(dominant))
                .build();
    }

}
