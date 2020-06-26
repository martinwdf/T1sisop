package controllers;

public class Timer {
  private final static int limiteDeInstrucoes = 10;

  public static boolean contaTempo(int count) { return count == limiteDeInstrucoes; }
}