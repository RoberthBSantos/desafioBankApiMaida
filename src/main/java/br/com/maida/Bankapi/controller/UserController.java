package br.com.maida.Bankapi.controller;

import br.com.maida.Bankapi.controller.dto.ErrorDTO;
import br.com.maida.Bankapi.controller.dto.UserDTO;
import br.com.maida.Bankapi.controller.form.UserForm;
import br.com.maida.Bankapi.models.User;
import br.com.maida.Bankapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<?> create(@RequestBody @Valid UserForm form, UriComponentsBuilder uriComponentsBuilder){
        Optional<User> userExists = Optional.ofNullable(userRepository.findByEmail(form.getEmail()));
        if (userExists.isPresent()){
            return ResponseEntity.badRequest().body(new ErrorDTO("Já existe um usuário com o email informado."));
        }

        User user = form.converter(userRepository);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        URI uri = uriComponentsBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(new UserDTO(user));
    }
}
