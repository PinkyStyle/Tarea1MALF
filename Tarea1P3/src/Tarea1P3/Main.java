package Tarea1P3;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        //Para ejecutar en la powershell: cmd /c 'java -jar Tarea1P3.jar < prueba.txt'

        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
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
        
        

        // System.out.println(estados);
        // System.out.println(sigma);
        // for(int i = 0; i < transiciones.size(); i++){
        //     System.out.println(transiciones.get(i));
        // }
        // System.out.println(estadoInicio);
        // System.out.println(estadosFinales);
        scanner.close();

        MinimizadorAFD min = new MinimizadorAFD(estados, sigma, transiciones, estadoInicio, estadosFinales);
        min.procesarDatos();
        //min.test();
        min.minimizar();
    }
}
