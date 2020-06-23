import java.util.concurrent.Semaphore;

import controllers.*;
import models.*;


public class App extends Thread {

    public static void main(String[] args) {
        GerenteDeProcesso ger;
        Semaphore semaCPU = new Semaphore(0);
        Semaphore semaSch = new Semaphore(0);
        FilaDeProntos prontos = new FilaDeProntos();
        Memoria memoria = new Memoria();
        RotTimer rot = new RotTimer(semaSch, prontos);
        CPU cpu = new CPU(memoria, semaCPU, rot);
        Escalonador esc = new Escalonador(prontos, semaSch, semaCPU, cpu);
        ger = new GerenteDeProcesso(semaSch, cpu, esc);
        Shell userShell = new Shell(ger);
        userShell.start();
        //cpu.start();
        //esc.start();

    }

}