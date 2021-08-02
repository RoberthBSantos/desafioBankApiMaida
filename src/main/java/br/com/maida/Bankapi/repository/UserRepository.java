package br.com.maida.Bankapi.repository;

import br.com.maida.Bankapi.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
}
