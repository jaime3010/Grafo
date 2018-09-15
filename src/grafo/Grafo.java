package grafo;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.HashMap;


public class Grafo { 
    
    private HashMap<Integer,nodo> Nodos;
    private HashMap<Integer,Arista> Aristas;
    
    public Grafo (HashMap<Integer,nodo> N, HashMap<Integer,Arista> A){
        this.Nodos=N;
        this.Aristas=A;
    }
    
    public void setNodos (HashMap<Integer,nodo> a){
        this.Nodos=a;
    }
    public void setAristas (HashMap<Integer,Arista> a){
        this.Aristas=a;
    }
    public HashMap<Integer,Arista> getAristas (){
        return this.Aristas;
    }
    public HashMap<Integer,nodo> getNodos (){
        return this.Nodos;
    }
    
    
    public static Grafo Erdos (int NumNodos,int NumAristas,int f2){
        HashMap<Integer,nodo> NodoS = new HashMap();
        HashMap<Integer,Arista> AristaS = new HashMap();     
        
        int AristasHechas;
        
        for(int i=0; i< NumNodos; i++){//Genera nodos
            NodoS.put(i, new nodo(i));
        }
        
        int a1=(int)(Math.random()*NumNodos), a2=(int)(Math.random()*NumNodos);
        AristaS.put(0, new Arista(NodoS.get(a1).get(), NodoS.get(a2).get()));
        
        while(a1==a2 && f2==0){
            a1=(int)(Math.random()*NumNodos);
            a2=(int)(Math.random()*NumNodos);
            AristaS.put(0, new Arista(NodoS.get(a1).get(), NodoS.get(a2).get()));
        }
        
        NodoS.get(a1).conectar();
        NodoS.get(a2).conectar();
        if(a1!=a2){
            NodoS.get(a1).IncGrado(1);
        }
        NodoS.get(a2).IncGrado(1);
        
        AristasHechas = 1;
        while(AristasHechas<NumAristas){
            a1 = (int)(Math.random()*NumNodos); 
            a2 = (int)(Math.random()*NumNodos);
            
            if(a1!=a2 || f2==1){
                int f1 = 1,cont=0;   
                while(f1==1 && cont<AristasHechas){
                    int a=AristaS.get(cont).getidN1(), b=AristaS.get(cont).getidN2();
                    if((a1==a && a2==b)||(a1==b && a2==a)){  f1 = 0;}
                    cont++;
                }                
                if(f1==1){
                    AristaS.put(AristasHechas, new Arista(NodoS.get(a1).get(), NodoS.get(a2).get()));
                    NodoS.get(a1).conectar();
                    NodoS.get(a2).conectar();
                    if(a1!=a2){   NodoS.get(a1).IncGrado(1);    }
                    NodoS.get(a2).IncGrado(1);
                    AristasHechas++;
                }               
            }
        }
        
        for(int i=0;i<NumAristas;i++){
            System.out.println(AristaS.get(i).getidN1()+"--"+AristaS.get(i).getidN2());
        }
        System.out.println("=======");
        
        Grafo G = new Grafo(NodoS,AristaS);
        return G;
        
    }    
    
    public static Grafo Gilbert (int NumNodos,double prob,int f2){
        HashMap<Integer,nodo> NodoS = new HashMap();
        HashMap<Integer,Arista> AristaS = new HashMap();
        
        int NumAristas=0;
        
        for(int i=0; i< NumNodos; i++){//Genera nodos
            NodoS.put(i, new nodo(i));
        }    
        
        for(int i=0;i<NumNodos;i++){
            for(int j=i;j<NumNodos;j++){
                if(j!=i || f2==1){
                    if (Math.random()<=prob){
                       AristaS.put(NumAristas, new Arista(NodoS.get(i).get(), NodoS.get(j).get())); 
                       NodoS.get(i).conectar();
                       NodoS.get(j).conectar();
                       if(i!=j){   NodoS.get(i).IncGrado(1);    }
                       NodoS.get(j).IncGrado(1);
                       NumAristas++;
                    }                    
                }
            }            
        }
        Grafo G = new Grafo(NodoS,AristaS);
        return G;
    }
    
    public static Grafo Geografico (int NumNodos, double distancia, int f2){
        HashMap<Integer,nodo> NodoS = new HashMap();
        HashMap<Integer,Arista> AristaS = new HashMap();
        int NumAristas=0;
        
        for(int i=0; i< NumNodos; i++){//Genera nodos
            NodoS.put(i, new nodo(i,Math.random(),Math.random()));
        }
        
        for(int i=0;i<NumNodos;i++){
            for(int j=i;j<NumNodos;j++){
                if(j!=i || f2==1){
                    double dis = Math.sqrt(Math.pow(NodoS.get(j).getX()-NodoS.get(i).getX(), 2)+
                                           Math.pow(NodoS.get(j).getY()-NodoS.get(i).getY(), 2));
                    if (dis<=distancia){
                       AristaS.put(NumAristas, new Arista(NodoS.get(i).get(), NodoS.get(j).get()));
                       NodoS.get(i).IncGrado(1);
                       NodoS.get(i).conectar();
                       if(j!=i){
                           NodoS.get(j).IncGrado(1);
                           NodoS.get(j).conectar();
                       }
                       NumAristas++;
                    }                   
                }
            }            
        }
        Grafo G = new Grafo(NodoS,AristaS);
        return G;
    }
    
    public static Grafo Barabasi (int NumNodos, double GradoMax, int f2){
        HashMap<Integer,nodo> NodoS = new HashMap();
        HashMap<Integer,Arista> AristaS = new HashMap();
        
        int NumAristas=0;
             
        for(int i=0; i< NumNodos; i++){//Genera nodos
            NodoS.put(i, new nodo(i));
        }
                
        for(int i=0;i<NumNodos;i++){
            int j=0;
            while(j<=i && NodoS.get(i).getGrado()<=GradoMax){
                if(j!=i || f2==1){
                    if(Math.random()<=1-NodoS.get(j).getGrado()/GradoMax){
                        AristaS.put(NumAristas, new Arista(NodoS.get(i).get(), NodoS.get(j).get()));
                        NodoS.get(i).IncGrado(1);
                        NodoS.get(i).conectar();
                        if(j!=i){
                            NodoS.get(j).IncGrado(1);
                            NodoS.get(j).conectar();
                        }
                        NumAristas++;
                    }
                }
                j++;
            }
        }
        Grafo G = new Grafo(NodoS,AristaS);
        return G;
    }
    
    public static void imprimir (String nombre, Grafo g){
        FileWriter fichero = null;
        PrintWriter pw = null;
        
                    System.out.println(g.getNodos().size());

        
        try{
            fichero = new FileWriter(nombre+".gv");
            pw = new PrintWriter(fichero);
            pw.println("graph 666{");
            for(int i=0;i<g.getNodos().size();i++){
                pw.println(g.getNodos().get(i).get());
            }
            pw.println();
            for(int i=0;i<g.getAristas().size();i++){
                pw.println(g.getAristas().get(i).getidN1()+"--"+g.getAristas().get(i).getidN2());
            }
            pw.println("}");          
            
        }catch (Exception e){
            e.printStackTrace();
        }finally {
           try {
           if (null != fichero)
              fichero.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
    }
    

    public static void main(String[] args) {
        
        Grafo G1;
        
        G1=Barabasi(100, 4, 0);
        
        
        imprimir("Erdos_30_270", G1);
        
        //Erdos("Erdos_30_270",30,270,0);
        //Erdos("Erdos_100_2000",100,2000,0);
        //Erdos("Erdos_500_75000",500,7000,0);
        
        //Gilbert("Gilbert_30_.5",30,.5,0);
        //Gilbert("Gilbert_100_.25",100,.25,0);
        //Gilbert("Gilbert_500_.1",500,.1,0);
        
        //Geografico("geografico_30_.5",30,.5,0);
        //Geografico("geografico_100_.2",110,.2,0);
        //Geografico("geografico_500_.05",500,.2,0);
        
        //Barabasi("Barabasi_30_5", 30, 5, 0);
        //Barabasi("Barabasi_100_4", 100, 4, 0);
        //Barabasi("Barabasi_500_20", 500, 20, 0);
        
        
        /*for(int i=0;i<G1.getAristas().size() ;i++){
            System.out.println(G1.getAristas().get(i).getidN1()+"--"+G1.getAristas().get(i).getidN2());
        }
        System.out.println("");
        for(int i=0; i<G1.getNodos().size();i++){
            System.out.println(G1.getNodos().get(i).get()+" "+
                                G1.getNodos().get(i).getConectado()+" "+
                                G1.getNodos().get(i).getGrado()
                                +" === "+G1.getNodos().get(i).getX()+" "+G1.getNodos().get(i).getY());
        }
        System.out.println("");
        for(int i=0; i<G1.getNodos().size();i++){
            if(G1.getNodos().get(i).getConectado() == 0){
                System.out.println(G1.getNodos().get(i).get());
            }
        }*/
        
               
        
    }
    
}
