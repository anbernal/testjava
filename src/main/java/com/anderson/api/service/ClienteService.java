package com.anderson.api.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.anderson.api.controllers.ClientesController;
import com.anderson.api.entities.Cliente;
import com.anderson.api.repositories.ClientesRepository;

@Service
public class ClienteService {
	
	private static final Logger log = LoggerFactory.getLogger(ClientesController.class);

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

	public Page<Cliente> buscarTodosCliente(PageRequest pageRequest) {
		log.info("Buscando todos Clientes");
		return this.clientes.findAll(pageRequest);
	}
	
}
