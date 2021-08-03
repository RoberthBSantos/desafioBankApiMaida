package br.com.maida.Bankapi.controller;

import br.com.maida.Bankapi.controller.dto.ErrorDTO;
import br.com.maida.Bankapi.controller.dto.TokenDTO;
import br.com.maida.Bankapi.controller.form.LoginForm;
import br.com.maida.Bankapi.models.User;
import br.com.maida.Bankapi.repository.UserRepository;
import br.com.maida.Bankapi.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    TokenService tokenService;

    @Autowired
    UserRepository userRepository;

    @PostMapping
    public ResponseEntity<?> auth(@RequestBody @Valid LoginForm form) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        UsernamePasswordAuthenticationToken login = form.convert();
        try {
            Authentication authentication = authenticationManager.authenticate(login);
            Optional<User> user = Optional.ofNullable(userRepository.findByEmail(form.getEmail()));
            String token = tokenService.gerarToken(authentication);

            return ResponseEntity.ok(new TokenDTO(user.get().getName(), form.getEmail(), token));

        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().body(new ErrorDTO("Usuário ou senha inválidos"));
        }
    }
}
