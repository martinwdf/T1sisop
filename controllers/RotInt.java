package controllers;

import java.util.concurrent.Semaphore;

import models.*;


public class RotInt {
        Semaphore semaSch;
        FilaDeProntos prontos;
        GerenteMemoria gerMem;
        Semaphore semaCPU;

        public RotInt(FilaDeProntos prontos, Semaphore semaSch){
            this.semaSch = semaSch;
            this.prontos = prontos;
            //this.gerMem = gerMem;
            //this.semaCPU = semaCPU;
        }
        public void tratamento(){
            prontos.removePronto();

        }
}
