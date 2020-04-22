
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
        pcb.setRodou();
        this.cpu_pcb(pcb);
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
    public void cpu_pcb(PCB pcb){
        pcb.setPC(cpu.getPC());
        pcb.setRegs(cpu.getRegs());
        pcb.setEstado(Estado.AGUARDANDO);
    }
    public void controlaProcessos(){
        //adiciona a quantidade de processos de acordo com o numero de programas  
        for(int i=0;i<qtdProgramas;i++){
            l = new Ler(s[i]);
            arquivo = l.criarVetor();
            this.criaProcesso(arquivo);
            }
        
        while(processos.size()!=0){
            //// vai passar a cpu do primero pcb 
             this.pcb_cpu();
             PCB head = processos.element();
            //pega o arquivo que o processo leu, para mandar para a cpu
            l = new Ler(s[head.getID()]);
            arquivo = l.criarVetor();

            //roda a cpu que estava no 1 pcb da fila, se retornar true eh pq o processo acabou
            if(cpu.rodaProg(arquivo, pcb.getLimiteSup()-127, pcb.getLimiteSup()-1, pcb.getRodou())==true){
                    head.setEstado(Estado.FINALIZADO);
                    processos.remove(head);
            }
            else{
                    //caso em que o processo ainda nao foi finalizado.
                    head.setEstado(Estado.AGUARDANDO);

                    //adiciona o processo recem rodado para o final da fila
                    processos.add(head);
                    //remove o processo do comeco da fila
                    processos.remove();
                }
        }
        cpu.printMemoria();
    
    }
}