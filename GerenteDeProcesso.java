
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class GerenteDeProcesso {    
    private String[] s, arquivo;
    private int qtdProgramas;
    private int ID;
    private Ler l;
    private CPU cpu;
    private Queue<PCB> processos;
    private PCB pcb;
    private  GerenteMemoria grtMemoria;

    public GerenteDeProcesso(String[] str, int qtdOProgramas){
        s=str;
        this.qtdProgramas=qtdOProgramas;
        processos = new LinkedList<PCB>();
        cpu =  new CPU();
        grtMemoria = new GerenteMemoria();
        this.ID=-1;

    }
    public int criaID(){
        ID++;
        return ID;
    }
    
    public void criaProcesso(String[] arquivo){
        pcb = new PCB(this.criaID());
        pcb.setLimiteSup(grtMemoria.alocar(pcb.getID())-1);
        pcb.setLimiteInf(pcb.getLimiteSup()-127);
        cpu.rodaProg(arquivo, pcb.getLimiteSup()-127, pcb.getLimiteSup()-1, pcb.getRodou());
        processos.add(pcb);
     
     
    }
    public  PCB removeProcesso(){
        PCB pcb1 = processos.remove();
        grtMemoria.Desaloca(pcb1.getLimiteSup());
       return pcb;
     
    }
    public void pcb_cpu(){
        PCB pcb = processos.element();   
        cpu.setPC(pcb.getPC());
        cpu.setRegs(pcb.getRegs());
        pcb.setEstado(Estado.EXECUTADANDO);
      
    }
    public void cpu_pcb(){
        PCB pcb = processos.element();   
        pcb.setPC(cpu.getPC());
        pcb.setRegs(cpu.getRegs());
        pcb.setEstado(Estado.AGUARDANDO);
    }
    public void controlaProcessos(){
        for(int i=0;i<qtdProgramas;i++){
            l = new Ler(s[i]);
            arquivo = l.criarVetor();
            this.criaProcesso(arquivo );
            }
            int n=0;
        while(processos.size()!=0){
           
        }
    }
    }