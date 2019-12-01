package com.anderson.api.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.lang3.EnumUtils;
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

import com.anderson.api.dtos.PedidoDTO;
import com.anderson.api.entities.Cliente;
import com.anderson.api.entities.Pedido;
import com.anderson.api.enums.StatusEnum;
import com.anderson.api.response.Response;
import com.anderson.api.service.ClienteService;
import com.anderson.api.service.PedidoService;

@RestController
@RequestMapping("/api/pedidos")
public class PedidosController {
	
	private static final Logger log = LoggerFactory.getLogger(PedidosController.class);
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Autowired
	private PedidoService pedidoService;
	
	@Autowired
	private ClienteService clienteService;
	
	@Value("${paginacao.qtd_por_pagina}")
	private int qtdPorPagina;
	
	
	public PedidosController() {
		
	}
	
	/**
	 * Retorna a listagem de pedidos
	 * '
	 * 
	 * @return ResponseEntity<Response<PedidoDTO>>
	 */
	@SuppressWarnings("deprecation")
	@GetMapping
	public ResponseEntity<Response<Page<PedidoDTO>>> listarTodasCategorias(
			@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "ASC") String dir) {
		log.info("Buscando todos pedidos, página: {}", pag);
		Response<Page<PedidoDTO>> response = new Response<Page<PedidoDTO>>();

		PageRequest pageRequest = new PageRequest(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);
		Page<Pedido> pedidos = this.pedidoService.buscarTodosPedidos(pageRequest);
		Page<PedidoDTO> pedidoDTO = pedidos.map(pedido -> this.converterPedidoDTO(pedido));

		response.setData(pedidoDTO);
		return ResponseEntity.ok(response);
	}

	/**
	 * Adiciona uma novo Pedido.
	 * 
	 * @param pedido
	 * @param result
	 * @return ResponseEntity<Response<PedidoDTO>>
	 * @throws ParseException 
	 */
	@PostMapping
	public ResponseEntity<Response<PedidoDTO>> adicionar(@Valid @RequestBody PedidoDTO pedidoDTO,
			BindingResult result) throws ParseException {
		log.info("Adicionando Pedido: {}", pedidoDTO.toString());
		Response<PedidoDTO> response = new Response<PedidoDTO>();
		validarCliente(pedidoDTO, result);
		Pedido pedido = this.converterDTOParaPedido(pedidoDTO, result);

		if (result.hasErrors()) {
			log.error("Erro validando pedido: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		pedido = this.pedidoService.persistir(pedido);
		response.setData(this.converterPedidoDTO(pedido));
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Converte um pedidoDTO para uma entidade Pedido.
	 * 
	 * @param pedidoDTO
	 * @param result
	 * @return pedido
	 * @throws ParseException 
	 */
	private Pedido converterDTOParaPedido(PedidoDTO pedidoDTO, BindingResult result) throws ParseException {
		Pedido pedido = new Pedido();
			
		if (pedidoDTO.getId().isPresent()) {
			Optional<Pedido> ped = this.pedidoService.buscarPorId(pedidoDTO.getId().get());
			if (ped.isPresent()) {
				pedido = ped.get();
			} else {
				result.addError(new ObjectError("pedido", "Pedido não encontrado."));
			}
		}else {
			pedido.setCliente(new Cliente());
			pedido.getCliente().setId(pedidoDTO.getClienteId());
		}
		
		if (EnumUtils.isValidEnum(StatusEnum.class, pedidoDTO.getStatus())) {
			pedido.setStatus(StatusEnum.valueOf(pedidoDTO.getStatus()));
		} else {
			result.addError(new ObjectError("Status", "Status inválido."));
		}
		
		pedido.setData(this.dateFormat.parse(pedidoDTO.getData()));
		

		return pedido;
	}
	
	/**
	 * Converte uma entidade pedido para seu respectivo DTO.
	 * 
	 * @param pedido
	 * @return PedidoDTO
	 */
	private PedidoDTO converterPedidoDTO(Pedido pedido) {
		PedidoDTO pedidoDTO = new PedidoDTO();
	
		pedidoDTO.setId(Optional.of(pedido.getId()));
		pedidoDTO.setData(this.dateFormat.format(pedido.getData()));
		pedidoDTO.setStatus(pedido.getStatus().toString());
		pedidoDTO.setClienteId(pedido.getCliente().getId());
		
		return pedidoDTO;
	}
	
	/**
	 * Valida uma categoria, verificando se ela é existente e válida no
	 * sistema.
	 * 
	 * @param produtoDto
	 * @param result
	 */
	private void validarCliente(PedidoDTO pedidoDTO, BindingResult result) {
		if (pedidoDTO.getClienteId() == null) {
			result.addError(new ObjectError("cliente", "Cliente não informado."));
			return;
		}

		log.info("Validando cliente id {}: ", pedidoDTO.getClienteId());
		Optional<Cliente> cliente = this.clienteService.buscarPorId(pedidoDTO.getClienteId());
		if (!cliente.isPresent()) {
			result.addError(new ObjectError("Clienteaid", "Cliente não encontrado. ID inexistente."));
		}
	}
}
