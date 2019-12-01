package com.anderson.api.enums;

public enum StatusEnum {
	ORCAMENTO("Orçamento"), 
	EMITIDA("Emitida"), 
	CANCELADA("Cancelada");

	private String descricao;

	StatusEnum(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
