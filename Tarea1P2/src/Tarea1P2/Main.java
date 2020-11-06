package Tarea1P2;

import java.util.Scanner;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        //Para ejecutar en la powershell: cmd /c 'java -jar Tarea1P2.jar < prueba.txt'

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

            scanner.close();


            AFD a = new AFD(transiciones, sigma, estados);

            System.out.println("AFD M:");
            System.out.println(a.getEstadosAFND());
            System.out.println(a.getSigmaAFND());
            System.out.println("delta:");

            a.print_delta();
            System.out.println("s=" + a.getEstadoInicialAFD());
            System.out.println("F="+ a.getEstadosFinalesAFD());




    }

  
}
