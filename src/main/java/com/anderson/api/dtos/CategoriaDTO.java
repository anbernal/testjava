package com.anderson.api.dtos;

import java.util.Optional;

public class CategoriaDTO {
	
	private Optional<Long> id = Optional.empty();
	private String nome;
	
	
	
	public CategoriaDTO() {
		
	}
	
	
	public Optional<Long> getId() {
		return id;
	}
	public void setId(Optional<Long> id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
	@Override
	public String toString() {
		return "CategoriaDTO [id=" + id + ", nome=" + nome + "]";
	}
	
	

}
