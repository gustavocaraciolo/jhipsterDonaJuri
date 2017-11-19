package com.br.discovery.web.rest;

import com.br.discovery.service.MailService;
import com.codahale.metrics.annotation.Timed;
import com.br.discovery.service.ConviteService;
import com.br.discovery.web.rest.errors.BadRequestAlertException;
import com.br.discovery.web.rest.util.HeaderUtil;
import com.br.discovery.web.rest.util.PaginationUtil;
import com.br.discovery.service.dto.ConviteDTO;
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
 * REST controller for managing Convite.
 */
@RestController
@RequestMapping("/api")
public class ConviteResource {

    private final Logger log = LoggerFactory.getLogger(ConviteResource.class);

    private static final String ENTITY_NAME = "convite";

    private final ConviteService conviteService;

    private final MailService mailService;

    public ConviteResource(ConviteService conviteService, MailService mailService) {
        this.conviteService = conviteService;
        this.mailService = mailService;
    }

    /**
     * POST  /convites : Create a new convite.
     *
     * @param conviteDTO the conviteDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new conviteDTO, or with status 400 (Bad Request) if the convite has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/convites")
    @Timed
    public ResponseEntity<ConviteDTO> createConvite(@Valid @RequestBody ConviteDTO conviteDTO) throws URISyntaxException {
        log.debug("REST request to save Convite : {}", conviteDTO);
        if (conviteDTO.getId() != null) {
            throw new BadRequestAlertException("A new convite cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConviteDTO result = conviteService.save(conviteDTO);
        mailService.sendConviteEmail(conviteDTO.getEmail());
        return ResponseEntity.created(new URI("/api/convites/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /convites : Updates an existing convite.
     *
     * @param conviteDTO the conviteDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated conviteDTO,
     * or with status 400 (Bad Request) if the conviteDTO is not valid,
     * or with status 500 (Internal Server Error) if the conviteDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/convites")
    @Timed
    public ResponseEntity<ConviteDTO> updateConvite(@Valid @RequestBody ConviteDTO conviteDTO) throws URISyntaxException {
        log.debug("REST request to update Convite : {}", conviteDTO);
        if (conviteDTO.getId() == null) {
            return createConvite(conviteDTO);
        }
        ConviteDTO result = conviteService.save(conviteDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, conviteDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /convites : get all the convites.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of convites in body
     */
    @GetMapping("/convites")
    @Timed
    public ResponseEntity<List<ConviteDTO>> getAllConvites(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Convites");
        Page<ConviteDTO> page = conviteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/convites");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /convites/:id : get the "id" convite.
     *
     * @param id the id of the conviteDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the conviteDTO, or with status 404 (Not Found)
     */
    @GetMapping("/convites/{id}")
    @Timed
    public ResponseEntity<ConviteDTO> getConvite(@PathVariable Long id) {
        log.debug("REST request to get Convite : {}", id);
        ConviteDTO conviteDTO = conviteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(conviteDTO));
    }

    /**
     * DELETE  /convites/:id : delete the "id" convite.
     *
     * @param id the id of the conviteDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/convites/{id}")
    @Timed
    public ResponseEntity<Void> deleteConvite(@PathVariable Long id) {
        log.debug("REST request to delete Convite : {}", id);
        conviteService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
