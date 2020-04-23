public class PCB {
    private double[] regs;
    private label[] PC;
    private final int ID;
    private Estado estado;
    private int limiteInf, limiteSup;
    private int rodou;

    public PCB(final int ID) {
        this.ID = ID;
        this.estado = Estado.PRONTO;
        this.regs = new double[8];
        this.PC = new label[128];
        this.rodou = 0;

    }

    public int getRodou() {
        return rodou;
    }

    public void setRodou() {
        rodou++;
    }

    public int getLimiteSup() {
        return limiteSup;
    }

    public void setLimiteSup(int limiteSup) {
        this.limiteSup = limiteSup;
    }

    public int getLimiteInf() {
        return limiteInf;
    }

    public void setLimiteInf(int limiteInf) {
        this.limiteInf = limiteInf;
    }

    public label[] getPC() {
        return PC;

    }

    public void setPC(label[] l) {
        PC = l;
    }

    public int getID() {
        return ID;
    }

    public double[] getRegs() {
        return regs;
    }

    public void setRegs(double[] regs) {
        this.regs = regs;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
}