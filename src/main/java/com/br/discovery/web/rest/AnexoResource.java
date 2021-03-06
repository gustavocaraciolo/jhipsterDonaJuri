package com.br.discovery.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.br.discovery.service.AnexoService;
import com.br.discovery.web.rest.errors.BadRequestAlertException;
import com.br.discovery.web.rest.util.HeaderUtil;
import com.br.discovery.web.rest.util.PaginationUtil;
import com.br.discovery.service.dto.AnexoDTO;
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

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Anexo.
 */
@RestController
@RequestMapping("/api")
public class AnexoResource {

    private final Logger log = LoggerFactory.getLogger(AnexoResource.class);

    private static final String ENTITY_NAME = "anexo";

    private final AnexoService anexoService;

    public AnexoResource(AnexoService anexoService) {
        this.anexoService = anexoService;
    }

    /**
     * POST  /anexos : Create a new anexo.
     *
     * @param anexoDTO the anexoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new anexoDTO, or with status 400 (Bad Request) if the anexo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/anexos")
    @Timed
    public ResponseEntity<AnexoDTO> createAnexo(@RequestBody AnexoDTO anexoDTO) throws URISyntaxException {
        log.debug("REST request to save Anexo : {}", anexoDTO);
        if (anexoDTO.getId() != null) {
            throw new BadRequestAlertException("A new anexo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AnexoDTO result = anexoService.save(anexoDTO);
        return ResponseEntity.created(new URI("/api/anexos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /anexos : Updates an existing anexo.
     *
     * @param anexoDTO the anexoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated anexoDTO,
     * or with status 400 (Bad Request) if the anexoDTO is not valid,
     * or with status 500 (Internal Server Error) if the anexoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/anexos")
    @Timed
    public ResponseEntity<AnexoDTO> updateAnexo(@RequestBody AnexoDTO anexoDTO) throws URISyntaxException {
        log.debug("REST request to update Anexo : {}", anexoDTO);
        if (anexoDTO.getId() == null) {
            return createAnexo(anexoDTO);
        }
        AnexoDTO result = anexoService.save(anexoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, anexoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /anexos : get all the anexos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of anexos in body
     */
    @GetMapping("/anexos")
    @Timed
    public ResponseEntity<List<AnexoDTO>> getAllAnexos(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Anexos");
        Page<AnexoDTO> page = anexoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/anexos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /anexos/:id : get the "id" anexo.
     *
     * @param id the id of the anexoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the anexoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/anexos/{id}")
    @Timed
    public ResponseEntity<AnexoDTO> getAnexo(@PathVariable Long id) {
        log.debug("REST request to get Anexo : {}", id);
        AnexoDTO anexoDTO = anexoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(anexoDTO));
    }

    /**
     * DELETE  /anexos/:id : delete the "id" anexo.
     *
     * @param id the id of the anexoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/anexos/{id}")
    @Timed
    public ResponseEntity<Void> deleteAnexo(@PathVariable Long id) {
        log.debug("REST request to delete Anexo : {}", id);
        anexoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
