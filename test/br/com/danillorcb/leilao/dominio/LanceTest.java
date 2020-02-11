package br.com.danillorcb.leilao.dominio;

import org.junit.Test;

import br.com.danillorcb.leilao.dominio.Lance;
import br.com.danillorcb.leilao.dominio.Usuario;

public class LanceTest {

	@Test(expected=IllegalArgumentException.class)
	public void deveRecusarLancesComValorNegativo() {
		new Lance(new Usuario("Jo�o"), -12.0);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void deveRecusarLancesComValorZerado() {
		new Lance(new Usuario("Jo�o"), 0);
	}
}
