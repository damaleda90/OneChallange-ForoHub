package com.danielara.ForoHub.controllers;
import static com.danielara.ForoHub.config.ApiConstans.*;

import com.danielara.ForoHub.models.topic.*;
import com.danielara.ForoHub.services.TopicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(TOPICS_PATH)
@SecurityRequirement(name = "bearer-key")
public class TopicsController {

    @Autowired
    TopicService topicService;

    @Operation(summary = "Listar todos los topics con paginación y filtros opcionales")
    @GetMapping
    @CacheEvict(value = "topicsList")
    public ResponseEntity<Page<TopicDetailsDTO>> getTopics(@RequestParam(required = false) String courseName,
                                                      @RequestParam(required = false) Integer year,
                                                      @PageableDefault(sort = "creationDate", direction = Sort.Direction.ASC, page = 0, size = 10) Pageable pagination) {
        var response = topicService.list(courseName, year, pagination);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Registra nuevo topic")
    @PostMapping
    @CacheEvict(value = "topicsList", allEntries = true)
    public ResponseEntity<TopicDetailsDTO> createTopic(@RequestBody @Valid TopicFormDTO form, UriComponentsBuilder uriBuilder) {
        TopicDetailsDTO topicDTO = topicService.register(form);

        URI uri = uriBuilder.path("/topics/{id}").buildAndExpand(topicDTO.id()).toUri();
        return ResponseEntity.created(uri).body(topicDTO);
    }

    @Operation(summary = "Detalles de un topic por ID")
    @GetMapping("/{id}")
    public ResponseEntity<TopicDetailsDTO> getTopicDetail(@PathVariable Long id) {
        TopicDetailsDTO dto = topicService.getDetails(id); // Maneja lógica y errores
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "Actualiza un topic existente")
    @PutMapping("/{id}")
    public ResponseEntity<TopicDetailsDTO> updateTopic(@PathVariable Long id, @RequestBody @Valid TopicUpdateFormDTO form) {
        var response = topicService.update(form);
        return ResponseEntity.ok(new TopicDetailsDTO(response));
    }

    @Operation(summary = "Elimina un topic por ID")
    @DeleteMapping("/{id}")
    @CacheEvict(value = "topicsList", allEntries = true)
    public ResponseEntity<?> deleteTopic(@PathVariable @Valid TopicCancelamientoDTO datos) {
        topicService.delete(datos);
        return ResponseEntity.noContent().build();
    }
}
