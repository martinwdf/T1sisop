package models;
import java.util.LinkedList;
import java.util.Queue;

public class FilaDeProntos {
    private Queue<PCB> prontos;

    public FilaDeProntos(){
        prontos = new LinkedList<PCB>();
    }
    public boolean addPronto(PCB pcb){
        if(this.getSize()>=8){
            return false;
        }
        return prontos.add(pcb);
    }
    public boolean removePronto(){
        if(prontos.isEmpty()){
            return false;
        }
        else{
            prontos.remove();
            return true;
        }
    }
    public int getSize(){return prontos.size();}
    public PCB getHead(){return prontos.peek();}
    public boolean isEmpty(){
        return prontos.isEmpty();
    }
}