package controllers;

import java.util.Queue;
import java.util.concurrent.Semaphore;

import models.*;

public class Escalonador extends Thread {
    private FilaDeProntos prontos;
    private Semaphore semaSch;
    // private Semaphore semaCPU;
    private CPU cpu;
    // private boolean estaSuspensa;
    private boolean semaphoreBlock;

    public Escalonador(FilaDeProntos prontos, CPU cpu, Semaphore semaSch) {
        //this.processos = processos;
        // this.semaCPU = new Semaphore(0);
        this.prontos = prontos;
        // this.semaSch = new Semaphore(1);
        this.semaSch = semaSch;
        this.cpu = cpu;
        // this.estaSuspensa = true;
        this.semaphoreBlock = true;
        // new Thread(this).start();
        start();

    }

    // escalona os processos prontos para a cpu
    @Override
    public void run() {
        // System.out.println("Esc :" + semaSch.availablePermits());
        while (true) {
            // semaSch.release();
            try {
                //System.out.println("run() try Escalonador");

                if (semaphoreBlock) {
                    semaSch.acquire();
                    //.out.println("run() semaSch.acquire Escalonador");
                } else {
                    semaSch.release();
                    //System.out.println("run() Escalonador");
                    rodaProcesso();
                    //cpu.setSemaphoreUnblock();
                    semaSch.acquire();
                } 

            } catch (InterruptedException e) {
                // TODO: handle exception
                e.printStackTrace();
            } finally {

            }

        }

    }


    public void setSemaphoreBlock() { this.semaphoreBlock = true; }

    public void setSemaphoreUnblock() { this.semaphoreBlock = false; }

    public synchronized void rodaProcesso() {
        // manda a head da fila de prontos para cpu, e depois atualiza a cpu do pcb.
        cpu.salvaContexto(prontos.getHead());
        // System.out.println("rodaProcesso() Escalonador");
        // PCB pcb = prontos.getHead();
        // System.out.println(pcb.getArquivo()[1] + "tamanho lista:" + prontos.getSize());
        // boolean rodaPrograma = cpu.rodaProg(arquivo, head.getLimiteSup() - 127,
        // head.getLimiteSup() - 1,head.getLinhaArq());
    }

}