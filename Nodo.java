/** Usado no AStar */
public class Nodo {
    public int i, j;
    public Nodo proximo;
    public int h;
    public int f;

    public boolean solucao;

    public Nodo(int i, int j) {
        this.i = i;
        this.j = j;
    }

    @Override
    public String toString() {
        return "[" + this.i + ", " + this.j + "]";
    }
}