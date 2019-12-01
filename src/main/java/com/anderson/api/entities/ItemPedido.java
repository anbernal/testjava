package com.anderson.api.entities;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "item_pedido")
public class ItemPedido {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull(message = "Valor é obrigatório")
	@DecimalMin(value = "0.50", message = "O valor do produto deve ser maior que R$0,50")
	@DecimalMax(value = "9999999.99", message = "O valor do produto deve ser menor que R$9.999.999,99")
	private BigDecimal valor;
	@NotNull(message = "A quantidade do item é obrigatória")
	@Max(value = 9999, message = "A quantidade do item deve ser menor que 9.999")
	@Column(name = "quantidade")
	private Integer quantidade;
	@Column(nullable = false)
	private BigDecimal subtotal;
	@ManyToOne(fetch = FetchType.EAGER)
	private Pedido pedido;
	@ManyToOne(fetch = FetchType.EAGER)
	private Produto produto;
	@Column(name = "data_criacao", nullable = false)
	private Date dataCriacao;
	@Column(name = "data_atualizacao", nullable = false)
	private Date dataAtualizacao;
	
	
	public ItemPedido() {
	
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
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


	public Pedido getPedido() {
		return pedido;
	}


	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}


	public Produto getProduto() {
		return produto;
	}


	public void setProduto(Produto produto) {
		this.produto = produto;
	}


	public Date getDataCriacao() {
		return dataCriacao;
	}


	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}


	public Date getDataAtualizacao() {
		return dataAtualizacao;
	}


	public void setDataAtualizacao(Date dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}
	
	
	@PreUpdate
    public void preUpdate() {
        dataAtualizacao = new Date();
    }
	
     
    @PrePersist
    public void prePersist() {
        final Date atual = new Date();
        dataCriacao = atual;
        dataAtualizacao = atual;
    
    }


	@Override
	public String toString() {
		return "ItemPedido [id=" + id + ", valor=" + valor + ", quantidade=" + quantidade + ", subtotal=" + subtotal
				+ ", pedido=" + pedido + ", produto=" + produto + ", dataCriacao=" + dataCriacao + ", dataAtualizacao="
				+ dataAtualizacao + "]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemPedido other = (ItemPedido) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
}
