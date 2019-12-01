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
import com.anderson.api.entities.ItemPedido;
import com.anderson.api.repositories.ItensPedidosRepository;

@Service
public class ItemPedidoService {

	private static final Logger log = LoggerFactory.getLogger(ClientesController.class);
	
	@Autowired
	private ItensPedidosRepository itensPedidos;
	

	public ItemPedido persistir(ItemPedido itemPedido) {
		return itensPedidos.save(itemPedido);
	}
	
	public List<ItemPedido> buscarTodos() {
		return itensPedidos.findAll();
	}
	

	public void remover(Long id) {
		itensPedidos.deleteById(id);
	}

	public Optional<ItemPedido> buscarPorId(Long id) {
		return itensPedidos.findById(id);
	}
	
	public Page<ItemPedido> buscarTodosItemPedido(PageRequest pageRequest) {
		log.info("Buscando todos Item");
		return this.itensPedidos.findAll(pageRequest);
	}
	
}
