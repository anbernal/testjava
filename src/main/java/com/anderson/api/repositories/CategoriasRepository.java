package com.anderson.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anderson.api.entities.Categoria;

public interface CategoriasRepository extends JpaRepository<Categoria, Long> {

	Categoria findByNome(String nome);

}
