package br.com.maida.Bankapi.controller;

import br.com.maida.Bankapi.controller.dto.AccountDTO;
import br.com.maida.Bankapi.controller.dto.BalanceDTO;
import br.com.maida.Bankapi.controller.dto.ErrorDTO;
import br.com.maida.Bankapi.controller.dto.TransferDTO;
import br.com.maida.Bankapi.controller.form.AccountForm;
import br.com.maida.Bankapi.controller.form.BalanceForm;
import br.com.maida.Bankapi.controller.form.TransferForm;
import br.com.maida.Bankapi.models.Account;
import br.com.maida.Bankapi.models.Transfer;
import br.com.maida.Bankapi.models.User;
import br.com.maida.Bankapi.repository.AccountRepository;
import br.com.maida.Bankapi.repository.TransferRepository;
import br.com.maida.Bankapi.repository.UserRepository;
import br.com.maida.Bankapi.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private TransferRepository transferRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<?> create(@RequestBody @Valid AccountForm form, @RequestHeader("Authorization") String token, UriComponentsBuilder uriComponentsBuilder){
        Account accountExists = accountRepository.findByNumber(form.getNumber());
        if (accountExists == null) {
            User user = authUser(token);
            Account account = form.convert(accountRepository, user);
            accountRepository.save(account);

            URI uri = uriComponentsBuilder.path("/accounts/").buildAndExpand(account.getId()).toUri();
            return ResponseEntity.created(uri).body(new AccountDTO(account));
        }
        return ResponseEntity.badRequest().body(new ErrorDTO("Já existe uma conta com o número informado."));
    }

    @PostMapping("/transfer")
    @Transactional
    public ResponseEntity<?> Transfer(@RequestBody @Valid TransferForm form, @RequestHeader("Authorization") String token, UriComponentsBuilder uriComponentsBuilder){
        User user = authUser(token);
        Transfer transfer = form.convert(accountRepository,user);

        List<ErrorDTO> errors = new ArrayList<>();
        if(transfer.getSourceAccount()==null){
            errors.add(new ErrorDTO("Conta de origem não encontrada para o usuário informado"));
        }else if(transfer.getSourceAccount().getBalance().doubleValue() < form.getAmount().doubleValue()){
            errors.add(new ErrorDTO("Saldo insuficiente"));
        }
        if(transfer.getDestinationAccount()==null){
            errors.add(new ErrorDTO("Conta de destino informada não foi encontrada"));
        }

        if(errors.size()>0){
            return ResponseEntity.badRequest().body(errors);
        }
        transfer.getSourceAccount().setBalance(transfer.getSourceAccount().getBalance().subtract(form.getAmount()));
        transferRepository.save(transfer);

        URI uri = uriComponentsBuilder.path("/accounts/transfer").buildAndExpand(transfer.getId()).toUri();
        return ResponseEntity.created(uri).body(new TransferDTO(transfer,user));

    }
    @PostMapping("/balance")
    public ResponseEntity<?> Balance(@RequestBody @Valid BalanceForm form, @RequestHeader("Authorization") String token,UriComponentsBuilder uriComponentsBuilder){
        Account account = form.accountRetrieve(accountRepository);

        if (account==null){
            return ResponseEntity.badRequest().body(new ErrorDTO("Conta de origem não encontrada para o usuário informado!"));
        }

        return ResponseEntity.ok(new BalanceDTO(account));
    }
    private User authUser(String token) {
        Long userId = tokenService.getIdUsuario(token.substring(7, token.length()));
        if(userId != null){
            return  userRepository.findById(userId).get();
        }
        return null;
    }
}
