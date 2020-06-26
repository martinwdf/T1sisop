package models;
import java.util.LinkedList;
import java.util.Queue;
import models.*;

public class FilaDeProntos {
    private static Queue<PCB> prontos;

    public FilaDeProntos() {
        prontos = new LinkedList<PCB>();
    }

    public boolean addPronto(PCB pcb) {
        // porque 8? <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
        if (this.getSize() >= 8) {
            return false;
        }
        return prontos.add(pcb);
    }

    public boolean removePronto() {
        if (prontos.isEmpty()) {
            return false;
        } else {
            prontos.remove();
            return true;
        }
    }

    public int getSize() {
        return prontos.size();
    }

    public PCB getHead() {
        return prontos.peek();
    }

    public boolean isEmpty() {
        return prontos.isEmpty();
    }

    public static void printFilaDeProntos() {

        for (PCB pcb : prontos) {
            System.out.println("Process Id: " + pcb.getID() + "/ Nome: " + pcb.getNomeArquivo() + "/ Estado: " + pcb.getEstado());
        }
    }
}