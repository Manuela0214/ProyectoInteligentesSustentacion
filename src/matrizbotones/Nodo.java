/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matrizbotones;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Manuela Arcila
 * @author Angie Camelo
 * 
 * Proyecto 1. Sistemas Inteligentes
 */
public class Nodo {
    
    private boolean visitado;
    private List<Nodo> hijos = new ArrayList<>();
    Nodo nodoPadre;
    private double heuristica;
    private int costo;    
    int x;
    int y;
    
    public Nodo(int x, int y){
        this.x = x;
        this.y = y;
        this.visitado = false;
    }
    
    public void agregarHijo(Nodo nodo){
        hijos.add(nodo);
    }

    public Nodo getNodoAnterior() {
        return nodoPadre;
    }

    public void setNodoAnterior(Nodo nodoPadre) {
        this.nodoPadre = nodoPadre;
    }

    public int getCosto() {
        return costo;
    }

    public void setCosto(int costo) {
        this.costo = costo;
    }

    public List<Nodo> getHijos() {
        return hijos;
    }    

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isVisitado() {
        return visitado;
    }

    public void setVisitado(boolean visitado) {
        this.visitado = visitado;
    }
    
    public double getHeuristica() {
        return heuristica;
    }

    public void setHeuristica(double heuristica) {
        this.heuristica = heuristica;
    }

    public void calcularHeuristica(Nodo objetivo, String tipo) {
        double heuristica;
        
        if(tipo.contains("E")){
            heuristica = Math.sqrt(Math.pow(this.x - objetivo.getX(), 2) + Math.pow(this.y - objetivo.getY(), 2));            
            setHeuristica(heuristica);
        }
        else if(tipo.contains("M")){
            heuristica = Math.abs(objetivo.getX() - this.getX()) + Math.abs(objetivo.getY() - this.getY());
            setHeuristica(heuristica);
        }
    }  
        
    public double calcularHeuristic(Nodo inicio, Nodo objetivo, String tipo) {
        double heuristica = 0;
        if(tipo.contains("E")){
            heuristica = Math.sqrt(Math.pow(inicio.getX() - objetivo.getX(), 2) + Math.pow(inicio.getY() - objetivo.getY(), 2));            
        }
        else if(tipo.contains("M")){
            heuristica = Math.abs(objetivo.getX() - inicio.getX()) + Math.abs(objetivo.getY() - inicio.getY());
        }
        return heuristica;
    }  
    
    
//    public double distanciaEuclidiana(Nodo inicio, Nodo fin) {
//        double heuristica = Math.sqrt(Math.pow((fin.getY() - inicio.getY()), 2) + Math.pow((fin.getX() - inicio.getX()), 2));
//        return heuristica;
//    }
    
    public double calcularCostoTotal() {
        return this.getCosto() + this.getHeuristica();
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Nodo)) {
            return false;
        }
        Nodo nodo = (Nodo) o;
        return x == nodo.x && y == nodo.y;
    }
    
}
