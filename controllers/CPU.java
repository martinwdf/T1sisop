package controllers;
import models.Label;

public class CPU {
    private double[] regs = new double[8];
    private String[] s;
    private Label[] memoria = new Label[1024];
    private int pc;

    private int i = 0;

    public CPU() {
    }

    public int getPc() {
        return pc;
    }

    public void setPc(int pc) {
        this.pc = pc;
    }

    public Label[] getMemoria() {
        return memoria;
    }

    public void setMemoria(Label[] memoria) {
        this.memoria = memoria;
    }

    public void setRegs(double[] regs) {
        this.regs = regs;
    }

    public double[] getRegs() {
        return regs;
    }

    public int getI() {
        return i;
    }

    public void desalocaMemoria(int n) {
        memoria[n] = null;
    }

    public boolean acabouArquivo(String[] arquivo, int tamanho) {
        if (arquivo.length >= tamanho) {
            return true;
        }
        return false;
    }

    public boolean rodaProg(String[] arquivo, int limiteInf, int limiteSup, int linhaArq) {

        int numero = 0;
        i = limiteInf + linhaArq;
        // System.out.println("VALOR DE linha do arquivo: " + linhaArq);

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

                //System.out.println(regs[memoria[i].findRD()]);

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
               // System.out.println("LDX | " + " REGS: " + memoria[i].findRD() + " " + regs[memoria[i].findRD()]);

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
        } while ((i >= limiteInf && i < limiteSup) && numero < 8);
        i -= limiteInf;
        return false;
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