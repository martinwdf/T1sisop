package controllers;

import java.util.concurrent.Semaphore;

import models.*;

public class Escalonador implements Runnable {
    private FilaDeProntos prontos;
    private Semaphore semaSch;
    //private Semaphore semaCPU;
    private CPU cpu;
    private boolean estaSuspensa;
    private boolean semaphoreBlock;
    

    public Escalonador(FilaDeProntos prontos, CPU cpu) {
        this.prontos = prontos;
        this.semaSch = new Semaphore(1);
        //this.semaCPU = new Semaphore(0);
        this.cpu = cpu;
        this.estaSuspensa = true;
        this.semaphoreBlock = true;
        new Thread(this).start();
    }

    // escalona os processos prontos para a cpu
    @Override
    public void run() {
        //System.out.println("Esc :" + semaSch.availablePermits());
        while (true) {
            // semaSch.release();
            try {
                System.out.println("run() try Escalonador");

                if (semaphoreBlock) {
                    semaSch.acquire();
                    System.out.println("run() semaSch.acquire Escalonador");
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
            semaSch.release();
            System.out.println("run() Escalonador");
            // manda o pcb pra cpu
            cpu.resume();
            cpu.setSemaphoreBlock();
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

    public void setSemaphoreBlock(){
        this.semaphoreBlock = false;
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