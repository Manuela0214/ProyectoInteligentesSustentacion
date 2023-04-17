/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestorArchivos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author Manuela Arcila
 * @author Angie Camelo
 * 
 * Proyecto 1. Sistemas Inteligentes
 */
public class Archivo {

    /**
     * Método pata leer el archivo de texto para guardarlo como String
     * @param nombre ruta con el nombre del archivo
     */
    public String[][] leer(String nombre) {
        try {
            File f;
            FileReader lectorArchivo;
            f = new File(nombre);
            lectorArchivo = new FileReader(f);
            BufferedReader br = new BufferedReader(lectorArchivo);
            String linea = "";
            String aux = "";
            int numFilas = 0;
            int numColumnas = 0;

            while (true) {
                aux = br.readLine();
                if (aux != null) {
                    linea = linea + aux;
                } else {
                    break;
                }
            }
            String[][] matriz = crearMatriz(linea);
            
            br.close();
            
            lectorArchivo.close();
            return matriz;
        } catch (IOException e) {
            System.out.println("Error:" + e.getMessage());
        }
        return null;
    }

    /**
     * Metodo que pasa el texto leido a una matriz
     * @param texto el texto cotenido en el archivo leido
     */
    private String[][] crearMatriz(String texto) {

        String[] arr = texto.split(" ");
        int filas = arr.length;
        int colum = arr[0].split(",").length;
        String matriz[][] = new String[filas][colum];

        for (int i = 0; i < filas; i++) {
            String[] row = arr[i].split(",");
            for (int j = 0; j < colum; j++) {
                matriz[i][j] = row[j];
            }
        }
        return matriz;
    }
}
