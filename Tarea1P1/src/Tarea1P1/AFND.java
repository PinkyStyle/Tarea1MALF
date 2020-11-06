package Tarea1P1;

import java.util.ArrayList;

public class AFND {

    private ArrayList<String> estados;
    private ArrayList<String> alfabeto;
    private ArrayList<Transicion> transiciones;   
    private String estadoInicial;
    private String estadoFinal; 
    private int numEstado;

    public AFND(){
        this.estados = new ArrayList<>();
        this.alfabeto = new ArrayList<>();
        this.transiciones = new ArrayList<>();        
    }

    public void crearAFND(ArrayList<String> cadena){
        this.numEstado = 0;        
        for(int i = 0; i < cadena.size(); i++){            
            char c = cadena.get(i).charAt(0);
            int ascii = (int)c;

            if(ascii >= 48 && ascii <= 57 || ascii >= 65 && ascii <= 90 || ascii >= 97 && ascii <= 122)
            {
                if(!this.alfabeto.contains(cadena.get(i))){
                    this.alfabeto.add(cadena.get(i));
                }
                this.crearTransicion(cadena.get(i));
            }
            else if(cadena.get(i).equals("*")){

                c = cadena.get(i-1).charAt(0);
                ascii = (int)c;

                if(ascii >= 48 && ascii <= 57 || ascii >= 65 && ascii <= 90 || ascii >= 97 && ascii <= 122){
                    Transicion previa = this.transiciones.get(this.transiciones.size()-1);
                    String inicio = "q"+this.numEstado;
                    this.numEstado++;
                    String termino = "q"+this.numEstado;
                    this.numEstado++;

                    Transicion t1 = new Transicion(previa.getTermino(),"_",previa.getInicio());
                    Transicion t2 = new Transicion(inicio,"_",previa.getInicio());
                    Transicion t3 = new Transicion(previa.getTermino(),"_",termino);
                    Transicion t4 = new Transicion(inicio,"_",termino);

                    this.estados.add(inicio);
                    this.estados.add(termino);

                    this.transiciones.add(t1);
                    this.transiciones.add(t2);
                    this.transiciones.add(t3);
                    this.transiciones.add(t4);
                    
                }
                else if(cadena.get(i-1).equals(".")){
                    boolean aux = false;
                    String sinEntrada = "";
                    String sinSalida= "";

                    for(int j = this.transiciones.size()-1; j >= 0 && !aux; j--){
                        Transicion t = this.transiciones.get(j);
                        if(this.verificarEntrada((t.getInicio()))){
                            sinEntrada = t.getInicio();
                            aux = true;
                        }
                    }
                    aux = false;  
                    for(int j = this.transiciones.size()-1; j >= 0 && !aux; j--){
                        Transicion t = this.transiciones.get(j);
                        if(this.verificarSalida(t.getTermino())){
                            sinSalida = t.getTermino();
                            aux = true;
                        }
                    }
                    Transicion ciclo = new Transicion(sinSalida,"_",sinEntrada);
                    this.transiciones.add(ciclo);
                    String inicio = "q"+this.numEstado;       
                    this.numEstado++;             
                    String termino = "q"+this.numEstado;
                    this.numEstado++;
                    Transicion t1 = new Transicion(inicio,"_",sinEntrada);
                    Transicion t2 = new Transicion(sinSalida,"_",termino);
                    Transicion t3 = new Transicion(inicio,"_",termino);

                    this.estados.add(inicio);
                    this.estados.add(termino);

                    this.transiciones.add(t1);
                    this.transiciones.add(t2);
                    this.transiciones.add(t3);                    
                }  
                else if(cadena.get(i-1).equals("|")){

                    String sinEntrada = "";
                    String sinSalida = "";

                    ArrayList<String> estadosSS = new ArrayList<>();
                    ArrayList<String> estadosSE = new ArrayList<>();

                    for( int j = 0; j < this.estados.size(); j++){
                        String estado = this.estados.get(j);
                        int contador = 0;
                        for(int k = 0; k < this.transiciones.size() && contador == 0; k++){
                            if(this.transiciones.get(k).getTermino().equals(estado)){
                                contador++;
                            }
                        }
                        if(contador == 0){
                            estadosSE.add(estado);
                        }
                    }
                    for( int j = 0; j < this.estados.size(); j++){
                        String estado = this.estados.get(j);
                        int contador = 0;
                        for(int k = 0; k < this.transiciones.size() && contador == 0; k++){
                            if(this.transiciones.get(k).getInicio().equals(estado)){
                                contador++;
                            }
                        }
                        if(contador == 0){
                            estadosSS.add(estado);
                        }                        
                    }
                    sinEntrada = estadosSE.get(0);
                    sinSalida = estadosSS.get(0);

                    Transicion ciclo = new Transicion(sinSalida,"_",sinEntrada);

                    String inicio = "q"+this.numEstado;
                    this.numEstado++;
                    String termino = "q"+this.numEstado;
                    this.numEstado++;

                    Transicion t1 = new Transicion(inicio,"_",sinEntrada);
                    Transicion t2 = new Transicion(sinSalida,"_",termino);
                    Transicion t3 = new Transicion(inicio,"_",termino);
                    

                    this.estados.add(inicio);
                    this.estados.add(termino);

                    this.transiciones.add(ciclo);
                    this.transiciones.add(t1);
                    this.transiciones.add(t2);
                    this.transiciones.add(t3);

                }              
            }
            else if(cadena.get(i).equals(".")){      
                boolean aux = false;
                String sinEntrada = "";
                for(int j = this.transiciones.size()-1 ; j>=0 && !aux ;j--){
                    Transicion t = this.transiciones.get(j);
                    if(this.verificarEntrada(t.getInicio())){
                        sinEntrada = t.getInicio();      
                        aux = true;                  
                    }
                }
                String sinSalida = "";
                int contador = 0;
                String fin = "";
                aux = false;
                for(int j = this.transiciones.size()-1; j >=0 && !aux ;j--){
                    Transicion t = this.transiciones.get(j);
                    if(this.verificarSalida(t.getTermino())){
                        if(contador == 0){
                            contador++;
                            fin = t.getTermino();
                        }
                        else{
                            if(!fin.equals(t.getTermino())){
                                sinSalida = t.getTermino();                                
                                aux = true;   
                            }                            
                        }
                    }
                }
                Transicion nuevaConcatenacion = new Transicion(sinSalida,"_",sinEntrada);
                this.transiciones.add(nuevaConcatenacion);          
            }
            else if(cadena.get(i).equals("|")){

                //Primeros 2 sin entrada

                Transicion sinEntrada1 = null;
                Transicion sinEntrada2 = null;
                int anteriorEntrada = 0;
                boolean aux = false;
                int contador = 0;

                for(int j = this.transiciones.size()-1 ; j >= 0 && !aux ; j--){
                    Transicion t = this.transiciones.get(j);
                    if(this.verificarEntrada(t.getInicio())){
                        if(contador == 0){
                            sinEntrada1 = t;
                            int anterior = Integer.parseInt(t.getInicio().replace("q", ""));
                            anteriorEntrada = anterior;
                            contador++;
                        }
                        else{
                            sinEntrada2 = t;
                            int anterior = Integer.parseInt(t.getInicio().replace("q", ""));
                            if(anterior < anteriorEntrada){
                                anteriorEntrada = anterior;
                            }
                            aux = true;
                        }
                    }
                }                          
                //Primeros 2 sin salida

                Transicion sinSalida1 = null;
                Transicion sinSalida2 = null;
                int anteriorSalida = 0;
                aux = false;
                contador = 0;

                for(int j = this.transiciones.size()-1 ; j >= 0 && !aux; j--){
                    Transicion t = this.transiciones.get(j);                    
                    if(this.verificarSalida(t.getTermino())){                        
                        if(contador == 0){
                            sinSalida1 = t;
                            int anterior = Integer.parseInt(t.getTermino().replace("q", ""));
                            anteriorSalida = anterior;
                            contador++;                            
                        }
                        else{
                            sinSalida2 = t;
                            int anterior = Integer.parseInt(t.getTermino().replace("q", ""));
                            if(anterior > anteriorSalida){
                                anteriorSalida = anterior;
                            }
                            aux = true;                            
                        }
                    }
                }                
                this.transiciones.add(new Transicion("q"+this.numEstado,"_",sinEntrada1.getInicio()));
                this.transiciones.add(new Transicion("q"+this.numEstado,"_",sinEntrada2.getInicio()));
                this.numEstado++;
                this.transiciones.add(new Transicion(sinSalida1.getTermino(),"_","q"+this.numEstado));
                this.transiciones.add(new Transicion(sinSalida2.getTermino(),"_","q"+this.numEstado));
                this.numEstado++;               
            }      
        }     
        this.obtenerEstadoInicial();
        this.obtenerEstadoFinal();   
    }

    public void crearTransicion(String letra){
        //q + numEstado para tener el mismo formato que pide la tarea;
        String inicio = "q"+this.numEstado;
        this.numEstado++;
        String termino = "q"+this.numEstado;
        this.numEstado++;
        this.estados.add(inicio);
        this.estados.add(termino);
        Transicion t = new Transicion(inicio, letra, termino);
        this.transiciones.add(t);
    }

    public boolean verificarEntrada(String entrada){
        
        for(int i = 0; i < this.transiciones.size(); i++){
            if(this.transiciones.get(i).getTermino().equals(entrada)){
                return false;
            }
        }
        return true;
    }

    public boolean verificarSalida(String salida){
        for(int i = 0; i < this.transiciones.size();i++){            
            if(this.transiciones.get(i).getInicio().equals(salida)){
                return false;
            }
        }        
        return true;
    }    

    public void obtenerEstadoInicial(){
        for(int i = 0; i < this.estados.size(); i++){    
            String estado = this.estados.get(i);
            if(verificarEntrada(estado)){
                this.estadoInicial = estado;
                break;
            }
        }
    public void imprimirAFND() {
        System.out.println("AFND M: ");

        System.out.print("K={");
        for(int i=0; i < this.estados.size(); i++){

            if(i < this.estados.size()-1){
                System.out.print(this.estados.get(i)+",");
            }
            else{
                System.out.print(this.estados.get(i));
            }
        }
        System.out.println("}");

        System.out.print("Sigma={");
        for(int i=0; i < this.alfabeto.size(); i++){
            if(i < this.alfabeto.size()-1){
                System.out.print(this.alfabeto.get(i)+",");
            }
            else{
                System.out.print(this.alfabeto.get(i));
            }
        }
        System.out.println("}");

        System.out.println("Delta:");
        for (int i = 0; i < this.transiciones.size(); i++) {
            System.out.println("("+this.transiciones.get(i).getInicio() + "," + this.transiciones.get(i).getUnion() + "," + this.transiciones.get(i).getTermino()+")");
        }

        System.out.println("s="+this.estados.get(0));
        System.out.println("F={}");
    }

    public void obtenerEstadoFinal(){
        for(int i = 0; i < this.estados.size(); i++){
            String estado = this.estados.get(i);
            if(verificarSalida(estado)){
                this.estadoFinal = estado;
                break;
            }
        }
    }

    public void mostrarAFND() {
        System.out.println("AFND M:");
        String alfabeto = "K={";
        for(int i = 0; i < this.estados.size(); i++){
            if(i != this.estados.size()-1){
                alfabeto = alfabeto+this.estados.get(i)+",";
            }
            else{
                alfabeto = alfabeto+this.estados.get(i)+"}";
            }
            
        }
        System.out.println(alfabeto);
        System.out.println("delta:");
        for(Transicion t: this.transiciones){
            System.out.println(t.toString());
        }
        System.out.println("s="+this.estadoInicial);
        System.out.println("F={"+this.estadoFinal+"}");

    }
}
