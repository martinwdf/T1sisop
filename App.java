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
        ger = new GerenteDeProcesso(semaSch, cpu);
        Shell user = new Shell(ger);
        Escalonador esc = new Escalonador(prontos, semaSch, semaCPU, cpu);
        user.start();
        cpu.start();
        esc.start();

    }

}