package com.br.discovery.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.br.discovery.service.ProcessoService;
import com.br.discovery.web.rest.errors.BadRequestAlertException;
import com.br.discovery.web.rest.util.HeaderUtil;
import com.br.discovery.web.rest.util.PaginationUtil;
import com.br.discovery.service.dto.ProcessoDTO;
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
 * REST controller for managing Processo.
 */
@RestController
@RequestMapping("/api")
public class ProcessoResource {

    private final Logger log = LoggerFactory.getLogger(ProcessoResource.class);

    private static final String ENTITY_NAME = "processo";

    private final ProcessoService processoService;

    public ProcessoResource(ProcessoService processoService) {
        this.processoService = processoService;
    }

    /**
     * POST  /processos : Create a new processo.
     *
     * @param processoDTO the processoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new processoDTO, or with status 400 (Bad Request) if the processo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/processos")
    @Timed
    public ResponseEntity<ProcessoDTO> createProcesso(@Valid @RequestBody ProcessoDTO processoDTO) throws URISyntaxException {
        log.debug("REST request to save Processo : {}", processoDTO);
        if (processoDTO.getId() != null) {
            throw new BadRequestAlertException("A new processo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProcessoDTO result = processoService.save(processoDTO);
        return ResponseEntity.created(new URI("/api/processos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /processos : Updates an existing processo.
     *
     * @param processoDTO the processoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated processoDTO,
     * or with status 400 (Bad Request) if the processoDTO is not valid,
     * or with status 500 (Internal Server Error) if the processoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/processos")
    @Timed
    public ResponseEntity<ProcessoDTO> updateProcesso(@Valid @RequestBody ProcessoDTO processoDTO) throws URISyntaxException {
        log.debug("REST request to update Processo : {}", processoDTO);
        if (processoDTO.getId() == null) {
            return createProcesso(processoDTO);
        }
        ProcessoDTO result = processoService.save(processoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, processoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /processos : get all the processos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of processos in body
     */
    @GetMapping("/processos")
    @Timed
    public ResponseEntity<List<ProcessoDTO>> getAllProcessos(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Processos");
        Page<ProcessoDTO> page = processoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/processos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /processos/:id : get the "id" processo.
     *
     * @param id the id of the processoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the processoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/processos/{id}")
    @Timed
    public ResponseEntity<ProcessoDTO> getProcesso(@PathVariable Long id) {
        log.debug("REST request to get Processo : {}", id);
        ProcessoDTO processoDTO = processoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(processoDTO));
    }

    /**
     * DELETE  /processos/:id : delete the "id" processo.
     *
     * @param id the id of the processoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/processos/{id}")
    @Timed
    public ResponseEntity<Void> deleteProcesso(@PathVariable Long id) {
        log.debug("REST request to delete Processo : {}", id);
        processoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
