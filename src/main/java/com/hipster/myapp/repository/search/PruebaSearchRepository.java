package com.hipster.myapp.repository.search;

import com.hipster.myapp.domain.Prueba;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Prueba entity.
 */
public interface PruebaSearchRepository extends ElasticsearchRepository<Prueba, Long> {
}
