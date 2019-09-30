import java.util.Random;

public class Ag {
    public String [][] matrizPopulacao;
    int contZero;
 
    public Ag(){};

    public void aplicarAG(Labirinto labirinto){

        System.out.println("\n Iniciando Algoritmo Genético...\n");
        criarPopulacao(labirinto.getTamLabirinto());

    }
    // Como campo é quadrático, a populacao inicial vai ser de 10 individuos com 100 cromossomos
    public static void criarPopulacao(int tam){
        System.out.println("\nCriando Populacao Inicial...\n");
        int [][] individuos = new int[tam][tam*tam] ; 
        Random gerador = new Random();
        int valCromossomo;

        // Gera primeira populacao aleatoriamente
        for(int i=0; i<tam; i++){
            for(int j = 0; j < individuos.length; j++){
                for(int k = 0; k < individuos[j].length; k++){
                    valCromossomo = gerador.nextInt(8);
                    //Garante que o numero randomizado nao seja 0
                    if(valCromossomo == 0){
                        while(valCromossomo == 0){
                            valCromossomo = gerador.nextInt(8);
                        }
                    }
                    individuos[j][k] = valCromossomo;

                }
            }
            
        }
    }

    public void aplicarAptidao(){

    }

}