package Tarea1P1;

public class Main {

    public static void main(String[] args) {
    // write your code here
    Postfijo pf = new Postfijo();
    AFND afnd = new AFND();
    System.out.println(pf.getSalida());
    afnd.crearAFND(pf.getSalida());    
    afnd.imprimirAFND();
    }
}
