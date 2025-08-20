package com.danielara.ForoHub.repository;

import com.danielara.ForoHub.models.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByNombre(String nombre);
}
