package controllers;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import models.*;

public class GerenteDeProcesso {
    private int ID;
    private PCB pcb;
    private Queue<PCB> processos;
    private GerenteMemoria grtMemoria;
    private FilaDeProntos prontos;
    private Semaphore semaSch;
    private Semaphore semaCPU;
    private Escalonador esc;
    private RotTimer rot;
    private Memoria memoria;
    private CPU cpu;

    public GerenteDeProcesso() {
        this.ID = -1;
        this.semaSch = new Semaphore(0);
        this.semaCPU = new Semaphore(0);

        this.processos = new LinkedList<PCB>();
        this.prontos = new FilaDeProntos();
        this.rot = new RotTimer(prontos, semaSch);

        this.memoria = new Memoria();
        this.cpu = new CPU(memoria, rot, semaCPU);
        this.grtMemoria = new GerenteMemoria();
        this.esc = new Escalonador(prontos, cpu, semaSch);
    }

    public void addProcesso(String nomeArquivo, String[] arquivo) {
        criaProcesso(nomeArquivo, arquivo);
    }

    public int getSize() {
        return processos.size();
    }

    public int criaID() {
        ID++;
        return ID;
    }

    public void criaProcesso(String nomeArquivo, String[] arquivo) {
        if (processos.size() == 0) {
            grtMemoria.primeiroLivre();
            pcb = new PCB(0, nomeArquivo, arquivo);
            pcb.setNomeArquivo(nomeArquivo);
            pcb.setLimiteSup(grtMemoria.alocar(0));
            pcb.setLimiteInf(pcb.getLimiteSup() - 127);
            processos.add(pcb);
            prontos.addPronto(pcb);
            //pcb.printIdPCB();
        } else {
            pcb = new PCB(grtMemoria.primeiroLivre(), nomeArquivo, arquivo);
            pcb.setLimiteSup(grtMemoria.alocar(pcb.getID()) - 1);
            pcb.setLimiteInf(pcb.getLimiteSup() - 127);
            processos.add(pcb);
            prontos.addPronto(pcb);
        }
    }

    public PCB removeProcesso() {
        PCB pcb1 = processos.remove();
        // prontos.removePronto();
        grtMemoria.Desaloca(pcb1.getLimiteSup());
        return pcb;
    }

    public PCB pcb_cpu() {
        PCB pcb = processos.element();
        cpu.setPc(pcb.getPC());
        cpu.setRegs(pcb.getRegs());
        pcb.setEstado(Estado.EXECUTADANDO);
        return pcb;
    }

    public void cpu_pcb(PCB pcb) {
        pcb.setLinhaArq(cpu.getI());
        pcb.setPC(cpu.getPc());
        pcb.setRegs(cpu.getRegs());
        pcb.setEstado(Estado.AGUARDANDO);
    }

    public void liberaEscalonador() throws InterruptedException {
        if (!prontos.isEmpty()) {

            System.out.println("run() GP");
            esc.setSemaphoreUnblock();;
            this.semaSch.release();
            this.semaCPU.release();

        } else {
            System.out.println("prontos.isEmpty() GP");
            this.semaSch.acquire();
            this.semaCPU.acquire();
            //semaShell.release();
        }
    }
}