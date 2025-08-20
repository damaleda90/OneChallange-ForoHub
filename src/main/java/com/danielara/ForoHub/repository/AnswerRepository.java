package com.danielara.ForoHub.repository;

import com.danielara.ForoHub.models.answer.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface AnswerRepository extends JpaRepository<Answer, Long> {

    List<Answer> findByTopicId(Long topicoId);

    List<Answer> findByAuthorId(Long autorId);

    List<Answer> findByTopicIdOrderByCreatedAtDesc(Long topicoId);
}

