package com.retorika.cuestionario.quiz.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@NoArgsConstructor  @AllArgsConstructor
@Getter  @Setter
@Table(name="options")
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    @Column(nullable = false, length = 1000)
    private String text;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StyleType styleType;

    @Column(nullable = false)
    @Builder.Default
    private Integer weight = 1; // normalmente 1
    @Column(name = "order_index", nullable = false)
    private Integer orderIndex;


}
