package controllers;

import java.util.concurrent.Semaphore;

import models.*;

public class Escalonador extends Thread {
    private FilaDeProntos prontos;
    private Semaphore escSem;

    public Escalonador(FilaDeProntos prontos) {
        this.prontos = prontos;
        escSem = new Semaphore(1);
    }

    // escalona os processos prontos para a cpu
    @Override
    public void run() {
        if (prontos.isEmpty()) {
            System.out.println("Fila de Prontos esta vazia");
            try {
                escSem.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        while (true) {
            try {
                escSem.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
                rodaProcesso();
        }
    }
    public void rodaProcesso(){
        //manda a head da fila de prontos para cpu, e depois atualiza a cpu do pcb.
    }
}