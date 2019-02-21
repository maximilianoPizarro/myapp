package com.hipster.myapp.web.rest;
import com.hipster.myapp.domain.Editorial;
import com.hipster.myapp.service.EditorialService;
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
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Editorial.
 */
@RestController
@RequestMapping("/api")
public class EditorialResource {

    private final Logger log = LoggerFactory.getLogger(EditorialResource.class);

    private static final String ENTITY_NAME = "editorial";

    private final EditorialService editorialService;

    public EditorialResource(EditorialService editorialService) {
        this.editorialService = editorialService;
    }

    /**
     * POST  /editorials : Create a new editorial.
     *
     * @param editorial the editorial to create
     * @return the ResponseEntity with status 201 (Created) and with body the new editorial, or with status 400 (Bad Request) if the editorial has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/editorials")
    public ResponseEntity<Editorial> createEditorial(@RequestBody Editorial editorial) throws URISyntaxException {
        log.debug("REST request to save Editorial : {}", editorial);
        if (editorial.getId() != null) {
            throw new BadRequestAlertException("A new editorial cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Editorial result = editorialService.save(editorial);
        return ResponseEntity.created(new URI("/api/editorials/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /editorials : Updates an existing editorial.
     *
     * @param editorial the editorial to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated editorial,
     * or with status 400 (Bad Request) if the editorial is not valid,
     * or with status 500 (Internal Server Error) if the editorial couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/editorials")
    public ResponseEntity<Editorial> updateEditorial(@RequestBody Editorial editorial) throws URISyntaxException {
        log.debug("REST request to update Editorial : {}", editorial);
        if (editorial.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Editorial result = editorialService.save(editorial);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, editorial.getId().toString()))
            .body(result);
    }

    /**
     * GET  /editorials : get all the editorials.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of editorials in body
     */
    @GetMapping("/editorials")
    public List<Editorial> getAllEditorials() {
        log.debug("REST request to get all Editorials");
        return editorialService.findAll();
    }

    /**
     * GET  /editorials/:id : get the "id" editorial.
     *
     * @param id the id of the editorial to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the editorial, or with status 404 (Not Found)
     */
    @GetMapping("/editorials/{id}")
    public ResponseEntity<Editorial> getEditorial(@PathVariable Long id) {
        log.debug("REST request to get Editorial : {}", id);
        Optional<Editorial> editorial = editorialService.findOne(id);
        return ResponseUtil.wrapOrNotFound(editorial);
    }

    /**
     * DELETE  /editorials/:id : delete the "id" editorial.
     *
     * @param id the id of the editorial to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/editorials/{id}")
    public ResponseEntity<Void> deleteEditorial(@PathVariable Long id) {
        log.debug("REST request to delete Editorial : {}", id);
        editorialService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/editorials?query=:query : search for the editorial corresponding
     * to the query.
     *
     * @param query the query of the editorial search
     * @return the result of the search
     */
    @GetMapping("/_search/editorials")
    public List<Editorial> searchEditorials(@RequestParam String query) {
        log.debug("REST request to search Editorials for query {}", query);
        return editorialService.search(query);
    }

}
