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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anderson.api.dtos.ClienteDTO;
import com.anderson.api.entities.Cliente;
import com.anderson.api.response.Response;
import com.anderson.api.service.ClienteService;
import com.anderson.api.utils.PasswordUtils;

@RestController
@RequestMapping("/api/clientes")
public class ClientesController {
	
	private static final Logger log = LoggerFactory.getLogger(ClientesController.class);
	
	@Autowired
	private ClienteService clienteService;
	
	@Value("${paginacao.qtd_por_pagina}")
	private int qtdPorPagina;
	
	
	public ClientesController() {
		
	}
	
	/**
	 * Retorna a listagem de cliente
	 * '
	 * 
	 * @return ResponseEntity<Response<ClienteDto>>
	 */
	@SuppressWarnings("deprecation")
	@GetMapping
	public ResponseEntity<Response<Page<ClienteDTO>>> listarTodosClientes(
			@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir) {
		log.info("Buscando todos Clientes, página: {}", pag);
		Response<Page<ClienteDTO>> response = new Response<Page<ClienteDTO>>();

		PageRequest pageRequest = new PageRequest(pag, this.qtdPorPagina, Direction.valueOf(dir), ord);
		Page<Cliente> clientes = this.clienteService.buscarTodosCliente(pageRequest);
		Page<ClienteDTO> clienteDTO = clientes.map(cliente -> this.converterClienteDTO(cliente));

		response.setData(clienteDTO);
		return ResponseEntity.ok(response);
	}

	/**
	 * Retorna um cliente por ID.
	 * 
	 * @param id
	 * @return ResponseEntity<Response<LancamentoDto>>
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<Response<ClienteDTO>> listarPorId(@PathVariable("id") Long id) {
		log.info("Buscando Cliente por ID: {}", id);
		Response<ClienteDTO> response = new Response<ClienteDTO>();
		Optional<Cliente> cliente = this.clienteService.buscarPorId(id);

		if (!cliente.isPresent()) {
			log.info("Cliente não encontrado para o ID: {}", id);
			response.getErrors().add("Cliente não encontrado para o id " + id);
			return ResponseEntity.badRequest().body(response);
		}

		response.setData(this.converterClienteDTO(cliente.get()));
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Adiciona um novo Cliente.
	 * 
	 * @param cliente
	 * @param result
	 * @return ResponseEntity<Response<ClienteDTO>>
	 * @throws ParseException 
	 */
	@PostMapping
	public ResponseEntity<Response<ClienteDTO>> adicionar(@Valid @RequestBody ClienteDTO clienteDTO,
			BindingResult result) throws ParseException {
		log.info("Adicionando Cliente: {}", clienteDTO.toString());
		Response<ClienteDTO> response = new Response<ClienteDTO>();
		Cliente cliente = this.converterDTOParaCliente(clienteDTO, result);

		if (result.hasErrors()) {
			log.error("Erro validando cliente: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		if (clienteDTO.getSenha().isPresent()) {
			cliente.setSenha(PasswordUtils.gerarBCrypt(clienteDTO.getSenha().get()));
		}

		cliente = this.clienteService.persistir(cliente);
		response.setData(this.converterClienteDTO(cliente));
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Atualiza os dados de um cliente.
	 * 
	 * @param id
	 * @param clienteDTO
	 * @return ResponseEntity<Response<Cliente>>
	 * @throws ParseException 
	 */
	@PutMapping(value = "/{id}")
	public ResponseEntity<Response<ClienteDTO>> atualizar(@PathVariable("id") Long id,
			@Valid @RequestBody ClienteDTO clienteDTO, BindingResult result) throws ParseException {
		log.info("Atualizando cliente: {}", clienteDTO.toString());
		Response<ClienteDTO> response = new Response<ClienteDTO>();
		clienteDTO.setId(Optional.of(id));
		Cliente cliente = this.converterDTOParaCliente(clienteDTO, result);

		if (result.hasErrors()) {
			log.error("Erro validando lançamento: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		if (clienteDTO.getSenha().isPresent()) {
			cliente.setSenha(PasswordUtils.gerarBCrypt(clienteDTO.getSenha().get()));
		}

		cliente = this.clienteService.persistir(cliente);
		response.setData(this.converterClienteDTO(cliente));
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Remove um cliente por ID.
	 * 
	 * @param id
	 * @return ResponseEntity<Response<Cliente>>
	 */
	@DeleteMapping(value = "/{id}")
	//@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<String>> remover(@PathVariable("id") Long id) {
		log.info("Removendo cliente: {}", id);
		Response<String> response = new Response<String>();
		Optional<Cliente> lancamento = this.clienteService.buscarPorId(id);

		if (!lancamento.isPresent()) {
			log.info("Erro ao remover o cliente ID: {} ser inválido.", id);
			response.getErrors().add("Erro ao remover cliente. Registro não encontrado para o id " + id);
			return ResponseEntity.badRequest().body(response);
		}

		this.clienteService.remover(id);
		return ResponseEntity.ok(new Response<String>());
	}

	/**
	 * Converte um clienteDTO para uma entidade Cliente.
	 * 
	 * @param clienteDTO
	 * @param result
	 * @return Cliente
	 * @throws ParseException 
	 */
	private Cliente converterDTOParaCliente(ClienteDTO clienteDTO, BindingResult result) throws ParseException {
		Cliente cliente = new Cliente();
			
	
		if (clienteDTO.getId().isPresent()) {
			Optional<Cliente> cli = this.clienteService.buscarPorId(clienteDTO.getId().get());
			if (cli.isPresent()) {
				cliente = cli.get();
			} else {
				result.addError(new ObjectError("cliente", "Cliente não encontrado."));
			}
		}
		
		cliente.setNome(clienteDTO.getNome());
		cliente.setEmail(clienteDTO.getEmail());
		//cliente.setSenha(clienteDTO.getSenha());
		cliente.setPerfil(clienteDTO.getPerfil());
		cliente.setRua(clienteDTO.getRua());
		cliente.setBairro(clienteDTO.getBairro());
		cliente.setCidade(clienteDTO.getCidade());
		cliente.setEstado(clienteDTO.getEstado());
		cliente.setCep(clienteDTO.getCep());


		return cliente;
	}
	
	/**
	 * Converte uma entidade Cliente para seu respectivo DTO.
	 * 
	 * @param cliente
	 * @return ClienteDTO
	 */
	private ClienteDTO converterClienteDTO(Cliente cliente) {
		
		ClienteDTO clienteDTO = new ClienteDTO();
		
		clienteDTO.setId(Optional.of(cliente.getId()));
		clienteDTO.setNome(cliente.getNome());
		clienteDTO.setEmail(cliente.getEmail());
		clienteDTO.setPerfil(cliente.getPerfil());
		clienteDTO.setRua(cliente.getRua());
		clienteDTO.setBairro(cliente.getBairro());
		clienteDTO.setCidade(cliente.getCidade());
		clienteDTO.setEstado(cliente.getEstado());
		clienteDTO.setCep(cliente.getCep());
		
		return clienteDTO;
	}
}
