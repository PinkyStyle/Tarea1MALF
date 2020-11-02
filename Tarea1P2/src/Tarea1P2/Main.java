package Tarea1P2;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	// write your code here
        try {
            File archivo = new File(args[0]);
            Scanner lector = new Scanner(archivo);
            AFD afd = new AFD();

            while (lector.hasNextLine()) {


            }

            lector.close();

            WriteFile salida = new WriteFile(args[1], true);
            salida.escribiraarchivo(afd.print_delta() + " " + afd.print_coneccionesDeCadaEstado());


        }


        catch (Exception e) {
            System.out.println("No se encontro el archivo afnd.txt");
            e.printStackTrace();
        }
    }

    public static class WriteFile {
        private String path;
        private boolean append = false;

        public WriteFile(String path) {
            this.path = path;
        }

        public WriteFile(String path, boolean append) {
            this.path = path;
            this.append = append;
        }

        public void escribiraarchivo(String texto) throws IOException {
            FileWriter write = new FileWriter(this.path, this.append);
            PrintWriter linea = new PrintWriter(write);

            linea.printf("%s" + "%n", texto);

            linea.close();

        }
    }
}
