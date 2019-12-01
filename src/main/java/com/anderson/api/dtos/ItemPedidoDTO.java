package com.anderson.api.dtos;

import java.math.BigDecimal;
import java.util.Optional;

public class ItemPedidoDTO {
	
	private Optional<Long> id = Optional.empty();
	private BigDecimal valor;
	private Integer quantidade;
	private BigDecimal subtotal;
	private Long pedidoId;
	private Long produtoId;
	
	
	public ItemPedidoDTO() {
		
	}


	public Optional<Long> getId() {
		return id;
	}


	public void setId(Optional<Long> id) {
		this.id = id;
	}


	public BigDecimal getValor() {
		return valor;
	}


	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}


	public Integer getQuantidade() {
		return quantidade;
	}


	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}


	public BigDecimal getSubtotal() {
		return subtotal;
	}


	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}


	public Long getPedidoId() {
		return pedidoId;
	}


	public void setPedidoId(Long pedidoId) {
		this.pedidoId = pedidoId;
	}


	public Long getProdutoId() {
		return produtoId;
	}


	public void setProdutoId(Long produtoId) {
		this.produtoId = produtoId;
	}


	@Override
	public String toString() {
		return "ItemPedidoDTO [id=" + id + ", valor=" + valor + ", quantidade=" + quantidade + ", subtotal=" + subtotal
				+ ", pedidoId=" + pedidoId + ", produtoId=" + produtoId + "]";
	}
	
	
}
