package Tarea1P1;

public class Transicion {
    
    private String inicio;
    private String union;
    private String termino;

    public Transicion(String inicio, String union, String termino){
        this.inicio = inicio;
        this.union = union;
        this.termino = termino;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getUnion() {
        return union;
    }

    public void setUnion(String union) {
        this.union = union;
    }

    public String getTermino() {
        return termino;
    }

    public void setTermino(String termino) {
        this.termino = termino;
    }

    @Override
    public String toString() {
        return "("+this.inicio+","+this.union+","+this.termino+")";
    }
}
