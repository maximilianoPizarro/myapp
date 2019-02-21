package com.hipster.myapp.service;

import com.hipster.myapp.domain.Prueba;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Prueba.
 */
public interface PruebaService {

    /**
     * Save a prueba.
     *
     * @param prueba the entity to save
     * @return the persisted entity
     */
    Prueba save(Prueba prueba);

    /**
     * Get all the pruebas.
     *
     * @return the list of entities
     */
    List<Prueba> findAll();


    /**
     * Get the "id" prueba.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Prueba> findOne(Long id);

    /**
     * Delete the "id" prueba.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the prueba corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<Prueba> search(String query);
}
