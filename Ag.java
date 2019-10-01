import java.util.ArrayList;
import java.util.Random;

public class Ag {
    // static int [][] individuos;
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    private ArrayList<Cromossomo> individuos = new ArrayList<Cromossomo>();
    private ArrayList<Cromossomo> individuosIntermediario = new ArrayList<Cromossomo>();
    public Labirinto labirinto = null;
    public int contZero;
    public int geracao = 0;
    public static int populacao = 1000;
    public boolean terminou = false;
    public int mutacao = 0;
 
    public Ag(){};

    public void aplicarAG(Labirinto labirinto, int valMut, int populacao){
        int cont = 0;
        this.populacao = populacao;
        this.labirinto = labirinto;
        System.out.println("\n Iniciando Algoritmo Genético...\n");
        criarPopulacaoInicial(this.labirinto.qtdCamposLivres(), valMut);
        // imprimeMatrizPopulacao();
        while(cont < 1000000 ) {
            System.out.println("\n GERAÇÃO : " + cont );
            for(int i = 0; i < this.individuos.size(); i++) {
                //this.individuos.get(i).aptidao = 0;
                 //System.out.println("Aptidão: " + this.individuos.get(i).aptidao);
                 
            }
            aplicarAptidao();
            aplicarSelecaoCBF();
            aplicarBrasileirao();
            int c = 0;
            while(c < this.mutacao) {
                c++;
                aplicarXmen();
            }
            
            //this.individuos = null; 
            this.individuos = this.individuosIntermediario;
            this.individuosIntermediario = new ArrayList<Cromossomo>();
            //aplicarXmen();
            cont++;
        }

    }
    // Como campo é quadrático, a populacao inicial vai ser de 10 individuos com 100 cromossomos
    public void criarPopulacaoInicial(int tam, int valMut){
        System.out.println("\nCriando Populacao Inicial...\n");
        Random gerador = new Random();
        int gene;
        System.out.println(tam);
        int tamCromossomo = tam; // talvez passar por parâmetro

        for(int i = 0; i < this.populacao; i++) {
            Cromossomo cromossomo = new Cromossomo();
            for(int j = 0; j < tamCromossomo; j++) { 
                gene = gerador.nextInt(7) + 1; // garante de 1 até 8
                cromossomo.addGene(gene);
            }
            this.individuos.add(cromossomo);
        }

        this.mutacao = ((tam*this.populacao)*valMut)/100;

        System.out.println("mutacao: " + this.mutacao);

        // for(int i = 0; i < individuos.size(); i++) {
        //     System.out.println(individuos.get(i));
        // }
    }

    public void imprimeMatrizPopulacao(){
        System.out.println("Número de individuos: " + individuos.size());
        for(int i = 0; i < this.individuos.size(); i++) {
            System.out.println(this.individuos.get(i).toString());
        }
    }

    public void validaAptidao(int xPos, int yPos, Cromossomo cromossomo) {
        String[][] campo = labirinto.getCampo();
        //System.out.println(xPos + " " + yPos);
        cromossomo.path.add(new Path(xPos, yPos));
        if(campo[xPos][yPos].equals("1")) {
            // System.out.println("Bateu parede");
            cromossomo.aptidao += 1;
        }
    }

    public boolean validaSolucao(int xPos, int yPos, Cromossomo cromossomo) {
        String[][] campo = labirinto.getCampo();
        if(campo[xPos][yPos].equals("S") && cromossomo.aptidao == 0) { 
            // System.out.println("Caminho: " + cromossomo.getGenes());
            System.out.println("Aptidão: " + cromossomo.aptidao);
            String path = "Path: ";
            for(int i = 0; i < cromossomo.path.size(); i++) {

                path += "(" + cromossomo.path.get(i).x + "," + cromossomo.path.get(i).y + ") ";                
            }
            System.out.println("Achou o caminho!!!!"); 
            System.out.println(path);
            int cont = 0;
            for (Path obj : cromossomo.path) {
                cont++;
                if(cromossomo.path.size() == cont ) break;
                //labirinto.getCampo()[obj.x][obj.y] = ANSI_RED + "$" + ANSI_RESET;    
                labirinto.getCampo()[obj.x][obj.y] = "$";          
            }
            System.out.println(labirinto.imprimeLabirinto());
            this.terminou = true;
            EscritaDeArquivo arquivo = new EscritaDeArquivo();
            try {
                arquivo.escreve(path + "\n" + labirinto.imprimeLabirinto(), "AlgoritmoGenetico.txt");
            } catch (Exception e) { 
                System.out.println("Ops não conseguiu criar o arquivo");
            }
            
            System.exit(0);        
        }
        if(campo[xPos][yPos].equals("S")) return true;
        return false;
    }

    public void aplicarAptidao(){
        String[][] campo = labirinto.getCampo();
        int index = 0;
        String caminho = "";
        for (Cromossomo cromossomo : this.individuos) {
            index++; 
            int xPos = 0; // novaPosicao
            int yPos = 0; // novaPosicao
            cromossomo.x = 0;
            cromossomo.y = 0;     
            //System.out.println(cromossomo.toString());
            for (Integer gene : cromossomo.getGenes()) {
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
        for (Cromossomo cromossomo : this.individuos) {
            if(min > cromossomo.aptidao) {
                min = cromossomo.aptidao;
                melhorAptidao = cromossomo;
            }
        }
        
        System.out.println("Aptidão :" + melhorAptidao.aptidao);
        melhorAptidao.aptidao = 0;
        melhorAptidao.path = new ArrayList<Path>();
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
        while(populacao > this.individuosIntermediario.size()) {
        //while(contTest < 3) {
            Cromossomo pai = null;
            Cromossomo mae = null;
            Cromossomo torneio1 = null;
            Cromossomo torneio2 = null;
            Cromossomo filho = new Cromossomo();
            Cromossomo filha = new Cromossomo();
            
            contTest++;
            torneio1 = this.individuos.get(index.nextInt(this.individuos.size()));
            torneio2 = this.individuos.get(index.nextInt(this.individuos.size()));
            while(torneio1.equals(torneio2)) {
                torneio2 = this.individuos.get(index.nextInt(this.individuos.size()));
            }
            // System.out.println("primeiro: " + torneio1.aptidao);
            //System.out.println("segundo: " + torneio2.aptidao);

            if(torneio1.aptidao < torneio2.aptidao) {
                pai = torneio1;
            } else pai = torneio2;

            //System.out.println("pai: " + pai.aptidao);

            torneio1 = this.individuos.get(index.nextInt(this.individuos.size()));
            torneio2 = this.individuos.get(index.nextInt(this.individuos.size()));
            while(torneio1.equals(torneio2)) {
                torneio2 = this.individuos.get(index.nextInt(this.individuos.size()));
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
                        pontos1 == this.individuos.size() - 1 || 
                        pontos2 == this.individuos.size() - 1) {
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