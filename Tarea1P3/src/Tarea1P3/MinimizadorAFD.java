package Tarea1P3;

import java.util.ArrayList;

public class MinimizadorAFD {

    //Variables Iniciales (Las lineas tomadas de la entrada estandar)
    private String est;
    private String sig;
    private ArrayList<String> trans;
    private String estInicial;
    private String estFinales;

    //Variables a Utilizar
    private ArrayList<String> estados;
    private ArrayList<String> sigma;
    private ArrayList<Transicion> transiciones;
    private String estadoInicial;
    private ArrayList<String> estadosFinales;
    private int tabla[][];

    public MinimizadorAFD(String estados, String sigma, ArrayList<String> transiciones, String estadoInicial, String estadosFinales) {     
        this.est = estados;
        this.sig = sigma;
        this.trans = transiciones;
        this.estInicial = estadoInicial;
        this.estFinales = estadosFinales;

        this.estados = new ArrayList<String>();
        this.sigma = new ArrayList<String>();
        this.transiciones = new ArrayList<Transicion>();
        this.estadosFinales = new ArrayList<String>();        
    }

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

    public void minimizar(){

        //Con la tabla inicializada con el tamaño correcto y los distintos datos del AFD, podemos comenzar a Minimizar

        //Lo primero que tenemos que hacer es encontrar los pares que no sean distinguibles, para ello, buscaremos los pares DISTINGUIBLES.
        //Al encontrar un par Distinguible, marcaremos ese par en la tabla, y asi con todos.
        //Los pares no marcados en la tabla seran los No Distinguibles, y por lo tanto, se pueden agrupar como un solo estado en el AFD

        //Paso 1: Marcar todos aquellos pares/transiciones que contengar un Estado Final.

        // for(int i = 0; i < this.estadosFinales.size() ; i++){
        //     String fin = this.estadosFinales.get(i);
        //     int pos1 = this.estados.indexOf(fin);                        
        //     for(int j = 0; j < this.transiciones.size(); j++){
        //         // Transicion t = this.transiciones.get(j);                
        //         // if(t.getEstado1().equals(fin)){ 
        //         //     int pos2 = this.estados.indexOf(t.getEstado2());
        //         //     if( pos1 > pos2){
        //         //         this.tabla[pos1][pos2] = 1;
        //         //     }
        //         //     else if(pos2 > pos1){
        //         //         this.tabla[pos2][pos1] = 1;
        //         //     }  
        //         // }
        //         // else if(t.getEstado2().equals(fin)){
        //         //     int pos2 = this.estados.indexOf(t.getEstado1());
        //         //     if(pos1 > pos2){
        //         //         this.tabla[pos1][pos2] = 1;
        //         //     }
        //         //     else if(pos2 > pos1){
        //         //         this.tabla[pos2][pos1] = 1;
        //         //     }                    
        //         // }
        //         if( verificarAceptacion(i, j)){
        //             this.tabla[i][j] = 1;
        //         }
        //     }
        //     // for(int x = 0; x < this.tabla.length; x++){
        //     //     for(int y = 0; y < x ; y++){
        //     //         if(x > pos1){
        //     //             this.tabla[x][pos1] = 1;
        //     //         }
        //     //     }
        //     // }
        // }

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
                // System.out.print(this.tabla[i][j]);
                // System.out.print(" ");
            }
            //System.out.println();
        }
        for(int i = 0; i < this.tabla.length; i++){
            for(int j = 0; j < i; j++){
                System.out.print(this.tabla[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }

        //Paso 3: Combinar los pares no distinguibles en bloques de estado, donde los estados (2 o mas) en el mismo bloque son equivalentes.



        //Paso 4: Mostrar el AFD Minimizado (con el formato correspondiente)

    }

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

    public void verificarPar(String e1, String e2){
                
        int cantSigma = this.sigma.size();
        int pos1 = this.estados.indexOf(e1);
        int pos2 = this.estados.indexOf(e2);
        System.out.println(pos1 +" "+ pos2);
        
        for(int s = 0; s < cantSigma; s++){
            String sig = this.sigma.get(s);
            
            Transicion t1 = this.transiciones.get(pos1*cantSigma+s);
            Transicion t2 = this.transiciones.get(pos2*cantSigma+s);

            String estado1 = t1.getEstado2();
            String estado2 = t2.getEstado2();

            System.out.println(estado1 + " "+ estado2);

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
