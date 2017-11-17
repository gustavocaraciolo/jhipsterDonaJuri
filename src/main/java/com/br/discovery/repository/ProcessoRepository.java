package com.br.discovery.repository;

import com.br.discovery.domain.Processo;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Processo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProcessoRepository extends JpaRepository<Processo, Long> {
    @Query("select distinct processo from Processo processo left join fetch processo.advogados")
    List<Processo> findAllWithEagerRelationships();

    @Query("select processo from Processo processo left join fetch processo.advogados where processo.id =:id")
    Processo findOneWithEagerRelationships(@Param("id") Long id);

}
