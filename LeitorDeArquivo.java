import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class LeitorDeArquivo {

    public static String[][] lerArquivo(String labirinto) {
        String[][] mapeando = null;
        System.out.printf("\nConteúdo do labirinto:\n");
        try {
            FileReader arquivo = new FileReader("./labirintos/" + labirinto + ".txt");
            BufferedReader lerArquivo = new BufferedReader(arquivo);

            String linha = lerArquivo.readLine();
            int tamanho = Integer.parseInt(linha);
            //System.out.printf("%s\n", tamanho);
            mapeando = new String[tamanho][tamanho];
            int cont = 0;
            while (linha != null) {
                linha = lerArquivo.readLine(); // lê da segunda até a última linha
                System.out.printf("%s\n", linha);
                if(linha == null) break;
                String[] conteudo = linha.split(" ");
                for(int i = 0; i < tamanho; i++){
                    
                    //System.out.printf("\nCont: " + cont + " " + "conteudo: " + conteudo[i] + "\n");
                    mapeando[cont][i] = conteudo[i];
                }
                //System.out.printf("cont %s\n", cont);
                cont++;
            }
            arquivo.close();
        } catch (IOException e) {
            System.err.printf("Problema ao abrir arquivo!\n", e.getMessage());
        }
        System.out.println();
        return mapeando;
    }

}