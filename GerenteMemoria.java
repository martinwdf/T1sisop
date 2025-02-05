
public class GerenteMemoria {
    private int[] b = new int[8];
    private CPU cpu;

    public GerenteMemoria() {
        for (int i = 0; i < 8; i++) {
            b[i] = -1;
        }
    }

    public int alocar(int ID) {
        int aux = -1;

        for (int i = 0; i < 8; i++) {
            if (b[i] == ID) {
                aux = i + 1;
            }
        }
        if (aux != -1) {
            return aux * 128;
        } else {
            for (int i = 0; i < 8; i++) {
                if (b[i] == -1) {
                    b[i] = ID;
                    return (i + 1) * 128;
                }
            }
        }
        return aux;
    }

    public void Desaloca(int limiteSup) {
        for (int i = limiteSup - 127; i < limiteSup; i++) {
            cpu.desalocaMemoria(i);
        }
    }
}