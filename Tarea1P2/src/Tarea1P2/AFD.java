package Tarea1P2;


import java.util.ArrayList;
import java.util.HashMap;


public class AFD {
    private ArrayList<String> sigmaAFND; //el sigma que se obtiene del AFND
    private ArrayList<String> estadosAFND; //Los estados que se obtienen del AFND
    private ArrayList<String> estadosFinalesAFD;

    private String sigmaS; //sigma String
    private String estadosS; //estados en String


    private HashMap<String,ArrayList<ArrayList<String>>> tabla; //esta tabla es para ordernar los estados

    private ArrayList<String> deltaS;
    public ArrayList<Transicion> deltaAFDN;  //El delta que se obtuvo del AFND

    private String estadoInicial;
    private String estadoFinal;
    private String estadoInicialAFD;

    private int contador; //numero de estados
    private ArrayList<String> k; //El K del AFD



    private ArrayList<Transicion> deltaAFD;



    public AFD(ArrayList<String> delta, String sigma, String estados) {
        this.deltaAFDN = new ArrayList<>();
        this.deltaS = delta;
        this.sigmaAFND = new ArrayList<>();
        this.sigmaS = sigma;
        this.estadosAFND = new ArrayList<>();
        this.estadosS = estados;
        this.tabla = new HashMap<>();
        this.k = new ArrayList<>();




        this.procesarSigma();
        this.procesarEstados();
        this.procesarTransicion();

        this.estadoInicial =this.obtenerEstadoInicial();
        this.estadoFinal= this.obtenerEstadoFinal();

        this.estadoInicialAFD = null;
        this.estadosFinalesAFD = new ArrayList<>();

        this.deltaAFD = new ArrayList<>();
        this.contador=0;
        this.AFND_a_AFD();





    }


    public void procesarSigma() {

        this.sigmaS = this.sigmaS.split("=")[1];
        this.sigmaS = this.sigmaS.replaceAll("[\\p{Ps}\\p{Pe}]", "");
        String[] aux = this.sigmaS.split(",");
        for(int i = 0; i < aux.length; i++) {
            this.sigmaAFND.add(aux[i]);
        }

    }

    public void procesarEstados() {
        this.estadosS = this.estadosS.split("=")[1];
        this.estadosS = this.estadosS.replaceAll("[\\p{Ps}\\p{Pe}]", "");
        String[] aux = this.estadosS.split(",");
        for(int i = 0; i < aux.length; i++){
            this.estadosAFND.add(aux[i]);
        }
    }

    public void procesarTransicion() {
        for(int i = 0; i < this.deltaS.size() ; i++){
            //Obtenemos el string perteneciente a una transicion.
            String t = this.deltaS.get(i);
            //Eliminamos los Parentesis a ambos lados
            t = t.replaceAll("[\\p{Ps}\\p{Pe}]", "");
            //Eliminamos las comas y separamos, obteniendo cada dato por separado
            String[] datos = t.split(",");
            //Con los datos obtenidos, creamos una transicion y la almacenamos en un arreglo para utilizarlo despues
            Transicion transicion = new Transicion(datos[0],datos[1],datos[2]);
            this.deltaAFDN.add(transicion);
        }

    }


    public String obtenerEstadoInicial() {
        String nuevo = null;
        for (int i = 0; i < this.estadosAFND.size() ; i++) {
            String estado = this.estadosAFND.get(i);
            int numero_apariciones=0;
            for (int j = 0; j <this.deltaAFDN.size() && numero_apariciones==0; j++) {
                if (estado.equals(this.deltaAFDN.get(j).getSegundo())) {
                    numero_apariciones++;
                }
            }
            if (numero_apariciones==0) {
                nuevo = estado;
            }
        }

        return nuevo;

    }

    public String obtenerEstadoFinal() {
        String nuevo = null;
        for (int i = 0; i < this.estadosAFND.size() ; i++) {
            String estado =  this.estadosAFND.get(i);
            int numero_apariciones = 0;
            for (int j = 0; j <this.deltaAFDN.size() && numero_apariciones==0; j++) {
                if (estado.equals(this.deltaAFDN.get(j).getPrimero())) {
                    numero_apariciones++;
                }
            }
            if (numero_apariciones==0) {
                nuevo = estado;
            }
        }

        return nuevo;
    }

    public void AFND_a_AFD() {
        //Se arma la tabla

        tabla.put("estados", new ArrayList<ArrayList<String>>());
        for(int i =0;i<this.sigmaAFND.size();i++)
        {
            tabla.put(this.sigmaAFND.get(i),new ArrayList<ArrayList<String>>());
        }
        //rellenar estado inicial
        tabla.get("estados").add(new ArrayList<String>());
        tabla.get("estados").get(0).add(estadoInicial);

        for (int i = 0; i < tabla.get("estados").get(0).size() ; i++) {
            ArrayList<String> siguientes = this.buscarAdyacencia(tabla.get("estados").get(0).get(i), "_");
            for (int j = 0; j < siguientes.size() ; j++) {
                if (tabla.get("estados").get(0).contains(j) == false) {
                    tabla.get("estados").get(0).add(siguientes.get(j));
                }
            }
        }

        tabla.get("estados").get(0).add("q"+this.contador);
        this.k.add("q" + this.contador);
        this.contador++;

        //A continuacion haremos la automatizacion
        for (int i = 0; i < tabla.get("estados").size(); i++) {
            ArrayList<String> estado_de_afd = tabla.get("estados").get(i);
            for (int j = 0; j < this.sigmaAFND.size(); j++) {
                String letra = this.sigmaAFND.get(j);
                //añadimos un nuevo estado
                this.tabla.get(letra).add(new ArrayList<String>());
                for (int l = 0; l < estado_de_afd.size(); l++) {
                    ArrayList<String> siguientes = this.buscarAdyacencia(estado_de_afd.get(l), letra);
                    for (int m = 0; m < siguientes.size(); m++) {
                        if (tabla.get(letra).get(i).contains(siguientes.get(m))==false) {
                            tabla.get(letra).get(i).add(siguientes.get(m));
                        }

                    }
                }

                for (int l = 0; l < tabla.get(letra).get(i).size(); l++) {
                    ArrayList<String> siguientes = this.buscarAdyacencia(tabla.get(letra).get(i).get(l), "_");
                    for (int m = 0; m < siguientes.size(); m++) {
                        if (tabla.get(letra).get(i).contains(siguientes.get(m))==false) {
                            tabla.get(letra).get(i).add(siguientes.get(m));
                        }
                    }
                }

                if (this.tabla.get(letra).get(i).isEmpty()==true) {
                    this.tabla.get(letra).get(i).add("basurero");
                }

                //añadimos el estado si este no estaba presente
                if (this.buscarEstado(this.tabla.get(letra).get(i))==null) {
                    tabla.get(letra).get(i).add("q"+this.contador);
                    this.k.add("q"+this.contador);
                    this.contador++;
                    tabla.get("estados").add(tabla.get(letra).get(i));
                }
                else {
                    tabla.get(letra).get(i).add(this.buscarEstado(this.tabla.get(letra).get(i)));
                }
                int pos = tabla.get(letra).get(i).size()-1;
                this.deltaAFD.add(new Transicion(estado_de_afd.get(estado_de_afd.size()-1),letra,tabla.get(letra).get(i).get(pos)));
            }

        }

        //Por último agregamos los estados iniciales y finales del AFD
        ArrayList<ArrayList<String>> estados = this.tabla.get("estados");
        for (int i = 0; i < estados.size(); i++) {
            ArrayList<String> estado_solo = estados.get(i);
            if (estado_solo.contains(this.estadoInicial)) {
                this.estadoInicialAFD = estado_solo.get(estado_solo.size()-1);
            }
            if (estado_solo.contains(this.estadoFinal)) {
                this.estadosFinalesAFD.add(estado_solo.get(estado_solo.size()-1));
            }
        }


    }

    //metodo que busca los estados adyacentes a otro estado
    public ArrayList<String> buscarAdyacencia(String estado, String caracter) {
        ArrayList<String> adyacentes =  new ArrayList<>();
        for (int i = 0; i < this.deltaAFDN.size(); i++) {
            Transicion objetivo = this.deltaAFDN.get(i);
            if (objetivo.getPrimero().equals(estado) && objetivo.getUnion().equals(caracter)) {
                adyacentes.add(objetivo.getSegundo());
            }
        }
        return adyacentes;

    }

    //metodo que busca al estado para verificar si ya fue agregado
    public String buscarEstado(ArrayList<String> nuevo) {
        ArrayList<ArrayList<String>> estadosA = tabla.get("estados");
        for (int i = 0; i < estadosA.size(); i++) {
            ArrayList<String> objetivo = estadosA.get(i);
            int aux = 0;
            for (int j = 0; j < objetivo.size()-1; j++) {
                if (nuevo.contains(objetivo.get(j))) {
                    aux++;
                }
            }
            if(aux==nuevo.size()) {
                return objetivo.get(objetivo.size()-1);
            }
        }
        return null;
    }



    public void print_delta() {
        for (int i = 0; i < this.deltaAFD.size(); i++) {
            System.out.println("(" + this.deltaAFD.get(i).getPrimero() + ","+this.deltaAFD.get(i).getUnion()+"," + this.deltaAFD.get(i).getSegundo()+")");
        }
    }

    public ArrayList<String> getSigmaAFND() {
        return sigmaAFND;
    }

    public void setSigmaAFND(ArrayList<String> sigmaAFND) {
        this.sigmaAFND = sigmaAFND;
    }

    public ArrayList<Transicion> getDeltaAFDN() {
        return deltaAFDN;
    }

    public void setDeltaAFDN(ArrayList<Transicion> deltaAFDN) {
        this.deltaAFDN = deltaAFDN;
    }

    public ArrayList<String> getEstadosAFND() {
        return estadosAFND;
    }

    public void setEstadosAFND(ArrayList<String> estadosAFND) {
        this.estadosAFND = estadosAFND;
    }

    public HashMap<String, ArrayList<ArrayList<String>>> getTabla() {
        return tabla;
    }

    public void setTabla(HashMap<String, ArrayList<ArrayList<String>>> tabla) {
        this.tabla = tabla;
    }

    public String getEstadoInicial() {
        return estadoInicial;
    }

    public void setEstadoInicial(String estadoInicial) {
        this.estadoInicial = estadoInicial;
    }

    public String getEstadoFinal() {
        return estadoFinal;
    }

    public void setEstadoFinal(String estadoFinal) {
        this.estadoFinal = estadoFinal;
    }

    public int getContador() {
        return contador;
    }

    public void setContador(int contador) {
        this.contador = contador;
    }

    public ArrayList<String> getK() {
        return k;
    }

    public void setK(ArrayList<String> k) {
        this.k = k;
    }

    public ArrayList<Transicion> getDeltaAFD() {
        return deltaAFD;
    }

    public void setDeltaAFD(ArrayList<Transicion> deltaAFD) {
        this.deltaAFD = deltaAFD;
    }

    public ArrayList<String> getEstadosFinalesAFD() {
        return estadosFinalesAFD;
    }

    public void setEstadosFinalesAFD(ArrayList<String> estadosFinalesAFD) {
        this.estadosFinalesAFD = estadosFinalesAFD;
    }

    public String getEstadoInicialAFD() {
        return estadoInicialAFD;
    }

    public void setEstadoInicialAFD(String estadoInicialAFD) {
        this.estadoInicialAFD = estadoInicialAFD;
    }
}