package controllers;

import java.util.concurrent.Semaphore;

import models.FilaDeProntos;
import models.PCB;

public class RotTimer {
    Semaphore semaSch;
    FilaDeProntos prontos;
    

    public RotTimer(Semaphore semaSch, FilaDeProntos prontos){
        this.semaSch = semaSch;
        this.prontos = prontos;
    }
    public void tratamento(PCB pcb){
        prontos.addPronto(pcb);
        
        semaSch.release();
    }

}