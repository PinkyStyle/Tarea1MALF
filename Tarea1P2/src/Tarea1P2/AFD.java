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


    public void crearEstados() {
        for (int i = 0; i < this.estadosAFD.size(); i++) {
            if (i==-1) {
                ArrayList<String> ep = new ArrayList<>();
                ep.add("_");
                this.estados.add(ep);
            }
            else {
                ArrayList<String> nuevo = new ArrayList<>();
                nuevo.add(this.estadosAFD.get(i));
                this.estados.add(nuevo);
            }
        }
    }

    public void llenarEstadosIniciales() {
        for (int i = 0; i <this.estados.size() ; i++) {
            String s= null;
            int cont=0;
            for (int j = 0; j < this.deltaAFD.size() ; j++) {
                boolean cond = false;
                if (this.deltaAFD.get(i).getUnion().equals(this.estados.get(i).get(0)) && !this.deltaAFD.get(j).getPrimero().equals(s) || this.deltaAFD.get(j).getUnion() .equals(this.estados.get(i).get(0)) && cont ==0 ) {
                    this.estados.get(i).add(this.deltaAFD.get(j).getSegundo());
                    String aux = null;
                    s = this.deltaAFD.get(j).getSegundo();
                    int cont2=0;
                    for (int k = 0; k < this.deltaAFD.size() ; k++) {
                        if (cont2==0) {
                            if (this.deltaAFD.get(k).getUnion().equals("_") && this.deltaAFD.get(k).getPrimero().equals(s)) {
                                aux = this.deltaAFD.get(k).getSegundo();
                            }
                        }
                    }
                }
            }

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