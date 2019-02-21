package com.hipster.myapp.web.rest;
import com.hipster.myapp.domain.TituloSecundario;
import com.hipster.myapp.repository.TituloSecundarioRepository;
import com.hipster.myapp.repository.search.TituloSecundarioSearchRepository;
import com.hipster.myapp.web.rest.errors.BadRequestAlertException;
import com.hipster.myapp.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing TituloSecundario.
 */
@RestController
@RequestMapping("/api")
public class TituloSecundarioResource {

    private final Logger log = LoggerFactory.getLogger(TituloSecundarioResource.class);

    private static final String ENTITY_NAME = "tituloSecundario";

    private final TituloSecundarioRepository tituloSecundarioRepository;

    private final TituloSecundarioSearchRepository tituloSecundarioSearchRepository;

    public TituloSecundarioResource(TituloSecundarioRepository tituloSecundarioRepository, TituloSecundarioSearchRepository tituloSecundarioSearchRepository) {
        this.tituloSecundarioRepository = tituloSecundarioRepository;
        this.tituloSecundarioSearchRepository = tituloSecundarioSearchRepository;
    }

    /**
     * POST  /titulo-secundarios : Create a new tituloSecundario.
     *
     * @param tituloSecundario the tituloSecundario to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tituloSecundario, or with status 400 (Bad Request) if the tituloSecundario has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/titulo-secundarios")
    public ResponseEntity<TituloSecundario> createTituloSecundario(@Valid @RequestBody TituloSecundario tituloSecundario) throws URISyntaxException {
        log.debug("REST request to save TituloSecundario : {}", tituloSecundario);
        if (tituloSecundario.getId() != null) {
            throw new BadRequestAlertException("A new tituloSecundario cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TituloSecundario result = tituloSecundarioRepository.save(tituloSecundario);
        tituloSecundarioSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/titulo-secundarios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }


    /**
     * PUT  /titulo-secundarios : Updates an existing tituloSecundario.
     *
     * @param tituloSecundario the tituloSecundario to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tituloSecundario,
     * or with status 400 (Bad Request) if the tituloSecundario is not valid,
     * or with status 500 (Internal Server Error) if the tituloSecundario couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/titulo-secundarios")
    public ResponseEntity<TituloSecundario> updateTituloSecundario(@Valid @RequestBody TituloSecundario tituloSecundario) throws URISyntaxException {
        log.debug("REST request to update TituloSecundario : {}", tituloSecundario);
        if (tituloSecundario.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TituloSecundario result = tituloSecundarioRepository.save(tituloSecundario);
        tituloSecundarioSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tituloSecundario.getId().toString()))
            .body(result);
    }

    /**
     * GET  /titulo-secundarios : get all the tituloSecundarios.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tituloSecundarios in body
     */
    @GetMapping("/titulo-secundarios")
    public List<TituloSecundario> getAllTituloSecundarios() {
        log.debug("REST request to get all TituloSecundarios");
        return tituloSecundarioRepository.findAll();
    }

    /**
     * GET  /titulo-secundarios/:id : get the "id" tituloSecundario.
     *
     * @param id the id of the tituloSecundario to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tituloSecundario, or with status 404 (Not Found)
     */
    @GetMapping("/titulo-secundarios/{id}")
    public ResponseEntity<TituloSecundario> getTituloSecundario(@PathVariable Long id) {
        log.debug("REST request to get TituloSecundario : {}", id);
        Optional<TituloSecundario> tituloSecundario = tituloSecundarioRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(tituloSecundario);
    }

    /**
     * DELETE  /titulo-secundarios/:id : delete the "id" tituloSecundario.
     *
     * @param id the id of the tituloSecundario to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/titulo-secundarios/{id}")
    public ResponseEntity<Void> deleteTituloSecundario(@PathVariable Long id) {
        log.debug("REST request to delete TituloSecundario : {}", id);
        tituloSecundarioRepository.deleteById(id);
        tituloSecundarioSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/titulo-secundarios?query=:query : search for the tituloSecundario corresponding
     * to the query.
     *
     * @param query the query of the tituloSecundario search
     * @return the result of the search
     */
    @GetMapping("/_search/titulo-secundarios")
    public List<TituloSecundario> searchTituloSecundarios(@RequestParam String query) {
        log.debug("REST request to search TituloSecundarios for query {}", query);
        return StreamSupport
            .stream(tituloSecundarioSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
