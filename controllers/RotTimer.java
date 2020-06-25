package controllers;

import java.util.concurrent.Semaphore;

import models.*;

public class RotTimer {
    //Semaphore semaSch;
    private FilaDeProntos prontos;
    private Semaphore semaSch;
    //private Escalonador esc;
    

    public RotTimer(FilaDeProntos prontos, Semaphore semaSch){
        this.semaSch = semaSch;
        this.prontos = prontos;
        //this.esc=esc;
    }
    public void tratamento(PCB pcb) throws InterruptedException {
        prontos.removePronto();
        prontos.addPronto(pcb);
        semaSch.release();
        //liberar a thread escalonador...
        //semaSch.notifyAll();
    }

}