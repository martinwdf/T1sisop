
public class GerenteMemoria {
   private String[] s, arquivo;
   private int qtdProgramas;
   private Ler l;
    CBU cbu;
    public GerenteMemoria(String[] str, int qtdProgramas){
        this.qtdProgramas=qtdProgramas;
        s  = str;
        cbu =  new CBU();
        
    }
    public void rodaProgramas(){
        int limiteInf, limiteSup;
        for(int i=0;i<qtdProgramas;i++){
            limiteInf = i*128;
            limiteSup = (i+1)*128;
            l = new Ler(s[i]);
            arquivo = l.criarVetor();
            cbu.rodaProg(arquivo, limiteInf, limiteSup);
        }
    }   
}