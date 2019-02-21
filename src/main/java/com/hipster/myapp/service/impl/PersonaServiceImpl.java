package com.hipster.myapp.service.impl;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hipster.myapp.domain.Persona;
import com.hipster.myapp.repository.PersonaRepository;
import com.hipster.myapp.repository.search.PersonaSearchRepository;
import com.hipster.myapp.service.PersonaService;


/**
 * Service Implementation for managing Book.
 */
@Service
@Transactional
public class PersonaServiceImpl implements PersonaService {
    private final Logger log = LoggerFactory.getLogger(PersonaServiceImpl.class);

    private final PersonaRepository personaRepository;

    private final PersonaSearchRepository personaSearchRepository;

    public PersonaServiceImpl(PersonaRepository personaRepository, PersonaSearchRepository personaSearchRepository) {
        this.personaRepository = personaRepository;
        this.personaSearchRepository = personaSearchRepository;
    }

    /**
     * Save a persona.
     *
     * @param persona the entity to save
     * @return the persisted entity
     */
    @Override
    public Persona save(Persona persona) {
        log.debug("Request to save persona : {}", persona);
        Persona result = personaRepository.save(persona);
        personaSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the personas.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Persona> findAll() {
        log.debug("Request to get all personas");
        return personaRepository.findAll();
    }


    /**
     * Get one persona by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Persona> findOne(Long id) {
        log.debug("Request to get Persona : {}", id);
        return personaRepository.findById(id);
    }

    /**
     * Delete the book by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete persona : {}", id);        personaRepository.deleteById(id);
        personaSearchRepository.deleteById(id);
    }

    /**
     * Search for the book corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Persona> search(String query) {
        log.debug("Request to search personas for query {}", query);
        return StreamSupport
            .stream(personaSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}
