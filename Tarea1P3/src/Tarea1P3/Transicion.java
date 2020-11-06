package Tarea1P3;

public class Transicion {
    
    private String estado1;
    private String entrada;
    private String estado2;

    public Transicion(String estado1, String entrada, String estado2) {
        this.estado1 = estado1;
        this.entrada = entrada;
        this.estado2 = estado2;
    }
    
    public String getEstado1() {
        return estado1;
    }

    public void setEstado1(String estado1) {
        this.estado1 = estado1;
    }

    public String getEntrada() {
        return entrada;
    }

    public void setEntrada(String entrada) {
        this.entrada = entrada;
    }

    public String getEstado2() {
        return estado2;
    }

    public void setEstado2(String estado2) {
        this.estado2 = estado2;
    }
    
    @Override
    public String toString() {
        return "("+this.estado1+","+this.entrada+","+this.estado2+")";
    }
}
