package br.com.maida.Bankapi.repository;

import br.com.maida.Bankapi.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByNumber(String source_account_number);

}
