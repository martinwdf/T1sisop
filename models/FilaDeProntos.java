package models;
import java.util.LinkedList;
import java.util.Queue;
import models.*;

public class FilaDeProntos {
    private static Queue<PCB> prontos;

    public FilaDeProntos() {
        prontos = new LinkedList<PCB>();
    }
    
    public int getSize() { return prontos.size(); }

    public PCB getHead() { return prontos.peek(); }

    public boolean isEmpty() { return prontos.isEmpty(); }

    public boolean addPronto(PCB pcb) {
        // porque 8? <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
        if (this.getSize() >= 8) {
            return false;
        }
        return prontos.add(pcb);
    }

    public void removePronto() {
        // if (prontos.isEmpty()) {
        //     return false;
        // } else {
            prontos.remove();
        //     return true;
        // }
    }

    public void printFilaDeProntos() {
        try {
            for (PCB pcb : prontos) {
                System.out.println("Process ID: " + pcb.getID() + " | Nome: " + pcb.getNomeArquivo());
            }
        } catch (Exception e) {
            //TODO: handle exception
            System.out.println("Erro no ForEach do prontFilaDeProntos()");
            e.printStackTrace();
        }
        
    }
}