
package grafo;


public class nodo {
    
    private int id;
    private int Grado;
    private double x,y;
    private int conectado;
    private boolean F;
    private double W;
    
    public nodo (){
        this.Grado=0;
        this.x =0;
        this.y =0;
        this.conectado =0;
        this.F=false;
        this.W = 0;
    }
    
    public nodo (nodo n1){
        this.Grado=n1.getGrado();
        this.id=n1.get();
        this.x=n1.getX();
        this.y=n1.getY();
        this.conectado=n1.getConectado();
        this.F = n1.getF();
        this.W = n1.getW();
    }   
    
    public nodo (int idDado){    
        this.id = idDado;
        this.Grado = 0;
        this.x =0;
        this.y =0;
        this.conectado =0;
        this.F=false;
        this.W = 0;
    } 
    
    public nodo (int idDado, int con, int gra, double x1, double y1, boolean g, double p){    
        this.id = idDado;
        this.conectado = con;
        this.Grado = gra;
        this.x = x1;
        this.y = y1;
        this.F = g;
        this.W = p;
    }
    
    public nodo (int idDado, double x1, double y1){    
        this.id = idDado;
        this.Grado = 0;
        this.x =x1;
        this.y =y1;
        this.F=false;
        this.conectado = 0;
        this.W = 0;
    }
    
    
    
    public void copiar (nodo n1){
        this.Grado=n1.getGrado();
        this.id=n1.get();
        this.x=n1.getX();
        this.y=n1.getY();
        this.conectado=n1.getConectado();
        this.F=n1.getF();
        this.W = n1.getW();
    }
    
    public void set (int idDado){
        this.id = idDado;
    }
    public int get (){
        return this.id;
    }    
    public void setGrado (int i){
        this.Grado = i;
    }    
    public int getGrado (){
        return this.Grado;
    }    
    public void IncGrado (int i){
        this.Grado=this.Grado+i;
    } 
    public void DecGrado (int i){
        this.Grado=this.Grado-i;
    }
    public void setX (double x1){
        this.x=x1;
    }
    public void setY (double x1){
        this.y=x1;
    }
    public double getX (){
        return this.x;
    }
    public double getY (){
        return this.y;
    }
    public void conectar(){
        this.conectado=1;
    }
    public int getConectado(){
        return this.conectado;
    }
    
    public void setF (boolean a){
        this.F = a;
    }
    
    public boolean getF (){
        return this.F;
    }
    
    public void setW (double p){
        this.W = p;
    }
    public double getW (){
        return this.W; 
    }
    
}
