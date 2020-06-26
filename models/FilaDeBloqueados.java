package models;

import java.util.*;

public class FilaDeBloqueados {
    static Queue<PCB> bloqueados;

    public FilaDeBloqueados() { 
        bloqueados = new LinkedList<PCB>(); 
    }

    public int getSize() { return bloqueados.size(); }
    
    public PCB getHead(){ return bloqueados.peek(); }

    public boolean isEmpty(){ return bloqueados.isEmpty(); }
    
    public static void addProcesso(PCB pcb) {
        bloqueados.add(pcb);
        System.out.println("\nAdicionou o processo " + pcb.getID() + " a fila de bloqueados por IO"); 
    }

    public boolean removeBloqueado() {   
        if(bloqueados.isEmpty()){
            return false;
        }
        else{
            bloqueados.remove();
            return true;
        }
    }


    public static void printFilaDeBloqueados() {
        for (PCB pcb: bloqueados) {
            System.out.println("Process Id: " + pcb.getID() + "/ Nome: " + pcb.getNomeArquivo() + "/ Estado: " + pcb.getEstado());
        }
    }
}