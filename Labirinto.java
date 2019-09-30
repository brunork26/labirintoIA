public class Labirinto {
    private static String[][] campo;

    public Labirinto(String[][] campo) {
        this.campo = campo;
    }
    public static void imprimeLabirinto() {
        for(int i = 0; i < campo.length; i++) {
            String line = "";
            for(int j = 0; j < campo[i].length; j++) {
                line = line + campo[i][j] + " ";
            }
            System.out.println(line);
        }
    }
    public int getTamLabirinto(){
        return campo[0].length;
    }
    public String[][] getCampo() {
        return this.campo;
    }

    public int qtdBloqueios() {
        int tamanho = 0;
        for(int i = 0; i < this.campo.length; i++) {    
            for(int j = 0; j < this.campo[i].length; j++) {
                //System.out.println(campo[i][j]);
                if(campo[i][j].equals("1")) {
                    tamanho++;
                }
            }
        }
        return tamanho;
    }
}