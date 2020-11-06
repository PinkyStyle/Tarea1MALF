package Tarea1P1;

public class Main {

    public static void main(String[] args) {
    // write your code here
    Postfijo pf = new Postfijo();
    AFND afnd = new AFND();
    afnd.crearAFND(pf.getSalida());    
    afnd.imprimirAFND();
    }
}
