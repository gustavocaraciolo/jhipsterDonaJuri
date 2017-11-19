package com.br.discovery.web.rest;

import com.br.discovery.DiscoveryApp;

import com.br.discovery.domain.Convite;
import com.br.discovery.domain.Escritorio;
import com.br.discovery.domain.UserExtra;
import com.br.discovery.repository.ConviteRepository;
import com.br.discovery.service.ConviteService;
import com.br.discovery.service.MailService;
import com.br.discovery.service.dto.ConviteDTO;
import com.br.discovery.service.mapper.ConviteMapper;
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

/**
 * Test class for the ConviteResource REST controller.
 *
 * @see ConviteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DiscoveryApp.class)
public class ConviteResourceIntTest {

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATA_ENVIO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA_ENVIO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DATA_ACEITADO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA_ACEITADO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DATA_REJEITADO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA_REJEITADO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private ConviteRepository conviteRepository;

    @Autowired
    private ConviteMapper conviteMapper;

    @Autowired
    private ConviteService conviteService;

    @Autowired
    private MailService mailService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restConviteMockMvc;

    private Convite convite;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ConviteResource conviteResource = new ConviteResource(conviteService, mailService);
        this.restConviteMockMvc = MockMvcBuilders.standaloneSetup(conviteResource, mailService)
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
    public static Convite createEntity(EntityManager em) {
        Convite convite = new Convite()
            .email(DEFAULT_EMAIL)
            .dataEnvio(DEFAULT_DATA_ENVIO)
            .dataAceitado(DEFAULT_DATA_ACEITADO)
            .dataRejeitado(DEFAULT_DATA_REJEITADO);
        // Add required entity
        Escritorio escritorio = EscritorioResourceIntTest.createEntity(em);
        em.persist(escritorio);
        em.flush();
        convite.getEscritorios().add(escritorio);
        // Add required entity
        UserExtra advogado = UserExtraResourceIntTest.createEntity(em);
        em.persist(advogado);
        em.flush();
        convite.getAdvogados().add(advogado);
        return convite;
    }

    @Before
    public void initTest() {
        convite = createEntity(em);
    }

    @Test
    @Transactional
    public void createConvite() throws Exception {
        int databaseSizeBeforeCreate = conviteRepository.findAll().size();

        // Create the Convite
        ConviteDTO conviteDTO = conviteMapper.toDto(convite);
        restConviteMockMvc.perform(post("/api/convites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conviteDTO)))
            .andExpect(status().isCreated());

        // Validate the Convite in the database
        List<Convite> conviteList = conviteRepository.findAll();
        assertThat(conviteList).hasSize(databaseSizeBeforeCreate + 1);
        Convite testConvite = conviteList.get(conviteList.size() - 1);
        assertThat(testConvite.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testConvite.getDataEnvio()).isEqualTo(DEFAULT_DATA_ENVIO);
        assertThat(testConvite.getDataAceitado()).isEqualTo(DEFAULT_DATA_ACEITADO);
        assertThat(testConvite.getDataRejeitado()).isEqualTo(DEFAULT_DATA_REJEITADO);
    }

    @Test
    @Transactional
    public void createConviteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = conviteRepository.findAll().size();

        // Create the Convite with an existing ID
        convite.setId(1L);
        ConviteDTO conviteDTO = conviteMapper.toDto(convite);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConviteMockMvc.perform(post("/api/convites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conviteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Convite in the database
        List<Convite> conviteList = conviteRepository.findAll();
        assertThat(conviteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = conviteRepository.findAll().size();
        // set the field null
        convite.setEmail(null);

        // Create the Convite, which fails.
        ConviteDTO conviteDTO = conviteMapper.toDto(convite);

        restConviteMockMvc.perform(post("/api/convites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conviteDTO)))
            .andExpect(status().isBadRequest());

        List<Convite> conviteList = conviteRepository.findAll();
        assertThat(conviteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllConvites() throws Exception {
        // Initialize the database
        conviteRepository.saveAndFlush(convite);

        // Get all the conviteList
        restConviteMockMvc.perform(get("/api/convites?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(convite.getId().intValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].dataEnvio").value(hasItem(sameInstant(DEFAULT_DATA_ENVIO))))
            .andExpect(jsonPath("$.[*].dataAceitado").value(hasItem(sameInstant(DEFAULT_DATA_ACEITADO))))
            .andExpect(jsonPath("$.[*].dataRejeitado").value(hasItem(sameInstant(DEFAULT_DATA_REJEITADO))));
    }

    @Test
    @Transactional
    public void getConvite() throws Exception {
        // Initialize the database
        conviteRepository.saveAndFlush(convite);

        // Get the convite
        restConviteMockMvc.perform(get("/api/convites/{id}", convite.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(convite.getId().intValue()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.dataEnvio").value(sameInstant(DEFAULT_DATA_ENVIO)))
            .andExpect(jsonPath("$.dataAceitado").value(sameInstant(DEFAULT_DATA_ACEITADO)))
            .andExpect(jsonPath("$.dataRejeitado").value(sameInstant(DEFAULT_DATA_REJEITADO)));
    }

    @Test
    @Transactional
    public void getNonExistingConvite() throws Exception {
        // Get the convite
        restConviteMockMvc.perform(get("/api/convites/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConvite() throws Exception {
        // Initialize the database
        conviteRepository.saveAndFlush(convite);
        int databaseSizeBeforeUpdate = conviteRepository.findAll().size();

        // Update the convite
        Convite updatedConvite = conviteRepository.findOne(convite.getId());
        updatedConvite
            .email(UPDATED_EMAIL)
            .dataEnvio(UPDATED_DATA_ENVIO)
            .dataAceitado(UPDATED_DATA_ACEITADO)
            .dataRejeitado(UPDATED_DATA_REJEITADO);
        ConviteDTO conviteDTO = conviteMapper.toDto(updatedConvite);

        restConviteMockMvc.perform(put("/api/convites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conviteDTO)))
            .andExpect(status().isOk());

        // Validate the Convite in the database
        List<Convite> conviteList = conviteRepository.findAll();
        assertThat(conviteList).hasSize(databaseSizeBeforeUpdate);
        Convite testConvite = conviteList.get(conviteList.size() - 1);
        assertThat(testConvite.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testConvite.getDataEnvio()).isEqualTo(UPDATED_DATA_ENVIO);
        assertThat(testConvite.getDataAceitado()).isEqualTo(UPDATED_DATA_ACEITADO);
        assertThat(testConvite.getDataRejeitado()).isEqualTo(UPDATED_DATA_REJEITADO);
    }

    @Test
    @Transactional
    public void updateNonExistingConvite() throws Exception {
        int databaseSizeBeforeUpdate = conviteRepository.findAll().size();

        // Create the Convite
        ConviteDTO conviteDTO = conviteMapper.toDto(convite);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restConviteMockMvc.perform(put("/api/convites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conviteDTO)))
            .andExpect(status().isCreated());

        // Validate the Convite in the database
        List<Convite> conviteList = conviteRepository.findAll();
        assertThat(conviteList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteConvite() throws Exception {
        // Initialize the database
        conviteRepository.saveAndFlush(convite);
        int databaseSizeBeforeDelete = conviteRepository.findAll().size();

        // Get the convite
        restConviteMockMvc.perform(delete("/api/convites/{id}", convite.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Convite> conviteList = conviteRepository.findAll();
        assertThat(conviteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Convite.class);
        Convite convite1 = new Convite();
        convite1.setId(1L);
        Convite convite2 = new Convite();
        convite2.setId(convite1.getId());
        assertThat(convite1).isEqualTo(convite2);
        convite2.setId(2L);
        assertThat(convite1).isNotEqualTo(convite2);
        convite1.setId(null);
        assertThat(convite1).isNotEqualTo(convite2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConviteDTO.class);
        ConviteDTO conviteDTO1 = new ConviteDTO();
        conviteDTO1.setId(1L);
        ConviteDTO conviteDTO2 = new ConviteDTO();
        assertThat(conviteDTO1).isNotEqualTo(conviteDTO2);
        conviteDTO2.setId(conviteDTO1.getId());
        assertThat(conviteDTO1).isEqualTo(conviteDTO2);
        conviteDTO2.setId(2L);
        assertThat(conviteDTO1).isNotEqualTo(conviteDTO2);
        conviteDTO1.setId(null);
        assertThat(conviteDTO1).isNotEqualTo(conviteDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(conviteMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(conviteMapper.fromId(null)).isNull();
    }
}
