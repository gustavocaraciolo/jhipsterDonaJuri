package com.br.discovery.web.rest;

import com.br.discovery.DiscoveryApp;

import com.br.discovery.domain.Escritorio;
import com.br.discovery.repository.EscritorioRepository;
import com.br.discovery.service.EscritorioService;
import com.br.discovery.service.dto.EscritorioDTO;
import com.br.discovery.service.mapper.EscritorioMapper;
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

/**
 * Test class for the EscritorioResource REST controller.
 *
 * @see EscritorioResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DiscoveryApp.class)
public class EscritorioResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    @Autowired
    private EscritorioRepository escritorioRepository;

    @Autowired
    private EscritorioMapper escritorioMapper;

    @Autowired
    private EscritorioService escritorioService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEscritorioMockMvc;

    private Escritorio escritorio;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EscritorioResource escritorioResource = new EscritorioResource(escritorioService);
        this.restEscritorioMockMvc = MockMvcBuilders.standaloneSetup(escritorioResource)
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
    public static Escritorio createEntity(EntityManager em) {
        Escritorio escritorio = new Escritorio()
            .nome(DEFAULT_NOME)
            .telefone(DEFAULT_TELEFONE)
            .email(DEFAULT_EMAIL);
        return escritorio;
    }

    @Before
    public void initTest() {
        escritorio = createEntity(em);
    }

    @Test
    @Transactional
    public void createEscritorio() throws Exception {
        int databaseSizeBeforeCreate = escritorioRepository.findAll().size();

        // Create the Escritorio
        EscritorioDTO escritorioDTO = escritorioMapper.toDto(escritorio);
        restEscritorioMockMvc.perform(post("/api/escritorios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(escritorioDTO)))
            .andExpect(status().isCreated());

        // Validate the Escritorio in the database
        List<Escritorio> escritorioList = escritorioRepository.findAll();
        assertThat(escritorioList).hasSize(databaseSizeBeforeCreate + 1);
        Escritorio testEscritorio = escritorioList.get(escritorioList.size() - 1);
        assertThat(testEscritorio.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testEscritorio.getTelefone()).isEqualTo(DEFAULT_TELEFONE);
        assertThat(testEscritorio.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    public void createEscritorioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = escritorioRepository.findAll().size();

        // Create the Escritorio with an existing ID
        escritorio.setId(1L);
        EscritorioDTO escritorioDTO = escritorioMapper.toDto(escritorio);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEscritorioMockMvc.perform(post("/api/escritorios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(escritorioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Escritorio in the database
        List<Escritorio> escritorioList = escritorioRepository.findAll();
        assertThat(escritorioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = escritorioRepository.findAll().size();
        // set the field null
        escritorio.setNome(null);

        // Create the Escritorio, which fails.
        EscritorioDTO escritorioDTO = escritorioMapper.toDto(escritorio);

        restEscritorioMockMvc.perform(post("/api/escritorios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(escritorioDTO)))
            .andExpect(status().isBadRequest());

        List<Escritorio> escritorioList = escritorioRepository.findAll();
        assertThat(escritorioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEscritorios() throws Exception {
        // Initialize the database
        escritorioRepository.saveAndFlush(escritorio);

        // Get all the escritorioList
        restEscritorioMockMvc.perform(get("/api/escritorios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(escritorio.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].telefone").value(hasItem(DEFAULT_TELEFONE.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())));
    }

    @Test
    @Transactional
    public void getEscritorio() throws Exception {
        // Initialize the database
        escritorioRepository.saveAndFlush(escritorio);

        // Get the escritorio
        restEscritorioMockMvc.perform(get("/api/escritorios/{id}", escritorio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(escritorio.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.telefone").value(DEFAULT_TELEFONE.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEscritorio() throws Exception {
        // Get the escritorio
        restEscritorioMockMvc.perform(get("/api/escritorios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEscritorio() throws Exception {
        // Initialize the database
        escritorioRepository.saveAndFlush(escritorio);
        int databaseSizeBeforeUpdate = escritorioRepository.findAll().size();

        // Update the escritorio
        Escritorio updatedEscritorio = escritorioRepository.findOne(escritorio.getId());
        updatedEscritorio
            .nome(UPDATED_NOME)
            .telefone(UPDATED_TELEFONE)
            .email(UPDATED_EMAIL);
        EscritorioDTO escritorioDTO = escritorioMapper.toDto(updatedEscritorio);

        restEscritorioMockMvc.perform(put("/api/escritorios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(escritorioDTO)))
            .andExpect(status().isOk());

        // Validate the Escritorio in the database
        List<Escritorio> escritorioList = escritorioRepository.findAll();
        assertThat(escritorioList).hasSize(databaseSizeBeforeUpdate);
        Escritorio testEscritorio = escritorioList.get(escritorioList.size() - 1);
        assertThat(testEscritorio.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testEscritorio.getTelefone()).isEqualTo(UPDATED_TELEFONE);
        assertThat(testEscritorio.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void updateNonExistingEscritorio() throws Exception {
        int databaseSizeBeforeUpdate = escritorioRepository.findAll().size();

        // Create the Escritorio
        EscritorioDTO escritorioDTO = escritorioMapper.toDto(escritorio);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEscritorioMockMvc.perform(put("/api/escritorios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(escritorioDTO)))
            .andExpect(status().isCreated());

        // Validate the Escritorio in the database
        List<Escritorio> escritorioList = escritorioRepository.findAll();
        assertThat(escritorioList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEscritorio() throws Exception {
        // Initialize the database
        escritorioRepository.saveAndFlush(escritorio);
        int databaseSizeBeforeDelete = escritorioRepository.findAll().size();

        // Get the escritorio
        restEscritorioMockMvc.perform(delete("/api/escritorios/{id}", escritorio.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Escritorio> escritorioList = escritorioRepository.findAll();
        assertThat(escritorioList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Escritorio.class);
        Escritorio escritorio1 = new Escritorio();
        escritorio1.setId(1L);
        Escritorio escritorio2 = new Escritorio();
        escritorio2.setId(escritorio1.getId());
        assertThat(escritorio1).isEqualTo(escritorio2);
        escritorio2.setId(2L);
        assertThat(escritorio1).isNotEqualTo(escritorio2);
        escritorio1.setId(null);
        assertThat(escritorio1).isNotEqualTo(escritorio2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EscritorioDTO.class);
        EscritorioDTO escritorioDTO1 = new EscritorioDTO();
        escritorioDTO1.setId(1L);
        EscritorioDTO escritorioDTO2 = new EscritorioDTO();
        assertThat(escritorioDTO1).isNotEqualTo(escritorioDTO2);
        escritorioDTO2.setId(escritorioDTO1.getId());
        assertThat(escritorioDTO1).isEqualTo(escritorioDTO2);
        escritorioDTO2.setId(2L);
        assertThat(escritorioDTO1).isNotEqualTo(escritorioDTO2);
        escritorioDTO1.setId(null);
        assertThat(escritorioDTO1).isNotEqualTo(escritorioDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(escritorioMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(escritorioMapper.fromId(null)).isNull();
    }
}
