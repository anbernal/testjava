package com.anderson.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anderson.api.entities.Pedido;

public interface PedidosRepository extends JpaRepository<Pedido, Long> {

}
