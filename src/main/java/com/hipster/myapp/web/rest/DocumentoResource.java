package com.hipster.myapp.web.rest;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.hipster.myapp.domain.Documento;
import com.hipster.myapp.domain.StampResponse;
import com.hipster.myapp.service.BlockchainService;
import com.hipster.myapp.service.DocumentoService;
import com.hipster.myapp.web.rest.errors.BadRequestAlertException;
import com.hipster.myapp.web.rest.util.HeaderUtil;

import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.hipster.myapp.web.rest.TituloSecundarioResource;
import com.hipster.myapp.domain.TituloSecundario;
import com.hipster.myapp.repository.TituloSecundarioRepository;
import com.hipster.myapp.repository.search.TituloSecundarioSearchRepository;

/**
 * REST controller for managing Documento.
 */
@RestController
@RequestMapping("/api")
public class DocumentoResource {

    private final Logger log = LoggerFactory.getLogger(DocumentoResource.class);

    private static final String ENTITY_NAME = "documento";

    private final DocumentoService documentoService;
    
    private final TituloSecundarioRepository tituloSecundarioRepository;

    private final TituloSecundarioSearchRepository tituloSecundarioSearchRepository;
    
    private final TituloSecundarioResource tituloSecundarioResource;
    
    private final BlockchainService blochchainService;
    
   


    public DocumentoResource(DocumentoService documentoService,TituloSecundarioRepository tituloSecundarioRepository, TituloSecundarioSearchRepository tituloSecundarioSearchRepository,TituloSecundarioResource tituloSecundarioResource,BlockchainService blochchainService ) {
        this.documentoService = documentoService;
        this.tituloSecundarioRepository = tituloSecundarioRepository;
        this.tituloSecundarioSearchRepository = tituloSecundarioSearchRepository;
        this.tituloSecundarioResource=tituloSecundarioResource;
        this.blochchainService=blochchainService;
    }

    /**
     * POST  /documentos : Create a new documento.
     *
     * @param documento the documento to create
     * @return the ResponseEntity with status 201 (Created) and with body the new documento, or with status 400 (Bad Request) if the documento has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     * @throws InterruptedException 
     */
    @PostMapping("/documentos")
    public ResponseEntity<Documento> createDocumento(@RequestBody Documento documento) throws URISyntaxException, InterruptedException {
        log.debug("REST request to save Documento : {}", documento);
        
        System.out.println(documento.toString());
        if (documento.getId() != null) {
            throw new BadRequestAlertException("A new documento cannot already have an ID", ENTITY_NAME, "idexists");
        }       
        
        try {
        	ObjectMapper mapper = new ObjectMapper();
        	TituloSecundario titulo=mapper.readValue(documento.getTipodocumento(), TituloSecundario.class);     
			StampResponse respuestaAlta=new Gson().fromJson(blochchainService.altaBloque(blochchainService.hashDocumento(documento.getTipodocumento())),StampResponse.class);
			System.out.println(respuestaAlta.toString());
			Thread.sleep(50000);			
			System.out.println(blochchainService.verificarBloque(blochchainService.hashDocumento(documento.getTipodocumento()),respuestaAlta.getTemporary_rd()));		
        	ResponseEntity<TituloSecundario> resultado=tituloSecundarioResource.createTituloSecundario(titulo);
        	if(resultado.getStatusCodeValue()==400) {
        		throw new BadRequestAlertException("Mensaje de error:", resultado.getBody().toString(), "-");
        	}
	        
		} catch (JsonParseException e) {			
			throw new BadRequestAlertException("Mensaje de error:", ENTITY_NAME, "-");
		} catch (JsonMappingException e) {
        	throw new BadRequestAlertException("Error:", ENTITY_NAME,"Tipo Documento mal Serializado");

		} catch (IOException e) {
			//e.printStackTrace();
        	throw new BadRequestAlertException("Error:", ENTITY_NAME,"Tipo Documento mal Serializado");

		}
        
        Documento result = documentoService.save(documento);
        
        return ResponseEntity.created(new URI("/api/documentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /documentos : Updates an existing documento.
     *
     * @param documento the documento to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated documento,
     * or with status 400 (Bad Request) if the documento is not valid,
     * or with status 500 (Internal Server Error) if the documento couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/documentos")
    public ResponseEntity<Documento> updateDocumento(@RequestBody Documento documento) throws URISyntaxException {
        log.debug("REST request to update Documento : {}", documento);
        if (documento.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Documento result = documentoService.save(documento);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, documento.getId().toString()))
            .body(result);
    }

    /**
     * GET  /documentos : get all the documentos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of documentos in body
     */
    @GetMapping("/documentos")
    public List<Documento> getAllDocumentos() {
        log.debug("REST request to get all Documentos");
        return documentoService.findAll();
    }

    /**
     * GET  /documentos/:id : get the "id" documento.
     *
     * @param id the id of the documento to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the documento, or with status 404 (Not Found)
     */
    @GetMapping("/documentos/{id}")
    public ResponseEntity<Documento> getDocumento(@PathVariable Long id) {
        log.debug("REST request to get Documento : {}", id);
        Optional<Documento> documento = documentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(documento);
    }

    /**
     * DELETE  /documentos/:id : delete the "id" documento.
     *
     * @param id the id of the documento to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/documentos/{id}")
    public ResponseEntity<Void> deleteDocumento(@PathVariable Long id) {
        log.debug("REST request to delete Documento : {}", id);
        documentoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/documentos?query=:query : search for the documento corresponding
     * to the query.
     *
     * @param query the query of the documento search
     * @return the result of the search
     */
    @GetMapping("/_search/documentos")
    public List<Documento> searchDocumentos(@RequestParam String query) {
        log.debug("REST request to search Documentos for query {}", query);
        return documentoService.search(query);
    }

}
