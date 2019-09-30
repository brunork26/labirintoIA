import java.util.ArrayList;
import java.util.Random;

public class Ag {
    // static int [][] individuos;
    private static ArrayList<Cromossomo> individuos = new ArrayList<Cromossomo>();
    private static ArrayList<Cromossomo> individuosIntermediario = new ArrayList<Cromossomo>();
    public Labirinto labirinto = null;
    public int contZero;
    public int geracao = 0;
    public static int populacao = 10;

 
    public Ag(){};

    public void aplicarAG(Labirinto labirinto){
        this.labirinto = labirinto;
        System.out.println("\n Iniciando Algoritmo Genético...\n");
        criarPopulacaoInicial(this.labirinto.qtdCamposLivres());
        imprimeMatrizPopulacao();
        aplicarAptidao();
        aplicarSelecaoCBF();
        aplicarBrasileirao();

    }
    // Como campo é quadrático, a populacao inicial vai ser de 10 individuos com 100 cromossomos
    public static void criarPopulacaoInicial(int tam){
        System.out.println("\nCriando Populacao Inicial...\n");
        Random gerador = new Random();
        int gene;
        System.out.println(tam);
        int tamCromossomo = tam; // talvez passar por parâmetro
        for(int i = 0; i < populacao; i++) {
            Cromossomo cromossomo = new Cromossomo();
            for(int j = 0; j < tamCromossomo; j++) { 
                gene = gerador.nextInt(7) + 1; // garante de 1 até 8
                cromossomo.addGene(gene);
            }
            individuos.add(cromossomo);
        }

        // for(int i = 0; i < individuos.size(); i++) {
        //     System.out.println(individuos.get(i));
        // }
    }

    public void imprimeMatrizPopulacao(){
        System.out.println("Número de individuos: " + individuos.size());
        for(int i = 0; i < individuos.size(); i++) {
            System.out.println(individuos.get(i).toString());
        }
    }

    public void validaAptidao(int xPos, int yPos, Cromossomo cromossomo) {
        String[][] campo = labirinto.getCampo();
        if(campo[xPos][yPos].equals("1")) {
            cromossomo.aptidao += 1;
        }
    }

    public void aplicarAptidao(){
        String[][] campo = labirinto.getCampo();
        int index = 0;
        for (Cromossomo cromossomo : individuos) {
            index++;        
            for (Integer gene : cromossomo.getGenes()) {
                int xPos = 0; // novaPosicao
                int yPos = 0; // novaPosicao
                // movimenta para cima
                if(gene == 1) {
                    xPos = cromossomo.x - 1;
                    yPos = cromossomo.y;
                    // sai labirinto topo ou baixo
                    if(xPos < 0 || xPos > labirinto.getTamLabirinto() - 1) {
                        cromossomo.aptidao += 100;
                    } else {
                        this.validaAptidao(xPos, yPos, cromossomo);
                        cromossomo.atualizaPosicao(xPos, yPos);
                    } 
                }
                // movimenta para diagonal direita cima
                if(gene == 2) {
                    xPos = cromossomo.x - 1;
                    yPos = cromossomo.y + 1;
                    if(xPos < 0 || yPos > labirinto.getTamLabirinto() - 1) {
                        cromossomo.aptidao += 100;
                    } else {
                        this.validaAptidao(xPos, yPos, cromossomo);
                        cromossomo.atualizaPosicao(xPos, yPos);
                    }
                }
                // movimenta para direita
                if(gene == 3) {
                    xPos = cromossomo.x;
                    yPos = cromossomo.y + 1;
                    if(yPos > labirinto.getTamLabirinto() - 1) {
                        cromossomo.aptidao += 100;
                    } else {
                        this.validaAptidao(xPos, yPos, cromossomo);
                        cromossomo.atualizaPosicao(xPos, yPos);
                    }
                }
                // movimenta para diagonal direita baixo
                if(gene == 4) {
                    xPos = cromossomo.x + 1;
                    yPos = cromossomo.y + 1;
                    if(xPos > labirinto.getTamLabirinto() - 1 || yPos > labirinto.getTamLabirinto() - 1) {
                        cromossomo.aptidao += 100;
                    } else {
                        this.validaAptidao(xPos, yPos, cromossomo);
                        cromossomo.atualizaPosicao(xPos, yPos);
                    }
                }
                // movimenta para baixo
                if(gene == 5) {
                    xPos = cromossomo.x + 1;
                    yPos = cromossomo.y;
                    if(xPos > labirinto.getTamLabirinto() - 1) {
                        cromossomo.aptidao += 100;
                    } else {
                        this.validaAptidao(xPos, yPos, cromossomo);
                        cromossomo.atualizaPosicao(xPos, yPos);
                    }
                }
                // movimenta para diagonal esquerda baixo
                if(gene == 6) {
                    xPos = cromossomo.x + 1;
                    yPos = cromossomo.y - 1;
                    if(xPos > labirinto.getTamLabirinto() - 1 || yPos < 0) {
                        cromossomo.aptidao += 100;
                    } else {
                        this.validaAptidao(xPos, yPos, cromossomo);
                        cromossomo.atualizaPosicao(xPos, yPos);
                    }
                }

                // movimenta para esquerda
                if(gene == 7) {
                    xPos = cromossomo.x;
                    yPos = cromossomo.y - 1;
                    if(yPos < 0) {
                        cromossomo.aptidao += 100;
                    } else {
                        this.validaAptidao(xPos, yPos, cromossomo);
                        cromossomo.atualizaPosicao(xPos, yPos);
                    }
                }
                // movimenta para diagonal esquerda cima
                if(gene == 8) {
                    xPos = cromossomo.x - 1;
                    yPos = cromossomo.y - 1;
                    if(xPos < 0 || yPos < 0) {
                        cromossomo.aptidao += 100;
                    } else {
                        this.validaAptidao(xPos, yPos, cromossomo);
                        cromossomo.atualizaPosicao(xPos, yPos);
                    }
                }        
            }
            if(cromossomo.aptidao < 30) {
                System.out.println("Cromossomo: " + index + " Aptidão: " + cromossomo.aptidao + "\n");
            }        
        }
    }

    public void aplicarSelecaoCBF() {
        int min = Integer.MAX_VALUE;
        Cromossomo melhorAptidao = null;
        for (Cromossomo cromossomo : individuos) {
            if(min > cromossomo.aptidao) {
                min = cromossomo.aptidao;
                melhorAptidao = cromossomo;
            }
        }
        
        System.out.println("Aptidão " + melhorAptidao.aptidao);
        System.out.println("Melhor cromossomo " + melhorAptidao.toString());
        this.individuosIntermediario.add(melhorAptidao);
    }

    public void aplicarBrasileirao() {
        Random index = new Random();
        // Cromossomo pai = null;
        // Cromossomo mae = null;
        // Cromossomo torneio1 = null;
        // Cromossomo torneio2 = null;
        int contTest = 0;
        //while(populacao > individuosIntermediario.size()) {
        while(contTest < 3) {
            Cromossomo pai = null;
            Cromossomo mae = null;
            Cromossomo torneio1 = null;
            Cromossomo torneio2 = null;
            Cromossomo filho = null;
            Cromossomo filha = null;
            
            contTest++;
            torneio1 = individuos.get(index.nextInt(individuos.size()));
            torneio2 = individuos.get(index.nextInt(individuos.size()));
            while(torneio1.equals(torneio2)) {
                torneio2 = individuos.get(index.nextInt(individuos.size()));
            }
            System.out.println("primeiro: " + torneio1.aptidao);
            System.out.println("segundo: " + torneio2.aptidao);

            if(torneio1.aptidao < torneio2.aptidao) {
                pai = torneio1;
            } else pai = torneio2;

            System.out.println("pai: " + pai.aptidao);

            torneio1 = individuos.get(index.nextInt(individuos.size()));
            torneio2 = individuos.get(index.nextInt(individuos.size()));
            while(torneio1.equals(torneio2)) {
                torneio2 = individuos.get(index.nextInt(individuos.size()));
            }

            if(torneio1.aptidao < torneio2.aptidao) {
                mae = torneio1;
            } else mae = torneio2;
            System.out.println("primeiro: " + torneio1.aptidao);
            System.out.println("segundo: " + torneio2.aptidao);

            System.out.println("mae: " + mae.aptidao);

            // cruzamento 
            Random pontos = new Random();
            int pontos1 = pontos.nextInt(individuos.size());
            int pontos2 = pontos.nextInt(individuos.size());
            while(pontos1 == pontos2) {
                pontos2 = pontos.nextInt(individuos.size());
            } 
            int aux = ponto1;
            // garante ponto 1 menor
            if(pontos2 < pontos1) {
                pontos1 = pontos2;
                pontos2 = aux;  
            }

            



        }
        


    }
}