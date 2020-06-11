package controllers;
import models.*;
import java.util.LinkedList;
import java.util.Queue;


public class GerenteDeProcesso {
    private String[]  arquivo;
    private int ID;
    private Ler l;
    private CPU cpu;
    private Queue<PCB> processos;
    private PCB pcb;
    private GerenteMemoria grtMemoria;

    public GerenteDeProcesso(String[] str, int qtdOProgramas) {
        processos = new LinkedList<PCB>();
        cpu = new CPU();
        grtMemoria = new GerenteMemoria();
        this.ID = -1;

        // adiciona a quantidade de processos de acordo com o numero de programas
      /*  for (int i = 0; i < qtdProgramas; i++) {
            l = new Ler(s[i]);
            arquivo = l.criarVetor();
            this.criaProcesso(arquivo);
        }
        */
    }
    public void addProcesso(String nomeArquivo){
        criaProcesso(nomeArquivo);
    }
    public int getSize() {return processos.size();}

    public int criaID() {
        ID++;
        return ID;
    }

    public void criaProcesso(String arquivo) {
        if(processos.size()==0){
            grtMemoria.primeiroLivre();
            pcb = new PCB(0, arquivo);
            pcb.setNomeArquivo(arquivo);
            pcb.setLimiteSup(grtMemoria.alocar(0));
            pcb.setLimiteInf(pcb.getLimiteSup() - 127);
            processos.add(pcb);
        }
        else{ 
        
        pcb = new PCB(grtMemoria.primeiroLivre(), arquivo);
        pcb.setLimiteSup(grtMemoria.alocar(pcb.getID()) - 1);
        pcb.setLimiteInf(pcb.getLimiteSup() - 127);
        processos.add(pcb);
        }
    }

    public PCB removeProcesso() {
        PCB pcb1 = processos.remove();
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

    public void controlaProcessos() {
        while (processos.size() != 0) {
            //// vai passar a cpu do primero pcb
            PCB head = this.pcb_cpu();
            // pega o arquivo que o processo leu, para mandar para a cpu
            l = new Ler(head.getNomeArquivo());
            arquivo = l.criarVetor();
            // roda a cpu que estava no 1 pcb da fila, se retornar true eh pq o processo
            // acabou

           // System.out.println("VALOR DO ID:" + head.getID());

            System.out.println("VALOR DO ID:" + head.getID());
            if (cpu.rodaProg(arquivo, head.getLimiteSup() - 127, head.getLimiteSup() - 1, head.getLinhaArq()) == true) {
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

        }
        cpu.printMemoria();
    }
}