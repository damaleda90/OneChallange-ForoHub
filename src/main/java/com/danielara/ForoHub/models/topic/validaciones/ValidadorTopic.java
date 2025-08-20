package com.danielara.ForoHub.models.topic.validaciones;

import com.danielara.ForoHub.domain.ValidationException;
import com.danielara.ForoHub.models.topic.TopicFormDTO;
import com.danielara.ForoHub.models.topic.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component("ValidadorDeTopicReserva")
public class ValidadorTopic implements ValidadorDeTopic{
    @Autowired
    private TopicRepository topicRepository;

    @Override
    public void validar(TopicFormDTO datos) {
        if (datos.title() == null || datos.message() == null){
            return;
        }

        if (topicRepository.existsByTitleAndMessage(datos.title(), datos.message())){
            throw new ValidationException("El tópico " + datos.title() + " ya existe");
        }
    }
}
