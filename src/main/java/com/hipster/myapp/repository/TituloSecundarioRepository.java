package com.hipster.myapp.repository;

import com.hipster.myapp.domain.TituloSecundario;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TituloSecundario entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TituloSecundarioRepository extends JpaRepository<TituloSecundario, Long> {

}
