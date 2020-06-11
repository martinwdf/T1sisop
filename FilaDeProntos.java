import java.util.LinkedList;
import java.util.Queue;

public class FilaDeProntos {
    private Queue<PCB> prontos;

    public FilaDeProntos(){
        prontos = new LinkedList<PCB>();
    }
    public boolean addPronto(PCB pcb){
        return prontos.add(pcb);
    }

}