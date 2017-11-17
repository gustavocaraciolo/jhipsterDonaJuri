package com.br.discovery.web.rest;

import com.br.discovery.DiscoveryApp;

import com.br.discovery.domain.Processo;
import com.br.discovery.domain.UserExtra;
import com.br.discovery.domain.UserExtra;
import com.br.discovery.domain.UserExtra;
import com.br.discovery.repository.ProcessoRepository;
import com.br.discovery.service.ProcessoService;
import com.br.discovery.service.dto.ProcessoDTO;
import com.br.discovery.service.mapper.ProcessoMapper;
import com.br.discovery.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.br.discovery.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.br.discovery.domain.enumeration.Status;
/**
 * Test class for the ProcessoResource REST controller.
 *
 * @see ProcessoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DiscoveryApp.class)
public class ProcessoResourceIntTest {

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    private static final Status DEFAULT_STATUS = Status.PENDENTE;
    private static final Status UPDATED_STATUS = Status.PERDIDO;

    private static final String DEFAULT_PARTEADVERSA = "AAAAAAAAAA";
    private static final String UPDATED_PARTEADVERSA = "BBBBBBBBBB";

    @Autowired
    private ProcessoRepository processoRepository;

    @Autowired
    private ProcessoMapper processoMapper;

    @Autowired
    private ProcessoService processoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProcessoMockMvc;

    private Processo processo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProcessoResource processoResource = new ProcessoResource(processoService);
        this.restProcessoMockMvc = MockMvcBuilders.standaloneSetup(processoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Processo createEntity(EntityManager em) {
        Processo processo = new Processo()
            .numero(DEFAULT_NUMERO)
            .status(DEFAULT_STATUS)
            .parteadversa(DEFAULT_PARTEADVERSA);
        // Add required entity
        UserExtra advogadoCorrente = UserExtraResourceIntTest.createEntity(em);
        em.persist(advogadoCorrente);
        em.flush();
        processo.setAdvogadoCorrente(advogadoCorrente);
        // Add required entity
        UserExtra cliente = UserExtraResourceIntTest.createEntity(em);
        em.persist(cliente);
        em.flush();
        processo.setCliente(cliente);
        // Add required entity
        UserExtra advogado = UserExtraResourceIntTest.createEntity(em);
        em.persist(advogado);
        em.flush();
        processo.getAdvogados().add(advogado);
        return processo;
    }

    @Before
    public void initTest() {
        processo = createEntity(em);
    }

    @Test
    @Transactional
    public void createProcesso() throws Exception {
        int databaseSizeBeforeCreate = processoRepository.findAll().size();

        // Create the Processo
        ProcessoDTO processoDTO = processoMapper.toDto(processo);
        restProcessoMockMvc.perform(post("/api/processos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(processoDTO)))
            .andExpect(status().isCreated());

        // Validate the Processo in the database
        List<Processo> processoList = processoRepository.findAll();
        assertThat(processoList).hasSize(databaseSizeBeforeCreate + 1);
        Processo testProcesso = processoList.get(processoList.size() - 1);
        assertThat(testProcesso.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testProcesso.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testProcesso.getParteadversa()).isEqualTo(DEFAULT_PARTEADVERSA);
    }

    @Test
    @Transactional
    public void createProcessoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = processoRepository.findAll().size();

        // Create the Processo with an existing ID
        processo.setId(1L);
        ProcessoDTO processoDTO = processoMapper.toDto(processo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProcessoMockMvc.perform(post("/api/processos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(processoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Processo in the database
        List<Processo> processoList = processoRepository.findAll();
        assertThat(processoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNumeroIsRequired() throws Exception {
        int databaseSizeBeforeTest = processoRepository.findAll().size();
        // set the field null
        processo.setNumero(null);

        // Create the Processo, which fails.
        ProcessoDTO processoDTO = processoMapper.toDto(processo);

        restProcessoMockMvc.perform(post("/api/processos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(processoDTO)))
            .andExpect(status().isBadRequest());

        List<Processo> processoList = processoRepository.findAll();
        assertThat(processoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProcessos() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList
        restProcessoMockMvc.perform(get("/api/processos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(processo.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].parteadversa").value(hasItem(DEFAULT_PARTEADVERSA.toString())));
    }

    @Test
    @Transactional
    public void getProcesso() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get the processo
        restProcessoMockMvc.perform(get("/api/processos/{id}", processo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(processo.getId().intValue()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.parteadversa").value(DEFAULT_PARTEADVERSA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProcesso() throws Exception {
        // Get the processo
        restProcessoMockMvc.perform(get("/api/processos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProcesso() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);
        int databaseSizeBeforeUpdate = processoRepository.findAll().size();

        // Update the processo
        Processo updatedProcesso = processoRepository.findOne(processo.getId());
        updatedProcesso
            .numero(UPDATED_NUMERO)
            .status(UPDATED_STATUS)
            .parteadversa(UPDATED_PARTEADVERSA);
        ProcessoDTO processoDTO = processoMapper.toDto(updatedProcesso);

        restProcessoMockMvc.perform(put("/api/processos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(processoDTO)))
            .andExpect(status().isOk());

        // Validate the Processo in the database
        List<Processo> processoList = processoRepository.findAll();
        assertThat(processoList).hasSize(databaseSizeBeforeUpdate);
        Processo testProcesso = processoList.get(processoList.size() - 1);
        assertThat(testProcesso.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testProcesso.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testProcesso.getParteadversa()).isEqualTo(UPDATED_PARTEADVERSA);
    }

    @Test
    @Transactional
    public void updateNonExistingProcesso() throws Exception {
        int databaseSizeBeforeUpdate = processoRepository.findAll().size();

        // Create the Processo
        ProcessoDTO processoDTO = processoMapper.toDto(processo);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProcessoMockMvc.perform(put("/api/processos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(processoDTO)))
            .andExpect(status().isCreated());

        // Validate the Processo in the database
        List<Processo> processoList = processoRepository.findAll();
        assertThat(processoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProcesso() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);
        int databaseSizeBeforeDelete = processoRepository.findAll().size();

        // Get the processo
        restProcessoMockMvc.perform(delete("/api/processos/{id}", processo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Processo> processoList = processoRepository.findAll();
        assertThat(processoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Processo.class);
        Processo processo1 = new Processo();
        processo1.setId(1L);
        Processo processo2 = new Processo();
        processo2.setId(processo1.getId());
        assertThat(processo1).isEqualTo(processo2);
        processo2.setId(2L);
        assertThat(processo1).isNotEqualTo(processo2);
        processo1.setId(null);
        assertThat(processo1).isNotEqualTo(processo2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProcessoDTO.class);
        ProcessoDTO processoDTO1 = new ProcessoDTO();
        processoDTO1.setId(1L);
        ProcessoDTO processoDTO2 = new ProcessoDTO();
        assertThat(processoDTO1).isNotEqualTo(processoDTO2);
        processoDTO2.setId(processoDTO1.getId());
        assertThat(processoDTO1).isEqualTo(processoDTO2);
        processoDTO2.setId(2L);
        assertThat(processoDTO1).isNotEqualTo(processoDTO2);
        processoDTO1.setId(null);
        assertThat(processoDTO1).isNotEqualTo(processoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(processoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(processoMapper.fromId(null)).isNull();
    }
}
