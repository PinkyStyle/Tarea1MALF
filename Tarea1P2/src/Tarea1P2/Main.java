package Tarea1P2;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.lang.reflect.Array;
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
            System.out.println(a.getEstadosAFD());
            System.out.println(a.getSigmaAFD());
            System.out.println("delta:");
            a.print_delta();
            a.print_coneccionesDeCadaEstado();
            System.out.println(estadoInicio);
            System.out.println(estadosFinales);




    }

  
}
