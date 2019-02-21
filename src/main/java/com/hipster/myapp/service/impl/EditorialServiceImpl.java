package com.hipster.myapp.service.impl;

import com.hipster.myapp.service.EditorialService;
import com.hipster.myapp.domain.Editorial;
import com.hipster.myapp.repository.EditorialRepository;
import com.hipster.myapp.repository.search.EditorialSearchRepository;
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
 * Service Implementation for managing Editorial.
 */
@Service
@Transactional
public class EditorialServiceImpl implements EditorialService {

    private final Logger log = LoggerFactory.getLogger(EditorialServiceImpl.class);

    private final EditorialRepository editorialRepository;

    private final EditorialSearchRepository editorialSearchRepository;

    public EditorialServiceImpl(EditorialRepository editorialRepository, EditorialSearchRepository editorialSearchRepository) {
        this.editorialRepository = editorialRepository;
        this.editorialSearchRepository = editorialSearchRepository;
    }

    /**
     * Save a editorial.
     *
     * @param editorial the entity to save
     * @return the persisted entity
     */
    @Override
    public Editorial save(Editorial editorial) {
        log.debug("Request to save Editorial : {}", editorial);
        Editorial result = editorialRepository.save(editorial);
        editorialSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the editorials.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Editorial> findAll() {
        log.debug("Request to get all Editorials");
        return editorialRepository.findAll();
    }


    /**
     * Get one editorial by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Editorial> findOne(Long id) {
        log.debug("Request to get Editorial : {}", id);
        return editorialRepository.findById(id);
    }

    /**
     * Delete the editorial by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Editorial : {}", id);        editorialRepository.deleteById(id);
        editorialSearchRepository.deleteById(id);
    }

    /**
     * Search for the editorial corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Editorial> search(String query) {
        log.debug("Request to search Editorials for query {}", query);
        return StreamSupport
            .stream(editorialSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
