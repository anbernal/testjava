package com.anderson.api.dtos;

import java.util.Optional;

public class PedidoDTO {
	
	private Optional<Long> id = Optional.empty();
	private String data;
	private String status;
	private Long clienteId;
	
	
	public PedidoDTO() {
		
	}
	
	
	public Optional<Long> getId() {
		return id;
	}
	public void setId(Optional<Long> id) {
		this.id = id;
	}


	public String getData() {
		return data;
	}


	public void setData(String data) {
		this.data = data;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public Long getClienteId() {
		return clienteId;
	}


	public void setClienteId(Long clienteId) {
		this.clienteId = clienteId;
	}


	@Override
	public String toString() {
		return "PedidoDTO [id=" + id + ", data=" + data + ", status=" + status + ", clienteId=" + clienteId + "]";
	}


}
