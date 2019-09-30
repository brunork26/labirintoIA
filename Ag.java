import java.util.ArrayList;
import java.util.Random;

public class Ag {
    // static int [][] individuos;
    private static ArrayList<Cromossomo> individuos = new ArrayList<Cromossomo>();
    private static ArrayList<Cromossomo> individuosIntermediario = new ArrayList<Cromossomo>();
    public Labirinto labirinto = null;
    public int contZero;
    public int geracao = 0;
    public static int populacao = 100;

 
    public Ag(){};

    public void aplicarAG(Labirinto labirinto){
        int cont = 100;
        
            this.labirinto = labirinto;
            System.out.println("\n Iniciando Algoritmo Genético...\n");
            criarPopulacaoInicial(this.labirinto.qtdCamposLivres());
            // imprimeMatrizPopulacao();
        while(cont > 0) {
            aplicarAptidao();
            aplicarSelecaoCBF();
            aplicarBrasileirao();
            aplicarXmen();
            this.individuos = this.individuosIntermediario;
            //aplicarXmen();
            cont--;
        }
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

    public boolean validaSolucao(int xPos, int yPos, Cromossomo cromossomo) {
        String[][] campo = labirinto.getCampo();
        if(campo[xPos][yPos].equals("S") && cromossomo.aptidao == 0) { 
            System.out.println("Acho o CAMINHO"); 
            System.exit(0);
            
        }
        if(campo[xPos][yPos].equals("S")) return true;
        return false;
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
                        if(this.validaSolucao(xPos, yPos, cromossomo)) {
                            break;
                        }                      
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
                        if(this.validaSolucao(xPos, yPos, cromossomo)) {
                            break;
                        } 
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
                        if(this.validaSolucao(xPos, yPos, cromossomo)) {
                            break;
                        } 
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
                        if(this.validaSolucao(xPos, yPos, cromossomo)) {
                            break;
                        } 
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
                        if(this.validaSolucao(xPos, yPos, cromossomo)) {
                            break;
                        } 
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
                        if(this.validaSolucao(xPos, yPos, cromossomo)) {
                            break;
                        } 
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
                        if(this.validaSolucao(xPos, yPos, cromossomo)) {
                            break;
                        } 
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
                        if(this.validaSolucao(xPos, yPos, cromossomo)) {
                            break;
                        } 
                        cromossomo.atualizaPosicao(xPos, yPos);
                    }
                }
                     
            }
            // if(cromossomo.aptidao < 30) {
                //System.out.println("Cromossomo: " + index + " Aptidão: " + cromossomo.aptidao + "\n");
            //}        
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
        melhorAptidao.aptidao = 0;
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
        while(populacao > individuosIntermediario.size()) {
        //while(contTest < 3) {
            Cromossomo pai = null;
            Cromossomo mae = null;
            Cromossomo torneio1 = null;
            Cromossomo torneio2 = null;
            Cromossomo filho = new Cromossomo();
            Cromossomo filha = new Cromossomo();
            
            contTest++;
            torneio1 = individuos.get(index.nextInt(individuos.size()));
            torneio2 = individuos.get(index.nextInt(individuos.size()));
            while(torneio1.equals(torneio2)) {
                torneio2 = individuos.get(index.nextInt(individuos.size()));
            }
            // System.out.println("primeiro: " + torneio1.aptidao);
            //System.out.println("segundo: " + torneio2.aptidao);

            if(torneio1.aptidao < torneio2.aptidao) {
                pai = torneio1;
            } else pai = torneio2;

            //System.out.println("pai: " + pai.aptidao);

            torneio1 = individuos.get(index.nextInt(individuos.size()));
            torneio2 = individuos.get(index.nextInt(individuos.size()));
            while(torneio1.equals(torneio2)) {
                torneio2 = individuos.get(index.nextInt(individuos.size()));
            }

            if(torneio1.aptidao < torneio2.aptidao) {
                mae = torneio1;
            } else mae = torneio2;
            // System.out.println("primeiro: " + torneio1.aptidao);
            // System.out.println("segundo: " + torneio2.aptidao);

            // System.out.println("mae: " + mae.aptidao);

            // cruzamento 
            Random pontos = new Random();
            int pontos1 = pontos.nextInt(labirinto.qtdCamposLivres());
            int pontos2 = pontos.nextInt(labirinto.qtdCamposLivres());
            while(pontos1 == pontos2 || 
                        pontos1 == 0 || 
                        pontos2 == 0 || 
                        pontos1 == individuos.size() - 1 || 
                        pontos2 == individuos.size() - 1) {
                            pontos1 = pontos.nextInt(labirinto.qtdCamposLivres());
                            pontos2 = pontos.nextInt(labirinto.qtdCamposLivres());
            } 
            int aux = pontos1;
            // garante ponto 1 menor
            if(pontos2 < pontos1) {
                pontos1 = pontos2;
                pontos2 = aux;  
            }
            // 0 --- ponto1
            // ponto1 + 1 --- ponto2
            // ponto2 + 1 --- fim se existir
            // System.out.println("Pai: " + pai.getGenes());
            // System.out.println("Mae: " + mae.getGenes());
            // System.out.println("Ponto1: " + pontos1);
            // System.out.println("Ponto2: " + pontos2);
            for(int i = 0; i < pontos1; i++) {
                filho.addGene(pai.getGenes().get(i));
                filha.addGene(mae.getGenes().get(i));
            }
            for(int i = pontos1; i < pontos2; i++) {
                filho.addGene(mae.getGenes().get(i));
                filha.addGene(pai.getGenes().get(i));
            }
            for(int i = pontos2; i < labirinto.qtdCamposLivres(); i++) {
                filho.addGene(pai.getGenes().get(i));
                filha.addGene(mae.getGenes().get(i));
            }
            
            // System.out.println("Filho: " + filho.getGenes());
            // System.out.println("Filha: " + filha.getGenes());

            this.individuosIntermediario.add(filho);
            this.individuosIntermediario.add(filha);

        }
        
        this.individuosIntermediario.remove(this.individuosIntermediario.size() - 1);

        // for(int i = 0; i < this.individuosIntermediario.size(); i++) {
        //     System.out.println(this.individuosIntermediario.get(i) + "\n");
        // }
    }

    public void aplicarXmen() {
        Random index = new Random();
        int i = index.nextInt(this.individuosIntermediario.size());
        Cromossomo wolverine = this.individuosIntermediario.get(i);
        this.individuosIntermediario.remove(i);
        //System.out.println(wolverine.toString());

        Random escolheGene = new Random();
        int nGene = escolheGene.nextInt(this.labirinto.qtdCamposLivres());

        Random valorGene = new Random();
        int vGene = valorGene.nextInt(7) + 1;

        wolverine.getGenes().set(nGene, vGene);
        this.individuosIntermediario.add(wolverine);
    }
}