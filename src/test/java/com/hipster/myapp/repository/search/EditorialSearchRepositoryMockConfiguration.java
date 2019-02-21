package com.hipster.myapp.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of EditorialSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class EditorialSearchRepositoryMockConfiguration {

    @MockBean
    private EditorialSearchRepository mockEditorialSearchRepository;

}
