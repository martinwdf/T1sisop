public class CPU {
    private double[] regs = new double[8];
    private String[] s;
    private label[] PC = new label[1024];

    private int i = 0;

    public CPU() { }

    public void desalocaPC(int n) {
        PC[n] = null;
    }

    public void setPC(label[] l) {
        PC = l;
    }

    public void setRegs(double[] regs) {
        this.regs = regs;
    }

    public label[] getPC() {
        return PC;
    }

    public double[] getRegs() {
        return regs;
    }
    public int getI(){ return i;}

    public boolean acabouArquivo(String[] arquivo, int tamanho) {
        if (arquivo.length >= tamanho) {
            return true;
        }
        return false;
    }

    public boolean rodaProg(String[] arquivo, int limiteInf, int limiteSup, int linhaArq) {
      
        int numero = 0;
        i = limiteInf+linhaArq;
        System.out.println("VALOR DE linha do arquivo: " +linhaArq);
   
        do {

            s = arquivo[linhaArq].split(" ");
            // System.out.println(arquivo[i]);
            //////////// intruction ADD
            if (s[0].equals("ADD")) {
                PC[i] = new label("ADD", s[1], s[2]);
                regs[PC[i].findRD()] = regs[PC[i].findRD()] + regs[PC[i].findRS()];
                System.out.println("ADD | " + " REGS: " +PC[i].findRD()+" "+ regs[PC[i].findRD()]);
            }
            //////////// intruction sub
            else if (s[0].equals("SUB")) {
                PC[i] = new label("SUB", s[1], s[2]);
                regs[PC[i].findRD()] = regs[PC[i].findRD()] - regs[PC[i].findRS()];
                System.out.println("SUB | " + " REGS: " +PC[i].findRD()+" "+ regs[PC[i].findRD()]);
            }
            //////////// intruction mult
            else if (s[0].equals("MULT")) {
                PC[i] = new label("MULT", s[1], s[2]);
                regs[PC[i].findRD()] = regs[PC[i].findRD()] * regs[PC[i].findRS()];
                System.out.println("MULT | " + " REGS: " +PC[i].findRD()+" "+ regs[PC[i].findRD()]);
            }
            //////////// intruction ADDi
            else if (s[0].equals("ADDI")) {
                PC[i] = new label("ADDI", s[1], Double.parseDouble(s[2]));
                regs[PC[i].findRD()] = regs[PC[i].findRD()] + Double.parseDouble(s[2]);
                System.out.println("ADDI | " + " REGS: " +PC[i].findRD()+" "+ regs[PC[i].findRD()]);
            }
            //////////// intruction subi
            else if (s[0].equals("SUBI")) {
                PC[i] = new label("SUBI", s[1], Double.parseDouble(s[2]));
                regs[PC[i].findRD()] = regs[PC[i].findRD()] - Double.parseDouble(s[2]);
                System.out.println("SUBI | " + " REGS: " +PC[i].findRD()+" "+ regs[PC[i].findRD()]);
            }
            //////////// intruction ldi
            else if (s[0].equals("LDI")) {
                PC[i] = new label("LDI", s[1], Double.parseDouble(s[2]));
                regs[PC[i].findRD()] = Double.parseDouble(s[2]);
                System.out.println("LDI | " + " REGS: " +PC[i].findRD()+" "+ regs[PC[i].findRD()]);
            }
            //////////// intruction jmp
            else if (s[0].equals("JMP")) {
                PC[i] = new label("JMP", Double.parseDouble(s[1]));
                linhaArq = (int) regs[PC[i].findRD()] - 1;
                i = Integer.parseInt(s[1]) - 1 + limiteInf;
                System.out.println(PC[i].print() + " Linha: " + i);
            }
            //////////// intruction jmpi
            else if (s[0].equals("JMPI")) {
                PC[i] = new label("JMPI", s[1], 0.0);
                linhaArq = (int) regs[PC[i].findRD()] - 1;
                i = (int) regs[PC[i].findRD()] - 1 + limiteInf;
                System.out.println(PC[i].print() + " Linha: " + i);
            }
            //////////// intruction jmpig
            else if (s[0].equals("JMPIG")) {
                PC[i] = new label("JMPIG", s[1], s[2]);
                if (regs[PC[i].findRS()] > 0) {
                    linhaArq = (int) regs[PC[i].findRD()] - 1;
                    i = (int) regs[PC[i].findRD()] - 1 + limiteInf;
                }
            //    System.out.println(PC[i].print() + " Linha: " + i);
            }
            //////////// intruction jmpil
            else if (s[0].equals("JMPIL")) {
                PC[i] = new label("JMPIL", s[1], s[2]);
                if (regs[PC[i].findRS()] < 0) {
                    linhaArq = (int) regs[PC[i].findRD()] - 1;
                    i = (int) regs[PC[i].findRD()] - 1 + limiteInf;
                }
                System.out.println(PC[i].print() + " Linha: " + i);
            }
            //////////// intruction jmpie
            else if (s[0].equals("JMPIE")) {
                PC[i] = new label("JMPIE", s[1], s[2]);
                if (regs[PC[i].findRS()] == 0) {
                    linhaArq = (int) regs[PC[i].findRD()] - 1;
                    i = (int) regs[PC[i].findRD()] - 1 + limiteInf;
                }
                System.out.println(PC[i].print() + " Linha: " + i);
            }

            //////////// intruction std
            else if (s[0].equals("STD")) {
                s[1] = s[1].replace("[", "");
                s[1] = s[1].replace("]", "");
                s[1] = s[1].replace(",", "");
                int j = Integer.parseInt(s[1]) + limiteInf;
                PC[i] = new label("STD", s[1], s[2]);
                PC[j] = new label("DADO", regs[PC[i].findRS()]);
                System.out.println(PC[i].print() + " Linha: " + i);
            }

            //////////// intruction ldd
            else if (s[0].equals("LDD")) {
                s[2] = s[2].replace("[", "");
                s[2] = s[2].replace("]", "");
                PC[i] = new label("LDD", s[1], s[2]);
                regs[PC[i].findRD()] = PC[Integer.parseInt(s[2])].getParametro();
                System.out.println(regs[PC[i].findRD()]);
            }

            //////////// intruction stdx
            else if (s[0].equals("STX")) {
                s[1] = s[1].replace("[", "");
                s[1] = s[1].replace("]", "");
                s[1] = s[1].replace(",", "");
                PC[i] = new label("STX", s[1], s[2]);
                int j = (int) regs[PC[i].findRD()] + limiteInf;
                PC[j] = new label("DADO", regs[PC[i].findRS()]);
                System.out.println(PC[i].print() + " Linha: " + i);
            }

            //////////// intruction ldx rd <- [rs]
            else if (s[0].equals("LDX")) {
                s[2] = s[2].replace("[", "");
                s[2] = s[2].replace("]", "");
                PC[i] = new label("LDX", s[1], s[2]);
                int j = (int) regs[PC[i].findRS()] + limiteInf;
                regs[PC[i].findRD()] = PC[j].getParametro();
                System.out.println("LDX | " + " REGS: " +PC[i].findRD()+" "+ regs[PC[i].findRD()]);
            }

            //////////// intruction stop
            else {
                System.out.println("STOP ");
                return true;
            }

            i++;
            numero++;
            linhaArq++;
        } while ((i >= limiteInf && i < limiteSup) && numero <8);
        i-=limiteInf;
        return false;
    }

    public void printMemoria() {
         for (int i = 0; i < 1024; i++) {
         System.out.println(i + " ");
         if (PC[i] != null) {
        System.out.println(PC[i].print());
         }
         }
        for (int n = 0; n < 8; n++) {
            System.out.println("r" + n + " " + regs[n]);
        }
    }
}