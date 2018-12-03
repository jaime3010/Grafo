package grafo;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Stack;

public class Grafo {

    private HashMap<Integer, nodo> Nodos;
    private HashMap<Integer, Arista> Aristas;

    public Grafo(HashMap<Integer, nodo> N, HashMap<Integer, Arista> A) {
        this.Nodos = new HashMap();
        for (int i = 0; i < N.size(); i++) {
            this.Nodos.put(i, new nodo(N.get(i)));
        }
        this.Aristas = new HashMap();
        for (int i = 0; i < A.size(); i++) {
            this.Aristas.put(i, new Arista(A.get(i)));
        }
    }

    public Grafo() {
        this.Nodos = new HashMap();
        this.Aristas = new HashMap();
    }

    public Grafo(Grafo g) {
        this.Nodos = new HashMap();
        for (int i = 0; i < g.getNodos().size(); i++) {
            this.Nodos.put(i, new nodo(g.getNodos().get(i)));
        }
        this.Aristas = new HashMap();
        for (int i = 0; i < g.getAristas().size(); i++) {
            this.Aristas.put(i, new Arista(g.getAristas().get(i)));
        }
    }

    public void setNodos(HashMap<Integer, nodo> a) {
        this.Nodos = a;
    }

    public void setAristas(HashMap<Integer, Arista> a) {
        this.Aristas = a;
    }

    public HashMap<Integer, Arista> getAristas() {
        return this.Aristas;
    }

    public HashMap<Integer, nodo> getNodos() {
        return this.Nodos;
    }

    public void setG(HashMap<Integer, nodo> a, HashMap<Integer, Arista> b) {
        this.Nodos = (HashMap) a;
        this.Aristas = (HashMap) b;
    }

    /**
     * El metodo asigna valores random dentro de un rango asignado por los
     * argumentos de entrada al metodo, estos argumentos son el maximo y minimo
     * del rango
     *
     * ljn,mnk
     *
     * @param min Asigna el limite minimo de los pesos aleatorios
     * @param max Asigna el limite maximo de los pesos aleatorios
     * @return Regresa un objeto tipo Grafo que es la copia del Grafo que lo
     * invoco pero con pesos nuevos.
     */
    public Grafo EdgeValues(double min, double max) {
        int NumAristas = this.Aristas.size();
        for (int i = 0; i < NumAristas; i++) {
            this.Aristas.get(i).setP(Math.random() * (max - min) + min);
        }
        Grafo G = new Grafo(this.Nodos, this.Aristas);
        return G;
    }

    public static void modificador(Grafo G) {
        for (int i = 0; i < G.getNodos().size(); i++) {
            G.getNodos().get(i).setF(true);
        }
    }

    public static Grafo Erdos(int NumNodos, int NumAristas, int f2) {
        HashMap<Integer, nodo> NodoS = new HashMap();
        HashMap<Integer, Arista> AristaS = new HashMap();

        int AristasHechas;

        for (int i = 0; i < NumNodos; i++) {//Genera nodos
            NodoS.put(i, new nodo(i));
        }

        int a1 = (int) (Math.random() * NumNodos), a2 = (int) (Math.random() * NumNodos);
        AristaS.put(0, new Arista(NodoS.get(a1).get(), NodoS.get(a2).get(), Math.random()));

        while (a1 == a2 && f2 == 0) {
            a1 = (int) (Math.random() * NumNodos);
            a2 = (int) (Math.random() * NumNodos);
            AristaS.put(0, new Arista(NodoS.get(a1).get(), NodoS.get(a2).get(), Math.random()));
        }

        NodoS.get(a1).conectar();
        NodoS.get(a2).conectar();
        if (a1 != a2) {
            NodoS.get(a1).IncGrado(1);
        }
        NodoS.get(a2).IncGrado(1);

        AristasHechas = 1;
        while (AristasHechas < NumAristas) {
            a1 = (int) (Math.random() * NumNodos);
            a2 = (int) (Math.random() * NumNodos);

            if (a1 != a2 || f2 == 1) {
                int f1 = 1, cont = 0;
                while (f1 == 1 && cont < AristasHechas) {
                    int a = AristaS.get(cont).getidN1(), b = AristaS.get(cont).getidN2();
                    if ((a1 == a && a2 == b) || (a1 == b && a2 == a)) {
                        f1 = 0;
                    }
                    cont++;
                }
                if (f1 == 1) {
                    AristaS.put(AristasHechas, new Arista(NodoS.get(a1).get(), NodoS.get(a2).get(), Math.random()));
                    NodoS.get(a1).conectar();
                    NodoS.get(a2).conectar();
                    if (a1 != a2) {
                        NodoS.get(a1).IncGrado(1);
                    }
                    NodoS.get(a2).IncGrado(1);
                    AristasHechas++;
                }
            }
        }

        /*for(int i=0;i<NumAristas;i++){
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
        System.out.println("=======");*/
        Grafo G = new Grafo(NodoS, AristaS);
        return G;

    }

    public static Grafo Gilbert(int NumNodos, double prob, int f2) {
        HashMap<Integer, nodo> NodoS = new HashMap();
        HashMap<Integer, Arista> AristaS = new HashMap();

        int NumAristas = 0;

        for (int i = 0; i < NumNodos; i++) {//Genera nodos
            NodoS.put(i, new nodo(i));
        }

        for (int i = 0; i < NumNodos; i++) {
            for (int j = i; j < NumNodos; j++) {
                if (j != i || f2 == 1) {
                    if (Math.random() <= prob) {
                        AristaS.put(NumAristas, new Arista(NodoS.get(i).get(), NodoS.get(j).get()));
                        NodoS.get(i).conectar();
                        NodoS.get(j).conectar();
                        if (i != j) {
                            NodoS.get(i).IncGrado(1);
                        }
                        NodoS.get(j).IncGrado(1);
                        NumAristas++;
                    }
                }
            }
        }
        Grafo G = new Grafo(NodoS, AristaS);
        return G;
    }

    public static Grafo Geografico(int NumNodos, double distancia, int f2) {
        HashMap<Integer, nodo> NodoS = new HashMap();
        HashMap<Integer, Arista> AristaS = new HashMap();
        int NumAristas = 0;

        for (int i = 0; i < NumNodos; i++) {//Genera nodos
            NodoS.put(i, new nodo(i, Math.random(), Math.random()));
        }

        for (int i = 0; i < NumNodos; i++) {
            for (int j = i; j < NumNodos; j++) {
                if (j != i || f2 == 1) {
                    double dis = Math.sqrt(Math.pow(NodoS.get(j).getX() - NodoS.get(i).getX(), 2)
                            + Math.pow(NodoS.get(j).getY() - NodoS.get(i).getY(), 2));
                    if (dis <= distancia) {
                        AristaS.put(NumAristas, new Arista(NodoS.get(i).get(), NodoS.get(j).get()));
                        NodoS.get(i).IncGrado(1);
                        NodoS.get(i).conectar();
                        if (j != i) {
                            NodoS.get(j).IncGrado(1);
                            NodoS.get(j).conectar();
                        }
                        NumAristas++;
                    }
                }
            }
        }
        Grafo G = new Grafo(NodoS, AristaS);
        return G;
    }

    public static Grafo Barabasi(int NumNodos, double GradoMax, int f2) {
        HashMap<Integer, nodo> NodoS = new HashMap();
        HashMap<Integer, Arista> AristaS = new HashMap();

        int NumAristas = 0;

        for (int i = 0; i < NumNodos; i++) {//Genera nodos
            NodoS.put(i, new nodo(i));
        }

        for (int i = 0; i < NumNodos; i++) {
            int j = 0;
            while (j <= i && NodoS.get(i).getGrado() <= GradoMax) {
                if (j != i || f2 == 1) {
                    if (Math.random() <= 1 - NodoS.get(j).getGrado() / GradoMax) {
                        AristaS.put(NumAristas, new Arista(NodoS.get(i).get(), NodoS.get(j).get()));
                        NodoS.get(i).IncGrado(1);
                        NodoS.get(i).conectar();
                        if (j != i) {
                            NodoS.get(j).IncGrado(1);
                            NodoS.get(j).conectar();
                        }
                        NumAristas++;
                    }
                }
                j++;
            }
        }
        Grafo G = new Grafo(NodoS, AristaS);
        return G;
    }

    public static void imprimir(String nombre, Grafo g) {
        FileWriter fichero = null;
        PrintWriter pw = null;

        System.out.println(g.getNodos().size());

        try {
            fichero = new FileWriter(nombre + ".gv");
            pw = new PrintWriter(fichero);
            pw.println("graph 666{");
            for (int i = 0; i < g.getNodos().size(); i++) {
                pw.println(g.getNodos().get(i).get() + "  " + "[Label = \"" + g.getNodos().get(i).get() + " (" + String.format("%.2f", g.getNodos().get(i).getW()) + ")\"]");
            }
            pw.println();
            for (int i = 0; i < g.getAristas().size(); i++) {
                pw.println(g.getAristas().get(i).getidN1() + "--" + g.getAristas().get(i).getidN2() + "  " + "[Label = \"" + String.format("%.2f", g.getAristas().get(i).getP()) + "\"]");
            }
            pw.println("}");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != fichero) {
                    fichero.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /**
     * Dijkstra
     *
     * @param raiz nodo raiz
     * @return Regresa un Objeto Grafo que contiene el arbol generado.
     */
    public Grafo Dijkstra(nodo raiz) {
        for (int i = 0; i < this.Nodos.size(); i++) {
            this.Nodos.get(i).setW(Double.POSITIVE_INFINITY);
        }
        double MA[][] = new double[this.Nodos.size()][this.Nodos.size()];//Matriz de adyacencia con pesos de arita

        //llena la matriz de adyacencia con infinitos
        for (int i = 0; i < this.Nodos.size(); i++) {
            for (int j = 0; j < this.Nodos.size(); j++) {
                MA[i][j] = Double.POSITIVE_INFINITY;
            }
        }
        //====System.out.println("");
        //Coloca el peso de cada arista dentro de la entrada que le corresponde en la matriz de forma 
        //simetrica con respecto a la diagonal para grafos no dirigidos.
        for (int i = 0; i < this.Aristas.size(); i++) {
            MA[this.Aristas.get(i).getidN1()][this.Aristas.get(i).getidN2()] = this.Aristas.get(i).getP();
            MA[this.Aristas.get(i).getidN2()][this.Aristas.get(i).getidN1()] = this.Aristas.get(i).getP();
        }
        //====System.out.println("grafo.Grafo.Dijkstra()");
        ArrayList<Integer> NodosEncontrados = new ArrayList<Integer>();//declaracion del conjunto de nodos encontrados
        //declaracio del vector que almacena las aristas para el arbol resultante 
        HashMap<Integer, Arista> AZ = new HashMap();
        int numA = 0;
        //Es colocado como primer elemento en el conjunto de nodos encontrados el nodo
        //que entro como parametro al metodo para ser la raiz del arbol
        NodosEncontrados.add(this.Nodos.get(raiz.get()).get());
        this.Nodos.get(raiz.get()).setF(true);
        this.Nodos.get(raiz.get()).setW(0);
        //====System.out.println("grafo.Grafo.Dijkstra()");
        //la variable aux guarda el valor del peso minio dentro de los nodos qe son encontrados
        //desde los nods que s encuentran en el conjunto de nodos encontrados,
        // las variables a y b guardan el nodo que econtro al nuevo nodo a agragar a la lista de 
        //nodos encontrados 
        double aux = 0;
        int a = 0, b = 0;
        boolean parar = true;
        //====System.out.println("grafo.Grafo.Dijkstra()");

        while (aux != Double.POSITIVE_INFINITY) {
            aux = Double.POSITIVE_INFINITY;
            //====System.out.println("================" + aux);
            for (int i = 0; i < NodosEncontrados.size(); i++) {
                //====System.out.println(NodosEncontrados.get(i));
                for (int j = 0; j < this.Nodos.size(); j++) {
                    //====System.out.print("");
                    if (this.Nodos.get(j).getF() != true && MA[NodosEncontrados.get(i)][j] != Double.POSITIVE_INFINITY) {
                        //====System.out.println("    "+j+" -> "+(this.Nodos.get(NodosEncontrados.get(i)).getW()+MA[NodosEncontrados.get(i)][j]));
                        if ((this.Nodos.get(NodosEncontrados.get(i)).getW() + MA[NodosEncontrados.get(i)][j]) < aux) {
                            this.Nodos.get(j).setW(this.Nodos.get(NodosEncontrados.get(i)).getW() + MA[NodosEncontrados.get(i)][j]);
                            aux = this.Nodos.get(j).getW();
                            a = NodosEncontrados.get(i);
                            b = j;
                            //====System.out.println("        " + aux + "   " + a+"-->"+b);
                        }
                    }
                }
            }
            if (aux != Double.POSITIVE_INFINITY) {
                this.Nodos.get(b).setF(true);
                NodosEncontrados.add(b);
                //====System.out.println("          " + aux + "   " + a +"-->"+ b);
                AZ.put(numA, new Arista(a, b, MA[a][b]));
                numA++;
            }

        }

        HashMap<Integer, nodo> NZ = new HashMap();
        for (int i = 0; i < NodosEncontrados.size(); i++) {
            NZ.put(i, new nodo(this.Nodos.get(NodosEncontrados.get(i))));
        }
        Grafo G1 = new Grafo(NZ, AZ);
        return G1;

    }

    public static Grafo BFS(Grafo G1, nodo nod) {
        Grafo G = new Grafo(G1.getNodos(), G1.getAristas());
        HashMap<Integer, HashMap> Ls = new HashMap(); //  Coleccion de colecciones
        HashMap<Integer, nodo> Ln1 = new HashMap();   //
        HashMap<Integer, nodo> Ln2 = new HashMap();   //
        HashMap<Integer, nodo> V = new HashMap();     //  map Nodos
        HashMap<Integer, Arista> Edg = new HashMap(); //  map Aristas
        int numL = 0, cv = 0, num = 0;

        G.getNodos().get(nod.get()).setF(true);
        Ln1.put(0, G.getNodos().get(nod.get()));
        Ls.put(numL, (HashMap) Ln1.clone());
        V.put(cv, G.getNodos().get(nod.get()));

        while (Ln1.isEmpty() == false) {
            Ln2.clear();
            num = 0;
            for (int i = 0; i < Ln1.size(); i++) {
                for (int j = 0; j < G.getAristas().size(); j++) {
                    if (Ln1.get(i).get() == G.getAristas().get(j).getidN1() && G.getNodos().get(G.getAristas().get(j).getidN2()).getF() == false) {
                        G.getNodos().get(G.getAristas().get(j).getidN2()).setF(true);
                        Ln2.put(num, G.getNodos().get(G.getAristas().get(j).getidN2()));
                        num++;
                        Edg.put(cv, G.getAristas().get(j));
                        cv++;
                        V.put(cv, G.getNodos().get(G.getAristas().get(j).getidN2()));
                    }
                    if (Ln1.get(i).get() == G.getAristas().get(j).getidN2() && G.getNodos().get(G.getAristas().get(j).getidN1()).getF() == false) {
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
            Ln1 = (HashMap) Ln2.clone();
            Ls.put(numL, (HashMap) Ln2.clone());
        }
        Grafo A = new Grafo();
        A.setG(V, Edg);
        return A;
    }

    public static Grafo DFS_I(Grafo G1, nodo nod) {
        Grafo G = new Grafo(G1.getNodos(), G1.getAristas());
        Grafo A = new Grafo();
        int z, cA = 0;
        boolean fl;

        boolean MA[][] = new boolean[G.getNodos().size()][G.getNodos().size()];
        /*Iterator<Map.Entry<Integer, Arista>> it = G.getAristas().entrySet().iterator();
        while (it.hasNext()) {
            Arista x = it.next().getValue();
            MA[x.getidN1()][x.getidN2()] = true;
            MA[x.getidN2()][x.getidN1()] = true;
        }*/
        for (int i = 0; i < G.getAristas().size(); i++) {
            if (G.getAristas().get(i).getF() == false) {
                MA[G.getAristas().get(i).getidN1()][G.getAristas().get(i).getidN2()]=true;
                MA[G.getAristas().get(i).getidN2()][G.getAristas().get(i).getidN1()]=true;
            }
            //MA[G.getAristas().get(i).getidN1()][G.getAristas().get(i).getidN2()]=true;
            //MA[G.getAristas().get(i).getidN2()][G.getAristas().get(i).getidN1()]=true;
        }

        Stack<Integer> pila = new Stack<>();
        pila.push(G.getNodos().get(nod.get()).get());
        G.getNodos().get(nod.get()).setF(true);
        A.getNodos().put(cA, new nodo(G.getNodos().get(nod.get())));

        while (pila.isEmpty() == false) {
            z = pila.peek();
            fl = false;
            for (int i = 0; i < G.getNodos().size(); i++) {
                if (MA[z][i] == true && G.getNodos().get(i).getF() == false) {
                    G.getNodos().get(i).setF(true);
                    A.getAristas().put(cA, new Arista(z, i));
                    cA++;
                    A.getNodos().put(cA, new nodo(G.getNodos().get(i)));
                    pila.push(i);
                    fl = true;
                    i = G.getNodos().size();
                }
                if (i == G.getNodos().size() - 1 && fl == false) {
                    pila.pop();
                }
            }
        }

        /*for(int i=0;i<A.getAristas().size() ;i++){
            System.out.println(A.getAristas().get(i).getidN1()+"--"+A.getAristas().get(i).getidN2());
        }
        System.out.println("");
        for(int i=0; i<A.getNodos().size();i++){
            System.out.println(A.getNodos().get(i).get()+" "+
                                A.getNodos().get(i).getConectado()+" "+
                                A.getNodos().get(i).getGrado()
                                +" === "+A.getNodos().get(i).getX()+" "+A.getNodos().get(i).getY());
        }
        System.out.println("");*/
        return A;
    }

    public static Grafo DFS_R(Grafo G, nodo N0) {
        Grafo A = new Grafo();
        Grafo B;
        boolean MA[][] = new boolean[G.getNodos().size()][G.getNodos().size()];
        for (int i = 0; i < G.getAristas().size(); i++) {
            MA[G.getAristas().get(i).getidN1()][G.getAristas().get(i).getidN2()] = true;
            MA[G.getAristas().get(i).getidN2()][G.getAristas().get(i).getidN1()] = true;
        }
        G.getNodos().get(N0.get()).setF(true);
        A.getNodos().put(0, new nodo(G.getNodos().get(N0.get())));
        for (int i = 0; i < G.getNodos().size(); i++) {
            if (MA[N0.get()][i] == true && G.getNodos().get(i).getF() == false) {
                B = DFS_R(G, G.getNodos().get(i));
                int tN = A.getNodos().size();
                for (int j = 0; j < B.getNodos().size(); j++) {
                    A.getNodos().put(tN + j, B.getNodos().get(j));
                }
                A.getAristas().put(A.getAristas().size(), new Arista(N0.get(), i));
                tN = A.getAristas().size();
                if (B.getAristas().isEmpty() != true) {
                    for (int j = 0; j < B.getAristas().size(); j++) {
                        A.getAristas().put(tN + j, B.getAristas().get(j));
                    }
                }
            }
        }
        return A;
    }

    public Grafo Prim() {
        //El grafo G es una compia del grafo que invoca al metodo, esto es para 
        //          no modificar los valores de los atributos del grafo original.
        //MA[][]: Matriz de adyacencia.
        Grafo G = new Grafo(this.Nodos, this.Aristas);
        Arista MA[][] = new Arista[G.getNodos().size()][G.getNodos().size()];//Matriz de adyacencia con pesos de arita

        //Construccion de la matriz de MA 
        for (int i = 0; i < G.getAristas().size(); i++) {
            MA[G.getAristas().get(i).getidN1()][G.getAristas().get(i).getidN2()] = G.getAristas().get(i);
            MA[G.getAristas().get(i).getidN2()][G.getAristas().get(i).getidN1()] = G.getAristas().get(i);
        }
        //====System.out.println("grafo.Grafo.Dijkstra()");
        //NodosEncontrados: es el cojunto de los nodos encontrados, cada que un nodo
        //                  es encontrado su valor ID es almacenado en este conjunto 
        ArrayList<Integer> NodosEncontrados = new ArrayList<Integer>();//declaracion del conjunto de nodos encontrados
        //AZ: vector que almacena las aristas para el arbol resultante 
        HashMap<Integer, Arista> AZ = new HashMap();
        int numA = 0;
        //Es colocado como primer elemento en el conjunto de nodos encontrados el nodo 0
        NodosEncontrados.add(G.getNodos().get(0).get());
        G.getNodos().get(0).setF(true);
        //====System.out.println("grafo.Grafo.Dijkstra()");
        PriorityQueue<Arista> pqAristas = new PriorityQueue<>();
        double em = 0;

        for (int z = 0; z < G.getAristas().size(); z++) {
            for (int i = 0; i < NodosEncontrados.size(); i++) {
                for (int j = 0; j < G.getNodos().size(); j++) {
                    if (MA[NodosEncontrados.get(i)][j] != null && G.getNodos().get(j).getF() == false) {
                        pqAristas.add(MA[NodosEncontrados.get(i)][j]);
                    }
                }
            }
            if (pqAristas.isEmpty() == false) {
                if (NodosEncontrados.contains(pqAristas.peek().getidN1())) {
                    G.getNodos().get(pqAristas.peek().getidN2()).setF(true);
                    NodosEncontrados.add(pqAristas.peek().getidN2());
                    AZ.put(numA, MA[pqAristas.peek().getidN1()][pqAristas.peek().getidN2()]);
                    numA++;
                    em = em + pqAristas.peek().getP();
                    pqAristas.clear();
                } else {
                    G.getNodos().get(pqAristas.peek().getidN1()).setF(true);
                    NodosEncontrados.add(pqAristas.peek().getidN1());
                    AZ.put(numA, MA[pqAristas.peek().getidN1()][pqAristas.peek().getidN2()]);
                    numA++;
                    em = em + pqAristas.peek().getP();
                    pqAristas.clear();
                }
            }
            if (AZ.size() == G.getNodos().size() - 1) {
                break;
            }
        }
        System.out.println("Peso total del arbol de expancion minima Prim = ."+em);
        Grafo A = new Grafo(G.getNodos(), AZ);
        return A;
    }

    public Grafo Kruskal_D() {
        Grafo G = new Grafo(this.Nodos, this.Aristas);
        
        //Almacena las aristas del arbol original  las acomodo colocando al tope
        //la arista con el peso minimo en el objeto pqAristas.
        //lleno la cola de prioridad pqAristas con cada arista del grafo prar 
        //que las comode.
        PriorityQueue<Arista> pqAristas = new PriorityQueue<>();
        for (int i = 0; i < G.getAristas().size(); i++) {
            pqAristas.add(G.getAristas().get(i));
        }

        //AristaMinExp: es un hashMap donde son guardadas las aristas que conforman 
        //              al arbol de expancion minima.
        //NodosEncontrados: es una coleccion que almacena el ID de cada nodo qe es
        //                  encontrado para conformar el arbol, a esta lista solo 
        //                  son agregados los nodos que son descubiertos pero que no 
        //                  hacen un cico
        HashMap<Integer, Arista> AristaMinExp = new HashMap();
        HashMap<Integer, nodo> NS = new HashMap();
        ArrayList<Integer> NodosEncontrados = new ArrayList<Integer>();
        int numAri = 0;
        int numNod = 0;
        double me = 0;
        // Gaux: guarda un arbol para verificar si la arista candidata a ser agregda 
        //      hace o no un ciclo.

        for (int i = 0; i < G.getAristas().size(); i++) {//El for corre sobre toda la coleccion de aristas
            if (NodosEncontrados.contains(pqAristas.peek().getidN1()) && NodosEncontrados.contains(pqAristas.peek().getidN2())) {
                Grafo Gaux1 = new Grafo(G.getNodos(), AristaMinExp);
                Grafo Gaux2 = new Grafo();
                Gaux2 = DFS_I(Gaux1, Gaux1.getNodos().get(pqAristas.peek().getidN1()));
                int flag = 0;
                for (int j = 0; j < Gaux2.getNodos().size(); j++) {
                    if (Gaux2.getNodos().get(j).get() == pqAristas.peek().getidN2()) {
                        flag = 1;
                    }
                }
                if (flag == 0) {
                    me = me + pqAristas.peek().getP();
                    AristaMinExp.put(numAri, pqAristas.poll());
                    numAri++;
                } else {
                    pqAristas.poll();
                }
            } else {
                if (NodosEncontrados.contains(pqAristas.peek().getidN1()) == false) {
                    NodosEncontrados.add(pqAristas.peek().getidN1());
                }
                if (NodosEncontrados.contains(pqAristas.peek().getidN2()) == false) {
                    NodosEncontrados.add(pqAristas.peek().getidN2());
                }
                me = me + pqAristas.peek().getP();
                AristaMinExp.put(numAri, pqAristas.poll());
                numAri++;
            }
            if (AristaMinExp.size() == G.getNodos().size() - 1) {
                i = G.getAristas().size();
            }
        }
        for (int i = 0; i < NodosEncontrados.size(); i++) {
            NS.put(i, G.getNodos().get(NodosEncontrados.get(i)));
        }
        System.out.println("Peso total del arbol de expancion minima Kruskal Dir = ."+me);
        Grafo A = new Grafo(NS, AristaMinExp);
        return A;
    }

    public Grafo Kruskal_I() {
        //G: Es una copia del grafo que invoca al metodo para que 
        Grafo G = new Grafo(this.Nodos, this.Aristas);
        for (int i = 0; i < G.getAristas().size(); i++) //Asigna ID a cada arista
            G.getAristas().get(i).setID(i);
        
        PriorityQueue<Arista> pqAristas = new PriorityQueue<>(Collections.reverseOrder());

        for (int i = 0; i < G.getAristas().size(); i++) //Mete aristas a la cola de prioridad
            pqAristas.add(G.getAristas().get(i));
        
        int numA = 0;
        HashMap<Integer, Arista> AristaMinExp = new HashMap();
        Grafo GAux = new Grafo();
        Arista quitar = new Arista();
        double ExpMin = 0;
        
        for (int i = 0; i < G.getAristas().size(); i++) {
            quitar = pqAristas.poll();
            G.getAristas().get(quitar.getID()).setF(true);
            GAux = DFS_I(G, G.getNodos().get(quitar.getidN1()));
            if (GAux.getNodos().size()<G.getNodos().size()){
                AristaMinExp.put(numA, new Arista(quitar.getidN1(), quitar.getidN2(), quitar.getP()));
                numA++;
                G.getAristas().get(quitar.getID()).setF(false);
                ExpMin = ExpMin + quitar.getP();
            } 
        }
        System.out.println("Peso total del arbol de expancion minima Kruskal Inv = ."+ExpMin);
        
        Grafo A = new Grafo(G.getNodos(), AristaMinExp);
        return A;
    }

    public static void main(String[] args) {

        HashMap<Integer, nodo> NodoS = new HashMap();
        NodoS.put(0, new nodo(0, 1, 3, 0.0, 0.0, false, Double.POSITIVE_INFINITY));
        NodoS.put(1, new nodo(1, 1, 2, 0.0, 0.0, false, Double.POSITIVE_INFINITY));
        NodoS.put(2, new nodo(2, 1, 5, 0.0, 0.0, false, Double.POSITIVE_INFINITY));
        NodoS.put(3, new nodo(3, 1, 3, 0.0, 0.0, false, Double.POSITIVE_INFINITY));
        NodoS.put(4, new nodo(4, 1, 5, 0.0, 0.0, false, Double.POSITIVE_INFINITY));
        NodoS.put(5, new nodo(5, 1, 4, 0.0, 0.0, false, Double.POSITIVE_INFINITY));
        NodoS.put(6, new nodo(6, 1, 4, 0.0, 0.0, false, Double.POSITIVE_INFINITY));
        NodoS.put(7, new nodo(7, 1, 4, 0.0, 0.0, false, Double.POSITIVE_INFINITY));
        HashMap<Integer, Arista> AristaS = new HashMap();
        AristaS.put(0, new Arista(0, 1, 9));
        AristaS.put(1, new Arista(0, 5, 14));
        AristaS.put(2, new Arista(0, 6, 15));
        AristaS.put(3, new Arista(1, 2, 24));
        AristaS.put(4, new Arista(2, 5, 18));
        AristaS.put(5, new Arista(2, 4, 2));
        AristaS.put(6, new Arista(2, 3, 6));
        AristaS.put(7, new Arista(2, 7, 19));
        AristaS.put(8, new Arista(3, 4, 11));
        AristaS.put(9, new Arista(3, 7, 6));
        AristaS.put(10, new Arista(4, 5, 30));
        AristaS.put(11, new Arista(4, 6, 20));
        AristaS.put(12, new Arista(4, 7, 16));
        AristaS.put(13, new Arista(5, 6, 5));
        AristaS.put(14, new Arista(6, 7, 44));
        Grafo G = new Grafo(NodoS, AristaS);
        
        Grafo G1 = new Grafo();
        //G1=Geografico(10,.7,0);
        //G1 = Erdos(300,1000,0);
        //G1 = Gilbert(500,.025,0);
        G1 = Barabasi(500, 10, 0);
        G1.EdgeValues(0, 10);
        String nombre = "Barabasi 500N";
        imprimir( nombre, G1);
        
        Grafo G2 = new Grafo();
        G2 = G1.Kruskal_D();
        imprimir(nombre+"  Kruskal_Dir", G2);
        Grafo G3 = new Grafo();
        G3 = G1.Kruskal_I();
        imprimir(nombre+"  Kruskal_Inv", G3);
        Grafo G4 = new Grafo();
        G4 = G1.Prim();
        imprimir(nombre+"  Prim", G4);//*/
        

        //Grafo G = new Grafo();
        //G=Erdos(30,270,0);
        //G=Erdos(100,2000,0);
        //G=Erdos(500,7000,0);
        //G=Gilbert(30,.5,0);
        //G=Gilbert(100,.25,0);
        //G=Gilbert(500,.1,0);
        //G=Geografico(30,.5,0);
        //G=Geografico(110,.2,0);
        //G=Geografico(10000,.3,0);
        //G=Barabasi(30, 5, 0);
        //G=Barabasi(100, 4, 0);
        //G=Barabasi(500, 20, 0);
        //imprimir("prueba", G);
        //Grafo G1 = new Grafo();
        //Grafo G2 = new Grafo();
        //====================================BFS  
        //Grafo G = new Grafo();
        //G = Geografico(500,.5,0);
        //imprimir("Geografico "+G.getNodos().size()+"N "+G.getAristas().size()+"A", G);
        //G1 = BFS(G,G.getNodos().get(0));
        //imprimir(G1.getNodos().size()+"N "+G1.getAristas().size()+"A BSF", G1);
        //==================================================================
        //====================================DFS_I
        //Grafo G = new Grafo();
        //G = Geografico(30,.5,0);
        //imprimir("Geografico "+G.getNodos().size()+"N "+G.getAristas().size()+"A", G);
        //G2 = DFS_I(G,G.getNodos().get(0));
        //imprimir("Geografico "+G2.getNodos().size()+"N "+G2.getAristas().size()+"A DFS_I", G2);
        //=================================================================
        //====================================DFS_R
        //Grafo G = new Grafo();
        //G = Geografico(30,.5,0);
        //imprimir("Geografico "+G.getNodos().size()+"N "+G.getAristas().size()+"A", G);
        //G = DFS_R(G,G.getNodos().get(0));
        //imprimir("Geografico "+G.getNodos().size()+"N "+G.getAristas().size()+"A DFS_R", G);
        //==================================================================
        /*//==============================            Funcionamiento de Dijkstra
        Grafo G3 = new Grafo();
        //G3 = Geografico(500,.5,0);
        //G3 = Erdos(400,1250,0);
        //G3 = Gilbert(500,.1,0);
        G3=Barabasi(500, 20, 0);
        G3.EdgeValues(0, 10);
        imprimir("Barabasi 30", G3);
        Grafo G2 = new Grafo(G3.Dijkstra(G3.getNodos().get(0)));
        imprimir("Barabasi 30 Dijkstra", G2);
        //====================================================================*/
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
