package models;
public class PCB {
    private double[] regs;
    private int PC;
    private final int ID;
    private Estado estado;
    private int limiteInf, limiteSup;
    private int linhaArq;
    private String nomeArquivo;

    public PCB(final int ID, String nomeArquivo) {
        this.ID = ID;
        this.estado = Estado.PRONTO;
        this.regs = new double[8];
        this.setLinhaArq(0);
        setNomeArquivo(nomeArquivo);
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
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