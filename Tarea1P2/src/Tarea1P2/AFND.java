package Tarea1P2;


import java.util.ArrayList;

public class AFND {
    private ArrayList<String> estados;
    private ArrayList<String> sigma;
    private ArrayList<Transicion> delta;
    private int contador;


    public AFND() {
        this.estados = new ArrayList<>();
        this.sigma = new ArrayList<>();
        this.delta = new ArrayList<>();

    }

    public void print_AFND() {
        for (int i = 0; i < this.delta.size(); i++) {
            System.out.println(this.delta.get(i).getPrimero() + " " + this.delta.get(i).getUnion() + " " + this.delta.get(i).getSegundo());
        }
    }

    public ArrayList<String> sinEntrada() {
        ArrayList<String> nuevo = new ArrayList<>();
        int apariciones = 0;
        for (int i = 0; i < this.estados.size(); i++) {
            String estado = this.estados.get(i);
            apariciones = 0;
            for (int j = 0; j < this.delta.size() && apariciones==0; j++) {
                if (estado.equals(this.delta.get(j).getSegundo())) {
                    apariciones++;
                }
            }
            if (apariciones==0) {
                nuevo.add(estado);
            }
        }

        return nuevo;

    }

    public boolean comprobarSinEntrada(String s) {
        for (int i = 0; i < this.delta.size(); i++) {
            if (this.delta.get(i).getSegundo().equals(s)) {
                return false;
            }
        }

        return true;
    }

    public ArrayList<String> sinSalida() {
        ArrayList<String> nuevo = new ArrayList<String>();
        int apariciones = 0;
        for (int i = 0; i < this.estados.size(); i++) {
            String estado = this.estados.get(i);
            apariciones = 0;
            for (int j = 0; j < this.delta.size() && apariciones==0; j++) {
                if (estado.equals(this.delta.get(j).getPrimero())) {
                    apariciones++;
                }
            }
            if (apariciones==0) {
                nuevo.add(estado);
            }
        }

        return nuevo;
    }

    public boolean comprobarSinSalida(String s) {
        for (int i = 0; i < this.delta.size(); i++) {
            if (this.delta.get(i).getPrimero().equals(s)) {
                return false;
            }
        }

        return true;
    }



    public void crearAFND(ArrayList<String> a) {
        this.contador=0;
        for (int i = 0; i < a.size(); i++) {
            int x = (int) a.get(i).charAt(0);
            if (x>=65 && a<= 90 || a>=97 && a<=122) {
                if (!this.sigma.contains(a.get(i))) {
                    this.sigma.add(a.get(i));
                }
            }
            else if (a.get(i).equals(".")) {
                int cont_Estados_Letra = 0;
                Transicion q_0 = new Transicion(null, null, null);
                Transicion q_1 = new Transicion(null, null, null);
                for (int j =  this.delta.size() -1; j >=0 ; j--) {
                    int y = (int) this.delta.get(j).getUnion().charAt(0);
                    if (y>=65 && y<= 90 || y>=97 && y<=122) {
                        if (cont_Estados_Letra == 0) {
                            q_0.setPrimero(this.delta.get(j).getPrimero());
                            q_0.setSegundo(this.delta.get(j).getSegundo());
                            q_0.setUnion(this.delta.get(j).getUnion());
                            System.out.println("Primero: " + q_0.getPrimero());
                            cont_Estados_Letra++;
                        }



                    }

                    else {

                        if (cont_Estados_Letra == 1) {
                            q_1.setPrimero(this.delta.get(j).getPrimero());
                            q_1.setSegundo(this.delta.get(j).getSegundo());
                            q_1.setUnion(this.delta.get(j).getUnion());
                            System.out.println("Segundo: " + q_0.getSegundo());
                            cont_Estados_Letra--;
                        }

                    }


                }

                Transicion e = new Transicion(q_1.getSegundo(), "_", q_0.getPrimero());
                this.delta.add(e);
                boolean flag_1 = false;
                String primer = "";
                for (int j = this.delta.size()-1; j >=0 && flag_1==false ; j--) {
                    Transicion objetivo = this.delta.get(j);
                    if (this.comprobarSinEntrada(objetivo.getPrimero())) {
                        primer = objetivo.getPrimero();
                        System.out.println("Sin entrada: " + primer);
                        flag_1=true;
                    }

                }

                boolean flag_2=false;
                String segundo= "";
                int cont = 0;
                for (int j = this.delta.size()-1; j >= 0 && flag_2==false ; j--) {
                    Transicion objetivo = this.delta.get(j);
                    if (this.comprobarSinSalida((objetivo.getSegundo()))) {
                        if (cont==0) {
                            cont++;
                        }
                        else {
                            segundo = objetivo.getSegundo();
                            flag_2=true;
                        }
                    }

                }

                Transicion concatenacion = new Transicion(segundo, "_", primer);
                this.delta.add(concatenacion);
            }

            if (a.get(i).equals("|")) {
                ArrayList<String> inicio = this.sinEntrada();
                for (int j = 0; j < inicio.size(); j++) {
                    Transicion nuevo = new Transicion("q"+this.contador,"_", inicio.get(j));
                    this.delta.add(nuevo);
                }
                this.estados.add("q"+this.contador);
                this.contador++;
                ArrayList<String> finals = this.sinSalida();
                for (int j = 0; j < inicio.size(); j++) {
                    Transicion nuevo = new Transicion(finals.get(j),"_","q"+this.contador);
                    this.delta.add(nuevo);
                }
                this.estados.add("q" + this.contador);
                this.contador++;
                String primerSinEntrada="";
                String segundoSinEntrada="";
                boolean flag_1 = false;
                int cont = 0;
                for (int j = this.delta.size()-1; j >= 0 && flag_1==false; j--) {
                    Transicion objetivo = this.delta.get(j);
                    if (this.comprobarSinEntrada(objetivo.getPrimero())) {
                        if (cont==0) {
                            primerSinEntrada = objetivo.getPrimero();
                            cont++;
                        }
                        else {
                            segundoSinEntrada = objetivo.getPrimero();
                            flag_1=true;
                        }
                    }

                }

                String primerSinSalida = "";
                String segundoSinSalida = "";
                boolean flag_2 = false;
                contador = 0;
                for (int j = this.delta.size()-1; j >=0 && flag_2==false ; j--) {
                    Transicion objetivo = this.delta.get(j);
                    if (this.comprobarSinSalida(objetivo.getSegundo())) {
                        if (cont==0) {
                            primerSinSalida = objetivo.getSegundo();
                            cont++;
                        }
                        else {
                            segundoSinSalida = objetivo.getSegundo();
                            flag_2=true;
                        }
                    }

                }

                this.contador++;
                this.delta.add(new Transicion("q"+this.contador, "_", primerSinEntrada));
                this.delta.add(new Transicion("q"+this.contador, "_", segundoSinEntrada));
                this.contador++;
                this.delta.add(new Transicion(primerSinSalida, "_", "q"+this.contador));
                this.delta.add(new Transicion(segundoSinSalida, "_", "q"+this.contador));
                this.contador++;
            }

            if (a.get(i).equals("*")) {
                if (a.get(i-1).equals(".")) {
                    boolean flag_1 = false;
                    String primerSinEntrada= "";
                    for (int j = this.delta.size()-1; j >= 0 && flag_1==false ; j--) {
                        Transicion objetivo = this.delta.get(j);
                        if (this.comprobarSinEntrada(objetivo.getPrimero())) {
                            primerSinEntrada = objetivo.getPrimero();
                            System.out.println("Sin entrada: " + primerSinEntrada);
                            flag_1=true;
                        }

                    }
                    boolean flag_2=false;
                    String primerSinSalida = "";
                    for (int j = this.delta.size()-1; j>=0 && flag_2==false; j--) {
                        Transicion objetivo = this.delta.get(j);
                        if (this.comprobarSinSalida(objetivo.getSegundo())) {
                            primerSinSalida = objetivo.getSegundo();
                            System.out.println("Sin salida: " + primerSinSalida);
                            flag_2=true;
                        }

                    }

                    Transicion loop = new Transicion(primerSinSalida, "_", primerSinEntrada);
                    this.delta.add(loop);
                    String start = "q" + this.contador;
                    this.estados.add(start);
                    this.contador++;
                    this.delta.add(new Transicion(start, "_", primerSinEntrada));
                    this.delta.add(new Transicion(primerSinSalida, "_", "q"+this.contador));
                    this.delta.add(new Transicion(start, "_", "q" + this.contador));
                    this.estados.add("q" + this.contador);
                    this.contador++;
                }

                if(a.get(i-1).equals("|")) {
                    String sinEntrada = this.sinEntrada().get(0);
                    String sinSalida = this.sinSalida().get(0);
                    Transicion loop = new Transicion(sinSalida, "_", sinEntrada);
                    this.delta.add(loop);
                    String start = "q"+this.contador;
                    this.estados.add(start);
                    this.contador++;
                    this.delta.add(new Transicion(start, "_", sinEntrada));
                    this.delta.add(new Transicion(sinSalida, "_", "q" + this.contador));
                    this.delta.add(new Transicion(start, "_", "q" + this.contador));
                    this.estados.add("q" + this.contador);
                    this.contador++;
                }
                int valorDeLetra = (int) a.get(i-1).charAt(0);

                if (valorDeLetra>=65 && valorDeLetra<=90 || valorDeLetra>=97 && valorDeLetra<= 122) {
                    Transicion previous = this.delta.get(delta.size()-1);
                    this.delta.add(new Transicion(previous.getSegundo(), "_", previous.getPrimero()));
                    String start = "q" + this.contador;
                    this.contador++;
                    this.estados.add(start);
                    this.delta.add(new Transicion(start, "_", previous.getPrimero()));
                    String end = "q" + this.contador;
                    this.contador++;
                    this.estados.add(end);
                    this.delta.add(new Transicion(previous.getSegundo(), "_", end));
                    this.contador++;
                    this.delta.add(new Transicion(start, "_", end));
                }


            }



        }



    }

    public void crear_Transicion(String l) {
        String start = "q" + this.contador;
        this.estados.add(start);
        this.contador++;
        String end = "q" + this.contador;
        this.estados.add(end);
        Transicion transicion_nueva = new Transicion(start, l, end);
        this.delta.add(transicion_nueva);
        this.contador++;

    }


    public ArrayList<String> getEstados() {
        return estados;
    }

    public void setEstados(ArrayList<String> estados) {
        this.estados = estados;
    }

    public ArrayList<String> getSigma() {
        return sigma;
    }

    public void setSigma(ArrayList<String> sigma) {
        this.sigma = sigma;
    }

    public ArrayList<Transicion> getDelta() {
        return delta;
    }

    public void setDelta(ArrayList<Transicion> delta) {
        this.delta = delta;
    }

    public int getContador() {
        return contador;
    }

    public void setContador(int contador) {
        this.contador = contador;
    }
}
