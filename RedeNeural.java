import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;


class RedeNeural {
    
    public static int bias = 1;
    /** 1-> Cima
     *  2-> Direita
     *  3-> Baixo
     *  4-> Esquerda
     */
    public ArrayList<Double> direcoes = new ArrayList(Arrays.asList(1.0,2.0,3.0,4.0));
    public ArrayList<Double> perceptron = new ArrayList<>();
    public ArrayList<Double> proximaCamada = new ArrayList<>();
    public static Labirinto labirinto = null;
    public static Path agente = new Path(0,0);
    public Utils ar = new Utils();

    public RedeNeural(Labirinto lab) {
        labirinto = lab;
    }

    public void aptidao(Cromossomo cromossomo) {
        ArrayList<Double> pesos = cromossomo.getGenes();
        // anda pelo cromossomo
        System.out.println("Tamanho = " + pesos.size());       
        this.perceptron = this.criaCamada(pesos, this.direcoes, 0, 12);
        System.out.println("Perceptron = " + perceptron.toString() + "\n");     
        for(int j = 0; j < perceptron.size(); j++) {
            double value = this.ar.sigmoid(perceptron.get(j));
            System.out.println("Valor de saída = " + value);
        }
        this.proximaCamada = criaCamada(pesos, this.perceptron, 16, 28);
        System.out.println("Camada seguinte = " + proximaCamada.toString() + "\n");

        // verifica celula
        agenteCaminha();
    }

    public void agenteCaminha() {
        double max = 0;
        int index = 0;
        for(int i = 0; i < this.proximaCamada.size(); i++) {
            if(this.proximaCamada.get(i) > max) {
                max = this.proximaCamada.get(i);
                index = i;
            }
        }
        // andar
        double pontuacao = 0;
        switch(index) {
            case 0: {
                pontuacao = this.verificaConteudo(x - 1, y);
            } break;
            case 1: {
                pontuacao = this.verificaConteudo(x, y + 1);
            } break;
            case 2: {
                pontuacao = this.verificaConteudo(x + 1, y);
            } break;
            case 3: {
                pontuacao = this.verificaConteudo(x, y - 1);
            } break;
            default: break;
        }

    }

    public double verificaConteudo(int x, int y) {
        String conteudo = labirinto.getPos(x, y);
        double pontuacao = 0;
        switch(conteudo) {
            case "M": 
                // adiciona moeda e pontuacao de distancia e move agente
                break;
            case "0": 
                // adiciona somente pontuacao de distancia e move agente
                break;
            case "1": 
                //parede não move agente e retira cromossomo
                return -1;
            default: break;
        }
        return pontuacao;
    }

    public double manhattan(int x, int y) {
        Path saida = labirinto.posSaida();
        return Math.abs(saida.x - x) + Math.abs(saida.y - y);
    }

    public ArrayList<Double> criaCamada(ArrayList<Double> peso, 
                                        ArrayList<Double> dir, 
                                        int inicio, int fim ) {
        int i = inicio;
        ArrayList<Double> saida  = new ArrayList();
        while(i <= fim) {
            System.out.println("Cont = " + i);
            saida.add(this.somador(peso.subList(i, i + 4), dir));
            i += 4;
        }
        return saida;
    }



    public double somador(List<Double> pesos, ArrayList<Double> dir) {
        double resultado = 0;
        System.out.println("Tamanho sublist = " + pesos.toString());
        for(int i = 0; i < pesos.size(); i++) {
            resultado = resultado + (pesos.get(i) * dir.get(i));
        }
        return this.ar.round((resultado + 1),2);
    }

}