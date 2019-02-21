package com.hipster.myapp.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of DocumentoSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class DocumentoSearchRepositoryMockConfiguration {

    @MockBean
    private DocumentoSearchRepository mockDocumentoSearchRepository;

}
