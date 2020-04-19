
import java.util.LinkedList;
import java.util.Queue;

public class GerenteDeProcesso {
    private Queue<PCB> processos;

    public GerenteDeProcesso(){
        processos = new LinkedList<PCB>();
    }

}