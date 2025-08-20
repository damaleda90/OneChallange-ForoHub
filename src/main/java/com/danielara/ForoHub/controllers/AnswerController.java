package com.danielara.ForoHub.controllers;

import static com.danielara.ForoHub.config.ApiConstans.*;
import com.danielara.ForoHub.models.answer.Answer;
import com.danielara.ForoHub.models.answer.AnswerDetailsDTO;
import com.danielara.ForoHub.models.answer.AnswerFormDTO;
import com.danielara.ForoHub.services.AnswerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;



@RestController
@RequestMapping(ANSWER_PATH)
@SecurityRequirement(name = "bearer-key")
public class AnswerController {
    @Autowired
    AnswerService answerService;

    @Operation(summary = "Registra una nueva respuesta")
    @PostMapping
    public ResponseEntity<AnswerDetailsDTO> postAnswer(@RequestBody @Valid AnswerFormDTO datos, UriComponentsBuilder uriBuilder) {
        Answer answer = answerService.postAnswer(datos, uriBuilder);
        URI uri = uriBuilder.path("/respuestas/{id}").buildAndExpand(answer.getId()).toUri();
        return ResponseEntity.created(uri).body(new AnswerDetailsDTO(answer));
    }

    @Operation(summary = "Consulta todas las respuestas")
    @GetMapping
    public ResponseEntity<List<AnswerDetailsDTO>> getAnswers() {
        List<AnswerDetailsDTO> response = answerService.getAnswersAll();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Actualiza información de una respuesta")
    @PutMapping("/{id}")
    public ResponseEntity<AnswerDetailsDTO> updateAnswer(@PathVariable Long id, @RequestBody @Valid AnswerFormDTO datos) {
        AnswerDetailsDTO response = answerService.updateAnswer(id, datos);

        if (response == null || response.id() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Respuesta no encontrada");
        }
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Elimina una respuesta por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnswer(@PathVariable Long id) {
        Boolean response = answerService.deleteAnswer(id);
        if (!response) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Respuesta no encontrada");
        }
        return ResponseEntity.noContent().build();
    }
}

