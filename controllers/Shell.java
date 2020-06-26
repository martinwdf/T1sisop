package controllers;

import models.*;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class Shell extends Thread {
    // private String[] programa;
    private GerenteDeProcesso ger;
    // private Semaphore semaShell;
    private Scanner scan;
    private String nomeArquivo;
    private String[] arquivo;
    private Ler l;
    // private boolean shellBock;

    public Shell(GerenteDeProcesso ger) {
        // sem = new Semaphore(1);
        scan = new Scanner(System.in);
        this.ger = ger;
        // this.shellBock = true;
        // this.semaShell = new Semaphore(1);
    }

    public void Sair() {
        try {  
            this.interrupt();
        } catch (Exception e) {
            System.out.println("Shell finalizado.");
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public void optionMenu(String nomeArquivo) {
        // nomeArquivo = program;
        System.out.println(nomeArquivo);
        l = new Ler(nomeArquivo);
        arquivo = l.criarVetor();
        ger.addProcesso(nomeArquivo, arquivo);

        try {
            ger.liberaEscalonador();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // Solicita processos para o usuario infinatamente
    @Override
    public void run() {
        int opc = 0;
        System.out.println("----------------------- MAJOGURA - Linux V1.0 ---------------------------");
        while (true) {
            // System.out.println("Escreva o nome do processo que deseja adiconar");
            // nomeArquivo = scan.next();
            // l = new Ler(nomeArquivo);
            // arquivo = l.criarVetor();
            // ger.addProcesso(nomeArquivo, arquivo);

            // try {
            // ger.liberaEscalonador();
            // } catch (InterruptedException e) {
            // // TODO Auto-generated catch block
            // e.printStackTrace();
            // }

            // System.out.println(nomeArquivo);
            // try { Thread.sleep(5000); } catch (InterruptedException e) {
            // e.printStackTrace(); }
            // ger.controlaProcessos()
            System.out.println("\n----------------------------------------------");
            System.out.println("Escolha o nome do processo que deseja adiconar");
            System.out.println("1 - P1 | 2 - P2 | 3 - P3 | 4 - P4 | 0 - Sair\n");

            opc = scan.nextInt();

            switch (opc) {
                case 1:
                    optionMenu("p1.txt");
                    break;
                case 2:
                    optionMenu("p2.txt");
                    break;
                case 3:
                    optionMenu("p3.txt");
                    break;
                case 4:
                    optionMenu("p4.txt");
                    break;
                case 0:

                    ger.interruptGP();
                    Sair();
                    System.exit(0);
                    return;

                default:
                    System.out.println("Opcao Invalida.");
                    break;
            }
        }
    }

    // public void shellBlock(){
    // this.shellBock = true;
    // }

    // public void shellUnblock(){
    // this.shellBock = false;
    // }
}