package com.hipster.myapp.service.impl;

import com.hipster.myapp.service.PruebaService;
import com.hipster.myapp.domain.Prueba;
import com.hipster.myapp.repository.PruebaRepository;
import com.hipster.myapp.repository.search.PruebaSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Prueba.
 */
@Service
@Transactional
public class PruebaServiceImpl implements PruebaService {

    private final Logger log = LoggerFactory.getLogger(PruebaServiceImpl.class);

    private final PruebaRepository pruebaRepository;

    private final PruebaSearchRepository pruebaSearchRepository;

    public PruebaServiceImpl(PruebaRepository pruebaRepository, PruebaSearchRepository pruebaSearchRepository) {
        this.pruebaRepository = pruebaRepository;
        this.pruebaSearchRepository = pruebaSearchRepository;
    }

    /**
     * Save a prueba.
     *
     * @param prueba the entity to save
     * @return the persisted entity
     */
    @Override
    public Prueba save(Prueba prueba) {
        log.debug("Request to save Prueba : {}", prueba);
        Prueba result = pruebaRepository.save(prueba);
        pruebaSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the pruebas.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Prueba> findAll() {
        log.debug("Request to get all Pruebas");
        return pruebaRepository.findAll();
    }


    /**
     * Get one prueba by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Prueba> findOne(Long id) {
        log.debug("Request to get Prueba : {}", id);
        return pruebaRepository.findById(id);
    }

    /**
     * Delete the prueba by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Prueba : {}", id);        pruebaRepository.deleteById(id);
        pruebaSearchRepository.deleteById(id);
    }

    /**
     * Search for the prueba corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Prueba> search(String query) {
        log.debug("Request to search Pruebas for query {}", query);
        return StreamSupport
            .stream(pruebaSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
