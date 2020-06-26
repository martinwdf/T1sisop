package controllers;

import java.util.concurrent.Semaphore;
import models.Label;
import models.Memoria;
import models.PCB;

public class CPU extends Thread {

    private Semaphore semaCPU;
    private PCB pcb;
    private RotTimer rotTimer;
    private RotInt rotInt;
    private Timer timerCPU;

    private int limiteDeInstrucoes;
    private boolean semaphoreBlock;
    private String[] s;
    private int pc;
    private int i;
    private double[] regs;
    private Label[] memoria;

    public CPU(Memoria memoria, RotTimer rotTimer, Semaphore semaCPU, RotInt rotInt) {

        this.timerCPU = new Timer();
        this.memoria = memoria.getMemoria();
        this.semaCPU = semaCPU;

        this.limiteDeInstrucoes = 0;
        this.semaphoreBlock = true;

        this.regs = new double[8];
        this.pc = 0;
        this.i = 0;

        this.rotTimer = rotTimer;
        this.rotInt = rotInt;
        start();

    }

    ////////////////////////////////////////////////////////////
    @Override
    public void run() {
        while (true) {
            try {
                // System.out.println("run() try CPU");
                System.out.println("limiteDeInstrucoes: " + limiteDeInstrucoes);

                if (semaphoreBlock) {
                    semaCPU.acquire();
                    // System.out.println("run() semaCPU.acquire CPU");
                } else {
                    semaCPU.release();
                    // System.out.println("run() CPU");
                    // rodaProg(getPCB());
                    // boolean b = rodaProg(getPCB());
                    boolean b = false;

                    //////////////////////////////////////////////////////////////////////////////////////////////////////////////

                    String[] arquivo = pcb.getArquivo();
                    int limiteSup = pcb.getLimiteSup();
                    int limiteInf = pcb.getLimiteInf();
                    int linhaArq = pcb.getLinhaArq();
                    int numero = 0;
                    i = limiteInf + linhaArq;
                    System.out.println("VALOR DE linha do arquivo: " + linhaArq);

                    do {

                        // if (s[0] == "TRAP") {
                        //     // rotina de tratamento de IO");
                        // }

                        if (timerCPU.contaTempo(limiteDeInstrucoes)) {
                            timerCPU();
                        }

                        s = arquivo[linhaArq].split(" ");
                        // System.out.println(arquivo[i]);
                        //////////// intruction ADD
                        switch (s[0]) {

                            case "ADD":
                                memoria[i] = new Label("ADD", s[1], s[2]);
                                regs[memoria[i].findRD()] = regs[memoria[i].findRD()] + regs[memoria[i].findRS()];
                                // System.out.println("ADD | " + " REGS: " + memoria[i].findRD() + " " +
                                // regs[memoria[i].findRD()]);
                                limiteDeInstrucoes++;
                                break;

                            //////////// intruction sub
                            case "SUB":
                                memoria[i] = new Label("SUB", s[1], s[2]);
                                regs[memoria[i].findRD()] = regs[memoria[i].findRD()] - regs[memoria[i].findRS()];
                                // System.out.println("SUB | " + " REGS: " + memoria[i].findRD() + " " +
                                // regs[memoria[i].findRD()]);
                                limiteDeInstrucoes++;
                                break;

                            //////////// intruction mult
                            case "MULT":
                                memoria[i] = new Label("MULT", s[1], s[2]);
                                regs[memoria[i].findRD()] = regs[memoria[i].findRD()] * regs[memoria[i].findRS()];
                                // System.out.println("MULT | " + " REGS: " + memoria[i].findRD() + " " +
                                // regs[memoria[i].findRD()]);
                                limiteDeInstrucoes++;
                                break;

                            //////////// intruction ADDi
                            case "ADDI":
                                memoria[i] = new Label("ADDI", s[1], Double.parseDouble(s[2]));
                                regs[memoria[i].findRD()] = regs[memoria[i].findRD()] + Double.parseDouble(s[2]);
                                // System.out.println("ADDI | " + " REGS: " + memoria[i].findRD() + " " +
                                // regs[memoria[i].findRD()]);
                                limiteDeInstrucoes++;
                                break;

                            //////////// intruction subi
                            case "SUBI":
                                memoria[i] = new Label("SUBI", s[1], Double.parseDouble(s[2]));
                                regs[memoria[i].findRD()] = regs[memoria[i].findRD()] - Double.parseDouble(s[2]);
                                // System.out.println("SUBI | " + " REGS: " + memoria[i].findRD() + " " +
                                // regs[memoria[i].findRD()]);
                                limiteDeInstrucoes++;
                                break;

                            //////////// intruction ldi
                            case "LDI":
                                memoria[i] = new Label("LDI", s[1], Double.parseDouble(s[2]));
                                regs[memoria[i].findRD()] = Double.parseDouble(s[2]);
                                // System.out.println("LDI | " + " REGS: " + memoria[i].findRD() + " " +
                                // regs[memoria[i].findRD()]);
                                limiteDeInstrucoes++;
                                break;

                            //////////// intruction jmp
                            case "JMP":
                                memoria[i] = new Label("JMP", Double.parseDouble(s[1]));
                                linhaArq = (int) regs[memoria[i].findRD()] - 1;
                                i = Integer.parseInt(s[1]) - 1 + limiteInf;
                                // System.out.println(memoria[i].print() + " Linha: " + i);
                                limiteDeInstrucoes++;
                                break;

                            //////////// intruction jmpi
                            case "JMPI":
                                memoria[i] = new Label("JMPI", s[1], 0.0);
                                linhaArq = (int) regs[memoria[i].findRD()] - 1;
                                i = (int) regs[memoria[i].findRD()] - 1 + limiteInf;
                                // System.out.println(memoria[i].print() + " Linha: " + i);
                                limiteDeInstrucoes++;
                                break;

                            //////////// intruction jmpig
                            case "JMPIG":
                                memoria[i] = new Label("JMPIG", s[1], s[2]);
                                if (regs[memoria[i].findRS()] > 0) {
                                    linhaArq = (int) regs[memoria[i].findRD()] - 1;
                                    i = (int) regs[memoria[i].findRD()] - 1 + limiteInf;
                                }
                                // System.out.println(memoria[i].print() + " Linha: " + i);
                                limiteDeInstrucoes++;
                                break;

                            //////////// intruction jmpil
                            case "JMPIL":
                                memoria[i] = new Label("JMPIL", s[1], s[2]);
                                if (regs[memoria[i].findRS()] < 0) {
                                    linhaArq = (int) regs[memoria[i].findRD()] - 1;
                                    i = (int) regs[memoria[i].findRD()] - 1 + limiteInf;
                                }
                                // System.out.println(memoria[i].print() + " Linha: " + i);
                                limiteDeInstrucoes++;
                                break;

                            //////////// intruction jmpie
                            case "JMPIE":
                                memoria[i] = new Label("JMPIE", s[1], s[2]);
                                if (regs[memoria[i].findRS()] == 0) {
                                    linhaArq = (int) regs[memoria[i].findRD()] - 1;
                                    i = (int) regs[memoria[i].findRD()] - 1 + limiteInf;
                                }
                                // System.out.println(memoria[i].print() + " Linha: " + i);
                                limiteDeInstrucoes++;
                                break;

                            //////////// intruction std
                            case "STD":
                                s[1] = s[1].replace("[", "");
                                s[1] = s[1].replace("]", "");
                                s[1] = s[1].replace(",", "");
                                int j = Integer.parseInt(s[1]) + limiteInf;
                                memoria[i] = new Label("STD", s[1], s[2]);
                                memoria[j] = new Label("DADO", regs[memoria[i].findRS()]);

                                // System.out.println(memoria[i].print() + " Linha: " + i);

                                System.out.println(memoria[i].print() + " Linha: " + i);
                                limiteDeInstrucoes++;
                                break;

                            //////////// intruction ldd
                            case "LDD":
                                s[2] = s[2].replace("[", "");
                                s[2] = s[2].replace("]", "");
                                memoria[i] = new Label("LDD", s[1], s[2]);
                                regs[memoria[i].findRD()] = memoria[Integer.parseInt(s[2]) + limiteInf].getParametro();

                                // System.out.println(regs[memoria[i].findRD()]);

                                System.out.println(regs[memoria[i].findRD()]);
                                limiteDeInstrucoes++;
                                break;

                            //////////// intruction stdx
                            case "STX":
                                s[1] = s[1].replace("[", "");
                                s[1] = s[1].replace("]", "");
                                s[1] = s[1].replace(",", "");
                                memoria[i] = new Label("STX", s[1], s[2]);
                                int l = (int) regs[memoria[i].findRD()] + limiteInf;
                                memoria[l] = new Label("DADO", regs[memoria[i].findRS()]);
                                System.out.println(memoria[i].print() + " Linha: " + i);
                                limiteDeInstrucoes++;
                                break;

                            //////////// intruction ldx rd <- [rs]
                            case "LDX":
                                s[2] = s[2].replace("[", "");
                                s[2] = s[2].replace("]", "");
                                memoria[i] = new Label("LDX", s[1], s[2]);
                                int k = (int) regs[memoria[i].findRS()] + limiteInf;
                                regs[memoria[i].findRD()] = memoria[k].getParametro();
                                // System.out.println("LDX | " + " REGS: " + memoria[i].findRD() + " " +
                                // regs[memoria[i].findRD()]);

                                System.out.println(
                                        "LDX | " + " REGS: " + memoria[i].findRD() + " " + regs[memoria[i].findRD()]);
                                limiteDeInstrucoes++;
                                break;

                            //////////// intruction stop
                            case "STOP":
                                System.out.println("STOP");
                                b = true;

                            default:
                                throw new IllegalArgumentException(
                                        "Não foi possível encontrar o VALOR de linha do arquivo: " + linhaArq);
                        }

                        i++;
                        numero++;
                        linhaArq++;
                        setPc(linhaArq);

                    } while ((i >= limiteInf && i < limiteSup) && numero < 15);
                    i -= limiteInf;

                    pcb.setLinhaArq(linhaArq);
                    setPCB(pcb);
                    b = false;
                    // return actived;

                    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
                    if (b) {
                        System.out.println("if(b) CPU");
                        semaCPU.acquire();
                        setSemaphoreBlock();
                        rotInt.tratamento();
                        // setSemaphoreBlock();
                    } else {
                        System.out.println("rot.tratamento(getPCB())  CPU");
                        semaCPU.acquire();
                        setSemaphoreBlock();
                        rotTimer.tratamento(getPCB());
                    }

                }

            } catch (InterruptedException e) {
                // TODO: handle exception
                e.printStackTrace();

            }
        }

    }

    public synchronized void salvaContexto(PCB pcb) {
        // System.out.println("salvaContexto(PCB pcb)");
        setRegs(pcb.getRegs());
        setPc(pcb.getPC());
        setPCB(pcb);
        setSemaphoreUnblock();
        // this.pcb = pcb;
        // this.regs = regs;
        // this.pc = pc;
    }

    public void timerCPU() {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> TIMER");
        try {
            // semaCPU.acquire();
            // setSemaphoreBlock();
            limiteDeInstrucoes = 0;
            // salvar contexto do PCB e envia para o final da fila de prontos
            // libera para chamar outro programa
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public void setSemaphoreBlock() { this.semaphoreBlock = true; }

    public void setSemaphoreUnblock() { this.semaphoreBlock = false; }

    ////////////////////////////////////////////////////////////
    public synchronized int getPc() { return pc; }

    public synchronized void setPc(int pc) { this.pc = pc; }
    
    public synchronized Label[] getMemoria() { return memoria; }

    public synchronized void setMemoria(Label[] memoria) { this.memoria = memoria; }

    public synchronized PCB getPCB() { return pcb; }

    public synchronized void setPCB(PCB pcb) { this.pcb = pcb; }

    public synchronized double[] getRegs() { return regs; }

    public synchronized void setRegs(double[] regs) { this.regs = regs; }

    public synchronized int getI() { return i; }


    public synchronized void desalocaMemoria(int n) { memoria[n] = null; }

    public synchronized boolean acabouArquivo(String[] arquivo, int tamanho) {
        if (arquivo.length >= tamanho) {
            return true;
        }
        return false;
    }

    public synchronized boolean rodaProg(PCB pcb) {
        String[] arquivo = pcb.getArquivo();
        int limiteSup = pcb.getLimiteSup();
        int limiteInf = pcb.getLimiteInf();
        int linhaArq = pcb.getLinhaArq();
        int numero = 0;
        i = limiteInf + linhaArq;
        System.out.println("VALOR DE linha do arquivo: " + linhaArq);
        try {
            do {

                s = arquivo[linhaArq].split(" ");
                // System.out.println(arquivo[i]);
                //////////// intruction ADD
                if (s[0].equals("ADD")) {
                    memoria[i] = new Label("ADD", s[1], s[2]);
                    regs[memoria[i].findRD()] = regs[memoria[i].findRD()] + regs[memoria[i].findRS()];
                    // System.out.println("ADD | " + " REGS: " + memoria[i].findRD() + " " +
                    // regs[memoria[i].findRD()]);
                    limiteDeInstrucoes++;
                    // System.out.println("limiteDeInstrucoes: " + limiteDeInstrucoes);
                }
                //////////// intruction sub
                else if (s[0].equals("SUB")) {
                    memoria[i] = new Label("SUB", s[1], s[2]);
                    regs[memoria[i].findRD()] = regs[memoria[i].findRD()] - regs[memoria[i].findRS()];
                    // System.out.println("SUB | " + " REGS: " + memoria[i].findRD() + " " +
                    // regs[memoria[i].findRD()]);
                    limiteDeInstrucoes++;
                    // System.out.println("limiteDeInstrucoes: " + limiteDeInstrucoes);

                }
                //////////// intruction mult
                else if (s[0].equals("MULT")) {
                    memoria[i] = new Label("MULT", s[1], s[2]);
                    regs[memoria[i].findRD()] = regs[memoria[i].findRD()] * regs[memoria[i].findRS()];
                    // System.out.println("MULT | " + " REGS: " + memoria[i].findRD() + " " +
                    // regs[memoria[i].findRD()]);
                    limiteDeInstrucoes++;
                    // System.out.println("limiteDeInstrucoes: " + limiteDeInstrucoes);

                }
                //////////// intruction ADDi
                else if (s[0].equals("ADDI")) {
                    memoria[i] = new Label("ADDI", s[1], Double.parseDouble(s[2]));
                    regs[memoria[i].findRD()] = regs[memoria[i].findRD()] + Double.parseDouble(s[2]);
                    // System.out.println("ADDI | " + " REGS: " + memoria[i].findRD() + " " +
                    // regs[memoria[i].findRD()]);
                    limiteDeInstrucoes++;
                }
                //////////// intruction subi
                else if (s[0].equals("SUBI")) {
                    memoria[i] = new Label("SUBI", s[1], Double.parseDouble(s[2]));
                    regs[memoria[i].findRD()] = regs[memoria[i].findRD()] - Double.parseDouble(s[2]);
                    // System.out.println("SUBI | " + " REGS: " + memoria[i].findRD() + " " +
                    // regs[memoria[i].findRD()]);
                    limiteDeInstrucoes++;
                    // System.out.println("limiteDeInstrucoes: " + limiteDeInstrucoes);

                }
                //////////// intruction ldi
                else if (s[0].equals("LDI")) {
                    memoria[i] = new Label("LDI", s[1], Double.parseDouble(s[2]));
                    regs[memoria[i].findRD()] = Double.parseDouble(s[2]);
                    // System.out.println("LDI | " + " REGS: " + memoria[i].findRD() + " " +
                    // regs[memoria[i].findRD()]);
                    limiteDeInstrucoes++;
                    // System.out.println("limiteDeInstrucoes: " + limiteDeInstrucoes);

                }
                //////////// intruction jmp
                else if (s[0].equals("JMP")) {
                    memoria[i] = new Label("JMP", Double.parseDouble(s[1]));
                    linhaArq = (int) regs[memoria[i].findRD()] - 1;
                    i = Integer.parseInt(s[1]) - 1 + limiteInf;
                    // System.out.println(memoria[i].print() + " Linha: " + i);
                    limiteDeInstrucoes++;
                    // System.out.println("limiteDeInstrucoes: " + limiteDeInstrucoes);

                }
                //////////// intruction jmpi
                else if (s[0].equals("JMPI")) {
                    memoria[i] = new Label("JMPI", s[1], 0.0);
                    linhaArq = (int) regs[memoria[i].findRD()] - 1;
                    i = (int) regs[memoria[i].findRD()] - 1 + limiteInf;
                    // System.out.println(memoria[i].print() + " Linha: " + i);
                    limiteDeInstrucoes++;
                    // System.out.println("limiteDeInstrucoes: " + limiteDeInstrucoes);

                }
                //////////// intruction jmpig
                else if (s[0].equals("JMPIG")) {
                    memoria[i] = new Label("JMPIG", s[1], s[2]);
                    if (regs[memoria[i].findRS()] > 0) {
                        linhaArq = (int) regs[memoria[i].findRD()] - 1;
                        i = (int) regs[memoria[i].findRD()] - 1 + limiteInf;
                    }
                    // System.out.println(memoria[i].print() + " Linha: " + i);
                    limiteDeInstrucoes++;
                    // System.out.println("limiteDeInstrucoes: " + limiteDeInstrucoes);

                }
                //////////// intruction jmpil
                else if (s[0].equals("JMPIL")) {
                    memoria[i] = new Label("JMPIL", s[1], s[2]);
                    if (regs[memoria[i].findRS()] < 0) {
                        linhaArq = (int) regs[memoria[i].findRD()] - 1;
                        i = (int) regs[memoria[i].findRD()] - 1 + limiteInf;
                    }
                    // System.out.println(memoria[i].print() + " Linha: " + i);
                    limiteDeInstrucoes++;
                    // System.out.println("limiteDeInstrucoes: " + limiteDeInstrucoes);

                }
                //////////// intruction jmpie
                else if (s[0].equals("JMPIE")) {
                    memoria[i] = new Label("JMPIE", s[1], s[2]);
                    if (regs[memoria[i].findRS()] == 0) {
                        linhaArq = (int) regs[memoria[i].findRD()] - 1;
                        i = (int) regs[memoria[i].findRD()] - 1 + limiteInf;
                    }
                    // System.out.println(memoria[i].print() + " Linha: " + i);
                    limiteDeInstrucoes++;
                    // System.out.println("limiteDeInstrucoes: " + limiteDeInstrucoes);

                }
                //////////// intruction std
                else if (s[0].equals("STD")) {
                    s[1] = s[1].replace("[", "");
                    s[1] = s[1].replace("]", "");
                    s[1] = s[1].replace(",", "");
                    int j = Integer.parseInt(s[1]) + limiteInf;
                    memoria[i] = new Label("STD", s[1], s[2]);
                    memoria[j] = new Label("DADO", regs[memoria[i].findRS()]);

                    // System.out.println(memoria[i].print() + " Linha: " + i);

                    System.out.println(memoria[i].print() + " Linha: " + i);
                    limiteDeInstrucoes++;
                    // System.out.println("limiteDeInstrucoes: " + limiteDeInstrucoes);

                }
                //////////// intruction ldd
                else if (s[0].equals("LDD")) {
                    s[2] = s[2].replace("[", "");
                    s[2] = s[2].replace("]", "");
                    memoria[i] = new Label("LDD", s[1], s[2]);
                    regs[memoria[i].findRD()] = memoria[Integer.parseInt(s[2]) + limiteInf].getParametro();

                    // System.out.println(regs[memoria[i].findRD()]);

                    System.out.println(regs[memoria[i].findRD()]);
                    limiteDeInstrucoes++;
                    // System.out.println("limiteDeInstrucoes: " + limiteDeInstrucoes);

                }
                //////////// intruction stdx
                else if (s[0].equals("STX")) {
                    s[1] = s[1].replace("[", "");
                    s[1] = s[1].replace("]", "");
                    s[1] = s[1].replace(",", "");
                    memoria[i] = new Label("STX", s[1], s[2]);
                    int j = (int) regs[memoria[i].findRD()] + limiteInf;
                    memoria[j] = new Label("DADO", regs[memoria[i].findRS()]);
                    System.out.println(memoria[i].print() + " Linha: " + i);
                    limiteDeInstrucoes++;
                    // System.out.println("limiteDeInstrucoes: " + limiteDeInstrucoes);

                }
                //////////// intruction ldx rd <- [rs]
                else if (s[0].equals("LDX")) {
                    s[2] = s[2].replace("[", "");
                    s[2] = s[2].replace("]", "");
                    memoria[i] = new Label("LDX", s[1], s[2]);
                    int j = (int) regs[memoria[i].findRS()] + limiteInf;
                    regs[memoria[i].findRD()] = memoria[j].getParametro();
                    // System.out.println("LDX | " + " REGS: " + memoria[i].findRD() + " " +
                    // regs[memoria[i].findRD()]);

                    System.out.println("LDX | " + " REGS: " + memoria[i].findRD() + " " + regs[memoria[i].findRD()]);
                    limiteDeInstrucoes++;
                    // System.out.println("limiteDeInstrucoes: " + limiteDeInstrucoes);

                }
                //////////// intruction stop
                else {
                    System.out.println("STOP ");
                    return true;
                }

                i++;
                numero++;
                linhaArq++;
                setPc(linhaArq);

            } while ((i >= limiteInf && i < limiteSup) && numero < 15);
            i -= limiteInf;
        } catch (ArithmeticException a) {
            System.out.println("no rodaProg() DivZero = 1");
            // this.flag = 1;
            // a.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException b) {
            System.out.println("no rodaProg() EndFormaLimite = 2");
            // this.flag = 2;
            // b.printStackTrace();
        } catch (RuntimeException c) {
            System.out.println("no rodaProg() STOP = 3");
            // this.flag = 3;
            // c.printStackTrace();
        }

        pcb.setLinhaArq(linhaArq);
        setPCB(pcb);
        return false;
        // return actived;

    }

    public void printMemoria() {
        String s = "";

        for (int i = 0; i < 300; i++) {

            if (i < 10){ s =  "00"; } 
            else if (i < 100) { s = "0"; } 
            else { s = ""; }

            System.out.println("["+ s + i + "]" + " ");
            if (memoria[i] != null) {
                System.out.println("["+ s + i + "]" + " " + memoria[i].print());
            }
        }
    }
}