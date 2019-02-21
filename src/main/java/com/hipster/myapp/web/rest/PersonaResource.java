package com.hipster.myapp.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.xml.ws.RequestWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hipster.myapp.domain.Persona;
import com.hipster.myapp.domain.Prueba;
import com.hipster.myapp.service.PersonaService;
import com.hipster.myapp.web.rest.errors.BadRequestAlertException;
import com.hipster.myapp.web.rest.util.HeaderUtil;

import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing Persona.
 */
@RestController
@RequestMapping("/api")
public class PersonaResource {

    private final Logger log = LoggerFactory.getLogger(PersonaResource.class);

    private static final String ENTITY_NAME = "persona";

    private final PersonaService personaService;

    public PersonaResource(PersonaService personaService) {
        this.personaService = personaService;
    }

    /**
     * POST  /personas : Create a new persona.
     *
     * @param persona the persona to create
     * @return the ResponseEntity with status 201 (Created) and with body the new persona, or with status 400 (Bad Request) if the persona has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/personas")
    public ResponseEntity<Persona> createpersona(@RequestBody Persona persona) throws URISyntaxException {
        log.debug("REST request to save persona : {}", persona);
        if (persona.getId() != null) {
            throw new BadRequestAlertException("A new persona cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Persona result = personaService.save(persona);
        return ResponseEntity.created(new URI("/api/personas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /personas : Updates an existing persona.
     *
     * @param persona the persona to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated persona,
     * or with status 400 (Bad Request) if the persona is not valid,
     * or with status 500 (Internal Server Error) if the persona couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/personas")
    public ResponseEntity<Persona> updatepersona(@RequestBody Persona persona) throws URISyntaxException {
        log.debug("REST request to update persona : {}", persona);
        if (persona.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Persona result = personaService.save(persona);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, persona.getId().toString()))
            .body(result);
    }

    /**
     * GET  /personas : get all the personas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of personas in body
     */
    @GetMapping("/personas")
    public List<Persona> getAllpersonas() {
        log.debug("REST request to get all personas");
        return personaService.findAll();
    }

    /**
     * GET  /personas/:id : get the "id" persona.
     *
     * @param id the id of the persona to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the persona, or with status 404 (Not Found)
     */
    @GetMapping("/personas/{id}")
    public ResponseEntity<Persona> getpersona(@PathVariable Long id) {
        log.debug("REST request to get persona : {}", id);
        Optional<Persona> persona = personaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(persona);
    }

    /**
     * DELETE  /personas/:id : delete the "id" persona.
     *
     * @param id the id of the persona to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/personas/{id}")
    public ResponseEntity<Void> deletepersona(@PathVariable Long id) {
        log.debug("REST request to delete persona : {}", id);
        personaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/personas?query=:query : search for the persona corresponding
     * to the query.
     *
     * @param query the query of the persona search
     * @return the result of the search
     */
    @GetMapping("/_search/personas")
    public List<Persona> searchpersonas(@RequestParam String query) {
        log.debug("REST request to search personas for query {}", query);
        return personaService.search(query);
    }


}
