package Tarea1P1;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
    // write your code here
    Scanner scanner = new Scanner(System.in);
    String expresionRegular = scanner.nextLine();
    scanner.close();
    Postfijo pf = new Postfijo();
    pf.InfijaAPostfija(expresionRegular);
    
    AFND afnd = new AFND();
    System.out.println(pf.getSalida());
    afnd.crearAFND(pf.getSalida());    
    afnd.mostrarAFND();    
    }
}
