/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matrizbotones;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jdk.nashorn.internal.ir.ForNode;

/**
 *
 * @author Manuela Arcila
 * @author Angie Camelo
 * 
 * Proyecto 1. Sistemas Inteligentes
 */
public class Matriz {

    //ArrayList<ArrayList<Integer>> listaAdyacencia;
    Map<Nodo, List<Nodo>> nodos = new HashMap<>();
    private List<Nodo> adyacentes = new ArrayList<>();
    Nodo nodo;

    String[][] matriz;

    public Matriz(String[][] matriz) {
        this.matriz = matriz;
    }
    
    void crearHashMap(Nodo objetivo, String tipo) {
        int contador = 0;
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                if (matriz[i][j].equals("C")) {
                    Nodo nodopapa = new Nodo(i, contador);
                    evaluarMovimientos(i, j, nodopapa);
                    nodos.put(nodopapa, nodopapa.getHijos());
                    // Calcular la heurística del nodo actual
                    nodopapa.calcularHeuristica(objetivo, tipo);
                }
                contador++;
            }
            contador = 0;
        }
    }

    public Map<Nodo, List<Nodo>> getNodos(Nodo objetivo, String tipo) {
        crearHashMap(objetivo,tipo);
        return nodos;
    }   
    

    /**
     * Método para evaluar los posibles movimientos que se pueden realizar
     * en la matriz para asi asociarlos como hijos del nodo de la posición
     * actual
     * @param x posicion x de la matriz
     * @param y posicion y de la matriz
     * @param padre el nodo de la posición actual
     */
    private void evaluarMovimientos(int x, int y, Nodo padre) {
        //abajo
        if (posicionValida(x + 1, y) && matriz[x + 1][y].equals("C")) {
            Nodo nodohijo = new Nodo(x + 1, y);
            padre.agregarHijo(nodohijo);
        }        
        //izquiera
        if (posicionValida(x, y - 1) && matriz[x][y - 1].equals("C")) {
            Nodo nodohijo = new Nodo(x, y - 1);
            padre.agregarHijo(nodohijo);
        }
        //arriba
        if (posicionValida(x - 1, y) && matriz[x - 1][y].equals("C")) {
            Nodo nodohijo = new Nodo(x - 1, y);
            padre.agregarHijo(nodohijo);
        }        
        //derecha
        if (posicionValida(x, y + 1) && y + 1 < matriz[x].length && 
                matriz[x][y + 1].equals("C")) {
            Nodo nodohijo = new Nodo(x, y + 1);
            padre.agregarHijo(nodohijo);
        }

        nodos.put(padre, padre.getHijos());
    }

    public void imprimirNodos() {
        for (Map.Entry<Nodo, List<Nodo>> entry : nodos.entrySet()) {
            Nodo key = entry.getKey();
            List<Nodo> value = entry.getValue();
            System.out.println("padre: (" + key.getX() + "," + key.getY() + ")");
            System.out.print("hijos: ");

            for (Nodo nodo : value) {
                System.out.print("(" + nodo.getX() + "," + nodo.getY() + ")");
            }
            System.out.println();
        }
    }

    private boolean posicionValida(int x, int y) {
        return (x >= 0 && x < matriz.length) && (y >= 0 && x < matriz[0].length);
    }
    
    

}
