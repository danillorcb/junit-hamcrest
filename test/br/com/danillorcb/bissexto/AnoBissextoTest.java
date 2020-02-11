package br.com.danillorcb.bissexto;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.com.danillorcb.bissexto.AnoBissexto;

public class AnoBissextoTest {

	@Test
	public void deveRetornarAnoBissexto() {        
	    AnoBissexto anoBissexto = new AnoBissexto();

	    assertEquals(true, anoBissexto.isAnoBissexto(2016));
	    assertEquals(true, anoBissexto.isAnoBissexto(2020));
	}

	@Test
	public void naoDeveRetornarAnoBissexto() {        
	    AnoBissexto anoBissexto = new AnoBissexto();        

	    assertEquals(false, anoBissexto.isAnoBissexto(2015));
	    assertEquals(false, anoBissexto.isAnoBissexto(2017));
	    assertEquals(false, anoBissexto.isAnoBissexto(2018));
	}
}
