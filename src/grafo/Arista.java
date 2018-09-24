package grafo;

public class Arista {
    
    private int idN1, idN2;
    
    public Arista (){
        
    }
    public Arista (int idN1, int idN2){
        this.idN1=idN1;
        this.idN2=idN2;
    }
    
    public Arista (Arista a1){
        this.idN1=a1.getidN1();
        this.idN2=a1.getidN2();
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
    
    public void Copiar (Arista a1){
        this.idN1=a1.getidN1();
        this.idN2=a1.getidN2();
    }
    
}
