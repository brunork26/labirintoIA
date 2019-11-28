import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;


class RedeNeural {
    
    public static int bias = 1;
    public static double menor = Double.MAX_VALUE;
    /** 1-> Cima
     *  2-> Direita
     *  3-> Baixo
     *  4-> Esquerda
     */
    public ArrayList<Double> direcoes = new ArrayList(Arrays.asList(1.0,2.0,3.0,4.0));
    public ArrayList<Double> perceptron = new ArrayList<>();
    public ArrayList<Double> proximaCamada = new ArrayList<>();
    public Cromossomo cromossomo = null;
    public static Labirinto labirinto = null;
    public static Path agente = new Path(0,0);
    public Utils ar = new Utils();
    public boolean enquanto = true;

    public RedeNeural(Labirinto lab) {
        labirinto = lab;
    }

    public void aptidao(Cromossomo c) {
        this.cromossomo = c;
        this.cromossomo.pontuacao = 0;
        this.cromossomo.path = new ArrayList();
        this.enquanto = true;
        this.agente.x = 0;
        this.agente.y = 0;
        // verifica celula
        System.out.println("NOVO CROMOSSOMO ENTROU NA REDE NEURAL"); 
        while(this.enquanto == true) {
            System.out.println(this.enquanto); 
            System.out.println("ÉPOCA INICIO"); 
            this.fazEpoca();
            System.out.println("ÉPOCA FINAL"); 
        }
        
        System.out.println("TO FORA DO WHILE DA REDE NEURAL\n"); 
    }

    public void fazEpoca() {
        // anda pelo cromossomo
        ArrayList<Double> pesos = this.cromossomo.getGenes(); 
        this.perceptron = this.criaCamada(pesos, this.direcoes, 0, 12); 

        // Faz funcão de ativação
        for(int j = 0; j < perceptron.size(); j++) {
            double value = this.ar.sigmoid(perceptron.get(j));
        }

        // cria a camada de saida
        this.proximaCamada = criaCamada(pesos, this.perceptron, 16, 28);

        // atualiza posição do agente
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
        int x = agente.x;
        int y = agente.y;
        double pontuacao = this.cromossomo.pontuacao;
        switch(index) {
            case 0: {
                pontuacao += this.verificaConteudo(x - 1, y);
                System.out.println("ANDOU CIMA x = " + agente.x + " y = " + agente.y);
                this.cromossomo.pontuacao = pontuacao;
                this.cromossomo.path.add(new Path(agente.x, agente.y));
            } break;
            case 1: {
                pontuacao += this.verificaConteudo(x, y + 1);
                System.out.println("ANDOU DIREITA x = " + agente.x + " y = " + agente.y);
                this.cromossomo.pontuacao = pontuacao;
                this.cromossomo.path.add(new Path(agente.x, agente.y));
            } break;
            case 2: {
                pontuacao += this.verificaConteudo(x + 1, y);
                System.out.println("ANDOU BAIXO x = " + agente.x + " y = " + agente.y);
                this.cromossomo.pontuacao = pontuacao;
                this.cromossomo.path.add(new Path(agente.x, agente.y));
            } break;
            case 3: {
                pontuacao += this.verificaConteudo(x, y - 1);
                System.out.println("ANDOU ESQUERDA x = " + agente.x + " y = " + agente.y);
                this.cromossomo.pontuacao = pontuacao;
                this.cromossomo.path.add(new Path(agente.x, agente.y));
            } break;
            default: break;
        }

    }

    public double verificaConteudo(int x, int y) {      
        double pontuacao = 0;
        if(x < 0 || y < 0 || x > 9 || y > 9) {
            // saiu para fora
            System.out.println("SAIU");
            this.enquanto = false;
            if(this.manhattan(agente.x, agente.y) < menor){
                menor = this.manhattan(agente.x, agente.y);
                pontuacao = 30; 
            }
            pontuacao -= 100;
            return pontuacao;
        }
        String conteudo = labirinto.getPos(x, y);
        switch(conteudo) {
            case "M": 
                // adiciona moeda e pontuacao de distancia e move agente
                System.out.println("M");
                this.setAgente(x, y);
                pontuacao = 50; //this.manhattan(x, y);               
                break;
            case "0": 
                // adiciona somente pontuacao de distancia e move agente
                System.out.println("0");
                this.setAgente(x, y);
                pontuacao = 20;
                break;
            case "S": 
                // ACHOOOOOOO
                System.out.println("S");
                this.setAgente(x, y);
                this.enquanto = false;
                break;
            case "1": 
                //parede não move agente e retira cromossomo
                System.out.println("SAIU");
                this.enquanto = false;
                if(this.manhattan(agente.x, agente.y) < menor){
                    menor = this.manhattan(agente.x, agente.y);
                    pontuacao = 30; 
                }
                pontuacao -= 100;
                return pontuacao;
            default: System.out.println("E"); break;
        }
        return pontuacao;
    }

    public void setAgente(int x, int y) {
        agente.x = x;
        agente.y = y;
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