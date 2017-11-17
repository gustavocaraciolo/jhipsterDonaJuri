package com.br.discovery.repository;

import com.br.discovery.domain.Escritorio;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Escritorio entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EscritorioRepository extends JpaRepository<Escritorio, Long> {

}
