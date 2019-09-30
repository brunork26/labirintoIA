import java.util.ArrayList;

public class Cromossomo {

    private ArrayList<Integer> genes = new ArrayList<Integer>();
    public int aptidao = 0;
    public int x = 0;
    public int y = 0;

    public Cromossomo() {

    }

    public void addGene(int gene) {
        genes.add(gene);
    }

    public ArrayList<Integer> getGenes() {
        return this.genes;
    }

    public void atualizaPosicao(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        String toString = "";
        for(int i = 0; i < genes.size(); i++) {
            toString = toString + genes.get(i) + "-";
        }
        return toString + "\n";
    }
}