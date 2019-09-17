import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class LeitorDeArquivo {

    public static void lerArquivo(String labirinto) {
        
        System.out.printf("\nConteúdo do labirinto:\n");
        try {
            FileReader arquivo = new FileReader("./labirintos/" + labirinto + ".txt");
            BufferedReader lerArquivo = new BufferedReader(arquivo);

            String linha = lerArquivo.readLine();
            while (linha != null) {
                System.out.printf("%s\n", linha);
                linha = lerArquivo.readLine(); // lê da segunda até a última linha
            }

            arquivo.close();
        } catch (IOException e) {
            System.err.printf("Problema ao abrir arquivo!\n", e.getMessage());
        }
        System.out.println();
    }

}