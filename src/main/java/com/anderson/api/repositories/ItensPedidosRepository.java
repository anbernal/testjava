package com.anderson.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anderson.api.entities.ItemPedido;

public interface ItensPedidosRepository extends JpaRepository<ItemPedido, Long>{

}
