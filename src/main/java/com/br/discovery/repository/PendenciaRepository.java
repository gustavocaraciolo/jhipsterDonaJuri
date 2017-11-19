package com.br.discovery.repository;

import com.br.discovery.domain.Pendencia;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Pendencia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PendenciaRepository extends JpaRepository<Pendencia, Long> {
    @Query("select distinct pendencia from Pendencia pendencia left join fetch pendencia.advogados left join fetch pendencia.anexos")
    List<Pendencia> findAllWithEagerRelationships();

    @Query("select pendencia from Pendencia pendencia left join fetch pendencia.advogados left join fetch pendencia.anexos where pendencia.id =:id")
    Pendencia findOneWithEagerRelationships(@Param("id") Long id);

}
