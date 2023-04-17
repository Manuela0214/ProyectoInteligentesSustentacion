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
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

/**
 *
 * @author Manuela Arcila
 * @author Angie Camelo
 *
 * Proyecto 1. Sistemas Inteligentes
 */
public class BusquedaInformada {

    Map<Nodo, List<Nodo>> nodos;
    String[][] matriz;

    public BusquedaInformada(Map<Nodo, List<Nodo>> nodos, String[][] matriz) {
        this.nodos = nodos;
        this.matriz = matriz;
    }

    /**
     * *
     * Método para realizar recorrido desde un nodo inicial a un nodo objetivo,
     * buscando siempre el mejor nodo adyacente (con la heurística más baja),
     * hasta encontrar la solución
     *
     * @param inicio Nodo en el que inicia el recorrido
     * @param objetivo Nodo al que se desea llegar
     * @param tipo tipo el tipo de heurística a utilizar (Euclidiana, Manhattan)
     * @return Lista de los nodos visi1tados durante el recorrido
     */
    public List<Nodo> recorridoHillClimbing(Nodo inicio, Nodo objetivo, String tipo) {
        List<Nodo> recorrido = new ArrayList<>();
        List<Nodo> visitados = new ArrayList<>();
        Nodo nodoActual = inicio;
        Stack<Nodo> pila = new Stack<>();
        pila.push(nodoActual);

        while (!pila.empty()) {
            nodoActual = pila.peek();
            visitados.add(nodoActual);
            if (nodoActual.equals(objetivo)) {
                // Encontramos la solución, salir del ciclo
                break;
            }
            List<Nodo> adyacentes = obtenerNodosAdyacentes(nodoActual);
            Nodo mejorAdyacente = null;
            nodoActual.calcularHeuristica(objetivo, tipo);

            for (Nodo adyacente : adyacentes) {
                adyacente.calcularHeuristica(objetivo, tipo);
                if ((mejorAdyacente == null || adyacente.getHeuristica() < mejorAdyacente.getHeuristica())
                        && noEstaEnLista(visitados, adyacente)) {
                    mejorAdyacente = adyacente;
                }
                if (adyacente.equals(objetivo)) {
                    // Encontramos la solución, salir del ciclo
                    break;
                }
            }
            if (mejorAdyacente == null) {
                // No hay adyacentes, retroceder
                pila.pop();
                continue;
            }
            if ((mejorAdyacente.getHeuristica() >= nodoActual.getHeuristica()) && nodoActual != inicio) {
                // El mejor adyacente no mejora la heurística, retroceder
                pila.pop();
                continue;
            }

            pila.push(mejorAdyacente);
            mejorAdyacente.getHeuristica();
        }
        // Convertir la pila en una lista y agregar el nodo objetivo
        while (!pila.empty()) {
            recorrido.add(pila.pop());
        }
        Collections.reverse(recorrido);

        return recorrido;
    }

    /**
     * *
     * Método para realizar recorrido desde un nodo inicial a un nodo objetivo,
     * buscando siempre el mejor nodo adyacente (con la heurística más baja),
     * hasta encontrar el camino más corto
     *
     * @param inicio Nodo en el que inicia el recorrido
     * @param objetivo Nodo al que se desea llegar
     * @param tipo tipo el tipo de heurística a utilizar (Euclidiana, Manhattan)
     * @return Lista de los nodos visitados durante el recorrido
     */
    public List<Nodo> recorridoBeamSearch(Nodo inicio, Nodo objetivo, String tipo) {
        int beta = calcularBeta();
        List<Nodo> recorrido = new ArrayList<>();
        List<Nodo> nivelActual = new ArrayList<>();
        nivelActual.add(inicio);

        while (!nivelActual.isEmpty() && !recorrido.contains(objetivo)) {
            List<Nodo> proximoNivel = new ArrayList<>();

            for (Nodo nodoActual : nivelActual) {
                nodoActual.calcularHeuristica(objetivo, tipo);
                List<Nodo> adyacentes = obtenerNodosAdyacentes(nodoActual);

                for (Nodo adyacente : adyacentes) {
                    adyacente.calcularHeuristica(objetivo, tipo);
                    if (!recorrido.contains(adyacente) && !nivelActual.contains(adyacente)) {
                        proximoNivel.add(adyacente);
                    }
                }
            }

            Collections.sort(proximoNivel, new Comparator<Nodo>() {
                @Override
                public int compare(Nodo nodo1, Nodo nodo2) {
                    return Double.compare(nodo1.getHeuristica(), nodo2.getHeuristica());
                }
            });

            nivelActual = new ArrayList<>(proximoNivel.subList(0, Math.min(beta, proximoNivel.size())));
            recorrido.addAll(nivelActual);
        }

        return recorrido;
    }

    /***
     * Método para realizar recorrido desde un nodo inicial a un nodo objetivo,
     * buscando el camino más optimo entre estos, se considera su costo actual y 
     * la heuristica como una estimación de los costos futuros.
     *
     * @param inicio Nodo en el que inicia el recorrido
     * @param objetivo Nodo al que se desea llegar
     * @param heuristica tipo el tipo de heurística a utilizar (Euclidiana, Manhattan)
     * @return Método reconstruir camino
     */
    public List<Nodo> recorridoAEstrella(Nodo inicio, Nodo objetivo, String heuristica) {
        PriorityQueue<Nodo> colaPrioridad = new PriorityQueue<>(Comparator.comparingDouble(nodo -> nodo.getCosto()));
        colaPrioridad.add(inicio);
        Map<Nodo, Nodo> padres = new HashMap<>();
        Map<Nodo, Double> gScores = new HashMap<>();
        gScores.put(inicio, 0.0);
        Map<Nodo, Double> fScores = new HashMap<>();
        fScores.put(inicio, inicio.calcularHeuristic(inicio, objetivo, heuristica));
        while (!colaPrioridad.isEmpty()) {
            Nodo actual = colaPrioridad.poll();
            if (actual.getX() == objetivo.getX() && actual.getY() == objetivo.getY()) {
                return reconstruirCamino(padres, actual);
            }
            List<Nodo> hijos = obtenerNodosAdyacentes(actual);
            for (Nodo hijo : hijos) {
                double gScore = gScores.get(actual) + 1;
                if (!gScores.containsKey(hijo) || gScore < gScores.get(hijo)) {
                    gScores.put(hijo, gScore);
                    double fScore = gScore + hijo.calcularHeuristic(inicio, objetivo, heuristica);
                    fScores.put(hijo, fScore);
                    hijo.setCosto((int) fScore);
                    if (!colaPrioridad.contains(hijo)) {
                        colaPrioridad.add(hijo);
                    }
                    padres.put(hijo, actual);
                }
            }
        }
        return null;
    }
    
    
    /***
     * Método para construir un camino óptimo desde un nodo inicio a un nodo fin,
     * los ordena
     * @param padres Mapa de los padres de un nodo
     * @param actual Nodo que estoy analizando
     * @return una lista con el camino ordenado
     */
     private List<Nodo> reconstruirCamino(Map<Nodo, Nodo> padres, Nodo actual) {
        List<Nodo> camino = new ArrayList<>();
        camino.add(actual);
        while (padres.containsKey(actual)) {
            actual = padres.get(actual);
            camino.add(0, actual);
        }
        return camino;
    }
     

    public boolean sonAdyacentes(Nodo nodo1, Nodo nodo2) {
        List<Nodo> adyacentes = obtenerNodosAdyacentes(nodo1);
        return adyacentes.contains(nodo2);
    }

    private boolean posicionValida(int x, int y) {
        return (x >= 0 && x < matriz.length) && (y >= 0 && x < matriz[0].length);
    }

    /**
     * Mostrar el nodo que pertenece a dicha posicion dada
     *
     * @return el nodo en esa posición
     */
    private Nodo obtenerNodo(int x, int y) {
        for (Map.Entry<Nodo, List<Nodo>> entry : nodos.entrySet()) {
            Nodo key = entry.getKey();
            if (key.getX() == x && key.getY() == y) {
                return key;
            }
        }
        return null;
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
        return null;
    }

    /**
     * Método para calcular el beta (máximo número de hijos) para el método del
     * recorrido Beam Search
     */
    public int calcularBeta() {
        int beta = 0;
        for (Map.Entry<Nodo, List<Nodo>> entry : nodos.entrySet()) {
            List<Nodo> value = entry.getValue();
            if (value.size() > beta) {
                beta = value.size();
            }
        }
        return beta;
    }

    public boolean noEstaEnLista(List<Nodo> recorrido, Nodo adyacente) {
        if (!recorrido.stream().anyMatch(nodo -> nodo.getX() == adyacente.getX()
                && nodo.getY() == adyacente.getY())) {
            return true;
        }
        return false;
    }

   
   

//    public List<EtiquetaAEstrella> recorridoAEstrella(Nodo inicio, Nodo fin) {
//        EtiquetaAEstrella etiqueta;
//        List<EtiquetaAEstrella> visitados = new ArrayList<>();    
//        if(inicio.getX() == fin.getX() && inicio.getY() == fin.getY()){
//            return visitados;
//        }
//        //List<AEstrella> recorridoAEstrella = new ArrayList<>();    
//        etiqueta = new EtiquetaAEstrella(inicio, 0, distanciaManhattan(inicio, fin));
//        visitados.add(etiqueta);
//        
//        evaluarMovimientosAEstrella(etiqueta.getId().getX(), etiqueta.getId().getY(),fin,etiqueta);        
//        if(!visitados.contains(nodoMenorFuncionCosto())){
//            visitados.add(nodoMenorFuncionCosto());
//        }
////        if(recorridoAEstrella(nodoMenorFuncionCosto().getId(), fin) == null){
////            vecinos.remove(nodoMenorFuncionCosto());
////            visitados.remove(nodoMenorFuncionCosto());
////            recorridoAEstrella(nodoMenorFuncionCosto().getId(), fin);
////        }
//        return null;
//    }
//
//    private List<EtiquetaAEstrella> evaluarMovimientosAEstrella(int x, int y, Nodo fin, EtiquetaAEstrella actual) {
//        EtiquetaAEstrella etiqueta;
//        Nodo nodoSiguiente;
//        vecinos.clear();
//        //movimientos ortogonales
//        if (posicionValida(x - 1, y) && matriz[x - 1][y].equals("C")) {
//            nodoSiguiente = obtenerNodo(x - 1, y);
//            etiqueta = new EtiquetaAEstrella(nodoSiguiente, actual.getPeso()+10, distanciaManhattan(nodoSiguiente, fin));  
//            vecinos.add(etiqueta);
//            etiqueta.agregarNodo(etiqueta);
//            recorridoAEstrella(nodoSiguiente, fin);  ////------posible linea a eliminar
//        }
//        if (posicionValida(x + 1, y) && matriz[x + 1][y].equals("C")) {
//            nodoSiguiente = obtenerNodo(x + 1, y);
//            etiqueta = new EtiquetaAEstrella(nodoSiguiente, actual.getPeso()+10, distanciaManhattan(nodoSiguiente, fin));
//            vecinos.add(etiqueta);
//            etiqueta.agregarNodo(etiqueta);
//            recorridoAEstrella(nodoSiguiente, fin);
//        }
//        if (posicionValida(x, y - 1) && matriz[x][y - 1].equals("C")) {
//            nodoSiguiente = obtenerNodo(x, y - 1);
//            etiqueta = new EtiquetaAEstrella(nodoSiguiente, actual.getPeso()+10, distanciaManhattan(nodoSiguiente, fin));
//            vecinos.add(etiqueta);
//            etiqueta.agregarNodo(etiqueta);
//            recorridoAEstrella(nodoSiguiente, fin);
//        }
//        if (posicionValida(x, y + 1) && y + 1 < matriz[x].length && matriz[x][y + 1].equals("C")) {
//            nodoSiguiente = obtenerNodo(x, y + 1);
//            etiqueta = new EtiquetaAEstrella(nodoSiguiente, actual.getPeso()+10, distanciaManhattan(nodoSiguiente, fin));
//            vecinos.add(etiqueta);
//            etiqueta.agregarNodo(etiqueta);
//            recorridoAEstrella(nodoSiguiente, fin);
//        }
//        //movimientos diagonales
//        if (posicionValida(x - 1, y + 1) && matriz[x - 1][y + 1].equals("C")) {
//            nodoSiguiente = obtenerNodo(x - 1, y + 1);
//            etiqueta = new EtiquetaAEstrella(nodoSiguiente, actual.getPeso()+14, distanciaManhattan(nodoSiguiente, fin));
//            vecinos.add(etiqueta);
//            etiqueta.agregarNodo(etiqueta);
//            recorridoAEstrella(nodoSiguiente, fin);
//        }
//        if (posicionValida(x + 1, y + 1) && matriz[x + 1][y + 1].equals("C")) {
//            nodoSiguiente = obtenerNodo(x + 1, y + 1);
//            etiqueta = new EtiquetaAEstrella(nodoSiguiente, actual.getPeso()+14, distanciaManhattan(nodoSiguiente, fin));
//            vecinos.add(etiqueta);
//            etiqueta.agregarNodo(etiqueta);
//            recorridoAEstrella(nodoSiguiente, fin);
//        }
//        if (posicionValida(x + 1, y - 1) && matriz[x + 1][y - 1].equals("C")) {
//            nodoSiguiente = obtenerNodo(x + 1, y - 1);
//            etiqueta = new EtiquetaAEstrella(nodoSiguiente, actual.getPeso()+14, distanciaManhattan(nodoSiguiente, fin));
//            vecinos.add(etiqueta);
//            etiqueta.agregarNodo(etiqueta);
//            recorridoAEstrella(nodoSiguiente, fin);
//        }
//        if (posicionValida(x - 1, y - 1) && matriz[x - 1][y - 1].equals("C")) {
//            nodoSiguiente = obtenerNodo(x - 1, y - 1);
//            etiqueta = new EtiquetaAEstrella(nodoSiguiente, actual.getPeso()+14, distanciaManhattan(nodoSiguiente, fin));
//            vecinos.add(etiqueta);
//            etiqueta.agregarNodo(etiqueta);
//            recorridoAEstrella(nodoSiguiente, fin);
//        }
//        return vecinos;
//    }
//    
}
