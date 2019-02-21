package com.hipster.myapp.repository;

import com.hipster.myapp.domain.Establecimiento;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Establecimiento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EstablecimientoRepository extends JpaRepository<Establecimiento, Long> {

}
