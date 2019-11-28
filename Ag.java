import java.util.ArrayList;
import java.util.Random;
import java.math.*;

public class Ag {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    private ArrayList<Cromossomo> individuos = new ArrayList<Cromossomo>();
    private ArrayList<Cromossomo> individuosIntermediario = new ArrayList<Cromossomo>();
    public Labirinto labirinto = null;
    public int contZero;
    public int geracao = 0;
    public static int populacao = 100;
    public boolean terminou = false;
    public int mutacao = 0;
    public int tamanhoCromossomo = 0;
    public Labirinto labirintoEv;
    public String nomeLab = "";
    private static RedeNeural redeNeural;

    public int contConvergencia = 0;
    public int aptConvergencia = 0;
    public int confirmaValorConvergencia = 0;
 
    public Ag(){};

    public void aplicarAG(Labirinto labirinto, int valMut, int populacao, String nomeLabirinto){
        this.nomeLab = nomeLabirinto;
        int cont = 0;
        this.populacao = populacao;
        this.labirinto = labirinto;
        redeNeural = new RedeNeural(labirinto);
        System.out.println("\n Iniciando Algoritmo Genético...\n");
        criarPopulacaoInicial(this.labirinto.qtdCamposLivres(), valMut);
        
        while(cont < 100 ) {
            System.out.println("\n GERAÇÃO : " + cont );
            aplicarAptidao();
            aplicarSelecaoCBF();
            aplicarBrasileirao();
            int c = 0;
            while(c < this.mutacao) {
                c++;
                aplicarXmen();
            }    
            this.individuos = this.individuosIntermediario;
            this.individuosIntermediario = new ArrayList<Cromossomo>();
            cont++;
        }

    }

    public double sigmoid(double x) {
        return 1 / (1 + Math.exp(-x));
    }

   

    /**Transforma valores entre 1 a 100 em um intervalo de 0 a 1 */
    public static double rand() {
        double min = 1;
        double max = 100;
        Random gerador = new Random();
        //double random = Double.valueOf((gerador.nextInt(100)+1)/100.0);
        double resposta = ((gerador.nextInt(100)+1) - min) / (max - min);
        Utils ar = new Utils();
        return ar.round(resposta, 2);
    }

    // Como campo é quadrático, a populacao inicial vai ser de 10 individuos com 100 cromossomos
    public void criarPopulacaoInicial(int tam, int valMut){
        System.out.println("\nCriando Populacao Inicial...\n");
        Random gerador = new Random();
        double gene;
        System.out.println(tam);
        this.tamanhoCromossomo = tam;
        int tamCromossomo = tam; // talvez passar por parâmetro

        for(int i = 0; i < populacao; i++) {
            Cromossomo cromossomo = new Cromossomo();
            for(int j = 0; j < tamCromossomo; j++) {            
                // gene = round(this.sigmoid((gerador.nextInt(5) + 1)), 2); // garante de 1 até 5
                gene = rand(); // (gerador.nextInt(100)+1)/100.0;
                cromossomo.addGene(gene);
            }
            this.individuos.add(cromossomo);
            System.out.println(cromossomo.toString());
        }

        this.mutacao = ((tam * populacao)*valMut)/100;

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

    /** Deve ser feito aqui o algoritmo de redes neurais */
    public void aplicarAptidao(){
        String[][] campo = labirinto.getCampo();
        int index = 0;
        String caminho = "";
        for (Cromossomo cromossomo : this.individuos) {
            index++;    
            System.out.println(cromossomo.toString());
            redeNeural.aptidao(cromossomo);          
        }
    }


    public int min = Integer.MAX_VALUE;
    public Cromossomo melhorAptidao = null;
    public void aplicarSelecaoCBF() {
        System.out.println("Aplicando seleção");
        ArrayList<Cromossomo> melhores = new ArrayList<>();
        for(int i = 0; i < 4; i++) {
            melhores.add(this.individuos.get(i));
        }
        for(int i = 4; i < this.individuos.size(); i++) {
            for(int j = 0; j < melhores.size(); j++) {
                if(this.individuos.get(i).pontuacao > melhores.get(j).pontuacao) {
                    melhores.remove(j);
                    melhores.add(this.individuos.get(i));
                }
            }
        }
        this.individuosIntermediario = melhores;
        for(int i = 0; i < this.individuosIntermediario.size(); i++) {
            //System.out.println(i + ". Pontuação: " + this.individuosIntermediario.get(i).pontuacao);
            //System.out.println("" + this.individuosIntermediario.get(i).printPath());
            System.out.println(this.individuosIntermediario.get(i).toString());
        }
    }

    public void aplicarBrasileirao() {
        Random index = new Random();
        int contTest = 0;
        while(populacao > this.individuosIntermediario.size()) {
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

            if(torneio1.pontuacao < torneio2.pontuacao) {
                pai = torneio1;
            } else pai = torneio2;


            torneio1 = this.individuos.get(index.nextInt(this.individuos.size()));
            torneio2 = this.individuos.get(index.nextInt(this.individuos.size()));
            while(torneio1.equals(torneio2)) {
                torneio2 = this.individuos.get(index.nextInt(this.individuos.size()));
            }

            if(torneio1.pontuacao < torneio2.pontuacao) {
                mae = torneio1;
            } else mae = torneio2;

            // cruzamento 
            ArrayList<Integer> rands = new ArrayList<>();
            for(int i = 0; i < this.individuos.size(); i++) {
                Random rand = new Random();
                rands.add(rand.nextInt(2));
            }
            for(int i = 0; i < rands.size(); i++) {
                if(rands.get(i) == 0) {
                    filho.addGene(pai.getGenes().get(i));
                    filha.addGene(mae.getGenes().get(i));
                } else {
                    filho.addGene(mae.getGenes().get(i));
                    filha.addGene(pai.getGenes().get(i));
                }
            }
            this.individuosIntermediario.add(filho);
            this.individuosIntermediario.add(filha);

        }
        this.individuosIntermediario.remove(this.individuosIntermediario.size() - 1);
    }

    public void aplicarXmen() {
        Random index = new Random();
        int i = index.nextInt(this.individuosIntermediario.size());
        Cromossomo wolverine = this.individuosIntermediario.get(i);
        this.individuosIntermediario.remove(i);
        

        Random escolheGene = new Random();
        int nGene = escolheGene.nextInt(this.individuosIntermediario.get(i).getGenes().size() - 1);

        Random valorGene = new Random();
        // double vGene = this.sigmoid(valorGene.nextInt(7) + 1);
        double vGene = rand();

        wolverine.getGenes().set(nGene, vGene);
        this.individuosIntermediario.add(wolverine);
    }


    public void evolucaoAg(ArrayList<Path> path, Cromossomo cromossomo) {
        Labirinto lab = this.labirintoEv;
        String saida = "Cromossomo: " + cromossomo.toString();

        System.out.println("Cromossomo: " + cromossomo.toString() + "\n");

        for (int i = 0; i < path.size(); i++) {
            lab.getCampo()[path.get(i).x][path.get(i).y] = "$";
            saida = saida + "Ponto: (" + path.get(i).x + "," + path.get(i).y + "):" + "\n" + lab.imprimeLabirinto();
            System.out.println("Ponto: (" + path.get(i).x + "," + path.get(i).y + "):" );
            System.out.println(lab.imprimeLabirinto());
        }

        EscritaDeArquivo arquivo = new EscritaDeArquivo();
        try {
            arquivo.escreve(saida, this.nomeLab + ".txt");
        } catch (Exception e) { 
            System.out.println("Ops não conseguiu criar o arquivo");
        }

    }
}