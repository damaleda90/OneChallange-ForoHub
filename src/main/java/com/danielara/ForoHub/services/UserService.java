package com.danielara.ForoHub.services;


import com.danielara.ForoHub.models.Profile;
import com.danielara.ForoHub.repository.ProfileRepository;
import com.danielara.ForoHub.models.user.User;
import com.danielara.ForoHub.models.user.UserDetailsDTO;
import com.danielara.ForoHub.models.user.UserFormDTO;
import com.danielara.ForoHub.models.user.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProfileRepository perfilRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public List<UserDetailsDTO> listarTodos() {
        return userRepository.findAll()
                .stream()
                .map(usuario -> new UserDetailsDTO(usuario))
                .collect(Collectors.toList());
    }

    @Transactional
    public User post(UserFormDTO datos) {
        if (userRepository.existsByLogin(datos.login())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El login ya está registrado");
        }

        Profile perfil = perfilRepository.findByNombre("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Profile 'ROLE_USER' no encontrado"));


        User user = new User(null, datos.name(), datos.login(), passwordEncoder.encode(datos.password()), Set.of(perfil));
        return userRepository.save(user);
    }

    @Transactional
    public User UpdateUser(long id, UserFormDTO usuario) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
        user.setName(usuario.name());
        user.setLogin(usuario.login());
        user.setPassword(passwordEncoder.encode(usuario.password()));
        userRepository.save(user);
        return user;
    }

    @Transactional
    public boolean deleteUser(Long id){
        if(userRepository.existsById(id)){
            userRepository.deleteById(id);
            return true;
        }else{
            log.info("Falla en la eliminación del usuario");
            return false;
        }
    }


}
