package controllers;
import java.util.Scanner;
import java.util.concurrent.Semaphore;
public class Shell extends Thread {
    private Semaphore sem;
    private Scanner scan;
    private String[] programa;
    static  GerenteDeProcesso ger;
    private String nomeArquivo;

    public Shell() {
        sem = new Semaphore(1);
        scan = new Scanner(System.in);
        ger = new GerenteDeProcesso(programa, 2);
    }

    @Override
    public void run() {
        while(true){ 
            try {
                sem.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Escreva o nome do processo que deseja adiconar");
            nomeArquivo=scan.nextLine();
            ger.addProcesso(nomeArquivo);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ger.controlaProcessos();
            sem.release();
        }
    }
}