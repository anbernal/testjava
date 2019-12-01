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
import com.anderson.api.entities.Categoria;
import com.anderson.api.repositories.CategoriasRepository;

@Service
public class CategoriaService {

	private static final Logger log = LoggerFactory.getLogger(ClientesController.class);
	
	@Autowired
	private CategoriasRepository categorias;
	

	public Categoria persistir(Categoria categoria) {
		return categorias.save(categoria);
	}
	
	public List<Categoria> buscarTodos() {
		return categorias.findAll();
	}
	

	public void remover(Long id) {
		categorias.deleteById(id);
	}

	public Optional<Categoria> buscarPorId(Long id) {
		return categorias.findById(id);
	}
	
	public Page<Categoria> buscarTodasCategorias(PageRequest pageRequest) {
		log.info("Buscando todas Categorias");
		return this.categorias.findAll(pageRequest);
	}
	
}
