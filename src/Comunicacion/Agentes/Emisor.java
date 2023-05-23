/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Comunicacion.Agentes;

import Comunicacion.Comportamientos.ComportamientoSimpleEmisor;
import jade.core.Agent;
import jade.Boot;

/**
 *
 * @author manu-
 */
public class Emisor extends Agent{
    public void setup(){
        addBehaviour(new ComportamientoSimpleEmisor(this));
    }
}
