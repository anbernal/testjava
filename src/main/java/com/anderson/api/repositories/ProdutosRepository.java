package com.anderson.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anderson.api.entities.Produto;

public interface ProdutosRepository extends JpaRepository<Produto, Long> {

	Produto findByProduto(String produto);

}
