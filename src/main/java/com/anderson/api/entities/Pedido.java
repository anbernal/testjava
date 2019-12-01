package com.anderson.api.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.anderson.api.enums.StatusEnum;

@Entity
@Table(name = "pedido")
public class Pedido {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Temporal(TemporalType.TIMESTAMP) @Column(name = "data_pedido", nullable = false)
	private Date data;
	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	private StatusEnum status;
	private String sessao;
	@ManyToOne(fetch = FetchType.EAGER)
	private Cliente cliente;
	@Column(name = "data_criacao", nullable = false)
	private Date dataCriacao;
	@Column(name = "data_atualizacao", nullable = false)
	private Date dataAtualizacao;
	
	public Pedido() {
	
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Date getData() {
		return data;
	}


	public void setData(Date data) {
		this.data = data;
	}


	public StatusEnum getStatus() {
		return status;
	}


	public void setStatus(StatusEnum status) {
		this.status = status;
	}


	public String getSessao() {
		return sessao;
	}


	public void setSessao(String sessao) {
		this.sessao = sessao;
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
	
	
	public Cliente getCliente() {
		return cliente;
	}


	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
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
		return "Pedido [id=" + id + ", data=" + data + ", status=" + status + ", sessao=" + sessao + ", cliente="
				+ cliente + ", dataCriacao=" + dataCriacao + ", dataAtualizacao=" + dataAtualizacao + "]";
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
		Pedido other = (Pedido) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
    
}
