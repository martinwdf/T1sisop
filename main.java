

public class main extends Thread {

    public static void main(String[] args) {
     /*   if (args.length == 0 || args.length>8) {
            System.out.println("Tamanho minimo de argumentos: 1 e maximo 8");
            System.exit(0);
        }

        // System.out.println("arqugmentos: " + args.length);
        final GerenteDeProcesso grt = new GerenteDeProcesso(args, args.length);
          grt.controlaProcessos();
    */
    Shell user = new Shell();
    user.start();
        }
    
}