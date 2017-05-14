
public class Caminho {
		Cidade vizinho;
		Integer distancia;
		
		public Caminho(Cidade vizinho, Integer distancia){
			this.vizinho = vizinho;
			this.distancia = distancia;
		}
		
		public Cidade getVizinho() {
			return vizinho;
		}

		public void setVizinho(Cidade vizinho) {
			this.vizinho = vizinho;
		}

		public Integer getDistancia() {
			return distancia;
		}

		public void setDistancia(Integer distancia) {
			this.distancia = distancia;
		}

}
