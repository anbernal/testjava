package com.anderson.api.repositories;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.anderson.api.entities.Categoria;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoriaRepositoryTest {
	
	@Autowired
	private CategoriasRepository categoriasRepository;
	private static final String NOME_CATEGORIA = "Eletronicos";
	

	@Before
	public void setUp() throws Exception {
		Categoria categoria = new Categoria();
		
		categoria.setNome("Eletronicos");
		this.categoriasRepository.save(categoria);
	}
	
	@After
    public final void tearDown() { 
		this.categoriasRepository.deleteAll();
	}

	@Test
	public void testBuscarPorNome() {
		Categoria categoria = this.categoriasRepository.findByNome(NOME_CATEGORIA);
	
		assertEquals(NOME_CATEGORIA, categoria.getNome());
	}

}


