package com.danielara.ForoHub.controllers;
import static com.danielara.ForoHub.config.ApiConstans.*;

import com.danielara.ForoHub.models.user.*;
import com.danielara.ForoHub.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(USERS_PATH)
@SecurityRequirement(name = "bearer-key")
public class UsersController {

    @Autowired
    UserService usuarioService;

    @Operation(summary = "Registra un nuevo usuario")
    @PostMapping
    public ResponseEntity<UserDetailsDTO> postUser(@RequestBody @Valid UserFormDTO datos, UriComponentsBuilder uriBuilder) {
        User user = usuarioService.post(datos);

        URI uri = uriBuilder.path("/usuario/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(new UserDetailsDTO(user));
    }

    @Operation(summary = "Muestra todos los usuarios")
    @GetMapping
    public ResponseEntity<List<UserDetailsDTO>> getAUsers() {
        List<UserDetailsDTO> usuarios = usuarioService.listarTodos();
        return ResponseEntity.ok(usuarios);
    }

    @Operation(summary = "Actualiza un usuario por ID")
    @PutMapping("/{id}")
    public ResponseEntity<UserDetailsDTO> updateUser(@PathVariable Long id, @RequestBody @Valid UserFormDTO request) {
        User user = usuarioService.UpdateUser(id, request);
        return ResponseEntity.ok(new UserDetailsDTO(user));
    }
    @Transactional
    @Operation(summary = "Elimina un usuario por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        boolean response = usuarioService.deleteUser(id);
        if (!response) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "usuario no encontrado");
        }
        return ResponseEntity.noContent().build();
    }
}

