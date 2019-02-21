package com.hipster.myapp.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import com.hipster.myapp.domain.Persona;

public interface PersonaSearchRepository extends ElasticsearchRepository<Persona,Long> {

}
