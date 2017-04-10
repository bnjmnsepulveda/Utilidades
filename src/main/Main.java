/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import sistema.Console;

/**
 *
 * @author Roberto
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       //System.out.println((char)27 + "[34mEjemplo de texto azul y fondo amarillo");
       //System.out.println("\033[32mEste texto es Verde");
       Console.println(Console.GREEN, "hola con verde");
        System.out.println("hola ");
        System.out.println("bye :D");
    }
    
}
