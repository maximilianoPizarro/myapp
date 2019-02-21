package com.hipster.myapp.service;

import java.util.List;
import java.util.Optional;

import com.hipster.myapp.domain.Persona;

public interface PersonaService {
    /**
     * Save a book.
     *
     * @param book the entity to save
     * @return the persisted entity
     */
    Persona save(Persona persona);

    /**
     * Get all the personas.
     *
     * @return the list of entities
     */
    List<Persona> findAll();


    /**
     * Get the "id" persona.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Persona> findOne(Long id);

    /**
     * Delete the "id" persona.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the persona corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<Persona> search(String query);

}
