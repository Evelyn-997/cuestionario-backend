package com.retorika.cuestionario.quiz.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name="submissions")
public class Submission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @CreationTimestamp
    private Instant createdAt;

    @OneToMany(mappedBy = "submission", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<SubmissionAnswer> answers = new ArrayList<>();

    // snapshot del resultado (opcional pero útil para “ver resultados por enlace”)
    private Double pctAgresivo;
    private Double pctAsertivo;
    private Double pctPasivo;

    @Enumerated(EnumType.STRING)
    private StyleType dominantStyle;
}
