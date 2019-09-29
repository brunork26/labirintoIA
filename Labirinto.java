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
        return campo.length;
    }
}