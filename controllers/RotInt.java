package controllers;

import java.util.concurrent.Semaphore;

import models.FilaDeProntos;
import models.PCB;

public class RotInt {
        Semaphore semaSch;
        FilaDeProntos prontos;
        GerenteMemoria gerMem;
        Semaphore semaCPU;

        public RotInt(Semaphore semaSch, FilaDeProntos prontos, GerenteMemoria gerMem, Semaphore semaCPU){
            this.semaSch = semaSch;
            this.prontos = prontos;
            this.gerMem = gerMem;
            this.semaCPU = semaCPU;
        }
        public PCB removeFila(PCB pcb){
            prontos.removePronto();
            return pcb;
        }
        public void stopCPU(){
            try {
                semaCPU.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
}