package com.hipster.myapp.web.rest;
import com.hipster.myapp.domain.Establecimiento;
import com.hipster.myapp.repository.EstablecimientoRepository;
import com.hipster.myapp.repository.search.EstablecimientoSearchRepository;
import com.hipster.myapp.web.rest.errors.BadRequestAlertException;
import com.hipster.myapp.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Establecimiento.
 */
@RestController
@RequestMapping("/api")
public class EstablecimientoResource {

    private final Logger log = LoggerFactory.getLogger(EstablecimientoResource.class);

    private static final String ENTITY_NAME = "establecimiento";

    private final EstablecimientoRepository establecimientoRepository;

    private final EstablecimientoSearchRepository establecimientoSearchRepository;

    public EstablecimientoResource(EstablecimientoRepository establecimientoRepository, EstablecimientoSearchRepository establecimientoSearchRepository) {
        this.establecimientoRepository = establecimientoRepository;
        this.establecimientoSearchRepository = establecimientoSearchRepository;
    }

    /**
     * POST  /establecimientos : Create a new establecimiento.
     *
     * @param establecimiento the establecimiento to create
     * @return the ResponseEntity with status 201 (Created) and with body the new establecimiento, or with status 400 (Bad Request) if the establecimiento has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/establecimientos")
    public ResponseEntity<Establecimiento> createEstablecimiento(@RequestBody Establecimiento establecimiento) throws URISyntaxException {
        log.debug("REST request to save Establecimiento : {}", establecimiento);
        if (establecimiento.getId() != null) {
            throw new BadRequestAlertException("A new establecimiento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Establecimiento result = establecimientoRepository.save(establecimiento);
        establecimientoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/establecimientos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /establecimientos : Updates an existing establecimiento.
     *
     * @param establecimiento the establecimiento to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated establecimiento,
     * or with status 400 (Bad Request) if the establecimiento is not valid,
     * or with status 500 (Internal Server Error) if the establecimiento couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/establecimientos")
    public ResponseEntity<Establecimiento> updateEstablecimiento(@RequestBody Establecimiento establecimiento) throws URISyntaxException {
        log.debug("REST request to update Establecimiento : {}", establecimiento);
        if (establecimiento.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Establecimiento result = establecimientoRepository.save(establecimiento);
        establecimientoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, establecimiento.getId().toString()))
            .body(result);
    }

    /**
     * GET  /establecimientos : get all the establecimientos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of establecimientos in body
     */
    @GetMapping("/establecimientos")
    public List<Establecimiento> getAllEstablecimientos() {
        log.debug("REST request to get all Establecimientos");
        return establecimientoRepository.findAll();
    }

    /**
     * GET  /establecimientos/:id : get the "id" establecimiento.
     *
     * @param id the id of the establecimiento to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the establecimiento, or with status 404 (Not Found)
     */
    @GetMapping("/establecimientos/{id}")
    public ResponseEntity<Establecimiento> getEstablecimiento(@PathVariable Long id) {
        log.debug("REST request to get Establecimiento : {}", id);
        Optional<Establecimiento> establecimiento = establecimientoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(establecimiento);
    }

    /**
     * DELETE  /establecimientos/:id : delete the "id" establecimiento.
     *
     * @param id the id of the establecimiento to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/establecimientos/{id}")
    public ResponseEntity<Void> deleteEstablecimiento(@PathVariable Long id) {
        log.debug("REST request to delete Establecimiento : {}", id);
        establecimientoRepository.deleteById(id);
        establecimientoSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/establecimientos?query=:query : search for the establecimiento corresponding
     * to the query.
     *
     * @param query the query of the establecimiento search
     * @return the result of the search
     */
    @GetMapping("/_search/establecimientos")
    public List<Establecimiento> searchEstablecimientos(@RequestParam String query) {
        log.debug("REST request to search Establecimientos for query {}", query);
        return StreamSupport
            .stream(establecimientoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
