package com.anderson.api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anderson.api.entities.Cliente;

public interface ClientesRepository extends JpaRepository<Cliente, Long> {
	
	Optional<Cliente> findById(Long id);
	
}
