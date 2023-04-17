/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matrizbotones;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 *
 * @author Manuela Arcila
 * @author Angie Camelo
 *
 * Proyecto 1. Sistemas Inteligentes
 */
public class BusquedaNOInformada {

    Map<Nodo, List<Nodo>> nodos;
    String[][] matriz;
    int nivel;

    public BusquedaNOInformada(Map<Nodo, List<Nodo>> nodos, String[][] matriz) {
        this.nodos = nodos;
        this.matriz = matriz;
    }

    /***
     * Método para realizar un recorrido BFS en el grafo desde el nodo de inicio 
     * hasta el nodo objetivo para encontrar el camino más corto 
     * @param inicio Nodo en el que inicia el recorrido
     * @param objetivo Nodo al que se desea llegar
     * @return  Lista de nodos que representa el recorrido en anchura desde
     * el nodo de inicio hasta el nodo objetivo.
     */
    public List<Nodo> recorridoEnAnchura(Nodo inicio, Nodo objetivo) {
        List<Nodo> recorrido = new ArrayList<>();
        List<Nodo> nodosVisitados = new ArrayList<>();
        Queue<Nodo> cola = new LinkedList<>();
        

        cola.add(inicio);
        nodosVisitados.add(inicio);

        while (!cola.isEmpty()) {
            recorrido.add(cola.peek());
            Nodo nodoActual = cola.poll();
            List<Nodo> adyacentes = obtenerNodosAdyacentes(nodoActual);
            for (Nodo adyacente : adyacentes) {
                if (noEstaEnLista(recorrido, adyacente)) {
                    cola.add(adyacente);
                    nodosVisitados.add(adyacente);
                }

            }
            this.nivel += 1 ;
            adyacentes.clear();
        }
        return recorrido;
    }
    
    public int calcularNivel(Nodo inicio, Nodo objetivo) {
        List<Nodo> recorrido = new ArrayList<>();
        List<Nodo> nodosVisitados = new ArrayList<>();
        Queue<Nodo> cola = new LinkedList<>();

        cola.add(inicio);
        nodosVisitados.add(inicio);
        int nivel = 0;

        while (!cola.isEmpty()) {
            int size = cola.size();
            for (int i = 0; i < size; i++) {
                Nodo nodoActual = cola.poll();
                recorrido.add(nodoActual);
                if (nodoActual.getX() == objetivo.getX() && nodoActual.getY() == objetivo.getY()) {
                    return nivel;
                }
                List<Nodo> adyacentes = obtenerNodosAdyacentes(nodoActual);
                for (Nodo adyacente : adyacentes) {
                    if (noEstaEnLista(nodosVisitados, adyacente)) {
                        cola.add(adyacente);
                        nodosVisitados.add(adyacente);
                    }
                }
                adyacentes.clear();
            }
            nivel++;
        }
        return -1; 
    }
    
    /***
     * Método para realizar un recorrido DFS en el grafo desde el nodo de inicio 
     * hasta el nodo objetivo para garantizar que encuentra un camino si existe 
     * @param inicio Nodo en el que inicia el recorrido
     * @param objetivo Nodo al que se desea llegar
     * @return  Lista de nodos que representa el recorrido en anchura desde
     * el nodo de inicio hasta el nodo objetivo.
     */
    public List<Nodo> recorridoEnProfundidad(Nodo inicio, Nodo objetivo) {
        List<Nodo> nodosVisitados = new ArrayList<>();
        Stack<Nodo> pila = new Stack();
        pila.push(inicio);
        nodosVisitados.add(inicio);

        while (!pila.isEmpty()) {
            Nodo nodoActual = pila.peek();
            if (pila.peek().getX() == objetivo.getX() && pila.peek().getY() == objetivo.getY()) {
                break;
            }
            List<Nodo> hijos = obtenerNodosAdyacentes(nodoActual);
            int contador = hijos.size();
            for (Nodo nodo : hijos) {
                if (noEstaEnLista(nodosVisitados, nodo)) {
                    pila.push(nodo);
                    nodosVisitados.add(nodo);
                    break;
                }
                contador -= 1;
                if (contador == 0) {
                    pila.pop();
                }
            }
        }
        return nodosVisitados;
    }
    
    
    public void recorridoEnProfundidadRecursivo(Nodo nodoActual, List<Nodo> nodosVisitados) {
        nodosVisitados.add(nodoActual);
        System.out.print("("+nodoActual.getX()+","+nodoActual.getY()+") ");

        List<Nodo> adyacentes = obtenerNodosAdyacentes(nodoActual);
        for (Nodo nodo : adyacentes) {
            if (noEstaEnLista(nodosVisitados, nodo)) {
                recorridoEnProfundidadRecursivo(nodo, nodosVisitados);
            }
        }
    }
    
    /***
     * Método para realizar un recorrido costo uniforme en el grafo desde el nodo de inicio 
     * hasta el nodo objetivo para garantizar que encuentra un camino mas corto 
     * @param inicio Nodo en el que inicia el recorrido
     * @param objetivo Nodo al que se desea llegar
     * @param heuristica tipo el tipo de heurística a utilizar (Euclidiana, Manhattan)
     * @return Lista de los nodos visitados durante el recorrido
     */
    public List<Nodo> recorridoCostoUniforme(Nodo inicio, Nodo objetivo, String heuristica) {
        PriorityQueue<Nodo> colaPrioridad = new PriorityQueue<>(Comparator.comparingDouble(nodo -> nodo.getCosto()));
        colaPrioridad.add(inicio);
        List<Nodo> visitados = new ArrayList<>();
        Map<Nodo, Nodo> padres = new HashMap<>();
        padres.put(inicio, null);
        while (!colaPrioridad.isEmpty()) {
            Nodo actual = colaPrioridad.poll();
            if (actual.getX() == objetivo.getX() && actual.getY()==objetivo.getY()) {
                visitados.add(actual);
                return visitados;
            }
            visitados.add(actual);
            List<Nodo> hijos = obtenerNodosAdyacentes(actual);
            for (Nodo hijo : hijos) {
                if (!visitados.contains(hijo) && !colaPrioridad.contains(hijo)) {
                    hijo.setCosto((int) (hijo.calcularHeuristic(inicio, objetivo, heuristica) + 1)); 
                    colaPrioridad.add(hijo);
                    padres.put(hijo, actual);
                }
            }
        }
        return null;
    }
    

    public boolean noEstaEnLista(List<Nodo> recorrido, Nodo adyacente) {
        if (!recorrido.stream().anyMatch(nodo -> nodo.getX() == adyacente.getX()
                && nodo.getY() == adyacente.getY())) {
            return true;
        }
        return false;
    }

    private List<Nodo> obtenerNodosAdyacentes(Nodo nodo) {
        List<Nodo> adyacentes = new ArrayList<>();

        for (Map.Entry<Nodo, List<Nodo>> entry : nodos.entrySet()) {
            Nodo padre = entry.getKey();
            List<Nodo> hijos = entry.getValue();
            if (padre.getX() == nodo.getX() && padre.getY() == nodo.getY()) {
                return hijos;
            }
        }
        return adyacentes;
    }

}
