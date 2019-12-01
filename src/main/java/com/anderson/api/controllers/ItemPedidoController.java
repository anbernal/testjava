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

import com.anderson.api.dtos.ItemPedidoDTO;
import com.anderson.api.entities.ItemPedido;
import com.anderson.api.entities.Pedido;
import com.anderson.api.entities.Produto;
import com.anderson.api.response.Response;
import com.anderson.api.service.ItemPedidoService;
import com.anderson.api.service.PedidoService;
import com.anderson.api.service.ProdutoService;

@RestController
@RequestMapping("/api/item-pedido")
public class ItemPedidoController {
	
	private static final Logger log = LoggerFactory.getLogger(ItemPedidoController.class);
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private PedidoService pedidoService;
	
	@Autowired
	private ItemPedidoService itemPedidoService;
	
	@Value("${paginacao.qtd_por_pagina}")
	private int qtdPorPagina;
	
	
	public ItemPedidoController() {
		
	}
	
	/**
	 * Retorna a listagem de item pedido
	 * '
	 * 
	 * @return ResponseEntity<Response<ItemPadidoDTO>>
	 */
	@SuppressWarnings("deprecation")
	@GetMapping
	public ResponseEntity<Response<Page<ItemPedidoDTO>>> listarTodasCategorias(
			@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "ASC") String dir) {
		log.info("Buscando todos item pedido, página: {}", pag);
		Response<Page<ItemPedidoDTO>> response = new Response<Page<ItemPedidoDTO>>();

		PageRequest pageRequest = new PageRequest(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);
		Page<ItemPedido> itensPedidos = this.itemPedidoService.buscarTodosItemPedido(pageRequest);
		Page<ItemPedidoDTO> itemPedidoDTO = itensPedidos.map(itemPedido -> this.converterItemPedidoDTO(itemPedido));

		response.setData(itemPedidoDTO);
		return ResponseEntity.ok(response);
	}

	/**
	 * Adiciona uma Item no Pedido.
	 * 
	 * @param Item Pedido
	 * @param result
	 * @return ResponseEntity<Response<ItemPedidoDTO>>
	 * @throws ParseException 
	 */
	@PostMapping
	public ResponseEntity<Response<ItemPedidoDTO>> adicionar(@Valid @RequestBody ItemPedidoDTO itemPedidoDTO,
			BindingResult result) throws ParseException {
		log.info("Adicionando Item no Pedido : {}", itemPedidoDTO.toString());
		Response<ItemPedidoDTO> response = new Response<ItemPedidoDTO>();
		validarProduto(itemPedidoDTO, result);
		validarPedido(itemPedidoDTO, result);
		ItemPedido itemPedido = this.converterDTOParaItemPedido(itemPedidoDTO, result);

		if (result.hasErrors()) {
			log.error("Erro validando Item pedido: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		itemPedido = this.itemPedidoService.persistir(itemPedido);
		response.setData(this.converterItemPedidoDTO(itemPedido));
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Converte um itemPedidoDTO para uma entidade ItemPedido.
	 * 
	 * @param itemPedidoDTO
	 * @param result
	 * @return itemPedido
	 * @throws ParseException 
	 */
	private ItemPedido converterDTOParaItemPedido(ItemPedidoDTO itemPedidoDTO, BindingResult result) throws ParseException {
		ItemPedido itemPedido = new ItemPedido();
			
		if (itemPedidoDTO.getId().isPresent()) {
			Optional<ItemPedido> itmp = this.itemPedidoService.buscarPorId(itemPedidoDTO.getId().get());
			if (itmp.isPresent()) {
				itemPedido = itmp.get();
			} else {
				result.addError(new ObjectError("Item Pedido", "Item Pedido não encontrado."));
			}
		}else {
			itemPedido.setPedido(new Pedido());
			itemPedido.getPedido().setId(itemPedidoDTO.getPedidoId());
			
			itemPedido.setProduto(new Produto());
			itemPedido.getProduto().setId(itemPedidoDTO.getProdutoId());
		}
		
		itemPedido.setValor(itemPedidoDTO.getValor());
		itemPedido.setQuantidade(itemPedidoDTO.getQuantidade());
		itemPedido.setSubtotal(itemPedidoDTO.getSubtotal());

		return itemPedido;
	}
	
	/**
	 * Converte uma entidade ItemPedido para seu respectivo DTO.
	 * 
	 * @param itemPedido
	 * @return itemPedidoDTO
	 */
	private ItemPedidoDTO converterItemPedidoDTO(ItemPedido itemPedido) {
		ItemPedidoDTO itemPedidoDTO = new ItemPedidoDTO();
	
		itemPedidoDTO.setId(Optional.of(itemPedido.getId()));
		itemPedidoDTO.setValor(itemPedido.getValor());
		itemPedidoDTO.setQuantidade(itemPedido.getQuantidade());
		itemPedidoDTO.setSubtotal(itemPedido.getSubtotal());
		itemPedidoDTO.setPedidoId(itemPedido.getPedido().getId());
		itemPedidoDTO.setProdutoId(itemPedido.getProduto().getId());
		
		return itemPedidoDTO;
	}
	
	/**
	 * Valida um pedid, verificando se ela é existente e válida no
	 * sistema.
	 * 
	 * @param pedidoDto
	 * @param result
	 */
	private void validarPedido(ItemPedidoDTO itemPedidoDTO, BindingResult result) {
		if (itemPedidoDTO.getPedidoId() == null) {
			result.addError(new ObjectError("pedido", "Pedido não informado."));
			return;
		}

		log.info("Validando pedido id {}: ", itemPedidoDTO.getPedidoId());
		Optional<Pedido> pedido = this.pedidoService.buscarPorId(itemPedidoDTO.getPedidoId());
		if (!pedido.isPresent()) {
			result.addError(new ObjectError("Pedidoid", "Pedido não encontrado. ID inexistente."));
		}
	}
	
	/**
	 * Valida um produto, verificando se ele é existente e válido no
	 * sistema.
	 * 
	 * @param itemPedidoDto
	 * @param result
	 */
	private void validarProduto(ItemPedidoDTO itemPedidoDTO, BindingResult result) {
		if (itemPedidoDTO.getProdutoId() == null) {
			result.addError(new ObjectError("produto", "Produto não informado."));
			return;
		}

		log.info("Validando produto id {}: ", itemPedidoDTO.getProdutoId());
		Optional<Produto> produto = this.produtoService.buscarPorId(itemPedidoDTO.getProdutoId());
		if (!produto.isPresent()) {
			result.addError(new ObjectError("Produtoid", "Produto não encontrado. ID inexistente."));
		}
	}
}
