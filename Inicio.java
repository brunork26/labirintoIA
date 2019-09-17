import java.util.Scanner;


public class Inicio {
    public static LeitorDeArquivo leLabirinto = new LeitorDeArquivo();;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Por favor identifique o nome do labirinto que se encontra na pasta /labirintos/ :");
        String nomeLabirinto = sc.nextLine();
        Labirinto labirinto = new Labirinto(labirinto.lerArquivo(nomeLabirinto));
        
    }
}