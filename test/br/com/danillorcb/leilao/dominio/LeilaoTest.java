package br.com.danillorcb.leilao.dominio;

import static br.com.danillorcb.leilao.matcher.LeilaoMatcher.temUmLance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import br.com.danillorcb.leilao.builder.LeilaoBuilder;
import br.com.danillorcb.leilao.dominio.Lance;
import br.com.danillorcb.leilao.dominio.Leilao;
import br.com.danillorcb.leilao.dominio.Usuario;


public class LeilaoTest {

    @Test
    public void deveReceberUmLance() {
        Leilao leilao = new Leilao("Macbook Pro 15");
        assertEquals(0, leilao.getLances().size());

        leilao.propoe(new Lance(new Usuario("Steve Jobs"), 2000));

        assertEquals(1, leilao.getLances().size());
        assertEquals(2000.0, leilao.getLances().get(0).getValor(), 0.00001);
    }

    @Test
    public void deveReceberVariosLances() {
        Leilao leilao = new Leilao("Macbook Pro 15");
        leilao.propoe(new Lance(new Usuario("Steve Jobs"), 2000));
        leilao.propoe(new Lance(new Usuario("Steve Wozniak"), 3000));

        assertEquals(2, leilao.getLances().size());
        assertEquals(2000.0, leilao.getLances().get(0).getValor(), 0.00001);
        assertEquals(3000.0, leilao.getLances().get(1).getValor(), 0.00001);
    }
    
    @Test
    public void naoDeveAceitarDoisLancesSeguidosDoMesmoUsuario() {
    	Leilao leilao = new Leilao("Macbook Pro 15");
        Usuario jobs = new Usuario("Steve Jobs");
        Usuario wozniak = new Usuario("Steve Wozniak");
        
        leilao.propoe(new Lance(jobs, 2000));
        leilao.propoe(new Lance(wozniak, 3000));
        
        // Não deve aceitar
        leilao.propoe(new Lance(wozniak, 4000));
        
        List<Lance> lances = leilao.getLances();
        assertEquals(2, lances.size());
        assertEquals(3000, lances.get(lances.size()-1).getValor(), 0.00001);
    }
    
    @Test
    public void naoDeveAceitarMaisDoQue5LancesDeUmMesmoUsuario() {
        Leilao leilao = new Leilao("Macbook Pro 15");
        Usuario jobs = new Usuario("Steve Jobs");
        Usuario wozniak = new Usuario("Steve Wozniak");
        
        leilao.propoe(new Lance(jobs, 2000));
        leilao.propoe(new Lance(wozniak, 3000));
        
        leilao.propoe(new Lance(jobs, 1000));
        leilao.propoe(new Lance(wozniak, 2000));
        
        leilao.propoe(new Lance(jobs, 5000));
        leilao.propoe(new Lance(wozniak, 4000));
        
        leilao.propoe(new Lance(jobs, 6000));
        leilao.propoe(new Lance(wozniak, 7000));
        
        leilao.propoe(new Lance(jobs, 8000));
        leilao.propoe(new Lance(wozniak, 9000));
        
        // Deve ser ignorado
        leilao.propoe(new Lance(jobs, 10000));
        
        List<Lance> lances = leilao.getLances();
		assertEquals(10, lances.size());
        assertEquals(9000, lances.get(lances.size()-1).getValor(), 0.00001);
    }
    
    @Test
    public void deveDobrarValorDoLance() {
        Leilao leilao = new Leilao("Macbook Pro 15");
        Usuario jobs = new Usuario("Steve Jobs");
        Usuario wozniak = new Usuario("Steve Wozniak");
        
        leilao.propoe(new Lance(jobs, 2000));
        leilao.propoe(new Lance(wozniak, 3000));
        
        leilao.dobraLance(jobs);
        
        List<Lance> lances = leilao.getLances();
		assertEquals(4000, lances.get(lances.size()-1).getValor(), 0.00001);
    }
    
    @Test
    public void naoDeveDobrarQuandoNaoHouverLanceAnterior() {
        Usuario jobs = new Usuario("Steve Jobs");
        Usuario wozniak = new Usuario("Steve Wozniak");

        Leilao leilao = new LeilaoBuilder().para("Macbook Pro 15")
        		.lance(wozniak, 3000.0)
        		.build();
        
        leilao.dobraLance(jobs);
        
        List<Lance> lances = leilao.getLances();
        assertThat(lances.size(), equalTo(1));
		assertThat(lances.get(lances.size()-1).getValor(), equalTo(3000.0));
    }
    
    @Test
    public void deveTerNoMinimoUmLance() {    	
        Leilao leilao = new LeilaoBuilder().para("Macbook Pro 15").build();
        assertEquals(0, leilao.getLances().size());

        Lance lance = new Lance(new Usuario("Steve Jobs"), 2000);
        leilao.propoe(lance);

        assertThat(leilao.getLances().size(), equalTo(1));
        assertThat(leilao, temUmLance(lance));
    }
    
   
}
