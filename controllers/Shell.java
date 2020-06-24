package controllers;


import models.*;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class Shell extends Thread {
    //private Semaphore sem;
    // private String[] programa;
    private Scanner scan;
    private GerenteDeProcesso ger;
    private String nomeArquivo;
    private String[] arquivo;
    private Ler l;

    public Shell(GerenteDeProcesso ger) {
        // sem = new Semaphore(1);
        scan = new Scanner(System.in);
        this.ger =ger;
    }

    //Solicita processos para o usuario infinatamente
    @Override
    public void run() {
        while (true) {
            System.out.println("Escreva o nome do processo que deseja adiconar");
            nomeArquivo = scan.next();
            l = new Ler(nomeArquivo);
            arquivo = l.criarVetor();
            ger.addProcesso(nomeArquivo, arquivo);
            ger.liberaEscalonador();
           // System.out.println(nomeArquivo); 
            //   try { Thread.sleep(5000); } catch (InterruptedException e) {
            //   e.printStackTrace(); }
           // ger.controlaProcessos()
        }
    }
}