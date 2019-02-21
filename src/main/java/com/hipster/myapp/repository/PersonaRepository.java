package com.hipster.myapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hipster.myapp.domain.Persona;

/**
 * Spring Data  repository for the TituloSecundario entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long>{

}
