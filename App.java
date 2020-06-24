import java.util.concurrent.Semaphore;

import controllers.*;
import models.*;


public class App extends Thread {

    public static void main(String[] args) {
        GerenteDeProcesso ger;
        Semaphore semaCPU = new Semaphore(0);
        Semaphore semaSch = new Semaphore(0);
        FilaDeProntos prontos = new FilaDeProntos();
        RotTimer rot = new RotTimer(semaSch, prontos);
        Memoria memoria = new Memoria();
        CPU cpu = new CPU(memoria, semaCPU, rot);
        ger = new GerenteDeProcesso(semaSch, cpu);
        Escalonador esc = new Escalonador(prontos, semaSch, semaCPU, cpu);
        Shell userShell = new Shell(ger);
        userShell.start();
        cpu.start();
        esc.start();

    }

}