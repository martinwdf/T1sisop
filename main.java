
public class main{
    
    public static void main(String[] args){
       /* if(args.length != 4){
            System.out.println("Tamanho minimo de argumentos: 4");
            System.exit(0);
        }
        */
        System.out.println("arqugmentos: " + args.length);
            GerenteMemoria grt = new GerenteMemoria(args, args.length);
            grt.rodaProgramas();
         
    }
}

