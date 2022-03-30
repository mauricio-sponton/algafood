package com.br.algafood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.algafood.domain.model.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long>{

}
