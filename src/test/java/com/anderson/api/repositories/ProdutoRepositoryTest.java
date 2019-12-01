package com.anderson.api.repositories;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.anderson.api.entities.Categoria;
import com.anderson.api.entities.Produto;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProdutoRepositoryTest {
	
	@Autowired
	private ProdutosRepository produtosRepository;
	@Autowired
	private CategoriasRepository categoriasRepository;
	private static final String NOME_PRODUTO = "Computador";
	

	@Before
	public void setUp() throws Exception {
		
		Categoria categoria = new Categoria();
		categoria.setNome("Eletronico");
		categoriasRepository.save(categoria);
		
		Produto produto = new Produto();
		produto.setProduto("Computador");
		produto.setPreco(new BigDecimal("200.0"));
		produto.setDescricao("Computador Dell");
		produto.setFoto("computador");
		produto.setQuantidadeEstoque(10);
		produto.setCategoria(categoria);
		produtosRepository.save(produto);
		
	}
	
	@After
    public final void tearDown() { 
		this.categoriasRepository.deleteAll();
		this.produtosRepository.deleteAll();
	}

	@Test
	public void testBuscarPorNome() {
		Produto produto = this.produtosRepository.findByProduto(NOME_PRODUTO);
	
		assertEquals(NOME_PRODUTO, produto.getProduto());
	}

}
