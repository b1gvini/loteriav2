package com.vinicius.loteria.domain.aposta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vinicius.loteria.domain.aposta.Aposta;

@Repository
public interface ApostaRepository extends JpaRepository<Aposta, Integer> {

	@Query(value = "SELECT * FROM apostas where usuario_id = :id order by criado_em ASC", nativeQuery = true)
	List<Aposta> findAllApostasById(@Param("id") Integer id);
}
