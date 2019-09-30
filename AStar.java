import java.util.PriorityQueue;

public class AStar {
    public static final int diagonal = 14;
    public static final int vert_hori = 10;

    private Nodo[][] grid;

    private PriorityQueue<Nodo> nodosAbertos;
    private boolean[][] nodosFechados;

    private int inicioI, inicioJ;
    private int fimI, fimJ;

    public AStar (int largura, int altura, int iI, int iJ, int eI, int eJ, int[][] blocks) {
        this.grid = new Nodo[largura][altura];
        this.nodosFechados = new boolean[largura][altura];
        this.nodosAbertos = new PriorityQueue<Nodo>((Nodo n1, Nodo n2) -> {
            return n1.f < n2.f ? -1 : n1.f > n2.f ? 1 : 0;
        });
        iniciaNodo(iI, iJ);
        fimNodo(eI, eJ);

        // inicio heuristica
        for (int i = 0; i < this.grid.length; i++) {
            for (int j = 0; j < this.grid[i].length; j ++) {
                this.grid[i][j] = new Nodo(i, j);
                this.grid[i][j].h = Math.abs(i - fimI) + Math.abs(j - fimJ);
                this.grid[i][j].solucao = false;
            }
        }

        this.grid[inicioI][inicioJ].f = 0;

        for(int i = 0; i < blocks.length; i++) {
            addBlockOnNodo(blocks[i][0], blocks[i][1]);
        }

    }

    public void addBlockOnNodo(int i, int j) {
        this.grid[i][j] = null;
    }

    public void iniciaNodo(int i, int j) {
        this.inicioI = i;
        this.inicioJ = j;
    }

    public void fimNodo(int i, int j) {
        this.fimI = i;
        this.fimJ = j;
    }

    public void atualizaCustoSeNecessario(Nodo atual, Nodo t, int custo) {
        if( t == null || nodosFechados[t.i][t.j]) return;

        int tF = t.h + custo;
        boolean aberto = nodosAbertos.contains(t);

        if (!aberto || tF < t.f) {
            t.f = tF;
            t.proximo = atual;

            if(!aberto) {
                nodosAbertos.add(t);
            }
        }
    }

    public void processo() {
        nodosAbertos.add(grid[inicioI][inicioJ]);
        Nodo atual;

        while(true) {
            atual = nodosAbertos.poll();

            if(atual == null) break;

            nodosFechados[atual.i][atual.j] = true;

            if(atual.equals(grid[fimI][fimJ])) return;

            Nodo t;

            if (atual.i - 1 >= 0 ) {
                t = grid[atual.i - 1][atual.j];
                atualizaCustoSeNecessario(atual, t, atual.f + this.vert_hori);

                if(atual.j - 1 >= 0) {
                    t = grid[atual.i - 1][atual.j-1];
                    atualizaCustoSeNecessario(atual, t, atual.f + this.diagonal);
                }

                if(atual.j + 1 < this.grid[0].length) {
                    t = grid[atual.i - 1][atual.j+1];
                    atualizaCustoSeNecessario(atual, t, atual.f + this.diagonal);
                }
            }

            if(atual.j - 1 >= 0){
                t = grid[atual.i][atual.j-1];
                atualizaCustoSeNecessario(atual, t, atual.f + this.vert_hori);
            }

            if(atual.j + 1 < this.grid[0].length) {
                t = grid[atual.i][atual.j+1];
                atualizaCustoSeNecessario(atual, t, atual.f + this.vert_hori);
            }

            if(atual.i + 1 < this.grid.length) {
                t = grid[atual.i + 1][atual.j];
                atualizaCustoSeNecessario(atual, t, atual.f + this.vert_hori);

                if(atual.j - 1 >= 0) {
                    t = grid[atual.i + 1][atual.j - 1];
                    atualizaCustoSeNecessario(atual, t, atual.f + this.diagonal);
                }

                if(atual.j + 1 < grid[0].length ) {
                    t = grid[atual.i + 1][atual.j + 1];
                    atualizaCustoSeNecessario(atual, t, atual.f + this.diagonal);
                }

            }

        }
    }

    public void display() {
        System.out.println("Grid :");

        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[i].length; j++) {
                if(i==inicioI && j==inicioJ) System.out.print("E  ");
                else if(i == fimI && j == fimJ) System.out.print("S  ");
                else if(grid[i][j] != null) System.out.printf("%-3d", 0);
                else System.out.print("1  ");
             }
             System.out.println();
        }
        System.out.println();
    }

    public void displayPontos() {
        System.out.println("\nPontos por nodos : ");

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
              if(grid[i][j] != null) System.out.printf("%-3d", grid[i][j].f);
              else System.out.print("BL  ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public void displayResultado() {
        if(nodosFechados[fimI][fimJ]) {
            System.out.println("Path: ");
            Nodo atual = grid[fimI][fimJ];
            System.out.println(atual);
            grid[atual.i][atual.j].solucao = true;

            while(atual.proximo != null) {
                System.out.println(" -> " + atual.proximo);
                grid[atual.proximo.i][atual.proximo.j].solucao = true;
                atual = atual.proximo;
            }
            System.out.println("\n");

            for(int i = 0; i < grid.length; i++) {
                for(int j = 0; j < grid[i].length; j++) {
                    if(i==inicioI && j==inicioJ) System.out.print("E  ");
                    else if(i == fimI && j == fimJ) System.out.print("S  ");
                    else if(grid[i][j] != null) System.out.printf("%-3s", grid[i][j].solucao ? "X" : "0");
                    else System.out.print("1  ");
                 }
                 System.out.println();
            }
            System.out.println();
        } else System.out.println("não há nenhum caminho possível");
    }    
}