package grafo;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Stack;


public class Grafo { 
    
    private HashMap<Integer,nodo> Nodos;
    private HashMap<Integer,Arista> Aristas;
    
    public Grafo (HashMap<Integer,nodo> N, HashMap<Integer,Arista> A){
        this.Nodos=N;
        this.Aristas=A;
    }
    public Grafo (){
        this.Nodos = new HashMap<>();
        this.Aristas = new HashMap<>();
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
    public void setG (HashMap<Integer,nodo> a, HashMap<Integer,Arista> b){
        this.Nodos = (HashMap)a;
        this.Aristas = (HashMap)b;
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
        System.out.println("");
        for(int i=0; i<NumNodos;i++){
            System.out.println(NodoS.get(i).get()+" "+NodoS.get(i).getConectado()+" "+NodoS.get(i).getGrado()+" === "+NodoS.get(i).getX()+" "+NodoS.get(i).getY());
        }
        System.out.println("");
        for(int i=0; i<NumNodos;i++){
            if(NodoS.get(i).getConectado() == 0){
                System.out.println(NodoS.get(i).get());
            }
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
    
    
    public static Grafo BFS (Grafo G){
        HashMap<Integer,HashMap> Ls = new HashMap(); //  Coleccion de colecciones
        HashMap<Integer,nodo> Ln1 = new HashMap();   //
        HashMap<Integer,nodo> Ln2 = new HashMap();   //
        HashMap<Integer,nodo> V = new HashMap();     //  map Nodos
        HashMap<Integer,Arista> Edg = new HashMap(); //  map Aristas
        int numL = 0, cv=0, num =0;
        
        G.getNodos().get(0).setF(true);
        Ln1.put(0,G.getNodos().get(0));
        Ls.put(numL,(HashMap)Ln1.clone());
        V.put(cv, G.getNodos().get(0));
        
        while(Ln1.isEmpty()==false){
            Ln2.clear();
            num = 0;
            for(int i = 0;i<Ln1.size();i++){
                for(int j = 0;j<G.getAristas().size();j++){
                    if(Ln1.get(i).get()==G.getAristas().get(j).getidN1() && G.getNodos().get(G.getAristas().get(j).getidN2()).getF()==false){
                        G.getNodos().get(G.getAristas().get(j).getidN2()).setF(true);
                        Ln2.put(num, G.getNodos().get(G.getAristas().get(j).getidN2()));
                        num++;
                        Edg.put(cv, G.getAristas().get(j));
                        cv++;
                        V.put(cv, G.getNodos().get(G.getAristas().get(j).getidN2()));
                    }
                    if(Ln1.get(i).get()==G.getAristas().get(j).getidN2() && G.getNodos().get(G.getAristas().get(j).getidN1()).getF()==false){
                        G.getNodos().get(G.getAristas().get(j).getidN1()).setF(true);
                        Ln2.put(num, G.getNodos().get(G.getAristas().get(j).getidN1()));
                        num++;
                        Edg.put(cv, G.getAristas().get(j));
                        cv++;
                        V.put(cv, G.getNodos().get(G.getAristas().get(j).getidN1()));
                    }
                }
            }
            numL++;
            Ln1=(HashMap)Ln2.clone();
            Ls.put(numL,(HashMap)Ln2.clone());
        }
        Grafo A = new Grafo();
        A.setG(V, Edg);
        return A;
    }
    
    public static Grafo DFS_I (Grafo G){
        Grafo A = new Grafo();
        int z,cA=0;
        boolean fl;
        
        boolean MA[][]=new boolean[G.getNodos().size()][G.getNodos().size()];
        for (int i = 0; i < G.getAristas().size(); i++) {
            MA[G.getAristas().get(i).getidN1()][G.getAristas().get(i).getidN2()]=true;
            MA[G.getAristas().get(i).getidN2()][G.getAristas().get(i).getidN1()]=true;
        }
        
        Stack<Integer>pila = new Stack<>();
        pila.push(G.getNodos().get(0).get());
        G.getNodos().get(0).setF(true);
        A.getNodos().put(cA, new nodo (G.getNodos().get(0)));
        
        
        while(pila.isEmpty()==false){
            z=pila.peek();
            fl=false;
            for (int i = 0; i < G.getNodos().size(); i++) {
                if(MA[z][i]==true && G.getNodos().get(i).getF()==false){
                    G.getNodos().get(i).setF(true);
                    A.getAristas().put(cA, new Arista(z,i));
                    cA++;
                    A.getNodos().put(cA,new nodo(G.getNodos().get(i)));
                    pila.push(i);
                    fl=true;
                    i=G.getNodos().size();
                }
                if (i==G.getNodos().size()-1 && fl==false) {
                    pila.pop();
                }
            }
        }
        
        for(int i=0;i<A.getAristas().size() ;i++){
            System.out.println(A.getAristas().get(i).getidN1()+"--"+A.getAristas().get(i).getidN2());
        }
        System.out.println("");
        for(int i=0; i<A.getNodos().size();i++){
            System.out.println(A.getNodos().get(i).get()+" "+
                                A.getNodos().get(i).getConectado()+" "+
                                A.getNodos().get(i).getGrado()
                                +" === "+A.getNodos().get(i).getX()+" "+A.getNodos().get(i).getY());
        }
        System.out.println("");
        return A;
    }
    
    public static Grafo DFS_R (Grafo G,nodo N0){
        Grafo A = new Grafo();
        Grafo B;
        boolean MA[][]=new boolean[G.getNodos().size()][G.getNodos().size()];
        for (int i = 0; i < G.getAristas().size(); i++) {
            MA[G.getAristas().get(i).getidN1()][G.getAristas().get(i).getidN2()]=true;
            MA[G.getAristas().get(i).getidN2()][G.getAristas().get(i).getidN1()]=true;
        }
        G.getNodos().get(N0.get()).setF(true);
        A.getNodos().put(0, new nodo (G.getNodos().get(N0.get())));
        for (int i = 0; i < G.getNodos().size(); i++) {
            if(MA[N0.get()][i]==true && G.getNodos().get(i).getF()==false){
                B=DFS_R(G,G.getNodos().get(i));
                int tN = A.getNodos().size();
                for (int j = 0; j < B.getNodos().size(); j++) {
                        A.getNodos().put(tN+j, B.getNodos().get(j));
                }
                A.getAristas().put(A.getAristas().size(), new Arista(N0.get(),i));
                tN=A.getAristas().size();
                if (B.getAristas().isEmpty()!=true) {
                    for (int j = 0; j < B.getAristas().size(); j++) {
                        A.getAristas().put(tN+j, B.getAristas().get(j));
                    }
                }
            }
        }
        return A;
    }
    
    public static void main(String[] args) {
        
        //====================================DFS Iterador Funciona chingon 
        //Grafo G = new Grafo();
        //G = Erdos(500,7000,0); 
        //G = DFS_I(G);
        //imprimir("A_DFS_I 500N", G);
        //=================================================================
        
        //====================================DFS Recursivo Funciona chingon 
        //Grafo G = new Grafo();
        //G = Erdos(500,7000,0); 
        //G = DFS_R(G,G.getNodos().get(0));
        //imprimir("A_DFS_R 500N", G);
        //==================================================================
        
        //====================================DFS Recursivo Funciona chingon 
        /*Grafo G = new Grafo();
        G = Erdos(500,7000,0); 
        G = BFS(G);
        imprimir("A_BFS 500N", G);*/
        //==================================================================
        
        //G1=Erdos(10,10,0);       
        //imprimir("Erdos_10_20", G1);
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
