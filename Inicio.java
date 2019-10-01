import java.util.ArrayList;
import java.util.Scanner;


public class Inicio {
    public static LeitorDeArquivo leLabirinto = new LeitorDeArquivo();
    public static Labirinto labirinto;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Por favor identifique o nome do labirinto que se encontra na pasta /labirintos/ :");
        String nomeLabirinto = sc.nextLine();

        String [][] lab = LeitorDeArquivo.lerArquivo(nomeLabirinto);
        Labirinto labirinto = new Labirinto(lab);
        Ag algoritmoAG = new Ag();
        // labirinto.imprimeLabirinto();
        
        algoritmoAG.aplicarAG(labirinto);  

        labirinto = new Labirinto(leLabirinto.lerArquivo(nomeLabirinto));
        labirinto.imprimeLabirinto();

        
        String[][] campo = labirinto.getCampo();
            
        int posicaoInicialI = 0;
        int posicaoInicialJ = 0;
        int posicaoFinalI = 0;
        int posicaoFinalJ = 0;
        int cont = 0;
        int tamanho = labirinto.qtdBloqueios();
        int[][] block = new int[tamanho][2];  
        for(int i = 0; i < campo.length; i++) {
            for(int j = 0; j < campo[i].length; j++) {
                //System.out.println(campo[i][j]);
                if(campo[i][j].equals("1")) {
                    System.out.println("[" + i + ", " + j + "]");
                    block[cont][0] = i;
                    block[cont][1] = j;
                    cont++;
                } else if(campo[i][j].equals("E")) {
                    posicaoInicialI = i;
                    posicaoInicialJ = j;
                } else if(campo[i][j].equals("S")) {
                    posicaoFinalI = i; 
                    posicaoFinalJ = j;
                }
            }
        }
        AStar astar = new AStar(campo.length, campo.length, posicaoInicialI, posicaoInicialJ, posicaoFinalI, posicaoFinalJ, 
            block);
        astar.display();
        astar.processo(); // aplica o A*
        // astar.displayPontos();
        astar.displayResultado();

        sc.close();

    }
}