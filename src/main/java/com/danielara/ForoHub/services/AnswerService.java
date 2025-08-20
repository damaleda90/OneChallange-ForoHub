package com.danielara.ForoHub.services;

import com.danielara.ForoHub.models.answer.Answer;
import com.danielara.ForoHub.models.answer.AnswerDetailsDTO;
import com.danielara.ForoHub.models.answer.AnswerFormDTO;
import com.danielara.ForoHub.repository.AnswerRepository;
import com.danielara.ForoHub.models.topic.Topic;
import com.danielara.ForoHub.models.topic.TopicRepository;
import com.danielara.ForoHub.models.user.User;
import com.danielara.ForoHub.models.user.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.List;
@Slf4j
@Service
@AllArgsConstructor
public class AnswerService {
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TopicRepository topicRepository;

    @Transactional
    public Answer postAnswer(AnswerFormDTO datos, UriComponentsBuilder uriBuilder) {
        User usuario = userRepository.findById(datos.autorId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tópico no encontrado"));

        Topic topico = topicRepository.findById(datos.topicoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tópico no encontrado"));

        return answerRepository.save(new Answer(datos.mensaje(),topico,usuario));

    }
    @Transactional
    public List<AnswerDetailsDTO> getAnswersAll() {
        List<AnswerDetailsDTO> answersList = answerRepository.findAll()
                .stream()
                .map(AnswerDetailsDTO::new).toList();

        return answersList;
    }

   @Transactional
   public AnswerDetailsDTO updateAnswer(Long id, AnswerFormDTO datos){
            Answer answer = answerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Respuesta no encontrada"));
            answer.setMessage(datos.mensaje());
            answerRepository.save(answer);
            return new AnswerDetailsDTO(answer);
        }
   @Transactional
   public boolean deleteAnswer(Long id) {
            if (answerRepository.existsById(id)) {
                answerRepository.deleteById(id);
                return true;
            } else {
                log.info("Fallo la eliminacion del Answer");
                return false;
            }
        }
}
