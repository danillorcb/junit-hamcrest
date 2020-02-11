package br.com.danillorcb.leilao.dominio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Leilao {

	private String descricao;
	private List<Lance> lances;

	public Leilao(String descricao) {
		this.descricao = descricao;
		this.lances = new ArrayList<Lance>();
	}

	public void propoe(Lance lance) {
		int qtdLances = qtdDeLancesDo(lance.getUsuario());

		if ((lances.size() == 0 || !ultimoLanceDado().getUsuario().equals(lance.getUsuario())) && (qtdLances < 5))
			lances.add(lance);
	}

	private int qtdDeLancesDo(Usuario usuario) {
		int total = 0;
		for (Lance l : lances) {
			if (l.getUsuario().equals(usuario))
				total++;
		}
		return total;
	}

	private Lance ultimoLanceDado() {
		return lances.get(lances.size() - 1);
	}
	
	public void dobraLance(Usuario usuario) {
		List<Lance> lancesDoUsuario = lancesDo(usuario);	
		
		if (lancesDoUsuario.size() > 0) {
			Lance ultimoLance = lancesDoUsuario.get(lancesDoUsuario.size()-1);
			this.propoe(new Lance(usuario, ultimoLance.getValor() * 2));
		}
	}

	private List<Lance> lancesDo(Usuario usuario) {
		List<Lance> lancesDoUsuario = new ArrayList<>();
		for (Lance l : lances) {
			if (l.getUsuario().equals(usuario))
				lancesDoUsuario.add(l);
		}
		
		return lancesDoUsuario;
	}

	public String getDescricao() {
		return descricao;
	}

	public List<Lance> getLances() {
		return Collections.unmodifiableList(lances);
	}

}
