package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Lapin;
import com.mycompany.myapp.repository.LapinRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Lapin}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class LapinResource {

    private final Logger log = LoggerFactory.getLogger(LapinResource.class);

    private static final String ENTITY_NAME = "lapin";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LapinRepository lapinRepository;

    public LapinResource(LapinRepository lapinRepository) {
        this.lapinRepository = lapinRepository;
    }

    /**
     * {@code POST  /lapins} : Create a new lapin.
     *
     * @param lapin the lapin to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new lapin, or with status {@code 400 (Bad Request)} if the lapin has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/lapins")
    public ResponseEntity<Lapin> createLapin(@RequestBody Lapin lapin) throws URISyntaxException {
        log.debug("REST request to save Lapin : {}", lapin);
        if (lapin.getId() != null) {
            throw new BadRequestAlertException("A new lapin cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Lapin result = lapinRepository.save(lapin);
        return ResponseEntity.created(new URI("/api/lapins/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /lapins} : Updates an existing lapin.
     *
     * @param lapin the lapin to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lapin,
     * or with status {@code 400 (Bad Request)} if the lapin is not valid,
     * or with status {@code 500 (Internal Server Error)} if the lapin couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/lapins")
    public ResponseEntity<Lapin> updateLapin(@RequestBody Lapin lapin) throws URISyntaxException {
        log.debug("REST request to update Lapin : {}", lapin);
        if (lapin.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Lapin result = lapinRepository.save(lapin);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, lapin.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /lapins} : get all the lapins.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of lapins in body.
     */
    @GetMapping("/lapins")
    public List<Lapin> getAllLapins() {
        log.debug("REST request to get all Lapins");
        return lapinRepository.findAll();
    }

    /**
     * {@code GET  /lapins/:id} : get the "id" lapin.
     *
     * @param id the id of the lapin to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the lapin, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/lapins/{id}")
    public ResponseEntity<Lapin> getLapin(@PathVariable Long id) {
        log.debug("REST request to get Lapin : {}", id);
        Optional<Lapin> lapin = lapinRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(lapin);
    }

    /**
     * {@code DELETE  /lapins/:id} : delete the "id" lapin.
     *
     * @param id the id of the lapin to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/lapins/{id}")
    public ResponseEntity<Void> deleteLapin(@PathVariable Long id) {
        log.debug("REST request to delete Lapin : {}", id);

        lapinRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
