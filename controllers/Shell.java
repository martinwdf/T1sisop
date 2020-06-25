package controllers;

import models.*;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class Shell extends Thread {
    // private String[] programa;
    private Semaphore semaShell;
    private Scanner scan;
    private GerenteDeProcesso ger;
    private String nomeArquivo;
    private String[] arquivo;
    private Ler l;
    // private boolean shellBock;

    public Shell(GerenteDeProcesso ger) {
        // sem = new Semaphore(1);
        scan = new Scanner(System.in);
        this.ger = ger;
        // this.shellBock = true;
        this.semaShell = new Semaphore(1);
    }

    // Solicita processos para o usuario infinatamente
    @Override
    public void run() {
        while (true) {
            System.out.println("Escreva o nome do processo que deseja adiconar");
            nomeArquivo = scan.next();
            l = new Ler(nomeArquivo);
            arquivo = l.criarVetor();
            ger.addProcesso(nomeArquivo, arquivo);

            try {
                ger.liberaEscalonador();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            // System.out.println(nomeArquivo);
            // try { Thread.sleep(5000); } catch (InterruptedException e) {
            // e.printStackTrace(); }
            // ger.controlaProcessos()
        }
    }

    // public void shellBlock(){
    // this.shellBock = true;
    // }

    // public void shellUnblock(){
    // this.shellBock = false;
    // }
}