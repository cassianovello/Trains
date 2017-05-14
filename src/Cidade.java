import java.util.ArrayList;

public class Cidade {
	String nome;
	Boolean visitada = false;
	ArrayList<Caminho> caminhos;
	
	public Cidade(String nome){
		this.nome = nome;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public ArrayList<Caminho> getCaminhos() {
		return caminhos;
	}

	public void setVizinho(Cidade cidade, Integer distancia) {
		if(caminhos==null)
			caminhos = new ArrayList<Caminho>();
		Caminho caminho = new Caminho(cidade,distancia);
		caminhos.add(caminho);
	}
	
	public Integer getVizinho(Cidade c){
		if(caminhos==null) return -1;
		Integer index = 0;
		for (Caminho caminho : caminhos) {
			if(caminho.getVizinho().getNome()==c.getNome())
				return index;
			index++;
		}
		return -1;
	}
	
	public Boolean getVisitada() {
		return visitada;
	}

	public void setVisitada(Boolean visitada) {
		this.visitada = visitada;
	}

}
