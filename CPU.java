public class  CPU{
    private double[] regs = new double[9];  
    private String[] s, arquivo;
    private label[] PC = new label[1024];
    private Ler l;
    private int i=0;

    public CPU(){

    }
    public void setPC(int n){
        PC[n] = null;
    }
    public void rodaProg(String[] arquivo ,int limiteInf, int limiteSup){
        i=limiteInf;
        int linhaArq=0;
        do {
            
            s = arquivo[linhaArq].split(" ");
           // System.out.println(arquivo[i]);
            ////////////intruction ADD
            if(s[0].equals("ADD") ){
                PC[i] = new label("ADD", s[1], s[2]);
                regs[PC[i].findRD()] = regs[PC[i].findRD()]+ regs[PC[i].findRS()];
            }
            ////////////intruction sub
            else if(s[0].equals("SUB") ){
                PC[i] = new label("SUB", s[1], s[2]);
                regs[PC[i].findRD()] = regs[PC[i].findRD()] - regs[PC[i].findRS()];
            }
            ////////////intruction mult
            else if(s[0].equals("MULT") ){
                PC[i] = new label("MULT", s[1], s[2]);
                regs[PC[i].findRD()] = regs[PC[i].findRD()] * regs[PC[i].findRS()];
            }
            ////////////intruction ADDi
            else if(s[0].equals("ADDI") ){
                PC[i] = new label("ADDI", s[1], Double.parseDouble(s[2]));
                regs[PC[i].findRD()] = regs[PC[i].findRD()] + Double.parseDouble(s[2]);
            }
            ////////////intruction subi
             else if(s[0].equals("SUBI") ){
                PC[i] = new label("SUBI", s[1], Double.parseDouble(s[2]));
                regs[PC[i].findRD()] = regs[PC[i].findRD()] - Double.parseDouble(s[2]);
            }
            ////////////intruction ldi
            else if(s[0].equals("LDI") ){
            PC[i] = new label("LDI", s[1], Double.parseDouble(s[2]));
                regs[PC[i].findRD()] = Double.parseDouble(s[2]);
            }
              ////////////intruction jmp
            else if(s[0].equals("JMP") ){
                PC[i] = new label("JMP", Double.parseDouble(s[1]));
                linhaArq = (int) regs[PC[i].findRD()] - 1;
                i = Integer.parseInt(s[1]) - 1+limiteInf;
            }
            ////////////intruction jmpi
            else if(s[0].equals("JMPI") ){
                PC[i] = new label("JMPI", s[1], 0.0);
                linhaArq = (int) regs[PC[i].findRD()] - 1;
                i = (int) regs[PC[i].findRD()] - 1+limiteInf;
            }
            ////////////intruction jmpig
            else if(s[0].equals("JMPIG") ){
                PC[i] = new label("JMPIG", s[1], s[2]);
                if(regs[PC[i].findRS()] > 0){
                    linhaArq = (int) regs[PC[i].findRD()] - 1;
                    i = (int) regs[PC[i].findRD()] - 1 + limiteInf;
                }
            }
            ////////////intruction jmpil
             else if(s[0].equals("JMPIL") ){
                PC[i] = new label("JMPIL", s[1], s[2]);
                    if(regs[PC[i].findRS()] < 0){
                        linhaArq = (int) regs[PC[i].findRD()] - 1;
                        i = (int) regs[PC[i].findRD()] - 1+ limiteInf;
                    }
            }
            ////////////intruction jmpie
            else if(s[0].equals("JMPIE") ){
                PC[i] = new label("JMPIE", s[1], s[2]);
                    if(regs[PC[i].findRS()] == 0){
                        linhaArq = (int) regs[PC[i].findRD()] - 1;
                        i = (int) regs[PC[i].findRD()] - 1+limiteInf;
                    }
                  
            }           
            ////////////intruction std
            else if(s[0].equals("STD") ){
                s[1]=s[1].replace("[", "");
                s[1]=s[1].replace("]", "");
                s[1]=s[1].replace(",", "");
                int j=Integer.parseInt(s[1])+limiteInf;
                PC[i] = new label("STD", s[1],s[2]);
                PC[j] = new label("DADO", regs[PC[i].findRS()]);
            }
            ////////////intruction ldd
            else if(s[0].equals("LDD") ){
                s[2]=s[2].replace("[", "");
                s[2]=s[2].replace("]", "");
                PC[i] = new label("LDD", s[1],s[2]);
                regs[PC[i].findRD()]= PC[Integer.parseInt(s[2])].getParametro();
            }
            ////////////intruction stdx
            else if(s[0].equals("STX") ){
                s[1]=s[1].replace("[", "");
                s[1]=s[1].replace("]", "");
                s[1]=s[1].replace(",", "");
                PC[i] = new label("STX", s[1], s[2]);
                int j = (int) regs[PC[i].findRD()]+limiteInf;
                PC[j] = new label("DADO", regs[PC[i].findRS()]);
            }
            ////////////intruction ldx rd <- [rs]
            else if(s[0].equals("LDX") ){
                s[2]=s[2].replace("[", "");
                s[2]=s[2].replace("]", "");
                PC[i] = new label("LDX", s[1], s[2]);
                int j = (int) regs[PC[i].findRS()]+limiteInf;
                regs[PC[i].findRD()] = PC[j].getParametro();
    
            }
            ////////////intruction stop
            else{
            //////////// imprime os registradores
                System.out.println("Fim do Programa");
                System.out.println("Registradores\n"); 
                for(int n=0; n<8; n++){
                    System.out.println(n + ": " + regs[n]); 
                    
                }
            //////////// imprime a memoria
                System.out.println("Memoria\n"); 
                for(int n=limiteInf; n<limiteSup; n++){
                    System.out.println(n + " ");
                    if(PC[n]!=null){
                        System.out.println(PC[n].print());
                    }
                    
                }
    
               i=-5;
            }
            i++; 
            linhaArq++;
        }while(i>=limiteInf && i<=limiteSup);
    }
}