package controllers;

import models.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class GerenteDeProcesso {
    private String[] arquivo;
    private int ID;
    private Ler l;
    private CPU cpu;
    private Queue<PCB> processos;
    private PCB pcb;
    private GerenteMemoria grtMemoria;
    private FilaDeProntos prontos;
    private Memoria memoria;
    private Semaphore semaSch;
    private Escalonador esc;

    public GerenteDeProcesso(Semaphore semaSch, CPU cpu, Escalonador esc) {
        processos = new LinkedList<PCB>();
        memoria = new Memoria();
       // this.cpu = new CPU(memoria);
        grtMemoria = new GerenteMemoria();
        this.ID = -1;
        prontos = new FilaDeProntos();
        this.semaSch = semaSch;
        this.esc = esc;
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

   /* public void controlaProcessos() {

        //// vai passar a cpu do primero pcb
        PCB head = this.pcb_cpu();
        // pega o arquivo que o processo leu, para mandar para a cpu
        l = new Ler(head.getNomeArquivo());
        arquivo = l.criarVetor();
        // roda a cpu que estava no 1 pcb da fila, se retornar true eh pq o processo
        // acabou

        // System.out.println("VALOR DO ID:" + head.getID());

        System.out.println("VALOR DO ID:" + head.getID());

        //boolean rodaPrograma = cpu.rodaProg(arquivo, head.getLimiteSup() - 127, head.getLimiteSup() - 1,
          //      head.getLinhaArq());

        if (rodaPrograma == true) {
            head.setEstado(Estado.FINALIZADO);
            processos.remove(head);
        } else {
            this.cpu_pcb(head);
            // caso em que o processo ainda nao foi finalizado.
            head.setEstado(Estado.AGUARDANDO);
            // adiciona o processo recem rodado para o final da fila
            processos.add(head);
            // remove o processo do comeco da fila
            processos.remove();
        }
        cpu.printMemoria();
    }
    */

    public void liberaEscalonador() {

        
        if (prontos.isEmpty()) {
            try {
                System.out.println("WAIT, prontos isEmpty");

                semaSch.acquire();

            } catch (InterruptedException e) {
                System.out.println("Interttupet Exception");
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        else{
            System.out.println("run() GP");
            esc.resume();
            //System.out.println("GP: " +semaSch.availablePermits());
            //semaSch.release();
            //System.out.println(semaSch.availablePermits());

            //cpu.setRun(true);
        }
        // System.out.println("run() GP");
        // esc.resume();
        //System.out.println("run() GP 2");

        //semaSch.release();
    }
}