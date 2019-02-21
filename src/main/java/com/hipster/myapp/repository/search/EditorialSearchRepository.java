package com.hipster.myapp.repository.search;

import com.hipster.myapp.domain.Editorial;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Editorial entity.
 */
public interface EditorialSearchRepository extends ElasticsearchRepository<Editorial, Long> {
}
