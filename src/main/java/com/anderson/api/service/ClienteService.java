package com.anderson.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anderson.api.entities.Cliente;
import com.anderson.api.repositories.ClientesRepository;

@Service
public class ClienteService {

	@Autowired
	private ClientesRepository clientes;
	

	public Cliente persistir(Cliente cliente) {
		return clientes.save(cliente);
	}
	
	public List<Cliente> buscarTodos() {
		return clientes.findAll();
	}
	

	public void remover(Long id) {
		clientes.deleteById(id);
	}

	public Optional<Cliente> buscarPorId(Long id) {
		return clientes.findById(id);
	}
	
	
	
}
