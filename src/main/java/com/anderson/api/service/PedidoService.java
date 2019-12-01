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
import com.anderson.api.entities.Pedido;
import com.anderson.api.repositories.PedidosRepository;

@Service
public class PedidoService {

	private static final Logger log = LoggerFactory.getLogger(ClientesController.class);
	
	@Autowired
	private PedidosRepository pedidos;
	

	public Pedido persistir(Pedido pedido) {
		return pedidos.save(pedido);
	}
	
	public List<Pedido> buscarTodos() {
		return pedidos.findAll();
	}
	

	public void remover(Long id) {
		pedidos.deleteById(id);
	}

	public Optional<Pedido> buscarPorId(Long id) {
		return pedidos.findById(id);
	}
	
	public Page<Pedido> buscarTodosPedidos(PageRequest pageRequest) {
		log.info("Buscando todos pedidos");
		return this.pedidos.findAll(pageRequest);
	}
}
