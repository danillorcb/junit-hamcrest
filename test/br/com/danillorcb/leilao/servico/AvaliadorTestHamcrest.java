package br.com.danillorcb.leilao.servico;

import static org.junit.Assert.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.com.danillorcb.leilao.builder.LeilaoBuilder;
import br.com.danillorcb.leilao.dominio.Lance;
import br.com.danillorcb.leilao.dominio.Leilao;
import br.com.danillorcb.leilao.dominio.Usuario;
import br.com.danillorcb.leilao.servico.Avaliador;

public class AvaliadorTestHamcrest {
	
	private Avaliador leiloeiro;
	private Usuario joao;
	private Usuario jose;
	private Usuario maria;

	@Before
	public void setUp() {
		leiloeiro = new Avaliador();
		joao = new Usuario("João");
		jose = new Usuario("José");
		maria = new Usuario("Maria");
	}
	
	@Test
    public void deveEntenderLancesEmOrdemCrescente() {
    	// cenario: 3 lances em ordem crescente
        Leilao leilao = new LeilaoBuilder().para("Playstation 3 Novo")
        		.lance(joao, 250.0)
        		.lance(jose, 300.0)
        		.lance(maria, 400.0)
        		.build();
        
        // executando a acao        
        leiloeiro.avalia(leilao);

		// comparando a saida com o esperado
		double maiorEsperado = 400;
		double menorEsperado = 250;
        
        assertThat(leiloeiro.getMaiorLance(), equalTo(maiorEsperado));
        assertThat(leiloeiro.getMenorLance(), equalTo(menorEsperado));
    }
	
	@Test
	public void deveEntenderValorMedioDosLances() {
    	// cenario: 3 lances em ordem crescente
        Leilao leilao = new LeilaoBuilder().para("Playstation 3 Novo")
        		.lance(joao, 200.0)
        		.lance(jose, 300.0)
        		.lance(maria, 400.0)
        		.build();

        // executando a acao
        leiloeiro.avalia(leilao);

		// comparando a saida com o esperado
		double media = 300;
		        
        assertThat(leiloeiro.getValorMedio(leilao), equalTo(media));
	}
	
	@Test
	public void deveEntenderLeilaoComApenasUmLance() {
        Leilao leilao = new LeilaoBuilder().para("Playstation 3 Novo")
        		.lance(joao, 250.0)
        		.build();
		
		leiloeiro.avalia(leilao);
		
        assertThat(leiloeiro.getMaiorLance(), equalTo(250.0));
        assertThat(leiloeiro.getMenorLance(), equalTo(250.0));
	}
	
	
	@Test
	public void deveEntenderLeilaoComLancesEmOrdemDecrescente() {
        Leilao leilao = new LeilaoBuilder().para("Playstation 3 Novo")
        		.lance(joao, 400.0)
        		.lance(maria, 300.0)
        		.lance(joao, 200.0)
        		.lance(maria, 100.0)
        		.build();
        
        leiloeiro.avalia(leilao);
		
		assertEquals(100, leiloeiro.getMenorLance(), 0.00001);
		assertEquals(400, leiloeiro.getMaiorLance(), 0.00001);
	}
	
	@Test
	public void deveEncontrarTresMaioresLancesDeCinco() {
		// Cenário
        Leilao leilao = new LeilaoBuilder().para("Playstation 3 Novo")
        		.lance(maria, 500.0)
        		.lance(maria, 300.0)
        		.lance(joao, 400.0)
        		.lance(maria, 600.0)
        		.lance(joao, 200.0)
        		.build();
		
		// Ação
		leiloeiro.avalia(leilao);
		
		// Verificação
		List<Lance> tresMaiores = leiloeiro.getTresMaiores();
		
		assertThat(tresMaiores, hasItems(
				new Lance(maria, 600.0),
				new Lance(maria, 500.0),
				new Lance(joao, 400.0)
		));
	}
	
	@Test
	public void deveEncontrarDoisMaioresLancesDeDois() {
		// Cenário
        Leilao leilao = new LeilaoBuilder().para("Playstation 3 Novo")
        		.lance(maria, 600.0)
        		.lance(joao, 200.0)
        		.build();
		
		// Ação
		leiloeiro.avalia(leilao);
		
		// Validação
		List<Lance> tresMaiores = leiloeiro.getTresMaiores();
		
		assertThat(tresMaiores.size(), equalTo(2));
		assertThat(tresMaiores, hasItems(
				new Lance(maria, 600.0),
        		new Lance(joao, 200.0)
		));
	}
	
	@Test(expected=RuntimeException.class) 
	public void deveEncontrarListaVaziaDeLances() {
        Leilao leilao = new LeilaoBuilder()
        		.para("Playstation 3 Novo")
        		.build();
        
		leiloeiro.avalia(leilao);
	}
}