package br.com.danillorcb.leilao.builder;

import br.com.danillorcb.leilao.dominio.Lance;
import br.com.danillorcb.leilao.dominio.Leilao;
import br.com.danillorcb.leilao.dominio.Usuario;

public class LeilaoBuilder {

	private Leilao leilao;

	public LeilaoBuilder para(String descricao) {
		this.leilao = new Leilao(descricao);
		return this;
	}

	public LeilaoBuilder lance(Usuario usuario, double valor) {
		leilao.propoe(new Lance(usuario, valor));
		return this;
	}

	public Leilao build() {
		return leilao;
	}
	
}
