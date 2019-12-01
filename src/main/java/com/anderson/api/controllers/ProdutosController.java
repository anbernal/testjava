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

import com.anderson.api.dtos.ProdutoDTO;
import com.anderson.api.entities.Categoria;
import com.anderson.api.entities.Produto;
import com.anderson.api.response.Response;
import com.anderson.api.service.CategoriaService;
import com.anderson.api.service.ProdutoService;

@RestController
@RequestMapping("/api/produtos")
public class ProdutosController {
	
	private static final Logger log = LoggerFactory.getLogger(ProdutosController.class);
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private CategoriaService categoriaService;
	
	@Value("${paginacao.qtd_por_pagina}")
	private int qtdPorPagina;
	
	
	public ProdutosController() {
		
	}
	
	/**
	 * Retorna a listagem de produto
	 * '
	 * 
	 * @return ResponseEntity<Response<ProdutoDTO>>
	 */
	@SuppressWarnings("deprecation")
	@GetMapping
	public ResponseEntity<Response<Page<ProdutoDTO>>> listarTodasCategorias(
			@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "ASC") String dir) {
		log.info("Buscando todos produtos, página: {}", pag);
		Response<Page<ProdutoDTO>> response = new Response<Page<ProdutoDTO>>();

		PageRequest pageRequest = new PageRequest(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);
		Page<Produto> produtos = this.produtoService.buscarTodosProdutos(pageRequest);
		Page<ProdutoDTO> produtoDTO = produtos.map(produto -> this.converterProdutoDTO(produto));

		response.setData(produtoDTO);
		return ResponseEntity.ok(response);
	}

	/**
	 * Adiciona uma novo Produto.
	 * 
	 * @param produto
	 * @param result
	 * @return ResponseEntity<Response<ProdutoDTO>>
	 * @throws ParseException 
	 */
	@PostMapping
	public ResponseEntity<Response<ProdutoDTO>> adicionar(@Valid @RequestBody ProdutoDTO produtoDTO,
			BindingResult result) throws ParseException {
		log.info("Adicionando Produto : {}", produtoDTO.toString());
		Response<ProdutoDTO> response = new Response<ProdutoDTO>();
		validarCategoria(produtoDTO, result);
		Produto produto = this.converterDTOParaProduto(produtoDTO, result);

		if (result.hasErrors()) {
			log.error("Erro validando produto: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		produto = this.produtoService.persistir(produto);
		response.setData(this.converterProdutoDTO(produto));
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Converte um produtoDTO para uma entidade Produto.
	 * 
	 * @param produtoDTO
	 * @param result
	 * @return produto
	 * @throws ParseException 
	 */
	private Produto converterDTOParaProduto(ProdutoDTO produtoDTO, BindingResult result) throws ParseException {
		Produto produto = new Produto();
			
		if (produtoDTO.getId().isPresent()) {
			Optional<Produto> prod = this.produtoService.buscarPorId(produtoDTO.getId().get());
			if (prod.isPresent()) {
				produto = prod.get();
			} else {
				result.addError(new ObjectError("produto", "Produto não encontrado."));
			}
		}else {
			produto.setCategoria(new Categoria());
			produto.getCategoria().setId(produtoDTO.getCategoriaId());
		}
		
		produto.setProduto(produtoDTO.getProduto());
		produto.setPreco(produtoDTO.getPreco());
		produto.setQuantidadeEstoque(produtoDTO.getQuantidadeEstoque());
		produto.setDescricao(produtoDTO.getDescricao());
		produto.setFoto(produtoDTO.getFoto());

		return produto;
	}
	
	/**
	 * Converte uma entidade Categoria para seu respectivo DTO.
	 * 
	 * @param categoria
	 * @return CategoriaDTO
	 */
	private ProdutoDTO converterProdutoDTO(Produto produto) {
		ProdutoDTO produtoDTO = new ProdutoDTO();
	
		produtoDTO.setId(Optional.of(produto.getId()));
		produtoDTO.setProduto(produto.getProduto());
		produtoDTO.setPreco(produto.getPreco());
		produtoDTO.setQuantidadeEstoque(produto.getQuantidadeEstoque());
		produtoDTO.setDescricao(produto.getDescricao());
		produtoDTO.setFoto(produto.getFoto());
		produtoDTO.setCategoriaId(produto.getCategoria().getId());
		
		return produtoDTO;
	}
	
	/**
	 * Valida uma categoria, verificando se ela é existente e válida no
	 * sistema.
	 * 
	 * @param produtoDto
	 * @param result
	 */
	private void validarCategoria(ProdutoDTO produtoDTO, BindingResult result) {
		if (produtoDTO.getCategoriaId() == null) {
			result.addError(new ObjectError("categoria", "Categoria não informado."));
			return;
		}

		log.info("Validando categoria id {}: ", produtoDTO.getCategoriaId());
		Optional<Categoria> categoria = this.categoriaService.buscarPorId(produtoDTO.getCategoriaId());
		if (!categoria.isPresent()) {
			result.addError(new ObjectError("Categoriaid", "Categoria não encontrado. ID inexistente."));
		}
	}
}
