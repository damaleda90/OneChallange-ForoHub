package com.danielara.ForoHub.models.topic.validaciones;

import com.danielara.ForoHub.domain.ValidationException;
import com.danielara.ForoHub.models.topic.TopicCancelamientoDTO;
import com.danielara.ForoHub.models.topic.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component("ValidadorDeTopicCancelamiento")
public class ValidadorCancelamientoTopic implements ValidadorCancelamientoDeTopic{
    @Autowired
    private TopicRepository topicRepository;

    @Override
    public void validar(TopicCancelamientoDTO datos) {
        if (!topicRepository.existsById(datos.id())) {
            throw new ValidationException("El tópico con ID " + datos.id() + " no fue encontrado");
        }
    }
}
