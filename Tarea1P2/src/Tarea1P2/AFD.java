package Tarea1P2;


import java.util.ArrayList;

public class AFD {
    private ArrayList<String> sigmaAFD;
    private ArrayList<String> estadosAFD;

    private String sigmaS; //sigma String
    private String estadosS; //estados en String


    private ArrayList<String> deltaS;
    public ArrayList<Transicion> deltaAFD;



    private ArrayList<ArrayList<String>> conecciones_de_cada_estado;



    public AFD(ArrayList<String> delta, String sigma, String estados) {
        this.deltaAFD = new ArrayList<>();
        this.deltaS = delta;
        this.sigmaAFD = new ArrayList<>();
        this.sigmaS = sigma;
        this.conecciones_de_cada_estado = new ArrayList<>();
        this.estadosAFD = new ArrayList<>();
        this.estadosS = estados;

        this.procesarSigma();
        this.procesarEstados();
        this.procesarTransicion();


        this.determinarConeccionesDeCadaEstado();

    }


    public void procesarSigma() {

        this.sigmaS = this.sigmaS.split("=")[1];
        this.sigmaS = this.sigmaS.replaceAll("[\\p{Ps}\\p{Pe}]", "");
        String[] aux = this.sigmaS.split(",");
        for(int i = 0; i < aux.length; i++) {
            this.sigmaAFD.add(aux[i]);
        }

    }

    public void procesarEstados() {
        this.estadosS = this.estadosS.split("=")[1];
        this.estadosS = this.estadosS.replaceAll("[\\p{Ps}\\p{Pe}]", "");
        String[] aux = this.estadosS.split(",");
        for(int i = 0; i < aux.length; i++){
            this.estadosAFD.add(aux[i]);
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
            this.deltaAFD.add(transicion);
        }

    }


    public String sinEntrada() {
        String nuevo = null;
        for (int i = 0; i < this.estadosAFD.size() ; i++) {
            String estado = this.estadosAFD.get(i);
            int numero_apariciones=0;
            for (int j = 0; j <this.deltaAFD.size() && numero_apariciones==0; j++) {
                if (estado.equals(this.deltaAFD.get(j).getSegundo())) {
                    numero_apariciones++;
                }
            }
            if (numero_apariciones==0) {
                nuevo = estado;
            }
        }

        return nuevo;

    }


    public void print_delta() {
        for (int i = 0; i < this.deltaAFD.size(); i++) {
            System.out.println(this.deltaAFD.get(i).getPrimero() + ", " + this.deltaAFD.get(i).getUnion() + ", " + this.deltaAFD.get(i).getSegundo());
        }

    }

    public void print_coneccionesDeCadaEstado() {
        for (int i = 0; i < this.conecciones_de_cada_estado.size() ; i++) {
            System.out.println(this.conecciones_de_cada_estado.get(i));
        }


    }



    public void determinarConeccionesDeCadaEstado() {
        for (int i = 0; i < this.deltaAFD.size(); i++) {
            ArrayList<String> aux = new ArrayList<>();
            aux.add(this.deltaAFD.get(i).getSegundo());
            for (int j = 0; j < this.deltaAFD.size(); j++) {
                if (this.deltaAFD.get(i).getSegundo().equals(this.deltaAFD.get(j).getPrimero())) {
                    aux.add(this.deltaAFD.get(j).getSegundo());
                }
            }

            this.conecciones_de_cada_estado.add(aux);
        }

    }

    public int coneccionesEstado(String s) {
        int numero_conecciones = 0;
        for (int i = 0; i < this.deltaAFD.size() ; i++) {
            if (this.deltaAFD.get(i).getPrimero().equals(s)) {
                numero_conecciones++;
            }
        }

        return numero_conecciones;

    }



    public ArrayList<String> coneccionesEstadoArray(String s) {
        ArrayList<String> aux = new ArrayList<>();
        for (int i = 0; i < this.deltaAFD.size(); i++) {
            if (this.deltaAFD.get(i).getPrimero().equals(s)) {
                aux.add(this.deltaAFD.get(i).getSegundo());
            }
        }
        return aux;

    }

    public ArrayList<String> getSigmaAFD() {
        return sigmaAFD;
    }

    public void setSigmaAFD(ArrayList<String> sigmaAFD) {
        this.sigmaAFD = sigmaAFD;
    }

    public ArrayList<Transicion> getDeltaAFD() {
        return deltaAFD;
    }

    public void setDeltaAFD(ArrayList<Transicion> deltaAFD) {
        this.deltaAFD = deltaAFD;
    }

    public ArrayList<String> getEstadosAFD() {
        return estadosAFD;
    }

    public void setEstadosAFD(ArrayList<String> estadosAFD) {
        this.estadosAFD = estadosAFD;
    }
}