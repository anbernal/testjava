package com.anderson.api.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.anderson.api.entities.Cliente;
import com.anderson.api.enums.PerfilEnum;


public class JwtUserFactory {

	private JwtUserFactory() {
	}

	/**
	 * Converte e gera um JwtUser com base nos dados de um Cliente.
	 * 
	 * @param cliente
	 * @return JwtUser
	 */
	public static JwtUser create(Cliente cliente) {
		return new JwtUser(cliente.getId(), cliente.getEmail(), cliente.getSenha(),
				mapToGrantedAuthorities(cliente.getPerfil()));
	}

	/**
	 * Converte o perfil do usuário para o formato utilizado pelo Spring Security.
	 * 
	 * @param perfilEnum
	 * @return List<GrantedAuthority>
	 */
	private static List<GrantedAuthority> mapToGrantedAuthorities(PerfilEnum perfilEnum) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(perfilEnum.toString()));
		return authorities;
	}

}
