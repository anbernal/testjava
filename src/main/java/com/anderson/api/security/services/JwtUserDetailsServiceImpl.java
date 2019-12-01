package com.anderson.api.security.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.anderson.api.entities.Cliente;
import com.anderson.api.repositories.ClientesRepository;
import com.anderson.api.security.JwtUserFactory;



@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private ClientesRepository clienteRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Cliente> cliente = clienteRepository.findByEmail(username);

		if (cliente.isPresent()) {
			return JwtUserFactory.create(cliente.get());
		}

		throw new UsernameNotFoundException("Email não encontrado.");
	}

}
