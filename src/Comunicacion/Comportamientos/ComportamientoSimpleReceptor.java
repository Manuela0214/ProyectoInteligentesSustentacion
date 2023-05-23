/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Comunicacion.Comportamientos;

import Comunicacion.Agentes.Receptor;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;

/**
 *
 * @author manu-
 */
public class ComportamientoSimpleReceptor extends SimpleBehaviour{
    Agent miAgente;
    public ComportamientoSimpleReceptor(Agent miAgente) {
        this.miAgente = miAgente;
    }

    @Override
    public void action() {
        ACLMessage mensajeRecibido = this.miAgente.blockingReceive();
        if(mensajeRecibido != null){
            System.out.println("Recibiendo mensaje "+mensajeRecibido.getContent());
            ACLMessage respuesta = mensajeRecibido.createReply();
            respuesta.setContent(" esta es una replica de emisor");
            System.out.println("Enviando replica desde receptor");
            this.miAgente.send(respuesta);
        }else{
            System.out.println("Receptor no me ha llegado mensaje");
        }
    }

    @Override
    public boolean done() {
        System.out.println("ya terminé");
        return true;
    }
    
}
