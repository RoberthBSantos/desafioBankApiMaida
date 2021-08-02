package br.com.maida.Bankapi.repository;

import br.com.maida.Bankapi.models.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferRepository extends JpaRepository<Transfer,Long> {
}
