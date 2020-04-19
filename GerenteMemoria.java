
public class GerenteMemoria {
   private String[] s, arquivo;
   private int qtdProgramas;
   private Ler l;
   private boolean[] b = new boolean[8];
   private CPU cpu;
    public GerenteMemoria(String[] str, int qtdProgramas){
        this.qtdProgramas=qtdProgramas;
        s  = str;
        cpu =  new CPU();
        for(int i=0;i>qtdProgramas;i++){
            b[i]=true;
        }
    }
    public void rodaProgramas(){
        int limiteInf, limiteSup;
        for(int i=0;i<qtdProgramas;i++){
            limiteInf = i*128;
            limiteSup = (i+1)*128;
            l = new Ler(s[i]);
            arquivo = l.criarVetor();
            cpu.rodaProg(arquivo, limiteInf, limiteSup);
        }
    }   
    public void Desaloca(){
        for(int i=0;i<1024;i++){
            cpu.setPC(i);
        }
    }
}