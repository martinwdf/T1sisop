
public class main{
    
    public static void main(String[] args){
        if(args.length == 0){
            System.out.println("Tamanho minimo de argumentos: 1");
            System.exit(0);
        }
        
        //System.out.println("arqugmentos: " + args.length);
        GerenteDeProcesso grt = new GerenteDeProcesso(args, args.length);
          grt.controlaProcessos();
          
    }
}

