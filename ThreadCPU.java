public class ThreadCPU extends Thread {
    // private CPU cpu;

    private double[] regs;
    private String[] s;
    private label[] memoria;
    private int pc;
    private int i = 0;

    public ThreadCPU() {
        // cpu = new CPU();
        regs = new double[8];
        memoria = new label[1024];
        start();
        new Thread(this).start();
    }

    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized int getPc() {
        return pc;
    }

    public synchronized void setPc(int pc) {
        this.pc = pc;
    }

    public synchronized label[] getMemoria() {
        return memoria;
    }

    public synchronized void setMemoria(label[] memoria) {
        this.memoria = memoria;
    }

    public synchronized void setRegs(double[] regs) {
        this.regs = regs;
    }

    public synchronized double[] getRegs() {
        return regs;
    }

    public synchronized int getI() {
        return i;
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

    public synchronized boolean rodaProg(String[] arquivo, int limiteInf, int limiteSup, int linhaArq) {

        int numero = 0;
        i = limiteInf + linhaArq;
        // System.out.println("VALOR DE linha do arquivo: " + linhaArq);

        do {

            s = arquivo[linhaArq].split(" ");
            // System.out.println(arquivo[i]);
            //////////// intruction ADD
            if (s[0].equals("ADD")) {
                memoria[i] = new label("ADD", s[1], s[2]);
                regs[memoria[i].findRD()] = regs[memoria[i].findRD()] + regs[memoria[i].findRS()];
                // System.out.println("ADD | " + " REGS: " + memoria[i].findRD() + " " +
                // regs[memoria[i].findRD()]);
            }
            //////////// intruction sub
            else if (s[0].equals("SUB")) {
                memoria[i] = new label("SUB", s[1], s[2]);
                regs[memoria[i].findRD()] = regs[memoria[i].findRD()] - regs[memoria[i].findRS()];
                // System.out.println("SUB | " + " REGS: " + memoria[i].findRD() + " " +
                // regs[memoria[i].findRD()]);
            }
            //////////// intruction mult
            else if (s[0].equals("MULT")) {
                memoria[i] = new label("MULT", s[1], s[2]);
                regs[memoria[i].findRD()] = regs[memoria[i].findRD()] * regs[memoria[i].findRS()];
                // System.out.println("MULT | " + " REGS: " + memoria[i].findRD() + " " +
                // regs[memoria[i].findRD()]);
            }
            //////////// intruction ADDi
            else if (s[0].equals("ADDI")) {
                memoria[i] = new label("ADDI", s[1], Double.parseDouble(s[2]));
                regs[memoria[i].findRD()] = regs[memoria[i].findRD()] + Double.parseDouble(s[2]);
                // System.out.println("ADDI | " + " REGS: " + memoria[i].findRD() + " " +
                // regs[memoria[i].findRD()]);
            }
            //////////// intruction subi
            else if (s[0].equals("SUBI")) {
                memoria[i] = new label("SUBI", s[1], Double.parseDouble(s[2]));
                regs[memoria[i].findRD()] = regs[memoria[i].findRD()] - Double.parseDouble(s[2]);
                // System.out.println("SUBI | " + " REGS: " + memoria[i].findRD() + " " +
                // regs[memoria[i].findRD()]);
            }
            //////////// intruction ldi
            else if (s[0].equals("LDI")) {
                memoria[i] = new label("LDI", s[1], Double.parseDouble(s[2]));
                regs[memoria[i].findRD()] = Double.parseDouble(s[2]);
                // System.out.println("LDI | " + " REGS: " + memoria[i].findRD() + " " +
                // regs[memoria[i].findRD()]);
            }
            //////////// intruction jmp
            else if (s[0].equals("JMP")) {
                memoria[i] = new label("JMP", Double.parseDouble(s[1]));
                linhaArq = (int) regs[memoria[i].findRD()] - 1;
                i = Integer.parseInt(s[1]) - 1 + limiteInf;
                // System.out.println(memoria[i].print() + " Linha: " + i);
            }
            //////////// intruction jmpi
            else if (s[0].equals("JMPI")) {
                memoria[i] = new label("JMPI", s[1], 0.0);
                linhaArq = (int) regs[memoria[i].findRD()] - 1;
                i = (int) regs[memoria[i].findRD()] - 1 + limiteInf;
                // System.out.println(memoria[i].print() + " Linha: " + i);
            }
            //////////// intruction jmpig
            else if (s[0].equals("JMPIG")) {
                memoria[i] = new label("JMPIG", s[1], s[2]);
                if (regs[memoria[i].findRS()] > 0) {
                    linhaArq = (int) regs[memoria[i].findRD()] - 1;
                    i = (int) regs[memoria[i].findRD()] - 1 + limiteInf;
                }
                // System.out.println(memoria[i].print() + " Linha: " + i);
            }
            //////////// intruction jmpil
            else if (s[0].equals("JMPIL")) {
                memoria[i] = new label("JMPIL", s[1], s[2]);
                if (regs[memoria[i].findRS()] < 0) {
                    linhaArq = (int) regs[memoria[i].findRD()] - 1;
                    i = (int) regs[memoria[i].findRD()] - 1 + limiteInf;
                }
                // System.out.println(memoria[i].print() + " Linha: " + i);
            }
            //////////// intruction jmpie
            else if (s[0].equals("JMPIE")) {
                memoria[i] = new label("JMPIE", s[1], s[2]);
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
                memoria[i] = new label("STD", s[1], s[2]);
                memoria[j] = new label("DADO", regs[memoria[i].findRS()]);
                System.out.println(memoria[i].print() + " Linha: " + i);
            }
            //////////// intruction ldd
            else if (s[0].equals("LDD")) {
                s[2] = s[2].replace("[", "");
                s[2] = s[2].replace("]", "");
                memoria[i] = new label("LDD", s[1], s[2]);
                regs[memoria[i].findRD()] = memoria[Integer.parseInt(s[2]) + limiteInf].getParametro();
                System.out.println(regs[memoria[i].findRD()]);
            }
            //////////// intruction stdx
            else if (s[0].equals("STX")) {
                s[1] = s[1].replace("[", "");
                s[1] = s[1].replace("]", "");
                s[1] = s[1].replace(",", "");
                memoria[i] = new label("STX", s[1], s[2]);
                int j = (int) regs[memoria[i].findRD()] + limiteInf;
                memoria[j] = new label("DADO", regs[memoria[i].findRS()]);
                System.out.println(memoria[i].print() + " Linha: " + i);
            }
            //////////// intruction ldx rd <- [rs]
            else if (s[0].equals("LDX")) {
                s[2] = s[2].replace("[", "");
                s[2] = s[2].replace("]", "");
                memoria[i] = new label("LDX", s[1], s[2]);
                int j = (int) regs[memoria[i].findRS()] + limiteInf;
                regs[memoria[i].findRD()] = memoria[j].getParametro();
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

    public synchronized void printMemoria() {
        for (int i = 0; i < 1024; i++) {
            System.out.println(i + " ");
            if (memoria[i] != null) {
                System.out.println(memoria[i].print());
            }
        }
    }
}