package Tarea1P1;

import java.util.ArrayList;
import java.util.Stack;

public class Postfijo {

    private ArrayList<String> salida;
    private Stack<String> operadores;    

    public Postfijo(){
        this.salida = new ArrayList<>();
        this.operadores = new Stack<>();                
    }

    public int getPrecedencia(String s){
        int valor = 5;
        if(s.equals("~") || s.equals("_")){
            valor = 5;
        }
        if(s.equals("*")){
            valor = 4;
        }
        if(s.equals(".")){
            valor = 3;
        }
        if(s.equals("|")){
            valor = 2;
        }
        if(s.equals("(")){
            valor = 1;
        }
        return valor;
    }

    public void InfijaAPostfija(String cadena)
    {
        String[] array = cadena.split("");

        for(int i = 0; i < array.length; i++){
            char c = array[i].charAt(0);
            int ascii = (int)c;

            // 48-57 (0123456789) 65-90 ABCDE...Z 97-122 abcde...z
            if(c >= 48 && c <= 57 || c >= 65 && c <= 90 || c >= 97 && c <= 122)
            {
                salida.add(array[i]);
            }
            else{
                if(array[i].equals("(") || operadores.size() == 0){
                    operadores.push(array[i]);
                }
                else{
                    if(array[i].equals(")")){
                        while(!operadores.peek().equals("(")){
                            salida.add(operadores.pop());
                        }
                        operadores.pop();
                    }
                    else{
                        if(this.getPrecedencia(operadores.peek()) >= this.getPrecedencia(array[i])){
                            salida.add(operadores.pop());
                            operadores.push(array[i]);
                        }
                        else if(this.getPrecedencia(operadores.peek()) < this.getPrecedencia(array[i])){
                            operadores.push(array[i]);
                        }
                    }
                }
            }
        }
        if(this.operadores.size() != 0){
            while(this.operadores.size() != 0){
                this.salida.add(this.operadores.pop());
            }
        }        
    }

    public ArrayList<String> getSalida() {
        return salida;
    }

    public void mostrarER(){
        for (int i = 0; i < this.salida.size(); i++) {
            System.out.print(this.salida.get(i));
        }
    }
}
