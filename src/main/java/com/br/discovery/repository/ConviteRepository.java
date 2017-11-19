package com.br.discovery.repository;

import com.br.discovery.domain.Convite;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Convite entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConviteRepository extends JpaRepository<Convite, Long> {

}
