package controllers;

import java.util.concurrent.Semaphore;

import models.*;

public class Escalonador extends Thread {
    private FilaDeProntos prontos;
    private Semaphore semaSch;
    private Semaphore semaCPU;
    private CPU cpu;
    private boolean actived;

    public Escalonador(FilaDeProntos prontos, Semaphore semaSch, Semaphore semaCPU, CPU cpu) {
        this.prontos = prontos;
        this.semaSch = semaSch;
        this.semaCPU = semaCPU;
        this.cpu = cpu;
        this.actived = false;
        start();
    }

    // escalona os processos prontos para a cpu
    @Override
    public void run() {
        while (actived) {
            // try {
            // semaSch.wait();
            // } catch (InterruptedException e) {
            // e.printStackTrace();
            // }
            // manda o pcb pra cpu
            System.out.println("run() Escalonador");
            rodaProcesso();
            // semaCPU.notifyAll();
            // semaSch.release();
        }
    }

    public void rodaProcesso() {
        // manda a head da fila de prontos para cpu, e depois atualiza a cpu do pcb.
        System.out.println("rodaProcesso() Escalonador");
        PCB pcb = prontos.getHead();
        cpu.salvaContexto(pcb);
        // boolean rodaPrograma = cpu.rodaProg(arquivo, head.getLimiteSup() - 127,
        // head.getLimiteSup() - 1,head.getLinhaArq());

    }

    public synchronized void setRun(boolean actived) {
        this.actived = actived;
        cpu.setRun(true);
    }
}