package com.anderson.api.dtos;

import java.math.BigDecimal;
import java.util.Optional;

public class ProdutoDTO {
	
	private Optional<Long> id = Optional.empty();
	private String produto;
	private BigDecimal preco;
	private Integer quantidadeEstoque;
	private String descricao;
	private String foto;
	private Long categoriaId;
	
	
	public ProdutoDTO() {
		
	}
	
	
	public Optional<Long> getId() {
		return id;
	}
	public void setId(Optional<Long> id) {
		this.id = id;
	}
	public String getProduto() {
		return produto;
	}
	public void setProduto(String produto) {
		this.produto = produto;
	}
	public BigDecimal getPreco() {
		return preco;
	}
	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}
	public Integer getQuantidadeEstoque() {
		return quantidadeEstoque;
	}
	public void setQuantidadeEstoque(Integer quantidadeEstoque) {
		this.quantidadeEstoque = quantidadeEstoque;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getFoto() {
		return foto;
	}
	public void setFoto(String foto) {
		this.foto = foto;
	}
	public Long getCategoriaId() {
		return categoriaId;
	}
	public void setCategoriaId(Long categoriaId) {
		this.categoriaId = categoriaId;
	}
	
	@Override
	public String toString() {
		return "ProdutoDTO [id=" + id + ", produto=" + produto + ", preco=" + preco + ", quantidadeEstoque="
				+ quantidadeEstoque + ", descricao=" + descricao + ", foto=" + foto + ", categoriaId=" + categoriaId
				+ "]";
	}
	
	
}
