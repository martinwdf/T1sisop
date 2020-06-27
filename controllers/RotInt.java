package controllers;

import java.util.concurrent.Semaphore;

import models.*;

public class RotInt {
    Semaphore semaSch;
    static FilaDeProntos prontos;
    GerenteMemoria gerMem;
    Semaphore semaCPU;

    public RotInt(FilaDeProntos prontos, Semaphore semaSch) {
        this.semaSch = semaSch;
        this.prontos = prontos;
        // this.gerMem = gerMem;
        // this.semaCPU = semaCPU;
    }

    public void tratamento() throws InterruptedException  {
        //semaSch.acquire();
        System.out.println("Funcao de tratamento RotInt: processo ID " + prontos.getHead().getID() +" removido da fila de prontos");
        prontos.removePronto();
        GerenteDeProcesso.esc.setSemaphoreUnblock();
        semaSch.release();

    }
}
