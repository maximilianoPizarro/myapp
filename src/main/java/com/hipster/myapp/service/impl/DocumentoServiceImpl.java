package com.hipster.myapp.service.impl;

import com.hipster.myapp.service.DocumentoService;
import com.hipster.myapp.domain.Documento;
import com.hipster.myapp.repository.DocumentoRepository;
import com.hipster.myapp.repository.search.DocumentoSearchRepository;
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
 * Service Implementation for managing Documento.
 */
@Service
@Transactional
public class DocumentoServiceImpl implements DocumentoService {

    private final Logger log = LoggerFactory.getLogger(DocumentoServiceImpl.class);

    private final DocumentoRepository documentoRepository;

    private final DocumentoSearchRepository documentoSearchRepository;

    public DocumentoServiceImpl(DocumentoRepository documentoRepository, DocumentoSearchRepository documentoSearchRepository) {
        this.documentoRepository = documentoRepository;
        this.documentoSearchRepository = documentoSearchRepository;
    }

    /**
     * Save a documento.
     *
     * @param documento the entity to save
     * @return the persisted entity
     */
    @Override
    public Documento save(Documento documento) {
        log.debug("Request to save Documento : {}", documento);
        Documento result = documentoRepository.save(documento);
        documentoSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the documentos.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Documento> findAll() {
        log.debug("Request to get all Documentos");
        return documentoRepository.findAll();
    }


    /**
     * Get one documento by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Documento> findOne(Long id) {
        log.debug("Request to get Documento : {}", id);
        return documentoRepository.findById(id);
    }

    /**
     * Delete the documento by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Documento : {}", id);        documentoRepository.deleteById(id);
        documentoSearchRepository.deleteById(id);
    }

    /**
     * Search for the documento corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Documento> search(String query) {
        log.debug("Request to search Documentos for query {}", query);
        return StreamSupport
            .stream(documentoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
