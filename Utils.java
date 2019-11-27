import java.math.*;
class Utils{
    public double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
    
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    public double sigmoid(double x) {
       // Random gerador = new Random();
        //int sinal = gerador.nextInt(2);
        // System.out.println(sinal);
        // if(sinal == 0) {
        //     sinal = 1;
        // } else {
        //     sinal = -1;
        // }
        //x = x * sinal;
        x *= (-1); 
            
        return 1 / (1 + Math.exp(x));
    }
}