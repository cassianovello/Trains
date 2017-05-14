import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
	static ArrayList<Cidade> mundo = new ArrayList<>();
	static final Integer infinito = 9999;
	static final Integer maiorRota = 30;
	static Integer menorDistancia = infinito;
	static Integer viagens = 0;
	 	
	public static void main(String[] caminhoArquivo) {
		
		//Leitura do arquivo de entrada
		BufferedReader br =null;
		FileReader fr = null;
		String entrada = null;
		try{
		fr = new FileReader(caminhoArquivo[0]);
		br = new BufferedReader(fr);		

		if((entrada = br.readLine()) != null) {
			escreve(entrada);
		}
		} catch(Exception e){
			e.getMessage();
			return;
		} finally {
			try {
				if (br != null) br.close();
				if (fr != null) fr.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		
		//Leitura das entradas e preenchimento do mundo
		String[] mapa = entrada.split(", ");
		for (String c : mapa) {
			escreve(c);
			String entrada1 =  c.substring(0,1);
			String entrada2 =  c.substring(1,2);
			Integer distancia = Integer.valueOf(c.substring(2,3));
			Cidade cidade1 = null, cidade2=null;
			
			cidade1 = achaCidade(entrada1);
			if(cidade1==null){
				cidade1 = new Cidade(entrada1);
				mundo.add(cidade1);
			}
			
			cidade2 = achaCidade(entrada2);
			if(cidade2==null){
				cidade2 = new Cidade(entrada2);
				mundo.add(cidade2);
			}
					
			cidade1.setVizinho(cidade2, distancia);
		}

		//Problema 1 - distância da rota A-B-C		
		System.out.println("Output #1: "+achaDistancia("ABC"));

		//Problema 2 - distância da rota A-D;
		System.out.println("Output #2: "+achaDistancia("AD"));
		
		//Problema 3 - distância da rota A-D-C;
		System.out.println("Output #3: "+achaDistancia("ADC"));		
		
		//Problema 4 - distância da rota A-E-B-C-D;
		System.out.println("Output #4: "+ achaDistancia("AEBCD"));
		
		//Problema 5 - distância da rota A-E-D
		System.out.println("Output #5: "+ achaDistancia("AED"));
				
		//Problema 6 - paradas da rota C-C
		System.out.println("Output #6: " + encontraParadas("C","C",0,6));
		
		viagens =0;
		//Problema 7 - paradas da rota A-C
		System.out.println("Output #7: " + encontraParadas("A","C",0,7));
		
		//Problema 8 - menor caminho A-C
		System.out.println("Output #8: " + encontraMenorCaminho("A","C",0));
		
		//Problema 9 - menor caminho B-B
		System.out.println("Output #9: " +  encontraMenorCaminho("B","B",0));
		
		viagens =0;
		//Problema 10 - rotas diferentes entre C e C com distância menor que 30
		System.out.println("Output #10: " + encontraRotaMenorQue("C", "C", 0));
		//imprimeMundo();			
	}
	
	public static String achaDistancia(String rotaBuscada){
		Integer distancia = 0;
		for(int i=0;i<rotaBuscada.length()-1;i++){
			Cidade c1 = achaCidade(String.valueOf(rotaBuscada.charAt(i)));
			Cidade c2 = achaCidade(String.valueOf(rotaBuscada.charAt(i+1)));
			Integer indexVizinho = c1.getVizinho(c2); 
			if(indexVizinho!=-1){
				distancia += c1.getCaminhos().get(indexVizinho).getDistancia();
			}
			else {
				distancia = 0;
				break;
			}
			
		}
		if(distancia== 0)
			return "NO SUCH ROUTE";
		else
			return String.valueOf(distancia);
	}
	
	
	/**
	 * Vê se a cidade buscada já existe no mundo
	 * @param cidadeBuscada
	 * @return
	 */
	public static Cidade achaCidade(String cidadeBuscada){
		for (Cidade cidade : mundo) {
			if(cidade.getNome().equals(cidadeBuscada))
				return cidade;				
		}
		return null;		
	}
		
	/**
	 * Busca em profundidade para encontrar o menor caminho
	 * @param cidadeParam1
	 * @param cidadeParam2
	 * @param distancia
	 * @return
	 */
	public static Integer encontraMenorCaminho(String cidadeParam1,String cidadeParam2, Integer distancia){
		Cidade origem = achaCidade(cidadeParam1);
		Cidade destino = achaCidade(cidadeParam2);
		
	
		if(origem.getCaminhos()!=null)
			escreve("Cidade"+ origem.getNome()+" tem "+ origem.getCaminhos().size() + " caminhos");
		else
			escreve("Cidade "+ origem.getNome()+"não tem caminhos");
		
		origem.setVisitada(true);		
		for (Caminho caminho : origem.caminhos) {
			escreve(origem.getNome() +" para " + caminho.getVizinho().getNome());
			//se achou o destino, para de descer neste ramo
			if(caminho.getVizinho().getNome()==destino.getNome()){
				 distancia += caminho.getDistancia();
				 escreve("Encontrou o destino"+ destino.getNome() + " distância" + distancia);
				 if(distancia<menorDistancia)
					 menorDistancia = distancia;	
				 origem.setVisitada(false);
				 continue;
			}			
						
			if(!caminho.getVizinho().getVisitada()){
				escreve("Visista o vizinho"+ caminho.getVizinho().getNome() + " distância"+ caminho.getDistancia());				
				encontraMenorCaminho(caminho.getVizinho().getNome(),destino.getNome(),distancia + caminho.getDistancia());
			}
			else
				escreve("Cidade já foi visitada" );
		}
		
		return menorDistancia;
	}
	
	
	/***
	 * Busca em profundidade
	 * @param cidadeParam1
	 * @param cidadeParam2
	 * @param paradas
	 * @return
	 */
	public static Integer encontraParadas(String cidadeParam1,String cidadeParam2, Integer paradas,Integer exercicio){
		Cidade origem = achaCidade(cidadeParam1);
		Cidade destino = achaCidade(cidadeParam2);
		
	
		if(origem.getCaminhos()!=null)
			escreve("Cidade"+ origem.getNome()+" tem "+ origem.getCaminhos().size() + " caminhos");
		else
			escreve("Cidade "+ origem.getNome()+"não tem caminhos");
				
		for (Caminho caminho : origem.caminhos) {
			escreve(origem.getNome() +" para " + caminho.getVizinho().getNome());
			paradas++;
			//se achou o destino, para de descer neste ramo
			if(caminho.getVizinho().getNome()==destino.getNome()){
				escreve("Achou o destino com " +paradas + "paradas");
				if(exercicio==6 && paradas<=3){
					viagens++;
					paradas--;
					continue;
				}
				if(exercicio==7 && paradas==4){
					viagens++;
					paradas--;
					continue;
				}
			}			
						
			if((exercicio==6 && paradas>3) || (exercicio==7 && paradas>4)){
				escreve("Fim do ramo com "+paradas+"paradas");
				paradas--;
				continue;
			}
			escreve("Visista o vizinho"+ caminho.getVizinho().getNome() + "com "+ paradas +" paradas");				
			encontraParadas(caminho.getVizinho().getNome(),destino.getNome(),paradas, exercicio);
			paradas--;
		}

		escreve("Fim do ramo com "+paradas+"paradas");
		return viagens;
	}
	
	/**
	 * Busca em profundidade para encontrar uma rota menor que 30
	 * @param cidadeParam1
	 * @param cidadeParam2
	 * @param distancia
	 * @return
	 */
	public static Integer encontraRotaMenorQue(String cidadeParam1,String cidadeParam2, Integer distancia){
		Cidade origem = achaCidade(cidadeParam1);
		Cidade destino = achaCidade(cidadeParam2);
		
	
		if(origem.getCaminhos()!=null)
			escreve("Cidade"+ origem.getNome()+" tem "+ origem.getCaminhos().size() + " caminhos");
		else
			escreve("Cidade "+ origem.getNome()+"não tem caminhos");
		
		for (Caminho caminho : origem.caminhos) {
			escreve(origem.getNome() +" para " + caminho.getVizinho().getNome() + " distancia " + caminho.getDistancia());
			distancia +=  caminho.getDistancia();
			
			//se achou o destino, soma viagens
			if(caminho.getVizinho().getNome()==destino.getNome()){
				 escreve("Encontrou o destino"+ destino.getNome() + " distância" + distancia);
				 if(distancia<30){
					 viagens++;
				 }
			}			
			
			if(distancia>maiorRota){
				escreve("Fim do ramo de distância "+ distancia);
				distancia -= caminho.getDistancia();
				continue;
			}
			escreve("Visista o vizinho"+ caminho.getVizinho().getNome() + " distância"+ (distancia));				
			encontraRotaMenorQue(caminho.getVizinho().getNome(),destino.getNome(),distancia);
			distancia -= caminho.getDistancia();
		}
		
		return viagens;
	}
	
	
	
	/**
	 * DEBUG: Exibe o mundo
	 */
	public static void imprimeMundo(){
		for (Cidade cidade : mundo) {	
			System.out.println("Cidade "+ cidade.getNome() +" possui caminhos para:");
			if(cidade.getCaminhos()==null) continue;
			for (Caminho caminho : cidade.getCaminhos()) {
				System.out.println(caminho.getVizinho().getNome() + caminho.getDistancia() +" - ");
			}
		}
		return;
	}
	
	/**
	 *DEBUG: escreve percurso na árvore
	 */
	public static void escreve(String entrada){
		//System.out.println(entrada);
	}
}
