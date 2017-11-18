package com.br.discovery.web.rest;

import com.br.discovery.DiscoveryApp;

import com.br.discovery.domain.Pendencia;
import com.br.discovery.domain.Processo;
import com.br.discovery.repository.PendenciaRepository;
import com.br.discovery.service.PendenciaService;
import com.br.discovery.service.dto.PendenciaDTO;
import com.br.discovery.service.mapper.PendenciaMapper;
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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.br.discovery.web.rest.TestUtil.sameInstant;
import static com.br.discovery.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.br.discovery.domain.enumeration.Status;
/**
 * Test class for the PendenciaResource REST controller.
 *
 * @see PendenciaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DiscoveryApp.class)
public class PendenciaResourceIntTest {

    private static final String DEFAULT_PROVIDENCIA = "AAAAAAAAAA";
    private static final String UPDATED_PROVIDENCIA = "BBBBBBBBBB";

    private static final String DEFAULT_OBSERVACOES = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACOES = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATA_INICIAL = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA_INICIAL = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DATA_FINAL = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA_FINAL = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Status DEFAULT_STATUS = Status.PENDENTE;
    private static final Status UPDATED_STATUS = Status.PERDIDO;

    @Autowired
    private PendenciaRepository pendenciaRepository;

    @Autowired
    private PendenciaMapper pendenciaMapper;

    @Autowired
    private PendenciaService pendenciaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPendenciaMockMvc;

    private Pendencia pendencia;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PendenciaResource pendenciaResource = new PendenciaResource(pendenciaService);
        this.restPendenciaMockMvc = MockMvcBuilders.standaloneSetup(pendenciaResource)
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
    public static Pendencia createEntity(EntityManager em) {
        Pendencia pendencia = new Pendencia()
            .providencia(DEFAULT_PROVIDENCIA)
            .observacoes(DEFAULT_OBSERVACOES)
            .dataInicial(DEFAULT_DATA_INICIAL)
            .dataFinal(DEFAULT_DATA_FINAL)
            .status(DEFAULT_STATUS);
        // Add required entity
        Processo processo = ProcessoResourceIntTest.createEntity(em);
        em.persist(processo);
        em.flush();
        pendencia.setProcesso(processo);
        return pendencia;
    }

    @Before
    public void initTest() {
        pendencia = createEntity(em);
    }

    @Test
    @Transactional
    public void createPendencia() throws Exception {
        int databaseSizeBeforeCreate = pendenciaRepository.findAll().size();

        // Create the Pendencia
        PendenciaDTO pendenciaDTO = pendenciaMapper.toDto(pendencia);
        restPendenciaMockMvc.perform(post("/api/pendencias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pendenciaDTO)))
            .andExpect(status().isCreated());

        // Validate the Pendencia in the database
        List<Pendencia> pendenciaList = pendenciaRepository.findAll();
        assertThat(pendenciaList).hasSize(databaseSizeBeforeCreate + 1);
        Pendencia testPendencia = pendenciaList.get(pendenciaList.size() - 1);
        assertThat(testPendencia.getProvidencia()).isEqualTo(DEFAULT_PROVIDENCIA);
        assertThat(testPendencia.getObservacoes()).isEqualTo(DEFAULT_OBSERVACOES);
        assertThat(testPendencia.getDataInicial()).isEqualTo(DEFAULT_DATA_INICIAL);
        assertThat(testPendencia.getDataFinal()).isEqualTo(DEFAULT_DATA_FINAL);
        assertThat(testPendencia.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createPendenciaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pendenciaRepository.findAll().size();

        // Create the Pendencia with an existing ID
        pendencia.setId(1L);
        PendenciaDTO pendenciaDTO = pendenciaMapper.toDto(pendencia);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPendenciaMockMvc.perform(post("/api/pendencias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pendenciaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Pendencia in the database
        List<Pendencia> pendenciaList = pendenciaRepository.findAll();
        assertThat(pendenciaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPendencias() throws Exception {
        // Initialize the database
        pendenciaRepository.saveAndFlush(pendencia);

        // Get all the pendenciaList
        restPendenciaMockMvc.perform(get("/api/pendencias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pendencia.getId().intValue())))
            .andExpect(jsonPath("$.[*].providencia").value(hasItem(DEFAULT_PROVIDENCIA.toString())))
            .andExpect(jsonPath("$.[*].observacoes").value(hasItem(DEFAULT_OBSERVACOES.toString())))
            .andExpect(jsonPath("$.[*].dataInicial").value(hasItem(sameInstant(DEFAULT_DATA_INICIAL))))
            .andExpect(jsonPath("$.[*].dataFinal").value(hasItem(sameInstant(DEFAULT_DATA_FINAL))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getPendencia() throws Exception {
        // Initialize the database
        pendenciaRepository.saveAndFlush(pendencia);

        // Get the pendencia
        restPendenciaMockMvc.perform(get("/api/pendencias/{id}", pendencia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pendencia.getId().intValue()))
            .andExpect(jsonPath("$.providencia").value(DEFAULT_PROVIDENCIA.toString()))
            .andExpect(jsonPath("$.observacoes").value(DEFAULT_OBSERVACOES.toString()))
            .andExpect(jsonPath("$.dataInicial").value(sameInstant(DEFAULT_DATA_INICIAL)))
            .andExpect(jsonPath("$.dataFinal").value(sameInstant(DEFAULT_DATA_FINAL)))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPendencia() throws Exception {
        // Get the pendencia
        restPendenciaMockMvc.perform(get("/api/pendencias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePendencia() throws Exception {
        // Initialize the database
        pendenciaRepository.saveAndFlush(pendencia);
        int databaseSizeBeforeUpdate = pendenciaRepository.findAll().size();

        // Update the pendencia
        Pendencia updatedPendencia = pendenciaRepository.findOne(pendencia.getId());
        updatedPendencia
            .providencia(UPDATED_PROVIDENCIA)
            .observacoes(UPDATED_OBSERVACOES)
            .dataInicial(UPDATED_DATA_INICIAL)
            .dataFinal(UPDATED_DATA_FINAL)
            .status(UPDATED_STATUS);
        PendenciaDTO pendenciaDTO = pendenciaMapper.toDto(updatedPendencia);

        restPendenciaMockMvc.perform(put("/api/pendencias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pendenciaDTO)))
            .andExpect(status().isOk());

        // Validate the Pendencia in the database
        List<Pendencia> pendenciaList = pendenciaRepository.findAll();
        assertThat(pendenciaList).hasSize(databaseSizeBeforeUpdate);
        Pendencia testPendencia = pendenciaList.get(pendenciaList.size() - 1);
        assertThat(testPendencia.getProvidencia()).isEqualTo(UPDATED_PROVIDENCIA);
        assertThat(testPendencia.getObservacoes()).isEqualTo(UPDATED_OBSERVACOES);
        assertThat(testPendencia.getDataInicial()).isEqualTo(UPDATED_DATA_INICIAL);
        assertThat(testPendencia.getDataFinal()).isEqualTo(UPDATED_DATA_FINAL);
        assertThat(testPendencia.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingPendencia() throws Exception {
        int databaseSizeBeforeUpdate = pendenciaRepository.findAll().size();

        // Create the Pendencia
        PendenciaDTO pendenciaDTO = pendenciaMapper.toDto(pendencia);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPendenciaMockMvc.perform(put("/api/pendencias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pendenciaDTO)))
            .andExpect(status().isCreated());

        // Validate the Pendencia in the database
        List<Pendencia> pendenciaList = pendenciaRepository.findAll();
        assertThat(pendenciaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePendencia() throws Exception {
        // Initialize the database
        pendenciaRepository.saveAndFlush(pendencia);
        int databaseSizeBeforeDelete = pendenciaRepository.findAll().size();

        // Get the pendencia
        restPendenciaMockMvc.perform(delete("/api/pendencias/{id}", pendencia.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Pendencia> pendenciaList = pendenciaRepository.findAll();
        assertThat(pendenciaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pendencia.class);
        Pendencia pendencia1 = new Pendencia();
        pendencia1.setId(1L);
        Pendencia pendencia2 = new Pendencia();
        pendencia2.setId(pendencia1.getId());
        assertThat(pendencia1).isEqualTo(pendencia2);
        pendencia2.setId(2L);
        assertThat(pendencia1).isNotEqualTo(pendencia2);
        pendencia1.setId(null);
        assertThat(pendencia1).isNotEqualTo(pendencia2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PendenciaDTO.class);
        PendenciaDTO pendenciaDTO1 = new PendenciaDTO();
        pendenciaDTO1.setId(1L);
        PendenciaDTO pendenciaDTO2 = new PendenciaDTO();
        assertThat(pendenciaDTO1).isNotEqualTo(pendenciaDTO2);
        pendenciaDTO2.setId(pendenciaDTO1.getId());
        assertThat(pendenciaDTO1).isEqualTo(pendenciaDTO2);
        pendenciaDTO2.setId(2L);
        assertThat(pendenciaDTO1).isNotEqualTo(pendenciaDTO2);
        pendenciaDTO1.setId(null);
        assertThat(pendenciaDTO1).isNotEqualTo(pendenciaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(pendenciaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(pendenciaMapper.fromId(null)).isNull();
    }
}
