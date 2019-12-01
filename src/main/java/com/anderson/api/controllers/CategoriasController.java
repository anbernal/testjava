package com.anderson.api.controllers;

import java.text.ParseException;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anderson.api.dtos.CategoriaDTO;
import com.anderson.api.entities.Categoria;
import com.anderson.api.response.Response;
import com.anderson.api.service.CategoriaService;

@RestController
@RequestMapping("/api/categorias")
public class CategoriasController {
	
	private static final Logger log = LoggerFactory.getLogger(CategoriasController.class);
	
	@Autowired
	private CategoriaService categoriaService;
	
	@Value("${paginacao.qtd_por_pagina}")
	private int qtdPorPagina;
	
	
	public CategoriasController() {
		
	}
	
	/**
	 * Retorna a listagem de categorias
	 * '
	 * 
	 * @return ResponseEntity<Response<CategoriaDTO>>
	 */
	@SuppressWarnings("deprecation")
	@GetMapping
	public ResponseEntity<Response<Page<CategoriaDTO>>> listarToddasCategorias(
			@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir) {
		log.info("Buscando todas categorias, página: {}", pag);
		Response<Page<CategoriaDTO>> response = new Response<Page<CategoriaDTO>>();

		PageRequest pageRequest = new PageRequest(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);
		Page<Categoria> categorias = this.categoriaService.buscarTodasCategorias(pageRequest);
		Page<CategoriaDTO> categoriaDTO = categorias.map(categoria -> this.converterCategoriaDTO(categoria));

		response.setData(categoriaDTO);
		return ResponseEntity.ok(response);
	}

	/**
	 * Adiciona uma nova Categoria.
	 * 
	 * @param categoria
	 * @param result
	 * @return ResponseEntity<Response<CategoriaDTO>>
	 * @throws ParseException 
	 */
	@PostMapping
	public ResponseEntity<Response<CategoriaDTO>> adicionar(@Valid @RequestBody CategoriaDTO categoriaDTO,
			BindingResult result) throws ParseException {
		log.info("Adicionando Categoria: {}", categoriaDTO.toString());
		Response<CategoriaDTO> response = new Response<CategoriaDTO>();
		Categoria categoria = this.converterDTOParaCategoria(categoriaDTO, result);

		if (result.hasErrors()) {
			log.error("Erro validando categoria: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		categoria = this.categoriaService.persistir(categoria);
		response.setData(this.converterCategoriaDTO(categoria));
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Converte um categoriaDTO para uma entidade Categoria.
	 * 
	 * @param categoriaDTO
	 * @param result
	 * @return Categoria
	 * @throws ParseException 
	 */
	private Categoria converterDTOParaCategoria(CategoriaDTO categoriaDTO, BindingResult result) throws ParseException {
		Categoria categoria = new Categoria();
			
		if (categoriaDTO.getId().isPresent()) {
			Optional<Categoria> cat = this.categoriaService.buscarPorId(categoriaDTO.getId().get());
			if (cat.isPresent()) {
				categoria = cat.get();
			} else {
				result.addError(new ObjectError("categoria", "Categoria não encontrado."));
			}
		}
		
		categoria.setNome(categoriaDTO.getNome());
		categoria.setNome(categoriaDTO.getNome());


		return categoria;
	}
	
	/**
	 * Converte uma entidade Categoria para seu respectivo DTO.
	 * 
	 * @param categoria
	 * @return CategoriaDTO
	 */
	private CategoriaDTO converterCategoriaDTO(Categoria categoria) {
		
		CategoriaDTO categoriaDTO = new CategoriaDTO();
		
		categoriaDTO.setId(Optional.of(categoria.getId()));
		categoriaDTO.setNome(categoria.getNome());
		
		return categoriaDTO;
	}
}
