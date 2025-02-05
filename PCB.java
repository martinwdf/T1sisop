public class PCB {
    private double[] regs;
    private int PC;
    private final int ID;
    private Estado estado;
    private int limiteInf, limiteSup;
    private int linhaArq;

    public PCB(final int ID) {
        this.ID = ID;
        this.estado = Estado.PRONTO;
        this.regs = new double[8];
        this.setLinhaArq(0);

    }

    public int getLinhaArq() {
        return linhaArq;
    }
    public void setLinhaArq(int linhaArq) {
        this.linhaArq = linhaArq;
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

    public int getPC() {
        return PC;
    }
    public void setPC(int PC) {
        this.PC = PC;
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