package com.br.discovery.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.br.discovery.service.PendenciaService;
import com.br.discovery.web.rest.errors.BadRequestAlertException;
import com.br.discovery.web.rest.util.HeaderUtil;
import com.br.discovery.web.rest.util.PaginationUtil;
import com.br.discovery.service.dto.PendenciaDTO;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Pendencia.
 */
@RestController
@RequestMapping("/api")
public class PendenciaResource {

    private final Logger log = LoggerFactory.getLogger(PendenciaResource.class);

    private static final String ENTITY_NAME = "pendencia";

    private final PendenciaService pendenciaService;

    public PendenciaResource(PendenciaService pendenciaService) {
        this.pendenciaService = pendenciaService;
    }

    /**
     * POST  /pendencias : Create a new pendencia.
     *
     * @param pendenciaDTO the pendenciaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pendenciaDTO, or with status 400 (Bad Request) if the pendencia has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pendencias")
    @Timed
    public ResponseEntity<PendenciaDTO> createPendencia(@Valid @RequestBody PendenciaDTO pendenciaDTO) throws URISyntaxException {
        log.debug("REST request to save Pendencia : {}", pendenciaDTO);
        if (pendenciaDTO.getId() != null) {
            throw new BadRequestAlertException("A new pendencia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PendenciaDTO result = pendenciaService.save(pendenciaDTO);
        return ResponseEntity.created(new URI("/api/pendencias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pendencias : Updates an existing pendencia.
     *
     * @param pendenciaDTO the pendenciaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pendenciaDTO,
     * or with status 400 (Bad Request) if the pendenciaDTO is not valid,
     * or with status 500 (Internal Server Error) if the pendenciaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pendencias")
    @Timed
    public ResponseEntity<PendenciaDTO> updatePendencia(@Valid @RequestBody PendenciaDTO pendenciaDTO) throws URISyntaxException {
        log.debug("REST request to update Pendencia : {}", pendenciaDTO);
        if (pendenciaDTO.getId() == null) {
            return createPendencia(pendenciaDTO);
        }
        PendenciaDTO result = pendenciaService.save(pendenciaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pendenciaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pendencias : get all the pendencias.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pendencias in body
     */
    @GetMapping("/pendencias")
    @Timed
    public ResponseEntity<List<PendenciaDTO>> getAllPendencias(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Pendencias");
        Page<PendenciaDTO> page = pendenciaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pendencias");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /pendencias/:id : get the "id" pendencia.
     *
     * @param id the id of the pendenciaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pendenciaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/pendencias/{id}")
    @Timed
    public ResponseEntity<PendenciaDTO> getPendencia(@PathVariable Long id) {
        log.debug("REST request to get Pendencia : {}", id);
        PendenciaDTO pendenciaDTO = pendenciaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(pendenciaDTO));
    }

    /**
     * DELETE  /pendencias/:id : delete the "id" pendencia.
     *
     * @param id the id of the pendenciaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pendencias/{id}")
    @Timed
    public ResponseEntity<Void> deletePendencia(@PathVariable Long id) {
        log.debug("REST request to delete Pendencia : {}", id);
        pendenciaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
