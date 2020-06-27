package controllers;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import models.*;

public class GerenteDeProcesso {
    private Semaphore semaSch;
    private Semaphore semaCPU;

    private int ID;
    private PCB pcb;
    private Queue<PCB> processos;
    private GerenteMemoria grtMemoria;
    private FilaDeProntos prontos;
    static Escalonador esc;
    private RotTimer rot;
    private Memoria memoria;
    private CPU cpu;
    private RotInt rotInt;

    public GerenteDeProcesso() {
        this.ID = -1;
        this.semaSch = new Semaphore(0);
        this.semaCPU = new Semaphore(0);

        this.processos = new LinkedList<PCB>();
        this.prontos = new FilaDeProntos();

        this.rot = new RotTimer(prontos, semaSch);
        this.rotInt = new RotInt(prontos, semaSch);

        this.memoria = new Memoria();
        this.cpu = new CPU(memoria, rot, semaCPU, rotInt);
        esc = new Escalonador(prontos, cpu, semaSch);
        this.grtMemoria = new GerenteMemoria();
    }

    public void addProcesso(String nomeArquivo, String[] arquivo) throws InterruptedException {
        criaProcesso(nomeArquivo, arquivo);
    }

    public int getSize() {
        return processos.size();
    }

    public int criaID() {
        return ID++;
    }

    public void criaProcesso(String nomeArquivo, String[] arquivo) throws InterruptedException {
        if (processos.size() == 0) {
            grtMemoria.primeiroLivre();
            pcb = new PCB(0, nomeArquivo, arquivo);
            pcb.setNomeArquivo(nomeArquivo);
            pcb.setLimiteSup(grtMemoria.alocar(0));
            pcb.setLimiteInf(pcb.getLimiteSup() - 127);
            processos.add(pcb);
            prontos.addPronto(pcb);
            System.out.println("Processo adicionado na particao: " + pcb.getID());
            // pcb.printIdPCB();
        } else {
            if (prontos.getSize()>=8) {
                System.out.println("Fila de Prontos esta cheia!");
                do {
                    // System.out.println("PRINT >=3");
                    this.liberaEscalonador();
                    System.out.println("GETSIZE " + prontos.getSize());
                } while (prontos.getSize() != 0); 
            } else {
                pcb = new PCB(grtMemoria.primeiroLivre(), nomeArquivo, arquivo);
                pcb.setLimiteSup(grtMemoria.alocar(pcb.getID()));
                pcb.setLimiteInf(pcb.getLimiteSup() - 127);
                processos.add(pcb);
                prontos.addPronto(pcb);
                System.out.println("Processo adicionado na particao: " + pcb.getID());
                System.out.println("Tamnaho fila de prontos " + prontos.getSize()); 

            }
        }
        
    }

    public PCB removeProcesso() {
        PCB pcb1 = processos.remove();
        prontos.removePronto();
        grtMemoria.Desaloca(pcb1.getLimiteSup());
        return pcb;
    }

    // nao estao mais sendo utilizadas
    /*
     * public PCB pcb_cpu() { PCB pcb = processos.element(); cpu.setPc(pcb.getPC());
     * cpu.setRegs(pcb.getRegs()); pcb.setEstado(Estado.EXECUTADANDO); return pcb; }
     * 
     * public void cpu_pcb(PCB pcb) { pcb.setLinhaArq(cpu.getI());
     * pcb.setPC(cpu.getPc()); pcb.setRegs(cpu.getRegs());
     * pcb.setEstado(Estado.AGUARDANDO); }
     */
    public void liberaEscalonador() throws InterruptedException {
        if (!prontos.isEmpty()) {

            // System.out.println("run() GP");
            // cpu.printMemoria();
            try {
                prontos.printFilaDeProntos();
                Thread.sleep(1000);
            } catch (Exception e) {
                //TODO: handle exception
            }
            esc.setSemaphoreUnblock();
            this.semaSch.release();
            this.semaCPU.release();

        } else {
            // >>>>> NUNCA CHEGA AQUI >>>>> System.out.println("prontos.isEmpty() GP");
            try {
                Thread.sleep(1000);
                //prontos.printFilaDeProntos();
            } catch (Exception e) {
                //TODO: handle exception
            }
            this.semaSch.acquire();
            // this.semaCPU.acquire();
            
            // semaShell.release();
            // cpu.printMemoria();
            
        }
    }

    public void interruptGP() {

        try {
            esc.interrupt();
            cpu.interrupt();
            System.out.println("Programa finalizado.");
        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao finalizar o programa. Chame um Engenheiro de Software!");
        }
    }
}