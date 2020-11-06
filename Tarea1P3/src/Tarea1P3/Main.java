package Tarea1P3;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        //Para ejecutar en la powershell: cmd /c 'java -jar Tarea1P3.jar < prueba.txt'

        Scanner scanner = new Scanner(System.in);
        String nombre = scanner.nextLine();
        String estados = scanner.nextLine();
        String sigma = scanner.nextLine();

        ArrayList<String> transiciones = new ArrayList<>();
        String trans = "";
        if(scanner.nextLine().equals("delta:")){
            trans = scanner.nextLine();
            while(trans.startsWith("(")){
                transiciones.add(trans);
                trans = scanner.nextLine();
            }                        
        }
        String estadoInicio = trans;
        String estadosFinales = scanner.nextLine();
        
        scanner.close();

        MinimizadorAFD min = new MinimizadorAFD(nombre, estados, sigma, transiciones, estadoInicio, estadosFinales);
        min.procesarDatos();
        //min.test();
        min.minimizar();
    }
}
