import java.util.ArrayList;

public class Cromossomo {

    private ArrayList<Double> genes = new ArrayList<Double>();
    public ArrayList<Path> path = new ArrayList<Path>();
    public int aptidao = 0; //o que deve ser calculado na rede neural
    public int moedas = 0;
    public int x = 0;
    public int y = 0;
    public double pontuacao = 0;

    /*Adiciona Gene ao cormossomo*/
    public void addGene(double gene) {
        genes.add(gene);
    }

    /*Retorna genes do cormossomo*/
    public ArrayList<Double> getGenes() {
        return genes;
    }
    /*Atualiza posição do agente*/
    public void atualizaPosicao(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String printPath() {
        String s = "";
        for(int i = 0; i < this.path.size(); i++) {
            s += "x: " + this.path.get(i).x + " y: " + this.path.get(i).y + " \n";
        }
        return s;
    }



    /*Imprime cromossomo*/
    @Override
    public String toString() {
        String toString = "";
        for(int i = 0; i < genes.size(); i++) {
            toString = toString + genes.get(i) + "-";
        }
        toString += "\nPontuacao: " +  this.pontuacao + "\n";
        toString += "Path: " + this.printPath() + "\n";
        return toString + "\n";
    }
}