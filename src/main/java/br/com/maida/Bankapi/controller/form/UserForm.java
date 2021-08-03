package br.com.maida.Bankapi.controller.form;

import br.com.maida.Bankapi.models.User;
import br.com.maida.Bankapi.repository.UserRepository;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserForm {
    @NotEmpty @NotNull
    private String name;
    @NotEmpty @NotNull @Email
    private String email;
    @NotEmpty @NotNull @Length(min = 6)
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User converter(UserRepository userRepository){
        return new User(name,email,password);
    }
}
