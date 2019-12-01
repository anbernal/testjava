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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "produto")
public class Produto {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank(message = "Nome do produto é obrigatório")
	private String produto;
	@NotNull(message = "Valor é obrigatório")
	@DecimalMin(value = "0.50", message = "O valor do produto deve ser maior que R$0,50")
	@DecimalMax(value = "9999999.99", message = "O valor do produto deve ser menor que R$9.999.999,99")
	private BigDecimal preco;
	@NotNull(message = "A quantidade em estoque é obrigatória")
	@Max(value = 9999, message = "A quantidade em estoque deve ser menor que 9.999")
	@Column(name = "quantidade_estoque")
	private Integer quantidadeEstoque;
	private String descricao;
	private String foto;
	@NotNull(message = "Categoria é obrigatoria")
	@ManyToOne(fetch = FetchType.EAGER)
	private Categoria categoria;
	@Column(name = "data_criacao", nullable = false)
	private Date dataCriacao;
	@Column(name = "data_atualizacao", nullable = false)
	private Date dataAtualizacao;
	

	public Produto() {

	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
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


	public Categoria getCategoria() {
		return categoria;
	}


	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
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
		return "Produto [id=" + id + ", produto=" + produto + ", preco=" + preco + ", quantidadeEstoque="
				+ quantidadeEstoque + ", descricao=" + descricao + ", foto=" + foto + ", dataCriacao=" + dataCriacao
				+ ", dataAtualizacao=" + dataAtualizacao + "]";
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
		Produto other = (Produto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
