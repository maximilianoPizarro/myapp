package com.hipster.myapp.service;

import com.hipster.myapp.domain.Documento;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Documento.
 */
public interface DocumentoService {

    /**
     * Save a documento.
     *
     * @param documento the entity to save
     * @return the persisted entity
     */
    Documento save(Documento documento);

    /**
     * Get all the documentos.
     *
     * @return the list of entities
     */
    List<Documento> findAll();


    /**
     * Get the "id" documento.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Documento> findOne(Long id);

    /**
     * Delete the "id" documento.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the documento corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<Documento> search(String query);
}
