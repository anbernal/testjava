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
import com.anderson.api.entities.Produto;
import com.anderson.api.repositories.ProdutosRepository;

@Service
public class ProdutoService {
	
	private static final Logger log = LoggerFactory.getLogger(ClientesController.class);

	@Autowired
	private ProdutosRepository produtos;
	

	public Produto persistir(Produto produto) {
		return produtos.save(produto);
	}
	
	public List<Produto> buscarTodos() {
		return produtos.findAll();
	}
	

	public void remover(Long id) {
		produtos.deleteById(id);
	}

	public Optional<Produto> buscarPorId(Long id) {
		return produtos.findById(id);
	}
	
	public Page<Produto> buscarTodosProdutos(PageRequest pageRequest) {
		log.info("Buscando todos Produto");
		return this.produtos.findAll(pageRequest);
	}

	
}
