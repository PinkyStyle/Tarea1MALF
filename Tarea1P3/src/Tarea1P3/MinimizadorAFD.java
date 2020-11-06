package Tarea1P3;

import java.util.ArrayList;
import java.util.Collections;

public class MinimizadorAFD {

    //Variables Iniciales (Las lineas tomadas de la entrada estandar)
    private String est;
    private String sig;
    private ArrayList<String> trans;
    private String estInicial;
    private String estFinales;

    //Variables a Utilizar
    private String nombreAutomata;
    private ArrayList<String> estados;
    private ArrayList<String> estadosMinimizados;
    private ArrayList<String> sigma;
    private ArrayList<Transicion> transiciones;
    private String estadoInicial;
    private ArrayList<String> estadosFinales;
    private int tabla[][];

    public MinimizadorAFD(String nombre, String estados, String sigma, ArrayList<String> transiciones, String estadoInicial, String estadosFinales) {     
        this.nombreAutomata = nombre;
        this.est = estados;
        this.sig = sigma;
        this.trans = transiciones;
        this.estInicial = estadoInicial;
        this.estFinales = estadosFinales;

        this.estados = new ArrayList<String>();
        this.estadosMinimizados = new ArrayList<String>();
        this.sigma = new ArrayList<String>();
        this.transiciones = new ArrayList<Transicion>();
        this.estadosFinales = new ArrayList<String>();        
    }

    /**
     * Metodo que procesa los datos obtenidos de la entrada estandar.
     */
    public void procesarDatos(){

        //separamos la linea de los estados y los almacenamos individualmente
        this.est = this.est.split("=")[1];
        // this.est.replace("\\{", "");
        // this.est.replace("\\}", "");
        this.est = this.est.replaceAll("[\\p{Ps}\\p{Pe}]", "");
        String[] aux = this.est.split(",");
        for(int i = 0; i < aux.length; i++){
            this.estados.add(aux[i]);
        }

        this.tabla = new int[this.estados.size()][this.estados.size()];

        for(int i = 0; i < this.tabla.length; i++){
            for(int j = i; j <this.tabla.length ; j++){
                this.tabla[i][j] = -1;
            }
        }

        //separamos la linea de Sigma para obtener los datos
        this.sig = this.sig.split("=")[1];
        // this.sig.replace("\\{", "");
        // this.sig.replace("\\}", "");
        this.sig = this.sig.replaceAll("[\\p{Ps}\\p{Pe}]", "");
        aux = this.sig.split(",");
        for(int i = 0; i < aux.length; i++){
            this.sigma.add(aux[i]);
        }

        //Transformamos las transiciones
        
        for(int i = 0; i < this.trans.size() ; i++){
            //Obtenemos el string perteneciente a una transicion. Ejemplo. (A,0,B)
            String t = this.trans.get(i);
            //Eliminamos los Parentesis a ambos lados
            // t.replace("\\(","");
            // t.replace("\\)","");
            t = t.replaceAll("[\\p{Ps}\\p{Pe}]", "");
            //Eliminamos las comas y separamos, obteniendo cada dato por separado
            String[] datos = t.split(",");
            //Con los datos obtenidos, creamos una transicion y la almacenamos en un arreglo para utilizarlo despues
            Transicion transicion = new Transicion(datos[0],datos[1],datos[2]);
            this.transiciones.add(transicion);

            //Repetimos hasta transformar todas las transiciones ingresadas.
        }
        
        //Obtenemos el estado Inicial

        this.estadoInicial = this.estInicial.split("=")[1];

        //Separamos los estados Finales.

        this.estFinales = this.estFinales.split("=")[1];
        // this.estFinales.replace("\\{", "");
        // this.estFinales.replace("\\}", "");
        this.estFinales = this.estFinales.replaceAll("[\\p{Ps}\\p{Pe}]", "");
        aux = this.estFinales.split(",");
        for(int i = 0; i < aux.length; i++){
            this.estadosFinales.add(aux[i]);
        }
    }

    /**
     * Metodo que toma los datos del Automata Finito Determinista para minimizarlo utilizando el metodo de llenado de tablas.
     */
    public void minimizar(){

        //Con la tabla inicializada con el tamaño correcto y los distintos datos del AFD, podemos comenzar a Minimizar

        //Lo primero que tenemos que hacer es encontrar los pares que no sean distinguibles, para ello, buscaremos los pares DISTINGUIBLES.
        //Al encontrar un par Distinguible, marcaremos ese par en la tabla, y asi con todos.
        //Los pares no marcados en la tabla seran los No Distinguibles, y por lo tanto, se pueden agrupar como un solo estado en el AFD

        //Paso 1: Marcar todos aquellos pares/transiciones que contengar un Estado Final.

        for(int i = 0; i < this.tabla.length; i++){
            for(int j = 0; j < i; j++){
                if( verificarAceptacion(i, j)){
                    this.tabla[i][j] = 1;
                }
            }            
        }
        //Paso 2: Verificar aquellos pares/transiciones que no estan marcados (q1,q2).
        // Marcamos el par en cuestion si existe un par [δ(q1, x),δ(q2,x)] que si este marcado para un determinado valor de entrada x.
        
        for(int i = 0 ; i < this.tabla.length ; i++){            
            for(int j = 0; j < i ; j++){
                if(this.tabla[i][j] == 0){
                    String estado1 = this.estados.get(i);
                    String estado2 = this.estados.get(j);
                    verificarPar(estado1,estado2);
                }
            }
        }

        //Paso 3: Combinar los pares no distinguibles en bloques de estado, donde los estados (2 o mas) en el mismo bloque son equivalentes.

        ArrayList<Integer> revisados = new ArrayList<>();
        for(int i = 0; i < this.estados.size(); i++){            
            if(!revisados.contains(i)){
                String estado =this.estados.get(i);
                ArrayList<String> iguales = new ArrayList<>();

                //Buscamos aquellos estados que son iguales y los guardamos en un arreglo
                for(int j = 0; j < this.tabla.length; j++){
                    for(int k = 0; k < j; k++){
                        if( i == j && !revisados.contains(k)){
                            if(this.tabla[j][k] == 0 ){
                                iguales.add( this.estados.get(k));                            
                            }
                        }
                        else if (i == k && !revisados.contains(j)){
                            if(this.tabla[j][k] == 0 ){
                                iguales.add(this.estados.get(j));                               
                            }
                        }
                    }
                }            
                //Si es que encontramos pares iguales, entonces creamos un estados que los represente a todos juntos
                //Unimos los estados
                if(iguales.size() != 0){                                
                    iguales.add(this.estados.get(i));
                    Collections.sort(iguales);
                    estado = "";
                    for(String s: iguales){
                        revisados.add(this.estados.indexOf(s));
                        if(!estado.equals("")){
                            estado = estado+"|"+s;
                        }
                        else{
                            estado = s;
                        }
                    }
                    //Como tenemos estamos que se hicieron uno solo, debemos cambiar las transiciones que estan relacionadas.
                    for(int j = 0; j < this.transiciones.size(); j++){
                        Transicion t = this.transiciones.get(j);
                        for(String s:iguales){
                            if(t.getEstado1().equals(s)){
                                t.setEstado1(estado);
                            }                       
                            if(t.getEstado2().equals(s)){
                                t.setEstado2(estado);
                            }
                        }
                    }
                    //Si es estado inicial era uno de los estados que se combinaron, entonces este nuevo estado sera el nuevo estado inicial
                    if(iguales.contains(this.estadoInicial)){
                        this.estadoInicial = estado;
                    }                    
                    this.estadosMinimizados.add(estado); 
                }
                else{
                    this.estadosMinimizados.add(estado);
                    revisados.add(this.estados.indexOf(estado));
                }
            }
        }

        //Luego de juntar todos los pares de estados, debemos eliminar las transiciones repetidas que se generaron.
        for(int i = 0; i <this.transiciones.size();i++){
            Transicion t1 = this.transiciones.get(i);
            for(int j = i+1; j < this.transiciones.size(); j++){
                Transicion t2 = this.transiciones.get(j);

                if( t1.getEstado1().equals(t2.getEstado1()) && t1.getEstado2().equals(t2.getEstado2()) && t1.getEntrada().equals(t2.getEntrada()) ){
                    this.transiciones.remove(j);
                    j--;
                }
            }
        }

        //Paso 4: Mostrar el AFD Minimizado (con el formato correspondiente) 
        
        //Mostramos el nombre o identificador del automata minimizado
        this.nombreAutomata = this.nombreAutomata.replaceFirst("AFD","AFDM");
        System.out.println(this.nombreAutomata);
        //Mostramos el alfabeto
        String alfabeto = "K={";
        for(int i = 0; i < this.estadosMinimizados.size(); i++){
            if(i != this.estadosMinimizados.size()-1){
                alfabeto = alfabeto+this.estadosMinimizados.get(i)+",";
            }
            else{
                alfabeto = alfabeto+this.estadosMinimizados.get(i)+"}";
            }
            
        }
        System.out.println(alfabeto);
        //Mostramos los valores de delta, es decir, las transiciones entre estados.
        System.out.println("delta:");
        for(Transicion s: this.transiciones){
            System.out.println(s.toString());
        }
        //Mostramos el estado Inicial
        System.out.println("s="+this.estadoInicial);
        String finales = "F={";
        for(int i = 0; i < this.estadosFinales.size(); i++){
            if(i != this.estadosFinales.size()-1){
                finales = finales+this.estadosFinales.get(i)+",";
            }
            else{
                finales = finales+this.estadosFinales.get(i)+"}";
            }
        }
        //Mostramos el estado Final.
        System.out.println(finales);
    }

    /**
     * Metodo que nos permite saber si, en un par de estados, uno de ellos es un estado de aceptacion o estado final.
     */
    public boolean verificarAceptacion(int p1, int p2){

        String estado1 = this.estados.get(p1);
        String estado2 = this.estados.get(p2);
        
        if( this.estadosFinales.contains(estado1) && !this.estadosFinales.contains(estado2)){
            return true;
        }
        else if( !this.estadosFinales.contains(estado1) && this.estadosFinales.contains(estado2) ){
            return true;
        }
        return false;        
    }

    /**
     * Metodo que nos permite verificar si un par de estados es distinguible o no.
     */
    public void verificarPar(String e1, String e2){
                
        int cantSigma = this.sigma.size();
        int pos1 = this.estados.indexOf(e1);
        int pos2 = this.estados.indexOf(e2);        
        
        for(int s = 0; s < cantSigma; s++){
            String sig = this.sigma.get(s);
            
            Transicion t1 = this.transiciones.get(pos1*cantSigma+s);
            Transicion t2 = this.transiciones.get(pos2*cantSigma+s);

            String estado1 = t1.getEstado2();
            String estado2 = t2.getEstado2();            

            int p1 = this.estados.indexOf(estado1);
            int p2 = this.estados.indexOf(estado2);

                if( p1 > p2){
                    if(this.tabla[p1][p2] == 1){
                        if(pos1 > pos2){
                            this.tabla[pos1][pos2] = 1;
                        }
                        else if( pos2 > pos1){
                            this.tabla[pos2][pos1] = 1;
                        }
                    }
                }
                else{
                    if(this.tabla[p2][p1] == 1){
                        if(pos1 > pos2){
                            this.tabla[pos1][pos2] = 1;
                        }
                        else if( pos2 > pos1){
                            this.tabla[pos2][pos1] = 1;
                        }
                    }
                }
            
        }
        
    }


    /**
     * Metodo utilizado para confirmar que los datos ingresados se procesaron bien
     * Solo se utilizo en la fase de pruebas, en la ejecucion de la minimizacion del automata no se utiliza.
     */
    public void test(){

        System.out.println("Prueba jeje");
        System.out.println("Estados:");
        for(int i = 0; i < this.estados.size(); i++){
            System.out.println(this.estados.get(i));
        }
        System.out.println("-------");
        System.out.println("Sigma:");
        for(int i = 0; i < this.sigma.size(); i++){
            System.out.println(this.sigma.get(i));
        }
        System.out.println("-------");
        System.out.println("Trans:");
        for(int i = 0; i < this.transiciones.size(); i++){
            System.out.println(this.transiciones.get(i).toString());
        }
        System.out.println("-------");
        System.out.println("Estado Inicial: "+ this.estadoInicial);
        System.out.println("-------");
        System.out.println("Estados Finales");
        for(int i = 0; i < this.estadosFinales.size(); i++){
            System.out.println(this.estadosFinales.get(i));
        }
        System.out.println("-------");
        
    }
}
