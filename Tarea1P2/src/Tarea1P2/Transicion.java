package Tarea1P2;


public class Transicion {

    private String primero;
    private String segundo;
    private String union;


    public Transicion(String primero, String union, String segundo) {
        this.primero = primero;
        this.segundo = segundo;
        this.union = union;
    }

    public String getPrimero() {
        return primero;
    }

    public void setPrimero(String primero) {
        this.primero = primero;
    }

    public String getSegundo() {
        return segundo;
    }

    public void setSegundo(String segundo) {
        this.segundo = segundo;
    }

    public String getUnion() {
        return union;
    }

    public void setUnion(String union) {
        this.union = union;
    }
}
