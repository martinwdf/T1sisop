package controllers;

import java.util.concurrent.Semaphore;

import models.FilaDeProntos;
import models.PCB;

public class RotTimer {
    //Semaphore semaSch;
    private FilaDeProntos prontos;
    private Escalonador esc;
    

    public RotTimer(FilaDeProntos prontos){
        //this.semaSch = semaSch;
        this.prontos = prontos;
        //this.esc=esc;
    }
    public void tratamento(PCB pcb){
        
        prontos.removePronto();
        prontos.addPronto(pcb);
        //liberar a thread escalonador...
        //semaSch.notifyAll();
    }

}