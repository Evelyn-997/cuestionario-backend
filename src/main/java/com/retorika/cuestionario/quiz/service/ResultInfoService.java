package com.retorika.cuestionario.quiz.service;

import com.retorika.cuestionario.quiz.domain.StyleType;
import com.retorika.cuestionario.quiz.dto.InfoBlockResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResultInfoService {
    public List<InfoBlockResponse>  buildInfoBlocks(StyleType dominant) {
        return switch (dominant) {
            case ASERTIVO -> List.of(
                    InfoBlockResponse.builder().title("Perfil principal: Asertivo").text("Comunicas con claridad, respeto y equilibrio. Mantienes límites sin agresividad.").build(),
                    InfoBlockResponse.builder().title("Recomendación").text("Sigue practicando escucha activa y mensajes en primera persona (“yo siento / yo necesito”).").build()
            );
            case AGRESIVO -> List.of(
                    InfoBlockResponse.builder().title("Perfil principal: Agresivo").text("Tiendes a imponer tu punto de vista. Puede percibirse como falta de empatía o tensión.").build(),
                    InfoBlockResponse.builder().title("Recomendación").text("Practica pausas antes de responder y reformula en términos de necesidades, no de ataques." +
                            "").build()
            );
            case PASIVO -> List.of(
                    InfoBlockResponse.builder().title("Perfil principal: Pasivo").text("Evitas el conflicto y priorizas agradar, a costa de tus necesidades.").build(),
                    InfoBlockResponse.builder().title("Recomendación").text("Entrena frases breves de límites (“Ahora no puedo”, “Prefiero…”). Empieza por situaciones pequeñas.").build()
            );
        };
    }

}
