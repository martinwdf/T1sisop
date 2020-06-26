package models;

public class Label {

    private final String opcode;
    private String[] reg = { "r0", "r1", "r2", "r3", "r4", "r5", "r6", "r7", "r8" };
    private String rd;
    private String rs;
    private double parametro;

    /////////////////// contructors methods
    public Label(String opcode, String rd, String rs) { // r2 type instructions
        this.opcode = opcode;
        this.setRd(rd);
        this.setRs(rs);
    }

    public Label(String opcode, String rd, double parametro) { // i type instrucitons
        this.opcode = opcode;
        this.rd = rd;
        this.setParametro(parametro);
    }

    public Label(String opcode, double d) { // jump type instructions and the instruction stop
        this.opcode = opcode;
        this.setParametro(d);
    }

    /////////////////// getters and setters

    public String getRd() { return rd; }

    public void setRd(String rd) { this.rd = rd; }

    public String getRs() { return rs; }

    public void setRs(String rs) { this.rs = rs; }

    public double getParametro() { return parametro; }

    public void setParametro(double parametro) { this.parametro = parametro; }

    public String[] getReg() { return reg; }

    public void setReg(String[] reg) { this.reg = reg; }

    public String getOpcode() { return opcode; }

    /////////////////////
    public void printOpcode() { System.out.println(opcode); }

    public int findRD() {
        int i = 0, j = 0;
        while (i < 10) {
            if (getRd().contains(getReg()[j])) {
                i = 10;
            }
            j++;
            i++;
        }
        return j - 1;
    }

    public int findRS() {
        int i = 0, j = 0;
        while (i < 10) {
            if (getRs().contains(getReg()[j])) {
                i = 10;
            }
            j++;
            i++;
        }
        return j - 1;
    }

    public String print() {
        if (getOpcode() == "DADO") {
            return (getOpcode() + " " + getParametro());
        } else
            return getOpcode();
    }
}