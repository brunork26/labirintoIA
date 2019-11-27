public class Labirinto {
    private static String[][] campo;

    public Labirinto(String[][] c) {
        campo = c;
    }
    /*Imprime labirinto*/
    public static String imprimeLabirinto() {
        String labirinto = "";
        for(int i = 0; i < campo.length; i++) {
            String line = "";
            for(int j = 0; j < campo[i].length; j++) {
                line = line + campo[i][j] + " ";
            }
            labirinto = labirinto + line + "\n";
        }
        return labirinto;
    }
    /*Retorna o tamanho do labirinto*/ 
    public int getTamLabirinto(){
        return campo[0].length;
    }
    /*Retorna o labirinto*/ 
    public String[][] getCampo() {
        return campo;
    }
    /*Retorna a quantidade de paredes*/ 
    public int qtdBloqueios() {
        int tamanho = 0;
        for(int i = 0; i < campo.length; i++) {    
            for(int j = 0; j < campo[i].length; j++) {
                //System.out.println(campo[i][j]);
                if(campo[i][j].equals("1")) {
                    tamanho++;
                }
            }
        }
        return tamanho;
    }
    /*Retorna a quantidade de campos livres*/ 
    public int qtdCamposLivres() {
        int tamanho = 0;
        for(int i = 0; i < campo.length; i++) {    
            for(int j = 0; j < campo[i].length; j++) {
                //System.out.println(campo[i][j]);
                if(campo[i][j].equals("0") || campo[i][j].equals("M")) {
                    tamanho++;
                }
            }
        }
        return tamanho;
    }
    /*Retorna a posição da saida*/ 
    public Path posSaida() {
        int posX = 0;
        int posY = 0;
        for(int i = 0; i < campo.length; i++) {    
            for(int j = 0; j < campo[i].length; j++) {
                //System.out.println(campo[i][j]);
                if(campo[i][j].equals("S")) {
                    posX = i;
                    posY = j;
                    break;
                }
            }
        }
        return new Path(posX, posY);
    }

    public String getPos(int x, int y) {
        return campo[x][y];
    }
}