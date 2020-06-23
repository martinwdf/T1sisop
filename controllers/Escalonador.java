package controllers;

import java.util.concurrent.Semaphore;

import models.*;

public class Escalonador implements Runnable {
    private FilaDeProntos prontos;
    private Semaphore semaSch;
    private Semaphore semaCPU;
    private CPU cpu;
    private boolean estaSuspensa;

    public Escalonador(FilaDeProntos prontos, Semaphore semaSch, Semaphore semaCPU, CPU cpu) {
        this.prontos = prontos;
        this.semaSch = semaSch;
        this.semaCPU = semaCPU;
        this.cpu = cpu;
        this.estaSuspensa = true;
        new Thread(this).start();
    }

    // escalona os processos prontos para a cpu
    @Override
    public void run() {
        //System.out.println("Esc :" + semaSch.availablePermits());
        while (true) {
            // semaSch.release();
            try {
                if (prontos.isEmpty()) {
                    //semaCPU.acquire();
                    System.out.println("run() semaCPU.acquire Escalonador");
                }
                synchronized (this) {
                    while (estaSuspensa) {
                        wait();
                    }
                }
            } catch (InterruptedException e) {
                // TODO: handle exception
                e.printStackTrace();
            }
            System.out.println("run() Escalonador");
            // manda o pcb pra cpu
            cpu.resume();
            //semaCPU.release();
            rodaProcesso();
        }

    }

    public void suspend() {
        this.estaSuspensa = true;
    }

    public synchronized void resume() {
        this.estaSuspensa = false;
        notifyAll();
    }

    public void rodaProcesso() {
        // manda a head da fila de prontos para cpu, e depois atualiza a cpu do pcb.
        System.out.println("rodaProcesso() Escalonador");
        PCB pcb = prontos.getHead();
        cpu.salvaContexto(pcb);
        // boolean rodaPrograma = cpu.rodaProg(arquivo, head.getLimiteSup() - 127,
        // head.getLimiteSup() - 1,head.getLinhaArq());

    }

}