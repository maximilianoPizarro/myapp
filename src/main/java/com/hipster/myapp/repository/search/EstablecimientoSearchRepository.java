package com.hipster.myapp.repository.search;

import com.hipster.myapp.domain.Establecimiento;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Establecimiento entity.
 */
public interface EstablecimientoSearchRepository extends ElasticsearchRepository<Establecimiento, Long> {
}
