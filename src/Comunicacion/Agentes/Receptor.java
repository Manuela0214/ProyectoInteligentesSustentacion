/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Comunicacion.Agentes;

import Comunicacion.Comportamientos.ComportamientoSimpleEmisor;
import Comunicacion.Comportamientos.ComportamientoSimpleReceptor;
import jade.core.Agent;

/**
 *
 * @author manu-
 */
public class Receptor extends Agent{
    public void setup(){
        addBehaviour(new ComportamientoSimpleReceptor(this));
    }
}
