package Tarea1P2;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AFD {
    private ArrayList<String> sigmaAFD;
    private ArrayList<String> estadosAFD;

    private ArrayList<Transicion> deltaAFD;



    private ArrayList<ArrayList<String>> estados;
    private ArrayList<ArrayList<String>> conecciones_de_cada_estado;



    public AFD(ArrayList<Transicion> delta, ArrayList<String> s, ArrayList<String> e) {
        this.deltaAFD = new ArrayList<>();
        this.deltaAFD = delta;
        this.sigmaAFD = new ArrayList<>();
        this.sigmaAFD = s;
        this.conecciones_de_cada_estado = new ArrayList<>();
        this.estados = new ArrayList<>();
        this.estadosAFD = new ArrayList<>();
        this.estadosAFD = e;

        this.print_delta();
        this.determinarConeccionesDeCadaEstado();
        this.print_coneccionesDeCadaEstado();

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
}