package com.br.discovery.bootstrap;

import com.br.discovery.config.Constants;
import com.br.discovery.domain.*;
import com.br.discovery.domain.enumeration.Status;
import com.br.discovery.repository.*;
import com.br.discovery.security.AuthoritiesConstants;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;


@Component
public class DonnaBootstrap implements CommandLineRunner {

    private final EscritorioRepository escritorioRepository;
    private final UserRepository userRepository;
    private final ProcessoRepository processoRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthorityRepository authorityRepository;
    private final UserExtraRepository userExtraRepository;

    public DonnaBootstrap(EscritorioRepository escritorioRepository, UserRepository userRepository,
                        PasswordEncoder passwordEncoder, AuthorityRepository authorityRepository,
                          UserExtraRepository userExtraRepository, ProcessoRepository processoRepository) {
        this.escritorioRepository = escritorioRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityRepository = authorityRepository;
        this.userExtraRepository = userExtraRepository;
        this.processoRepository = processoRepository;
    }
    @Transactional
    @Override
    public void run(String... strings) throws Exception {

        // init RFB Locations
        if(escritorioRepository.count() == 0){
            //only load data if no data loaded
            initData();
        }

    }

    private void initData() {
        Set<String> authorities = new HashSet<>();
        authorities.add(AuthoritiesConstants.ADVOGADO);

        Escritorio escritorio = getEscritorio("NEVES, COUTINHO E ADVOGADOS ASSOCIADOS","escritorio@teste.com","+55 81 9 9819-1200");

        UserExtra advogadoCorrente = getUser("Monalisa", "monalisa@teste.com", roleCoordenador(), escritorio);

        UserExtra cliente = getUser("João", "joao@teste.com", roleCliente(), escritorio);

        Processo processo = getProcesso("0000575-40.2017.1.13.5212", "CELPE", cliente, advogadoCorrente);
    }

    private Processo getProcesso(String numero, String parteAdversa, UserExtra cliente, UserExtra advogadoCorrente) {
        Processo processo = new Processo();
        processo.setNumero(numero);
        processo.setParteadversa(parteAdversa);
        processo.setStatus(Status.PENDENTE);
        processo.setCliente(cliente);
        processo.setAdvogadoCorrente(advogadoCorrente);
        processoRepository.save(processo);
        return processo;
    }

    private UserExtra getUser(String nome, String email, Set<Authority> authorities, Escritorio escritorio) {
        User user = new User();
        user.setFirstName(nome);
        user.setPassword(passwordEncoder.encode("admin"));
        user.setLogin(email);
        user.setEmail(email);
        user.setActivated(true);
        user.setAuthorities(authorities);
        user.setLangKey(Constants.DEFAULT_LANGUAGE);
        user.setImageUrl("http://www.jhipster.tech/images/logo/logo-jhipster2x.png");
        userRepository.save(user);

        // Create and save the UserExtra entity
        UserExtra newUserExtra = new UserExtra();
        newUserExtra.setUser(user);
        newUserExtra.setEscritorio(escritorio);
        userExtraRepository.save(newUserExtra);
        return newUserExtra;
    }

    private Set<Authority> roleAdvogado() {
        Set<Authority> authorities = new HashSet<>();
        Authority authority = new Authority();
        authority.setName(AuthoritiesConstants.ADVOGADO);
        authorities.add(authority);
        return authorities;
    }

    private Set<Authority> roleCoordenador() {
        Set<Authority> authorities = new HashSet<>();
        Authority authorityCoordenador = new Authority(AuthoritiesConstants.COORDENADOR);
        authorities.add(authorityCoordenador);
        Authority authorityAdvogado = new Authority(AuthoritiesConstants.ADVOGADO);
        authorities.add(authorityAdvogado);
        return authorities;
    }

    private Set<Authority> roleCliente() {
        Set<Authority> authorities = new HashSet<>();
        Authority authority = new Authority(AuthoritiesConstants.CLIENTE);
        authorities.add(authority);
        return authorities;
    }

    private Escritorio getEscritorio(String nome, String email, String telefone) {
        Escritorio escritorio = new Escritorio();
        escritorio.setNome(nome);
        escritorio.setEmail(email);
        escritorio.setTelefone(telefone);
        escritorioRepository.save(escritorio);
        return escritorio;
    }
}
