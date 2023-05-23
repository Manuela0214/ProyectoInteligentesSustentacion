/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Comunicacion.Comportamientos;

import Comunicacion.Agentes.Emisor;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;

/**
 *
 * @author manu-
 */
public class ComportamientoSimpleEmisor extends SimpleBehaviour{
    Agent miAgente;
    public ComportamientoSimpleEmisor(Agent miAgente) {
        this.miAgente = miAgente;
    }    

    @Override
    public void action() {
        ACLMessage mensaje = new ACLMessage(ACLMessage.INFORM);
        mensaje.addReceiver(new AID("receptor",AID.ISLOCALNAME));
        mensaje.setContent("Today it's raining");
        this.miAgente.send(mensaje);
        ACLMessage mensajeRespuesta = this.miAgente.blockingReceive();
        if(mensajeRespuesta != null){
            System.out.println("Acabo de recibir "+mensajeRespuesta.getContent());
        }
    }

    @Override
    public boolean done() {
        System.out.println("ya terminé");
        return true;
    }
}
