package com.anderson.api.dtos;

import java.util.Optional;

import com.anderson.api.enums.PerfilEnum;


public class ClienteDTO {
	
	private Optional<Long> id = Optional.empty();
	private String nome;
	private String email;
	private Optional<String> senha = Optional.empty();
	private PerfilEnum perfil;
	private String rua;
	private String bairro;
	private String cidade;
	private String estado;
	private String cep;
	
	
	public ClienteDTO() {
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public Optional<String> getSenha() {
		return senha;
	}



	public void setSenha(Optional<String> senha) {
		this.senha = senha;
	}



	public PerfilEnum getPerfil() {
		return perfil;
	}



	public void setPerfil(PerfilEnum perfil) {
		this.perfil = perfil;
	}



	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}



	@Override
	public String toString() {
		return "ClienteDTO [id=" + id + ", nome=" + nome + ", email=" + email + ", senha=" + senha + ", perfil="
				+ perfil + ", rua=" + rua + ", bairro=" + bairro + ", cidade=" + cidade + ", estado=" + estado
				+ ", cep=" + cep + "]";
	}


}
