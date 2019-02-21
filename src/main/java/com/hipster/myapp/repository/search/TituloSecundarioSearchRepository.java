package com.hipster.myapp.repository.search;

import com.hipster.myapp.domain.TituloSecundario;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the TituloSecundario entity.
 */
public interface TituloSecundarioSearchRepository extends ElasticsearchRepository<TituloSecundario, Long> {
}
