package com.danielara.ForoHub.models.topic;

import com.danielara.ForoHub.models.Course;
import com.danielara.ForoHub.repository.CourseRepository;
import com.danielara.ForoHub.models.user.User;
import com.danielara.ForoHub.models.user.UserRepository;
import jakarta.validation.constraints.NotBlank;

public record TopicFormDTO (
        @NotBlank String title,
        @NotBlank String message,
        @NotBlank String authorName,
        @NotBlank String courseName){
    public Topic convert(CourseRepository courseRepository, UserRepository userRepository) {
        User author = userRepository.findByName(authorName).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Course course = courseRepository.findByName(courseName).orElseThrow(() -> new IllegalArgumentException("Course not found"));
        return new Topic(title, message, course, author);
    }
}
