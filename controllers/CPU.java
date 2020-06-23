package controllers;

import java.util.concurrent.Semaphore;
import models.Label;
import models.Memoria;
import models.PCB;

public class CPU extends Thread {
    private Semaphore semaCPU;
    // private Semaphore semeTimer;
   // private Semaphore sema

    private double[] regs;
    private String[] s;
    //private Label[] memoria;
    private int pc;
    // flag: DivZero = 1 - EndFormaLimite = 2 - STOP = 3 - TRAp = 4
    private int flag;
    private int i;
    private boolean actived;
    private Label[] memoria;
    private PCB pcb;
    private RotTimer rot;

    public CPU(Memoria memoria, Semaphore semaCPU, RotTimer rot) {

        this.regs = new double[8];
        this.memoria = memoria.getMemoria();
        this.pc = 0;
        this.flag = 0;
        this.i = 0;
        this.actived = false;
        this.rot = rot;
        this.semaCPU = semaCPU;
        start();
    }

    ////////////////////////////////////////////////////////////
    @Override
    public void run() {
        while (actived) {
          /*  try {
                semaCPU.acquire();
                // semeCPU.wait();
                // semeTimer.signal();
                // Thread.sleep(5000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            */
       /*     if (flag == 1) {
                System.out.println("no Run() DivZero = 1");
                // tratamento
                flag = 0;
            }
            if (flag == 2) {
                System.out.println("no Run() EndFormaLimite = 2");
                // tratamento
                flag = 0;
            }
            if (flag == 3) {
                System.out.println("no Run() STOP = 3");
                // tratamento
                flag = 0;
            }
            if (flag == 4) {
                System.out.println("no Run() TRAP = 4");
                // tratamento
                flag = 0;
            }
            */
            System.out.println("run() CPU");
            // rodaProg(getPCB());
            if(rodaProg(getPCB())){
                printMemoria();
                //rotina de tratamento
            }
            if(!rodaProg(getPCB()))
            {
               rot.tratamento(getPCB());
            }
            semaCPU.release();
        }
    }

    ////////////////////////////////////////////////////////////
    public synchronized int getPc() {
        return pc;
    }

    public synchronized void setPc(int pc) {
        this.pc = pc;
    }

    public synchronized Label[] getMemoria() {
        return memoria;
    }

    public synchronized void setMemoria(Label[] memoria) {
        this.memoria = memoria;
    }

    public synchronized void setRegs(double[] regs) {
        this.regs = regs;
    }

    public synchronized void setPCB(PCB pcb){
        this.pcb = pcb;
    }
    public synchronized void salvaContexto(PCB pcb){
        setRegs(pcb.getRegs());
        setPc(pcb.getPC());
        setPCB(pcb);
    }
    public synchronized PCB getPCB() {
        return pcb;
    }

    public synchronized double[] getRegs() {
        return regs;
    }

    public synchronized int getI() {
        return i;
    }

    public synchronized void setRun(boolean actived){
        this.actived = actived;
    }

    public synchronized void desalocaMemoria(int n) {
        memoria[n] = null;
    }

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
        // System.out.println("VALOR DE linha do arquivo: " + linhaArq);
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
                }
                //////////// intruction sub
                else if (s[0].equals("SUB")) {
                    memoria[i] = new Label("SUB", s[1], s[2]);
                    regs[memoria[i].findRD()] = regs[memoria[i].findRD()] - regs[memoria[i].findRS()];
                    // System.out.println("SUB | " + " REGS: " + memoria[i].findRD() + " " +
                    // regs[memoria[i].findRD()]);
                }
                //////////// intruction mult
                else if (s[0].equals("MULT")) {
                    memoria[i] = new Label("MULT", s[1], s[2]);
                    regs[memoria[i].findRD()] = regs[memoria[i].findRD()] * regs[memoria[i].findRS()];
                    // System.out.println("MULT | " + " REGS: " + memoria[i].findRD() + " " +
                    // regs[memoria[i].findRD()]);
                }
                //////////// intruction ADDi
                else if (s[0].equals("ADDI")) {
                    memoria[i] = new Label("ADDI", s[1], Double.parseDouble(s[2]));
                    regs[memoria[i].findRD()] = regs[memoria[i].findRD()] + Double.parseDouble(s[2]);
                    // System.out.println("ADDI | " + " REGS: " + memoria[i].findRD() + " " +
                    // regs[memoria[i].findRD()]);
                }
                //////////// intruction subi
                else if (s[0].equals("SUBI")) {
                    memoria[i] = new Label("SUBI", s[1], Double.parseDouble(s[2]));
                    regs[memoria[i].findRD()] = regs[memoria[i].findRD()] - Double.parseDouble(s[2]);
                    // System.out.println("SUBI | " + " REGS: " + memoria[i].findRD() + " " +
                    // regs[memoria[i].findRD()]);
                }
                //////////// intruction ldi
                else if (s[0].equals("LDI")) {
                    memoria[i] = new Label("LDI", s[1], Double.parseDouble(s[2]));
                    regs[memoria[i].findRD()] = Double.parseDouble(s[2]);
                    // System.out.println("LDI | " + " REGS: " + memoria[i].findRD() + " " +
                    // regs[memoria[i].findRD()]);
                }
                //////////// intruction jmp
                else if (s[0].equals("JMP")) {
                    memoria[i] = new Label("JMP", Double.parseDouble(s[1]));
                    linhaArq = (int) regs[memoria[i].findRD()] - 1;
                    i = Integer.parseInt(s[1]) - 1 + limiteInf;
                    // System.out.println(memoria[i].print() + " Linha: " + i);
                }
                //////////// intruction jmpi
                else if (s[0].equals("JMPI")) {
                    memoria[i] = new Label("JMPI", s[1], 0.0);
                    linhaArq = (int) regs[memoria[i].findRD()] - 1;
                    i = (int) regs[memoria[i].findRD()] - 1 + limiteInf;
                    // System.out.println(memoria[i].print() + " Linha: " + i);
                }
                //////////// intruction jmpig
                else if (s[0].equals("JMPIG")) {
                    memoria[i] = new Label("JMPIG", s[1], s[2]);
                    if (regs[memoria[i].findRS()] > 0) {
                        linhaArq = (int) regs[memoria[i].findRD()] - 1;
                        i = (int) regs[memoria[i].findRD()] - 1 + limiteInf;
                    }
                    // System.out.println(memoria[i].print() + " Linha: " + i);
                }
                //////////// intruction jmpil
                else if (s[0].equals("JMPIL")) {
                    memoria[i] = new Label("JMPIL", s[1], s[2]);
                    if (regs[memoria[i].findRS()] < 0) {
                        linhaArq = (int) regs[memoria[i].findRD()] - 1;
                        i = (int) regs[memoria[i].findRD()] - 1 + limiteInf;
                    }
                    // System.out.println(memoria[i].print() + " Linha: " + i);
                }
                //////////// intruction jmpie
                else if (s[0].equals("JMPIE")) {
                    memoria[i] = new Label("JMPIE", s[1], s[2]);
                    if (regs[memoria[i].findRS()] == 0) {
                        linhaArq = (int) regs[memoria[i].findRD()] - 1;
                        i = (int) regs[memoria[i].findRD()] - 1 + limiteInf;
                    }
                    // System.out.println(memoria[i].print() + " Linha: " + i);
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
                }
                //////////// intruction ldd
                else if (s[0].equals("LDD")) {
                    s[2] = s[2].replace("[", "");
                    s[2] = s[2].replace("]", "");
                    memoria[i] = new Label("LDD", s[1], s[2]);
                    regs[memoria[i].findRD()] = memoria[Integer.parseInt(s[2]) + limiteInf].getParametro();

                    // System.out.println(regs[memoria[i].findRD()]);

                    System.out.println(regs[memoria[i].findRD()]);

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
            this.flag = 1;
            // a.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException b) {
            this.flag = 2;
            System.out.println("no rodaProg() EndFormaLimite = 2");
            // b.printStackTrace();
        } catch (RuntimeException c) {
            System.out.println("no rodaProg() STOP = 3");
            this.flag = 3;
            // c.printStackTrace();
        }
        // catch (Rxception d) {
        // System.out.println("TRAP = 4");
        // this.flag = 4;
        // c.printStackTrace();
        // }
        setPCB(pcb);
        return false;
        // return actived;

    }

    public void printMemoria() {
        for (int i = 0; i < 300; i++) {
            System.out.println(i + " ");
            if (memoria[i] != null) {
                System.out.println(memoria[i].print());
            }
        }
    }
}