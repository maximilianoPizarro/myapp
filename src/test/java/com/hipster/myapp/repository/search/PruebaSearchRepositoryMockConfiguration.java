package com.hipster.myapp.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of PruebaSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class PruebaSearchRepositoryMockConfiguration {

    @MockBean
    private PruebaSearchRepository mockPruebaSearchRepository;

}
