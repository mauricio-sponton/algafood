package com.br.algafood.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.br.algafood.domain.model.Restaurante;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long>{

	@Query("from Restaurante r join fetch r.cozinha")
	List<Restaurante> findAll();
	
	@Query("select case when count(1) > 0 then true else false end from Restaurante rest join rest.responsaveis resp where rest.id = :restauranteId and resp.id = :usuarioId")
	boolean existsResponsavel(Long restauranteId, Long usuarioId);
}
