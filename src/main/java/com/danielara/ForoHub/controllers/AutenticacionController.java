package com.danielara.ForoHub.controllers;
import static com.danielara.ForoHub.config.ApiConstans.*;
import com.danielara.ForoHub.config.security.DataJWTToken;
import com.danielara.ForoHub.models.user.User;
import com.danielara.ForoHub.config.security.TokenService;
import com.danielara.ForoHub.models.user.UserAuthenticationData;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(LOGIN_PATH)
public class AutenticacionController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Operation(summary = "Autentificacion del usuario")
    @PostMapping
    public ResponseEntity userLogin(@RequestBody @Valid @NotNull UserAuthenticationData userAuthenticationData) {
        //log.info("Datos de autenticación recibidos: {}", userAuthenticationData);
        try {
            var authenticationToken = new UsernamePasswordAuthenticationToken(
                    userAuthenticationData.login(),
                    userAuthenticationData.password()
            );

            var authenticatedUser = authenticationManager.authenticate(authenticationToken);
            System.out.println(authenticatedUser.getPrincipal());
            var JWTtoken = tokenService.generateToken((User) authenticatedUser.getPrincipal());
            return ResponseEntity.ok(new DataJWTToken(JWTtoken));

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid Credentials");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing the request");

        }
    }
}
