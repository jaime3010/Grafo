package grafo;
import java.util.Collections;

public class Arista implements Comparable <Arista>{
    
    private int idN1, idN2;
    private double W;
    
    public Arista (){
        
    }
    public Arista (int idN1, int idN2){
        this.idN1=idN1;
        this.idN2=idN2;
        this.W=0;
    }
    
    public Arista (int idN1, int idN2, double p){
        this.idN1=idN1;
        this.idN2=idN2;
        this.W=p;
    }
    
    public Arista (Arista a1){
        this.idN1=a1.getidN1();
        this.idN2=a1.getidN2();
        this.W=a1.getP();
    }
    
    public int getidN1 (){
        return this.idN1;
    }
    public int getidN2 (){
        return this.idN2;
    }
    public void setidN1 (int idNodo){
        this.idN1 = idNodo;
    }
    public void setidN2 (int idNodo){
        this.idN2 = idNodo;
    }
    public void setP (double p){
        this.W=p;
    }
    public double getP (){
        return this.W;
    }
    
    public void Copiar (Arista a1){
        this.idN1=a1.getidN1();
        this.idN2=a1.getidN2();
        this.W=a1.getP();
    }

@Override
public int compareTo(Arista A1){
    if (this.W > A1.getP()) return 1;
    else if (this.W < A1.getP()) return -1;
    else return 0;
}
    



}
