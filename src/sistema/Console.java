/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema;

/**
 *
 * @author Roberto
 */
public class Console {

    /**
     * Resetea el solor de la salida.
     */
    public static final String RESET = "\u001B[0m";
    /**
     * Color Negro, se coloca al inicio de la cadena.
     */
    public static final String BLACK = "\u001B[30m";
    /**
     * Color Rojo, se coloca al inicio de la cadena.
     */
    public static final String RED = "\u001B[31m";
    /**
     * Color Verde, se coloca al inicio de la cadena.
     */
    public static final String GREEN = "\u001B[32m";
    /**
     * Color Amarillo, se coloca al inicio de la cadena.
     */
    public static final String YELLOW = "\u001B[33m";
    /**
     * Color Azul, se coloca al inicio de la cadena.
     */
    public static final String BLUE = "\u001B[34m";
    /**
     * Color Purpura, se coloca al inicio de la cadena.
     */
    public static final String PURPLE = "\u001B[35m";
    /**
     * Color Cyan, se coloca al inicio de la cadena.
     */
    public static final String CYAN = "\u001B[36m";
    /**
     * Color Blanco, se coloca al inicio de la cadena.
     */
    public static final String WHITE = "\u001B[37m";

    
    /**
     * Imprime una salida de consola con el color especificado.
     * @param color
     * @param out 
     */
    public static void println(String color, String out) {
        System.out.println(color + out);
        System.out.print(RESET);
    }
    
    /**
     * Imprime una salida de consola con el color especificado.
     * @param color
     * @param out 
     */
    public static void print(String color, String out) {
        System.out.print(color + out);
        System.out.print(RESET);
    }
}
