package com.hipster.myapp.repository.search;

import com.hipster.myapp.domain.Documento;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Documento entity.
 */
public interface DocumentoSearchRepository extends ElasticsearchRepository<Documento, Long> {
}
