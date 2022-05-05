package com.br.algafood.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.br.algafood.domain.model.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long>, JpaSpecificationExecutor<Pedido>{
	
	@Query("from Pedido where codigo = :codigo")
	Optional<Pedido> findByCodigo(String codigo);

	@Query("from Pedido p join fetch p.cliente join fetch p.restaurante r join fetch r.cozinha")
	List<Pedido> findAll();
	
	@Query("select case when count(1) > 0 then true else false end from Pedido p join p.restaurante rest join rest.responsaveis resp where p.codigo = :codigoPedido and resp.id = :usuarioId")
	boolean isPedidoGerenciadoPor(String codigoPedido, Long usuarioId);
}
