package com.retorika.cuestionario.quiz.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Builder
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@Table(name="quizzes")
public class Quiz {
    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String slug; // ej: "estilos-comunicacion"

    @Column(nullable = false)
    private String title;

    @Column(length = 2000)
    private String description;

    @Builder.Default
    private boolean active = true;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("orderIndex ASC")
    @Builder.Default
    //private List<Question> questions = new ArrayList<>();
    private Set<Question> questions = new LinkedHashSet<>();
}
